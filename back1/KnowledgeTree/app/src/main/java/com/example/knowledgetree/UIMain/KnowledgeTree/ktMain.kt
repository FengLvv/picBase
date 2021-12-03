package com.example.knowledgetree.UIMain.MyInfomation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.knowledgetree.Database.Article
import com.example.knowledgetree.Database.ArticleGroup
import com.example.knowledgetree.Database.User
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.*


@Composable
fun KtMain(
    screenWidth: Int,
    user: User,
    percentage: Float,
    screenHeight: Int,
    recentLearn: String = "最近学到了",
    ktGroup: List<ArticleGroup>,
    ktArticles: (Int) -> List<Article>,
    read: (articleId: Int) -> Boolean,
    onSearchIconClicked: () -> Unit,
    onHeadImageClicked: () -> Unit,
    onArticleClicked: (Article) -> Unit
) {

    Box(modifier = Modifier) {

        KtBg()
        Column(
            modifier = Modifier
                .padding((screenWidth * 0.035).dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.padding(0.dp, (screenHeight * 0.01f).dp))
            KtTopRow(
                modifier = Modifier.padding(0.dp, 5.dp, (screenWidth * 0.02).dp, 0.dp),
                screenWidth,
                user.headImage, onSearchIconClicked, onHeadImageClicked
            )
            Spacer(modifier = Modifier.padding(0.dp, (screenHeight * 0.005f).dp))
            Text(
                stringResource(id = R.string.android_design),
                style = MaterialTheme.typography.h5.copy(
                    color = Color.White,
                    fontSize = 28.sp
                )
            )
            Column(
                modifier = Modifier
                    .padding((screenWidth * 0.015).dp)
                    .fillMaxSize()
            ) {
                KtIndicator(
                    modifier = Modifier,
                    percentage = percentage,
                    screenWidth = screenWidth
                )
                KtUserInformation(modifier = Modifier.padding(0.dp, 10.dp))
                Card(
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = Color.White,
                    contentColor = darkGary,
                    elevation = 0.dp
                ) {
                    RecentLearning(Modifier.padding(20.dp, 10.dp), recentLearn)
                }
                KtContent(ktGroup, ktArticles, read, onArticleClicked)
            }
        }
    }
}

@Composable
fun KtBg() {
    Column() {
        //todo：先用图片垫着，有空在画
        Image(
            painter = painterResource(id = R.drawable.kt_1),
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

@Composable
fun KtContent(

    ktGroup: List<ArticleGroup>,
    ktArticles: (Int) -> List<Article>,
    read: (articleId: Int) -> Boolean,
    onArticleClicked: (Article) -> Unit
) {
    var ktTitleSelected by remember { mutableStateOf(0) }  //储存选择状态

    LazyColumn(
        modifier = Modifier.padding(0.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(ktGroup) { index, item ->
            KtArticleGroupCard(
                { ktTitleSelected = index },//选择状态转换
                ktGroup[index].articleGroupName,
                ktTitleSelected == index
            )
            if (ktTitleSelected == index) {
                // Log.d("bug",index.toString())
                KtArticleCard(
                    modifier = Modifier.padding(0.dp, 15.dp),
                    ktArticles(index + 1),
                    read,
                    onArticleClicked
                )
            }
        }
    }
}

@Composable
fun KtArticleGroupCard(
    ktTitleClick: () -> Unit,
    articleGroupName: String,
    selected: Boolean,
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.Black.copy(alpha = 0.04f),
        contentColor = darkGary,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { ktTitleClick() },
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = articleGroupName,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.button
            )
            if (selected) {
                Icon(painterResource(id = R.drawable.kt_3), contentDescription = null)
            } else {
                Icon(painterResource(id = R.drawable.kt_2), contentDescription = null)
            }
        }
    }
}


@Composable
fun KtArticleCard(
    modifier: Modifier,
    ktArticles: List<Article>,
    read: (articleId: Int) -> Boolean,
    onArticleClicked: (Article) -> Unit
) {
    var readed: Boolean  //todo 判断读过
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        ktArticles.forEach {
            readed = read(it.articleId)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp)
                    .clickable { onArticleClicked(it) },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(it.articleName)
                Surface(
                    modifier = Modifier
                        .size(15.dp)
                        .aspectRatio(1f),
                    shape = CircleShape,
                    color = if (readed) remindGreen4 else lightGary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Done, contentDescription = null)
                }
            }
        }
    }

}


@Composable
fun KtUserInformation(
    modifier: Modifier,
    todayLearning: Int = 0,
    learningDays: Int = 0,
    noteNum: Int = 0,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White.copy(alpha = 0.75f),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(0.dp, 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            KtUserInformationDetail(todayLearning, stringResource(id = R.string.today_learning))
            KtUserInformationDetail(learningDays, stringResource(id = R.string.learning_days))
            KtUserInformationDetail(noteNum, stringResource(id = R.string.notes))
        }
    }

}

@Composable
fun KtUserInformationDetail(number: Int = 0, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$number", style = MaterialTheme.typography.h5, color = darkGary)
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(
                color = lightGary,
                letterSpacing = 2.sp,
            )
        )
    }
}

@Composable
fun RecentLearning(modifier: Modifier, recentLearn: String = "最近学的是") {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.recently_learn),
            modifier = Modifier.weight(1f),
            color = remindGreen1,
        )
        Text(
            text = recentLearn,
            modifier = Modifier.weight(3f),
        )
        Icon(painterResource(id = R.drawable.kt_2), null)


    }
}

@Composable
fun HeadImage(screenWidth: Int, headImage: String, onHeadImageClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .width((screenWidth * 0.13f).dp)
                .aspectRatio(1f),
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(1.5.dp, Color.White)
        ) {}
        Image(
            painter = rememberImagePainter(
                data = headImage,
                onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.demo)
                }
            ),//todo 初始头像
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width((screenWidth * 0.1f).dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .clickable { onHeadImageClicked() }
        )
    }
}

@Composable
fun SearchIcon(screenWidth: Int, onclick: () -> Unit) {
    val gradient =
        Brush.linearGradient(
            listOf(
                lightYellow,
                lightGreen1,
                remindGreen4
            ),
            start = Offset(Float.POSITIVE_INFINITY, 0f),
            end = Offset(0f, Float.POSITIVE_INFINITY)
        )
    GradientButton(
        modifier = Modifier.aspectRatio(1f),
        gradient = gradient,
        width = (screenWidth * 0.1f),
        shape = CircleShape,
        contentAlignment = Alignment.Center,
        onclick = onclick
    ) {
        Icon(
            painterResource(id = R.drawable.mi_search),
            modifier = Modifier.fillMaxSize(0.5f),
            contentDescription = null
        )
    }
}


@Composable
fun GradientButton(
    modifier: Modifier,
    gradient: Brush,
    width: Float,
    shape: Shape,
    contentAlignment: Alignment,
    elevation: ButtonElevation = ButtonDefaults.elevation(5.dp, 10.dp, 0.dp),
    onclick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = { onclick() },//todo
        modifier = Modifier
            .width(width.dp)
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White
        ),
        elevation = elevation,
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width.dp)
                .then(modifier)
                .background(gradient),
            contentAlignment = contentAlignment
        )
        {
            content()
        }
    }
}

@Composable
fun KtTopRow(
    modifier: Modifier,
    screenWidth: Int,
    headImage: String,
    onSearchIconClicked: () -> Unit,
    onHeadImageClicked: () -> Unit,
) {
    //第一条：头像，搜索按钮
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        //  .height(intrinsicSize = IntrinsicSize.Min)
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeadImage(screenWidth = screenWidth, headImage, onHeadImageClicked)
        SearchIcon(screenWidth) { onSearchIconClicked() }

    }
}

@Composable
fun KtIndicator(modifier: Modifier, percentage: Float = 0.5f, screenWidth: Int) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        LinearProgressIndicator(
            percentage,
            modifier = Modifier.width((screenWidth * 0.4).dp),
            color = Color.White,
            backgroundColor = remindGreen4
        )
        Text(
            modifier = Modifier.padding(10.dp, 0.dp),
            text = "${percentage * 100f}%",
            style = MaterialTheme.typography.body1.copy(color = Color.White)
        )
    }

}


