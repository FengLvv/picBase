package com.example.knowledgetree.UIMain.Articles

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.knowledgetree.Database.Article
import com.example.knowledgetree.Database.getArticleComments
import com.example.knowledgetree.Navigation.Screen
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ArticleMain(
    navController: NavController,
    screenWidth: Int,
    comments: List<getArticleComments>,
    onGoodNoteClicked: (Int) -> Unit,
    onAddNoteConfirmClicked: (String) -> Unit,
    article: Article,
    ifCollected: Boolean,
    onCollectClicked: () -> Unit,
    refresh: () -> Unit
) {

    ArticleUI(
        onBackClick = { navController.popBackStack() },
        screenWidth = screenWidth,
        comments = comments,
        onGoodClicked = { onGoodNoteClicked(it) },
        onAddNoteConfirmClicked = onAddNoteConfirmClicked,
        article = article,
        onSelfTestClicked = {
            navController.navigate(
                "${Screen.SelfTestScreen.route}/${article.articleId}"
            )
        },
        ifCollected = ifCollected,
        onCollectClicked = {
            onCollectClicked()
            refresh()
        },
        refresh = { refresh() }
    )

}

@Composable
fun ArticleUI(
    onBackClick: () -> Unit,
    screenWidth: Int,
    comments: List<getArticleComments>,
    onGoodClicked: (Int) -> Unit,
    onAddNoteConfirmClicked: (String) -> Unit,
    article: Article, //todo 应用article
    onSelfTestClicked: () -> Unit,
    ifCollected: Boolean,
    onCollectClicked: () -> Unit,
    refresh: () -> Unit
) {

    val articleContent = mutableListOf<List<String>>()
    articleContent.add(listOf("pic", R.drawable.demo.toString()))
    articleContent.add(listOf("title", "如何定义Toolbar"))
    articleContent.add(
        listOf(
            "catalog", "1.Toolbar的好处\n" +
                    "2.Toolbar基础使用\n" +
                    "3.Toolbar配置主题Theme\n" +
                    "4.Toolbar中常用的控件设置\n" +
                    "5.Toolbar测试题"
        )
    )
    articleContent.add(
        listOf(
            "content", "Toolbar好处\n" +
                    "1. 可以显示出用户所处的当前位置； \n" +
                    "2. 可以提供一些重要的交互操作，比如搜索操作； \n" +
                    "3. 实现导航功能，让用户快速回到Home Activity；"
        )
    )
    articleContent.add(
        listOf(
            "code",
            "@Composable\n" +
                    "fun ArticleCode(codeText: String) {\n" +
                    "    var indexNumber = remember {\n" +
                    "        mutableStateListOf<Int>()\n" +
                    "    }\n" +
                    "    var codeTextEachLine = remember {\n" +
                    "        mutableStateListOf<String>()\n" +
                    "    }\n" +
                    "\n" +
                    "    //每行分割\n" +
                    "    codeText.split(\"\\n\").forEachIndexed() { index, code ->\n" +
                    "        indexNumber.add(index + 1)\n" +
                    "        codeTextEachLine.add(code)\n" +
                    "    }\n" +
                    "\n" +
                    "    Surface(color = lightGary_l, shape = RoundedCornerShape(10.dp)) {\n" +
                    "        Row(modifier = Modifier.fillMaxWidth()) {\n" +
                    "            Surface(modifier = Modifier.weight(1f), color = lightGary_m) {\n" +
                    "                Column(modifier = Modifier.fillMaxWidth().padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally) {\n" +
                    "                    indexNumber.forEach {\n" +
                    "                        Text(text = it.toString())\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            }\n" +
                    "            Surface(modifier = Modifier.weight(15f), color = lightGary_l) {\n" +
                    "                Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {\n" +
                    "                    codeTextEachLine.forEach {\n" +
                    "                        Text(text = it)\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            }\n" +
                    "\n" +
                    "        }\n" +
                    "    }\n" +
                    "}"
        )
    )
    articleContent.add(
        listOf(
            "ref", "开发文档\n" +
                    "https://developer.android.google.cn/reference" +
                    "/kotlin/androidx/appcompat/widget/Toolbar\n" +
                    "开发文档2\n" +
                    "https://developer.android.google.cn/reference" +
                    "/kotlin/androidx/appcompat/widget/Toolbar"
        )
    )


    var ifAddNoteCard by remember {
        mutableStateOf(false)
    }
    var addNoteValue by remember {
        mutableStateOf("")
    }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        //设置snackbar参数
        snackbarHost = {
            SnackbarHost(it) { data ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 80.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Snackbar(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .aspectRatio(5.5f),
                        shape = CircleShape,
                        elevation = 5.dp,
                        backgroundColor = lightGary_m.copy(alpha = 0.8f)
                    ) {
                        Text(
                            text = data.message,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        },
        topBar = {
            val collectFinished = stringResource(id = R.string.collect_finished)
            val collectUnfinished = stringResource(id = R.string.collect_unfinished)
            ArticleTopBar(
                seeCollect = true,
                ifCollect = ifCollected,
                text = null,
                onBackClicked = { onBackClick() },
            ) {
                onCollectClicked()
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        if (ifCollected) collectUnfinished else collectFinished,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    ) {
        var ifCatalogueExpanded by remember { mutableStateOf(false) }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (screenWidth * 0.04).dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            items(articleContent) {
                when {
                    it[0] == "pic" -> ArticleImage(it[1].toInt())
                    it[0] == "title" -> ArticleTitle(it[1])
                    it[0] == "catalog" -> ArticleCatalog(
                        it[1],
                        ifCatalogueExpanded
                    ) { ifCatalogueExpanded = !ifCatalogueExpanded }
                    it[0] == "content" -> ArticleContent(it[1])
                    it[0] == "code" -> ArticleCode(it[1])
                    it[0] == "ref" -> ArticleRef(it[1], {})
                }
            }
            item {
                ArticleTestCard { onSelfTestClicked() }
            }
            item {
                val sendFinished = stringResource(id = R.string.send_finished)
                noteCard(
                    onAddNoteClicked = { ifAddNoteCard = !ifAddNoteCard },
                    comments = comments,
                    onGoodClicked = { onGoodClicked(it) },
                    ifAddNoteCard = ifAddNoteCard,
                    addNoteValue = addNoteValue,
                    onAddNoteValueChanged = { addNoteValue = it },
                    onAddNoteCancelClicked = { ifAddNoteCard = false },
                    onAddNoteConfirmClicked = {
                        onAddNoteConfirmClicked(addNoteValue)
                        ifAddNoteCard = false
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = sendFinished,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
            }
        }
    }
}

@Composable
fun ArticleTopBar(
    seeCollect: Boolean,
    ifCollect: Boolean,
    text: String?,
    onBackClicked: () -> Unit,
    onCollectClicked: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.height(50.dp),
        backgroundColor = Color.White,
        elevation = 10.dp,
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            )
            {
                IconButton(
                    onClick = { onBackClicked() },//todo
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.dp_detail_1),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight(0.45f)
                            .aspectRatio(1f)
                    )
                }
                if (!text.isNullOrBlank()) Text(text = text, style = MaterialTheme.typography.h6)

                IconButton(
                    onClick = { onCollectClicked() },//todo
                    modifier = Modifier,
                    enabled = seeCollect
                ) {
                    if (seeCollect)
                        Image(
                            painter = painterResource(
                                id = when (ifCollect) {
                                    false -> R.drawable.article_1
                                    true -> R.drawable.article_collected
                                }
                            ),
                            contentDescription = null, modifier = Modifier
                                .fillMaxHeight(0.45f)
                                .aspectRatio(1f)
                        )
                }
            }
        }
    }
}


@Composable
fun ArticleImage(painterId: Int) {
    Image(
        painter = painterResource(id = painterId),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}


@Composable
fun ArticleTitle(titleText: String) {
    Text(text = titleText, style = MaterialTheme.typography.h4)
}

@Composable
fun ArticleCatalog(
    catalogText: String,
    ifCatalogueExpanded: Boolean,
    onCatalogTextChanged: () -> Unit,
) {
    Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(5.dp),
            color = remindGreen1,
            shape = CircleShape
        ) { }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.diagram),
                    style = MaterialTheme.typography.h6
                )
                IconButton(
                    onClick = { onCatalogTextChanged() },
                    modifier = Modifier.padding(horizontal = 0.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (ifCatalogueExpanded) R.drawable.kt_2 else R.drawable.kt_3
                        ),
                        contentDescription = null,
                        modifier = Modifier,
                        tint = darkGary
                    )
                }
            }
            Text(
                text = catalogText,
                maxLines = if (ifCatalogueExpanded) Int.MAX_VALUE else 3,
                modifier = Modifier.padding(horizontal = 10.dp),
            )
            if (!ifCatalogueExpanded) Text(text = "...")
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
        }
    }
}


@Composable
fun ArticleContent(contentText: String) {
    Text(text = contentText)
}


@Composable
fun ArticleCode(codeText: String) {
    //每行分割
    Surface(color = lightGary_l, shape = RoundedCornerShape(10.dp)) {
        Column(modifier = Modifier) {
            Surface(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(), color = lightGary_m
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "code", fontWeight = FontWeight.Medium)
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Surface(modifier = Modifier.weight(1.3f), color = lightGary_m) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        codeText.split("\n").forEachIndexed { index, code ->
                            Column(
                                modifier = Modifier.size(17.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = (index + 1).toString(),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
                Surface(modifier = Modifier.weight(12.5f), color = lightGary_l) {
                    LazyRow() {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                            ) {
                                codeText.split("\n").forEach { code ->
                                    Text(text = code)
                                }
                            }
                        }
                    }
                }
            }
            Surface(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(), color = lightGary_m
            ) {}
        }
    }
}

@Composable
fun ArticleRef(refArticle: String, onAddLinkClicked: () -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {

        //第一行：链接资源xxx
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.resources),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
            )
            Row(
                Modifier.clickable { onAddLinkClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.article_2),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(20.dp),
                    tint = remindGreen1
                )
                Text(text = stringResource(id = R.string.add_link), color = remindGreen1)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            refArticle.split("\n").forEach {
                Text(text = it)
            }
        }
    }
}

@Composable
fun ArticleTestCard(onSelfTestCardClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.7f),
            shape = CircleShape,
            onClick = { onSelfTestCardClicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = remindGreen1)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.self_test_enter),
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun noteCard(
    onAddNoteClicked: () -> Unit,
    comments: List<getArticleComments>,
    onGoodClicked: (Int) -> Unit,
    ifAddNoteCard: Boolean,
    addNoteValue: String,
    onAddNoteValueChanged: (String) -> Unit,
    onAddNoteConfirmClicked: () -> Unit,
    onAddNoteCancelClicked: () -> Unit,
) {
    Column() {
        //第一行：链接资源xxx
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.notes),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
            )
            Row(
                Modifier.clickable { onAddNoteClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.article_3),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(20.dp),
                    tint = remindGreen1
                )
                Text(text = stringResource(id = R.string.notes), color = remindGreen1)
            }
        }
//添加笔记
        if (ifAddNoteCard) {
            addContentCard(
                addNoteValue = addNoteValue,
                onAddNoteValueChanged = { onAddNoteValueChanged(it) },
                onAddNoteConfirmClicked = { onAddNoteConfirmClicked() },
                onAddNoteCancelClicked = { onAddNoteCancelClicked() },
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
        comments.forEachIndexed { index, it ->
            noteCardDetail(it, onGoodClicked = { onGoodClicked(it.userId) })
        }


    }

}


//输入笔记
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun addContentCard(
    addNoteValue: String,
    onAddNoteValueChanged: (String) -> Unit,
    onAddNoteConfirmClicked: () -> Unit,
    onAddNoteCancelClicked: () -> Unit,
    modifier: Modifier
) {
    Card(modifier = modifier) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            TextField(
                value = addNoteValue,
                onValueChange = { onAddNoteValueChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enterContent),
                        color = lightGary_m
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        onAddNoteConfirmClicked()
                    }),
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { onAddNoteCancelClicked() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = lightGary_m
                    ),
                    modifier = Modifier
                        .height(35.dp)
                        .aspectRatio(1.7f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = Color.White,
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                TextButton(
                    onClick = { onAddNoteConfirmClicked() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = remindGreen1
                    ),
                    modifier = Modifier
                        .height(35.dp)
                        .aspectRatio(1.7f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.send),
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun noteCardDetail(comment: getArticleComments, onGoodClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp
    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Column() {
                Image(
                    painter = rememberImagePainter(
                        data = "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
                        onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.demo)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(50.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(30.dp))
                TextButton(
                    modifier = Modifier
                        .height(70.dp)
                        .aspectRatio(0.6f),
                    onClick = { onGoodClicked() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = lightGreen5),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.article_4),
                            contentDescription = null, tint = remindGreen1,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(text = comment.commentGoods.toString(), color = remindGreen1)
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(comment.name, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Text(text = comment.commentContent)
            }
        }
    }
}