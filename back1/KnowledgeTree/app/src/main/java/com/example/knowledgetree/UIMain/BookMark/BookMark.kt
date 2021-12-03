package com.example.knowledgetree.UIMain.BookMark

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.knowledgetree.Database.Article
import com.example.knowledgetree.Database.Bookmark
import com.example.knowledgetree.Database.GetBookMark
import com.example.knowledgetree.Database.getArticleComments
import com.example.knowledgetree.R
import com.example.knowledgetree.UIMain.Note.ArticleInKtCard
import com.example.knowledgetree.ui.theme.lightGary_l
import com.example.knowledgetree.ui.theme.lightGary_m
import com.example.knowledgetree.ui.theme.lightGreen6
import com.example.knowledgetree.ui.theme.remindGreen3


@Composable
fun BookmarkMain(
    navController: NavController,
    bookMarksOfKt: List<GetBookMark>,
    bookMarksOfOthers: List<GetBookMark>,
    screenWidth: Int,
    onArticleClicked: (Int) -> Unit,
) {
    var ktExpand by remember {
        mutableStateOf(false)
    }



    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                backgroundColor = Color.White,
                elevation = 0.dp,
            ) {
                Box(Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.dp_detail_1),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.bookmarks),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) {
        Surface(color = lightGary_l, modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.padding((screenWidth * 0.03f).dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item() {
                    bookMarkCardKt(
                        screenWidth,
                        ktExpand,
                        bookMarksOfKt,
                        onArticleClicked,
                        onExpandClicked = { ktExpand = !ktExpand })
                }
                item {
                    bookMarkCardOthers(
                        screenWidth,
                        bookMarksOfOthers,
                        onArticleClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun bookMarkCardKt(
    screenWidth: Int,
    ktExpand: Boolean,
    bookMarks: List<GetBookMark>,
    onArticleClicked: (Int) -> Unit,
    onExpandClicked: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
        elevation = 0.dp
    ) {

        Column(
            modifier = Modifier.padding((screenWidth * 0.06).dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.knowledge_tree),

                    )
                Icon(Icons.Filled.List, contentDescription = null)
            }
            bookMarks.forEachIndexed() { index, bookMark ->
                if (!ktExpand) {
                    if (index < 3) {
                        Divider(Modifier.padding(vertical = 5.dp))
                        ArticleInKtCard(
                            { onArticleClicked(bookMark.articleId) },
                            articleName = bookMark.articleName,
                            articleDescription = bookMark.articleDescription,
                            imageRes = R.drawable.search_4
                        )
                    }
                } else {
                    Divider(Modifier.padding(vertical = 5.dp))
                    ArticleInKtCard(
                        { onArticleClicked(bookMark.articleId) },
                        articleName = bookMark.articleName,
                        articleDescription = bookMark.articleDescription,
                        imageRes = R.drawable.search_4

                    )
                }
            }
        }
    }
}

@Composable
private fun bookMarkCardOthers(
    screenWidth: Int,
    bookMarks: List<GetBookMark>,
    onArticleClicked: (Int) -> Unit,

    ) {

    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding((screenWidth * 0.06).dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.article),

                    )
            }
            bookMarks.forEach() { bookMark ->
                Divider(Modifier.padding(vertical = 5.dp))
                bookMarkItemOthers(onArticleClicked, bookMark)
            }
        }
    }
}

@Composable
private fun bookMarkItemOthers(
    onArticleClicked: (Int) -> Unit,
    bookMark: GetBookMark
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp)
            .clickable { onArticleClicked(bookMark.articleId) }
    ) {
        Image(
            painter = rememberImagePainter(
                data = bookMark.imageRes,
                onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.demo)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .width(50.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp)), contentScale = ContentScale.Crop
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(text = bookMark.articleName)
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



