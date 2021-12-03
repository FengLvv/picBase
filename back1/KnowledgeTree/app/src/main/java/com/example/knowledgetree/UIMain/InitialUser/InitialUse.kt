package com.example.knowledgetree.UIMain.InitialUser

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.lightGreen4
import com.example.knowledgetree.ui.theme.remindGreen1
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IUMain(screenWidth: Int,onStartClick:()->Unit) {
    val pagerState = rememberPagerState()
    Box(
        Modifier.fillMaxHeight(),
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier,
        ) { page ->
            when (page) {
                0 -> {
                    IUContent(
                        image = R.drawable.iu_1,
                        stringRes1 = R.string.intro11,
                        stringRes2 = R.string.intro12,
                        screenWidth = screenWidth
                    )
                }
                1 -> {
                    IUContent(
                        image = R.drawable.iu_2,
                        stringRes1 = R.string.intro21,
                        stringRes2 = R.string.intro22,
                        screenWidth = screenWidth
                    )
                }
                2 -> {
                    IUContent(
                        image = R.drawable.iu_3,
                        stringRes1 = R.string.intro31,
                        stringRes2 = R.string.intro32,
                        screenWidth = screenWidth
                    )
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 150.dp),
            inactiveColor = lightGreen4,
            activeColor = remindGreen1,
            indicatorWidth = 8.dp,
            indicatorHeight = 8.dp,
            spacing = 10.dp
        )
        if (pagerState.currentPage == 2) {
            OutlinedButton( modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 80.dp),
                onClick = { onStartClick() },
                shape = CircleShape,
                border = BorderStroke(1.dp, remindGreen1),
                contentPadding = PaddingValues(horizontal = 25.dp)
            ) {
                Box(modifier = Modifier) {
                    Text(text = stringResource(id = R.string.start), color = remindGreen1)
                }
            }
        }

    }
}

@Composable
fun IUContent(image: Int, stringRes1: Int, stringRes2: Int, screenWidth: Int) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Image(
            painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(
                stringResource(stringRes1),
                fontSize = 20.sp,
                color = remindGreen1,
                letterSpacing = 2.sp
            )
            Text(
                text = stringResource(id = stringRes2),
                textAlign = TextAlign.Center,
                letterSpacing = 3.sp
            )
        }
        Spacer(modifier = Modifier.padding(0.dp))

    }
}