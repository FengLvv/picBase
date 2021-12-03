package com.example.knowledgetree.UIMain.DesignPerformance

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.knowledgetree.Database.Article
import com.example.knowledgetree.Navigation.Screen
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.*
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun fragmentContent(navController: NavController,screenWidth: Int, articles: List<Article>) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
                backgroundColor = Color.White,
                elevation = 0.dp,
            ) {
                Box(Modifier.fillMaxWidth()) {
                    IconButton(onClick = { navController.navigate(Screen.DpScreen.route)},
                        modifier = Modifier.align(Alignment.CenterStart)) {
                        Icon(painter = painterResource(id = R.drawable.dp_detail_1),
                            contentDescription = null)
                    }
                    Text(text = stringResource(id = R.string.fragment_style),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    ) {

        Surface(color = lightGary_l, modifier = Modifier.fillMaxSize()) {
            FlowRow(
                modifier = Modifier
                    .verticalScroll(ScrollState(0))
                    .padding(0.dp, 30.dp),
                mainAxisSize = SizeMode.Wrap,
                mainAxisAlignment = FlowMainAxisAlignment.Center,
                mainAxisSpacing = (screenWidth * 0.06).dp,
                crossAxisSpacing = (screenWidth * 0.06).dp,
                lastLineMainAxisAlignment = FlowMainAxisAlignment.Center
            ) {
                articles.forEach {
                    fragmentCard(modifier = Modifier, screenWidth, it.articleName,{})
                }
            }
        }
    }
}


@Composable
fun fragmentCard(modifier: Modifier, screenWidth: Int, title: String,onClick:() -> Unit) {
    val dpcolors = listOf<Color>(dpPink, dpPurple, dpBlue, dpBlue2, dpBlue3, dpGreen1, dpGreen2)
    Box(modifier = modifier) {
        DpStyleCard(modifier = Modifier.aspectRatio(1f),
            width = (screenWidth * 0.36f),
            gradientFirstColor = dpcolors.random(),
            elevation = ButtonDefaults.elevation(),onclick = onClick) {
            Column(
                Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.15f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, color = Color.White, fontSize = 24.sp)
            }
        }

    }
}

