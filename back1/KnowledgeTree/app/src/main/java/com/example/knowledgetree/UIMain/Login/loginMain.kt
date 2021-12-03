package com.example.knowledgetree.UIMain.Login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.knowledgetree.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.knowledgetree.Navigation.Screen
import com.example.knowledgetree.ui.theme.*


@Composable
fun loginMain(
    navController: NavController,
    screenWidth: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column() {
            Image(
                painter = painterResource(id = R.drawable.login_1),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        "随便逛逛",
                        modifier = Modifier
                            .padding(20.dp, 40.dp)
                            .clickable {
                                navController.navigate(Screen.KtScreen.route)
                            },// TODO:
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.End,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val cornerSize = screenWidth * 0.06
                    Spacer(modifier = Modifier.padding(20.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(cornerSize.dp),
                        color = Color.White,
                        elevation = 40.dp
                    ) {
                        Image(
                            modifier = Modifier.padding(5.dp),
                            painter = painterResource(id = R.drawable.login_2),
                            contentDescription = null,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Inside
                        )
                    }
                    Text(
                        "Seka Tree",
                        Modifier.padding(20.dp),
                        style = MaterialTheme.typography.h5.copy(
                            color = darkGary,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.padding(50.dp))
                    Button(
                        onClick = { },// TODO:
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .aspectRatio(7f),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = wxGreen,
                            disabledBackgroundColor = wxGreen
                        ),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            disabledElevation = 0.dp
                        ),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.login_3),
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 1f),
                                modifier = Modifier
                                    .fillMaxHeight(1f)
                                    .aspectRatio(1f)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(
                                "微信登录",
                                style = MaterialTheme.typography.body1.copy(color = Color.White)
                            )
                        }
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = false,
                            onClick = { /*TODO:我已阅读的选择*/ },
                        )
                        Text("我已同意", style = MaterialTheme.typography.body2)
                        Text(
                            "《用户协议》",
                            color = remindGreen1,
                            modifier = Modifier.clickable { },
                            style = MaterialTheme.typography.body2)//todo
                        Text("和", style = MaterialTheme.typography.body2)
                        Text(
                            "《隐私政策》",
                            color = remindGreen1,
                            modifier = Modifier.clickable {},
                            style = MaterialTheme.typography.body2)//todo:

                    }
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.15f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Divider(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        color = Color(240, 240, 240, 255),
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("其他登录方式")
                    }
                }

                Button(
                    onClick = {},// TODO:
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, remindGreen4),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = remindGreen4,
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .padding(20.dp, 0.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.login_4),
                            null,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text("手机一键登录", color = remindGreen4)
                    }

                }
            }


        }
    }

}


@Preview
@Composable
fun prevLogin() {
    KnowledgeTreeTheme() {

    }
}