package com.example.knowledgetree.UIMain.Search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.knowledgetree.Database.Article
import com.example.knowledgetree.R
import com.example.knowledgetree.data.iconText
import com.example.knowledgetree.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchMain(
    navController: NavController,
    screenHeight: Int,
    screenWidth: Int,
    onArticleClicked: (Article) -> Unit,
    onSearchClicked: (String) -> Unit,
    articles: List<Article>,
    recentSearch: List<String>,
    clearRecentSearch: () -> Unit,
    onKeyBoardClick: (String) -> Unit,
) {

    var textFieldValue by remember { mutableStateOf("") }
    val articlesOfOthers = remember { mutableStateListOf<Article>() }
    val articlesOfKt = remember { mutableStateListOf<Article>() }


    //每次article改变发起重构，重写两种类型的文章，
    articles.forEach { article ->
        when (article.plateName) {
            "kt" -> {
                articlesOfKt.add(article)
            }
            else -> {
                articlesOfOthers.add(article)
            }
        }
    }


    Scaffold(
        topBar = {
            SearchTopBar(
                screenHeight = screenHeight,
                textFieldValue = textFieldValue,
                onTextFieldValueChanged = {
                    textFieldValue = it
                    articlesOfOthers.clear()
                    articlesOfKt.clear()
                    onSearchClicked(textFieldValue)   //字符改变，清空两种文章内容生成新的文章
                },
                onBackIconClicked = { navController.popBackStack() },
                clearTextField = { textFieldValue = "" },
                onKeyBoardClick = { onKeyBoardClick(it) }
            )
        },
        backgroundColor = lightGary_l
    ) {
        LazyColumn(
            modifier = Modifier.padding((screenWidth * 0.04).dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (textFieldValue.isBlank()) {
                item {
                    SearchTextFieldBlankContent(recentSearch,
                        { textFieldValue = it }, clearRecent = { clearRecentSearch() })
                }
            } else {
                if (articlesOfOthers.isNotEmpty()) {
                    item {
                        SearchCardForOthers(articlesOfOthers) {
                            onArticleClicked(it)
                        }
                    }
                }

                if (articlesOfKt.isNotEmpty()) {
                    item { SearchCardForKt(articlesOfKt) { onArticleClicked(it) } }
                }

            }
        }


    }
}

@Composable
fun SearchCardForKt(articles: List<Article>, onArticleClicked: (Article) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), elevation = 0.dp) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp), verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(text = stringResource(id = R.string.knowledge_tree))
            Divider(color = lightGary_m, thickness = 1.dp)

            articles.forEach { article ->
                ArticleInKtCard({ onArticleClicked(it) }, article)
            }
        }
    }
}

@Composable
fun ArticleInKtCard(
    onArticleClicked: (Article) -> Unit,
    article: Article,
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clickable { onArticleClicked(article) }
    ) {
        Image(
            painterResource(id = R.drawable.search_4),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
            ) {
                Text(text = article.articleName, style = MaterialTheme.typography.body1)
                Surface(
                    color = lightGreen6,
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = article.articleDescription,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            )
                            .fillMaxWidth(0.8f),
                        color = remindGreen3,
                    )
                }

                Spacer(modifier = Modifier.padding(1.dp))
            }
            Icon(
                painter = painterResource(id = R.drawable.search_5),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = lightGary_m
            )

        }
    }
}

@Composable
fun SearchCardForOthers(articles: List<Article>, onArticleClicked: (Article) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), elevation = 0.dp) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp), verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(text = stringResource(id = R.string.article))
            Divider(color = lightGary_m, thickness = 1.dp)

            articles.forEach { article ->
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable { onArticleClicked(article) }) {
                    Icon(
                        painterResource(id = R.drawable.mi_search),
                        contentDescription = null,
                        tint = lightGary_m,
                        modifier = Modifier.size(20.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = article.articleName,
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.search_5),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = lightGary_m
                        )

                    }

                }
            }


        }

    }
}


@Composable
fun SearchTextFieldBlankContent(
    recentSearch: List<String>,
    onRecentCardClick: (String) -> Unit,
    clearRecent: () -> Unit,
) {
    Column() {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search_2),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = darkGary
            )
            Text(
                text = stringResource(id = R.string.recent_search),
                modifier = Modifier.padding(horizontal = 10.dp),
                style = MaterialTheme.typography.body1
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { clearRecent() },
                    modifier = Modifier
                        .size(20.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_3),
                        contentDescription = null,
                    )
                }
            }
        }
        //最近搜索的flow
        FlowRow(mainAxisSpacing = 10.dp, crossAxisSpacing = 10.dp) {
            recentSearch.forEach { searchContent: String ->

                TextButton(
                    shape = CircleShape,
                    onClick = { onRecentCardClick(searchContent) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier.height(25.dp), contentPadding = PaddingValues(3.dp)
                ) {
                    Text(
                        text = searchContent,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTopBar(
    screenHeight: Int,
    onTextFieldValueChanged: (String) -> Unit,
    textFieldValue: String,
    onBackIconClicked: () -> Unit,
    clearTextField: () -> Unit,
    onKeyBoardClick: (String) -> Unit,
) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(
                (screenHeight * 0.075).dp
            ),

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            IconButton(
                onClick = { onBackIconClicked() }, modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight(0.3f)
                    .aspectRatio(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search_1),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            searchTextField(
                textFieldValue,
                onTextFieldValueChanged,
                clearTextField,
                onKeyBoardClick = { onKeyBoardClick(it) },
                modifier = Modifier.align(Alignment.Center)
            )

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun searchTextField(
    textFieldValue: String,
    onTextFieldValueChanged: (String) -> Unit,
    clearTextField: () -> Unit,
    onKeyBoardClick: (String) -> Unit,
    modifier: Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(modifier) {
        Surface() {
            Surface(
                shape = CircleShape,
                border = BorderStroke(1.dp, darkGary),
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth(0.83f)

            ) {
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { onTextFieldValueChanged(it) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body1,
                    keyboardOptions = KeyboardOptions(imeAction = Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            onKeyBoardClick(textFieldValue)
                        }),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.mi_search),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(20.dp)
                                        .padding(horizontal = 15.dp)
                                        .aspectRatio(1f)
                                )
                                Box {
                                    if (textFieldValue == "") Text("输入查找的标题", color = lightGary)
                                    innerTextField()
                                }
                            }
                            IconButton(onClick = { clearTextField() }) {
                                Icon(Icons.Filled.Close, contentDescription = null)
                            }
                        }
                    }
                )
            }
        }
    }
}

