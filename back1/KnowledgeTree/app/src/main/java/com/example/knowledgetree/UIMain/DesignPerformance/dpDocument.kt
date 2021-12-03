package com.example.knowledgetree.UIMain.DesignPerformance

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.knowledgetree.Database.Article
import com.example.knowledgetree.Navigation.Screen
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.*
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun documentMain(
    navController: NavController,
    screenWidth: Int,
    articles: List<Article>,
) {
    var articlesPrincipleTips = remember { mutableStateListOf<Article>() }
    var articlesPrincipleGoodArticle = remember { mutableStateListOf<Article>() }
    var elementArticles = remember { mutableStateListOf<Article>() }
    var handbookArticles = remember { mutableStateListOf<Article>() }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        articles.forEachIndexed { index, article ->
            when (article.articleAlignGroupId) {
                4 -> articlesPrincipleTips.add(article)
                5 -> articlesPrincipleGoodArticle.add(article)
                6 -> elementArticles.add(article)
                7 -> handbookArticles.add(article)
            }
        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
                backgroundColor = Color.White,
                elevation = 0.dp,
            ) {
                Box(Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { navController.navigate(Screen.DpScreen.route) },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.dp_detail_1),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.document),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    )
    {
        val designPrinciple = stringResource(id = R.string.design_principle)
        val visualSpecification = stringResource(id = R.string.visual_specification)
        val pages = remember {
            listOf(designPrinciple, visualSpecification)
        }
        val coroutineScope = rememberCoroutineScope()
        // Remember a PagerState
        val pagerState = rememberPagerState()
        Column(Modifier.fillMaxSize()) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = remindGreen1
                    )
                },
                backgroundColor = Color.White
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // Animate to the selected page when clicked
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                // Add 16.dp padding to 'center' the pages
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) { page ->
                // Our content for each page
                Surface(modifier = Modifier.fillMaxSize(), color = lightGary_l) {
                    when (page) {
                        0 -> designPrinciple(
                            screenWidth,
                            articlesPrincipleTips,
                            articlesPrincipleGoodArticle
                        )
                        1 -> visualSpecification(
                            elementArticles,
                            handbookArticles,
                            screenWidth
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun visualSpecification(
    elementArticles: List<Article>,
    handbookArticles: List<Article>,
    screenWidth: Int,

    ) {
    Column(
        Modifier
            .padding(
                (screenWidth * 0.05).dp,
                (screenWidth * 0.05).dp,
                (screenWidth * 0.05).dp,
                0.dp
            )
            .verticalScroll(ScrollState(0))
    ) {
        Text(
            text = stringResource(id = R.string.element),
            style = MaterialTheme.typography.body1
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            elementArticles.forEachIndexed { index, article ->
                if (index <= 1) vsElement(article, screenWidth)
            }
        }

        Text(
            text = stringResource(id = R.string.design_handbook),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        vsDesignHandbookBigCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            articles = handbookArticles, screenWidth
        )

    }
}

@Preview
@Composable
fun viedd() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
}

@Composable
fun vsElement(article: Article, screenWidth: Int) {

    Card(
        modifier = Modifier
            .width((screenWidth * 0.43).dp)
            .aspectRatio(1.1f),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = rememberImagePainter(
                data = article.imageRes,
                onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.demo)
                    transformations(CircleCropTransformation())
                }
            ), contentDescription = null,
                Modifier.weight(3f), contentScale = ContentScale.Fit)
            Divider(Modifier.fillMaxWidth(), color = lightGary, thickness = 3.dp)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = article.articleName,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
            }

        }
    }
}

@Composable
fun vsDesignHandbookBigCard(modifier: Modifier, articles: List<Article>, screenWidth: Int) {
    var vsHandBookCounter by remember {
        mutableStateOf(false)
    }
    FlowRow(
        modifier,
        mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween,
        crossAxisSpacing = 20.dp
    ) {
        articles.forEachIndexed { index, article ->
            if (!vsHandBookCounter && index < 4) {
                vsDesignHandbookCard(
                    modifier = Modifier
                        .width((screenWidth * 0.43).dp)
                        .aspectRatio(1.1f), article = article
                )
            } else if (vsHandBookCounter) {
                vsDesignHandbookCard(
                    modifier = Modifier
                        .width((screenWidth * 0.43).dp)
                        .aspectRatio(1.1f), article = article
                )
            }
        }
    }
    if (vsHandBookCounter) Text(
        text = "----------  ${stringResource(id = R.string.to_end)}  -----------",
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Center
    )

    //展开
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .height(40.dp)
            .clickable { vsHandBookCounter = !vsHandBookCounter },
        backgroundColor = lightGreen6,
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (vsHandBookCounter) Icon(
                painter = painterResource(id = R.drawable.kt_2),
                contentDescription = null,
                Modifier
            )
            else Icon(
                painter = painterResource(id = R.drawable.kt_3),
                contentDescription = null,
                Modifier
            )
            Spacer(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp))
            Text(
                text = stringResource(id = R.string.unfold),
                style = MaterialTheme.typography.body1
            )
        }
    }
}


@Composable
fun vsDesignHandbookCard(modifier: Modifier, article: Article) {
    Box(modifier) {
        Surface(
            modifier = Modifier.matchParentSize(),
            color = lightYellow2,
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                Column(
                    Modifier
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(article.articleName, style = MaterialTheme.typography.body1)
                    Divider(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        color = lightYellow3,
                        thickness = 3.dp
                    )
                    Column(Modifier.fillMaxSize(0.9f)) {
                        Text(text = article.articleContent)
                    }
                }
            }
        }
    }
}

/*下面是设计原则的内容*/
@Composable
fun designPrinciple(screenWidth: Int, tipArticles: List<Article>, goodArticles: List<Article>) {

    Column(Modifier.padding(20.dp)) {
        dp_tips(screenWidth, tipArticles)
        dp_goodArticles(articles = goodArticles)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun dp_tips(screenWidth: Int, articles: List<Article>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
    ) {
        Column(modifier = Modifier.matchParentSize()) {
            val pagerState = rememberPagerState()


            Text(
                text = stringResource(id = R.string.design_Tips),
                style = MaterialTheme.typography.body1
            )

            HorizontalPager(
                count = if (articles.size < 5) articles.size else 4,  //最多四个
                state = pagerState,
                contentPadding = PaddingValues(end = (screenWidth * 0.3).dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                if (page <= 3) tipItem(
                    page = page,
                    modifier = Modifier
                        .width((screenWidth * 0.55).dp)
                        .aspectRatio(1.8f),
                    articles[page]
                )
                else tipItem(
                    page = page,
                    modifier = Modifier
                        .width((screenWidth * 0.6).dp)
                        .aspectRatio(1.8f),
                    articles[page]
                )
            }
            LaunchedEffect(pagerState.currentPage) {
                if (pagerState.pageCount > 0) {
                    delay(2000)
                    if (pagerState.currentPage < 3) pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    else pagerState.animateScrollToPage(0)
                }
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                inactiveColor = lightGreen4,
                activeColor = remindGreen1,
                indicatorWidth = 7.dp,
                indicatorHeight = 7.dp
            )
        }
    }
}


@Composable
internal fun tipItem(
    page: Int,
    modifier: Modifier = Modifier,
    article: Article,
) {
    Box(modifier) {
        Surface(
            modifier = Modifier.matchParentSize(),
            color = lightYellow2,
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                Column(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.8f)
                ) {
                    Text(article.articleName, style = MaterialTheme.typography.body1)
                    Divider(
                        Modifier
                            .fillMaxWidth(0.6f)
                            .padding(vertical = 10.dp),
                        color = lightYellow3,
                        thickness = 3.dp
                    )
                    Column() {
                        Text(text = article.articleContent)
                    }
                }
            }
        }
    }
}


@Composable
fun dp_goodArticles(articles: List<Article>) {
    Column() {
        Text(
            text = stringResource(id = R.string.goodArticles),
            modifier = Modifier.padding(vertical = 10.dp), style = MaterialTheme.typography.body1
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            itemsIndexed(articles) { index, article ->
                dp_goodArticles_card(
                    111,
                    222, 333, article, painterResource(id = R.drawable.demo)
                )
            }

        }
    }
}

@Composable
fun dp_goodArticles_card(
    viewNum: Int,
    likeNum: Int,
    collectNum: Int,
    article: Article,
    headImage: Painter,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f),
        elevation = 0.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row(
                Modifier
                    .padding(20.dp, 20.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(article.articleName, style = MaterialTheme.typography.body1)
                    Text(
                        "$viewNum ${stringResource(id = R.string.viewNum)} · " +
                                "$likeNum ${stringResource(id = R.string.likeNum)} · " +
                                "$collectNum ${stringResource(id = R.string.collectNum)}"
                    )
                    Text(article.articleData)


                }
                Image(
                    painter = painterResource(id = R.drawable.demo),
                    contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .aspectRatio(1.5f)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
    }
}