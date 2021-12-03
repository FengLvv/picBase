package com.example.knowledgetree.UIMain.MyInfomation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.knowledgetree.Database.User
import com.example.knowledgetree.Navigation.Screen
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.lightGreen1
import com.example.knowledgetree.ui.theme.remindGreen1


@Composable
fun miMain(
    user:User,
    screenWidth: Int,
    onMiItemClick: (stringRes: Int) -> Unit
) {

    Box(Modifier.fillMaxSize(), Alignment.Center) {
        //背景
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mi_bg),
                contentDescription = null,
                Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth(0.9f)
        ) {

            //头像一栏
            miHeader(user.headImage, screenWidth = screenWidth, user.name, 12) //todo:徽章数量
            //树苗
            miSapling(screenWidth, 5)
            //导航区
            miNav {
                onMiItemClick(it)
            }

        }
    }
}


//todo: 添加侧边栏的导航
@Composable
fun miNav(onMiItemClick: (stringRes: Int) -> Unit) {
    LazyColumn(
        Modifier.padding(0.dp, 40.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        item {
            miNavDetail(
                modifier = Modifier,
                painterResource(id = R.drawable.mi_note),
                stringRes = R.string.notes
            ) { onMiItemClick(R.string.notes) }
        }
        item {
            miNavDetail(
                modifier = Modifier,
                painterResource(id = R.drawable.mi_bookmark),
                stringRes = R.string.bookmarks
            ) { onMiItemClick(R.string.bookmarks) }
        }
        item {
            Divider()
        }
        item {
            miNavDetail(
                modifier = Modifier,
                painterResource(id = R.drawable.mimain_setting),
                stringRes = R.string.setting
            ) { onMiItemClick(R.string.setting) }
        }
        item {
            miNavDetail(
                modifier = Modifier,
                painterResource(id = R.drawable.mimain_feedback),
                stringRes = R.string.feedback
            ) { onMiItemClick(R.string.feedback) }
        }
    }
}

//传入Painter
@Composable
fun miNavDetail(modifier: Modifier, icon: Painter, stringRes: Int, onclick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = true, color = lightGreen1)
        ) { onclick() }
        .then(modifier), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, Modifier.size(20.dp))
        Spacer(modifier = Modifier.padding(10.dp, 0.dp))
        Text(stringResource(id = stringRes), style = MaterialTheme.typography.body1)
    }
}


@Composable
fun miSapling(screenWidth: Int, saplingNum: Int) {
    Card(
        elevation = 5.dp, modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .width((screenWidth * 0.1f).dp)
                        .aspectRatio(1f),
                    color = Color.Transparent,
                    border = BorderStroke(3.dp, remindGreen1),
                    shape = CircleShape
                ) {
                    Image(
                        painterResource(id = R.drawable.mi_3),
                        contentDescription = null, contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp, 0.dp))
                Text(
                    saplingNum.toString(),
                    style = MaterialTheme.typography.body1,
                               )
                Text(
                    " " + stringResource(id = R.string.sapling),
                    style = MaterialTheme.typography.body1,
                )
            }
            TextButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(remindGreen1, Color.White),
                shape = CircleShape,
                contentPadding = PaddingValues(12.dp, 0.dp),
                elevation = ButtonDefaults.elevation(),
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .weight(0.4f)
                    .height(25.dp)

            ) {
                Text(stringResource(id = R.string.exchange), color = Color.White)
                Spacer(modifier = Modifier.padding(2.dp))
                Icon(painterResource(id = R.drawable.kt_2), contentDescription = null)
            }


        }

    }
}

@Composable
fun miHeader(
    headerIcon: String,
    screenWidth: Int,
    userName: String,
    medalNum: Int,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height((screenWidth.toFloat() * 0.2f).dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = headerIcon,
                onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.demo)
                    transformations(CircleCropTransformation())
                }
            ),
            null,
            modifier = Modifier
                .requiredWidth((screenWidth.toFloat() * 0.15f).dp)
                .aspectRatio(1f)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.padding((screenWidth.toFloat() * 0.03f).dp))

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(userName, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.padding((screenWidth.toFloat() * 0.03f).dp))
                Row(
                    Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(id = R.drawable.mi_1),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(stringResource(id = R.string.change),modifier = Modifier.padding(3.dp))
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row() {
                Button(
                    onClick = {},//todo
                    modifier = Modifier.height(25.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    elevation = ButtonDefaults.elevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        lightGreen1,
                                        remindGreen1
                                    )
                                )
                            )
                            .height(30.dp)
                            .padding(10.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            painterResource(id = R.drawable.mi_2),
                            null,
                            tint = Color.White,
                            modifier = Modifier
                                .height(16.dp)
                                .aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(
                            text = "$medalNum" + stringResource(id = R.string.medal),
                            style=MaterialTheme.typography.body2,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp, 0.dp))
                Column {
                    Spacer(modifier = Modifier.padding(0.dp, 10.dp))
                    Box(
                        modifier = Modifier.requiredHeight(40.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Image(
                            painterResource(id = R.drawable.mi_4),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(
                                        bounded = false,
                                        radius = 0.dp
                                    ),
                                ) { } //todo:树苗获取攻略
                        )
                        Text(
                            stringResource(id = R.string.How_to_get_sapling),
                            modifier = Modifier.padding(2.dp),style=MaterialTheme.typography.body2
                        )
                    }

                }

            }


        }

    }
}


