package com.example.knowledgetree.UIMain.Note

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.knowledgetree.Database.Article
import com.example.knowledgetree.Database.getArticleComments

import com.example.knowledgetree.Navigation.Screen
import com.example.knowledgetree.R
import com.example.knowledgetree.UIMain.Search.ArticleInKtCard
import com.example.knowledgetree.ui.theme.lightGary_l
import com.example.knowledgetree.ui.theme.lightGary_m
import com.example.knowledgetree.ui.theme.lightGreen6
import com.example.knowledgetree.ui.theme.remindGreen3

@Composable
fun NoteMain(
    navController: NavController,
    comments: List<getArticleComments>,
    screenWidth: Int,
    onArticleClicked: (Int) -> Unit,
) {
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
                        text = stringResource(id = R.string.notes),
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
                modifier = Modifier.padding((screenWidth * 0.06).dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(comments) { comment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(intrinsicSize = IntrinsicSize.Min),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    (screenWidth * 0.02).dp
                                ), verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {

                                Text(text = comment.commentContent)
                                Text(text = comment.commentTime, fontSize = 16.sp)
                                Divider(Modifier.padding(vertical = 10.dp))
                                ArticleInKtCard(
                                    { onArticleClicked(comment.articleId) },
                                    articleName = comment.articleName,
                                    articleDescription = comment.articleDescription,
                                    imageRes = R.drawable.search_4  //todo 修正文章图片
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ArticleInKtCard(
    onArticleClicked: () -> Unit,
    articleName: String,
    articleDescription: String,
    imageRes: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onArticleClicked() }
    ) {
        Image(
            painterResource(id = imageRes),
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
                Text(text = articleName, style = MaterialTheme.typography.body1)
                Surface(
                    color = lightGreen6,
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = articleDescription,
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        ).fillMaxWidth(0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = remindGreen3
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

