package com.example.knowledgetree.UIMain.ExampleDevelopment

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.knowledgetree.Database.Article
import com.example.knowledgetree.R

@Composable
fun EdMain(
    screenWidth: Int,
    screenHeight: Int,
    articles: List<Article>,
    onSearchButtonClicked: () -> Unit,
    onArticleClicked: (Article) -> Unit
) {
    Log.d("ed", articles.toString())
    Box(modifier = Modifier.fillMaxSize()) {
        EdBg()
        Column(modifier = Modifier.padding((screenWidth * 0.04).dp)) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                item {
                    EdTitle(
                        modifier = Modifier.padding(
                            0.dp,
                            (screenHeight * 0.08).dp,
                            0.dp,
                            (screenHeight * 0.045).dp
                        ), onSearchButtonClicked = onSearchButtonClicked
                    )
                }
                itemsIndexed(articles) { index, article ->
                    EdCard(
                        article = article,
                        screenWidth = screenWidth,
                        screenHeight = screenHeight
                    ) {
                        onArticleClicked(article)
                    }
                }
            }

        }
    }
}

@Composable
fun EdCard(article: Article, screenWidth: Int, screenHeight: Int, onArticleClicked: () -> Unit) {
    Card(shape = RoundedCornerShape(20.dp),
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight * 0.65).dp)
            .clickable { onArticleClicked() }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ed_2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
            )
            Column(
                modifier = Modifier.padding(10.dp, 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = rememberImagePainter(
                    data = article.imageRes,
                    onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.demo)
                    }
                ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp, horizontal = (screenWidth * 0.04).dp)
                ) {
                    Text(
                        text = article.articleName,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(text = article.articleDescription)

                }

            }
        }
    }
}

@Composable
fun EdTitle(modifier: Modifier, onSearchButtonClicked: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .then(modifier), horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = stringResource(id = R.string.example_development),
            style = MaterialTheme.typography.h4,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        IconButton(onClick = { onSearchButtonClicked() }) {
            Image(painter = painterResource(id = R.drawable.ed_1), contentDescription = null)
        }


    }
}

@Composable
fun EdBg() {
    Image(
        painter = painterResource(id = R.drawable.ed_bg),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}