package com.example.knowledgetree.UIMain.Articles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ANMain(
    ifCollected: Boolean,
    onCollectClicked: () -> Unit,
    onBackClick: () -> Unit,
    screenWidth: Int
) {
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
                    scaffoldState.snackbarHostState.showSnackbar(if (ifCollected) collectUnfinished else collectFinished)
                }
            }
        }
    ) {

        ANContent(screenWidth = screenWidth)

    }
}

@Composable
fun ANContent(screenWidth: Int) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = (screenWidth * 0.04).dp, vertical = 25.dp)
    ) {

        item {
            Column() {
                Text(text = "B端设计师要懂的信息架构", style = MaterialTheme.typography.h5)
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Text(text = "author")
                    Text("time", color = lightGary)
                }
            }
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Image(painter = rememberImagePainter(
                    data = "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
                                     builder = {
                        crossfade(true)
                        placeholder(R.drawable.demo)
                    }
                ), contentDescription = null,
                    modifier = Modifier.width(screenWidth.dp).aspectRatio(1.5f),
                contentScale = ContentScale.Crop)
            }
        }
        item {
            Text(
                text = "1.1前言\n" +
                        "\n" +
                        "这篇文章的起源，来源于最近看到的话题“B端设计师会被组件库取代吗？”。从表面上看，在组件库越来越完善的时代，很多页面设计依靠组件库就能够快速搭建。\n" +
                        "\n" +
                        "那么在这种情况下，B端设计师存在的意义和价值到底体现在哪里呢？其实B端设计的重点并不在页面的视觉上，视觉只是作为设计师最终输出成果的一小部分。个人认为B端设计师工作重心体现在做「正确的设计」，比如以下几个方面：\n" +
                        "\n" +
                        "1.这个设计能否完成对应的商业目标和产品目标；\n" +
                        "\n" +
                        "2.我们的信息呈现是否合理以及能否解决当前需求；\n" +
                        "\n" +
                        "3.用户能否在页面上快速找到想要的信息；"
            )
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .aspectRatio(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = lightGreen5),
                    shape = CircleShape,
                    onClick = {}
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.an_1),
                            contentDescription = null,
                            tint = remindGreen1
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = "3", color = remindGreen1)
                    }
                }
            }
        }
        item {
            Divider(modifier = Modifier.requiredWidth(screenWidth.dp))
        }
        item {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "分享给好友")
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(0.08f)
                        .aspectRatio(1f)
                        .clickable { },
                    colors = ButtonDefaults.buttonColors(backgroundColor = wxGreen),
                    shape = CircleShape,
                    onClick = {},
                    contentPadding = PaddingValues()
                ) {
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(id = R.drawable.login_3),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}