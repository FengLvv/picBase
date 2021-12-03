package com.example.knowledgetree.UIMain.DesignPerformance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.knowledgetree.UIMain.MyInfomation.GradientButton
import com.example.knowledgetree.UIMain.MyInfomation.KtTopRow
import com.example.knowledgetree.ui.theme.*


@Composable
fun dpMain(
    navController: NavController,
    screenWidth: Int,
    screenHeight: Int,
    headImageRes: String,
    articles: List<Article>,
    onSearchIconClicked: () -> Unit,
    onHeadImageClicked: () -> Unit,
    onArticleClicked: (Article) -> Unit
) {
    Box(modifier = Modifier) {
        DpBg()
        Column() {
            Column(
                modifier = Modifier
                    .padding((screenWidth * 0.035).dp)
            ) {
                Spacer(modifier = Modifier.padding(0.dp, (screenHeight * 0.01f).dp))
                KtTopRow(
                    modifier = Modifier.padding(0.dp, 5.dp, (screenWidth * 0.02).dp, 0.dp),
                    screenWidth,
                    headImageRes,
                    onSearchIconClicked = onSearchIconClicked,
                    onHeadImageClicked = onHeadImageClicked
                )

                //设计表现和button
                DpHeadText(
                    Modifier
                        .padding((screenWidth * 0.02).dp)
                )
            }

            //下面的大card
            DpContent(
                screenWidth,
                articles = articles,
                { 1 },
                { 1 },
                { 1 },
                navController,
                onArticleClicked = { onArticleClicked(it) })
        }
    }
}

@Composable
fun DpContent(
    screenWidth: Int,
    articles: List<Article>,
    getViewNumById: (Int) -> Int,
    getLikeNumById: (Int) -> Int,
    getCollectNumById: (Int) -> Int,
    navController: NavController,
    onArticleClicked: (Article) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        shape = RoundedCornerShape(0.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = (screenWidth * 0.05).dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                Text(stringResource(id = R.string.style), style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.padding(15.dp))
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DpStyleCard(Modifier.aspectRatio(1.6f),
                        (screenWidth * 0.427f),
                        remindGreen4,
                        onclick = { navController.navigate(Screen.DpFragmentScreen.route) }) {
                        Box() {
                            Image(
                                painterResource(id = R.drawable.dp_1),
                                contentDescription = null,
                                modifier = Modifier.align(
                                    Alignment.BottomEnd
                                )
                            )
                            Column(
                                Modifier
                                    .fillMaxWidth(0.9f)
                                    .fillMaxHeight(0.65f)
                            ) {
                                Text(
                                    stringResource(id = R.string.fragment_style),
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text(
                                    stringResource(id = R.string.scaffold),
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    DpStyleCard(Modifier.aspectRatio(1.6f),
                        (screenWidth * 0.427f),
                        blue,
                        onclick = { navController.navigate(Screen.DpDocumentScreen.route) }) {
                        Box() {
                            Image(
                                painterResource(id = R.drawable.dp_2),
                                contentDescription = null,
                                modifier = Modifier.align(
                                    Alignment.BottomEnd
                                )
                            )
                            Column(
                                Modifier
                                    .fillMaxWidth(0.9f)
                                    .fillMaxHeight(0.65f)
                            ) {
                                Text(
                                    stringResource(id = R.string.interactionEffect),
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text(stringResource(id = R.string.animation), color = Color.White)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(15.dp))
                Text(stringResource(id = R.string.designWorks), style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.padding(15.dp))
            }
            items(articles) { article ->
                DpDesignCard(
                    article.imageRes,
                    article.articleName,
                    viewNum = getViewNumById(article.articleId),
                    likeNum = getLikeNumById(article.articleId),
                    collectNum = getCollectNumById(article.articleId),
                    time=article.articleData,
                    onArticleClicked = { onArticleClicked(article) }
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
            item {
                //最下面空出bottomBar
                Spacer(modifier = Modifier.padding(vertical = 40.dp))
            }
        }
    }
}

@Composable
fun DpDesignCard(
    articleImage: String,
    articleTitle: String,
    viewNum: Int,
    likeNum: Int,
    collectNum: Int,
    onArticleClicked: () -> Unit,
    time:String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.25f)
            .clickable { onArticleClicked() },
        shape = RoundedCornerShape(20.dp),
        elevation = 10.dp
    ) {
        Column(Modifier.fillMaxSize()) {
            Image(
                painter = rememberImagePainter(
                    data = articleImage,
                    onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.demo)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .weight(4f)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(articleTitle, style = MaterialTheme.typography.body1)
                    Text(
                        "$viewNum ${stringResource(id = R.string.viewNum)} · " +
                                "$likeNum ${stringResource(id = R.string.likeNum)} · " +
                                "$collectNum ${stringResource(id = R.string.collectNum)}",
                        style = MaterialTheme.typography.body2
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),horizontalArrangement = Arrangement.End
                ) {
                                        Text(text=time,style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}

@Composable
fun DpStyleCard(
    modifier: Modifier,
    width: Float,
    gradientFirstColor: Color,
    elevation: ButtonElevation = ButtonDefaults.elevation(5.dp, 10.dp, 0.dp),
    onclick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val gradient = Brush.linearGradient(
        listOf(gradientFirstColor, Color.White),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )
    GradientButton(
        modifier = modifier,
        gradient = gradient,
        width = width,
        shape = RoundedCornerShape(10.dp),
        Alignment.Center,
        elevation = elevation,
        onclick = onclick
    ) {
        content()
    }
}


@Composable
fun DpHeadText(modifier: Modifier) {
    Column(modifier = modifier) {

        Text(
            text = stringResource(id = R.string.design_performance),
            style = MaterialTheme.typography.h4.copy(
                color = Color.White
            ), modifier = Modifier.padding(vertical = 10.dp)
        )

        Button(
            onClick = {},//todo
            modifier = Modifier.height(30.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                lightGreen3,
                                remindGreen1
                            )
                        )
                    )
                    .height(32.dp)
                    .padding(15.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    stringResource(id = R.string.visualization),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Icon(painterResource(id = R.drawable.kt_3), contentDescription = null)
            }
        }
    }
}


@Composable
fun DpBg() {
    Column() {
        //todo：先用图片垫着，有空在画
        Image(
            painter = painterResource(id = R.drawable.dp_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentScale = ContentScale.FillWidth
        )
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.11f)
        ) {}
    }
}

