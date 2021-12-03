package com.example.knowledgetree.UIMain.SaplingMain

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.knowledgetree.Database.UserGetCommodities
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.*

@Composable
fun SamplingMain(
    navController: NavController, screenWidth: Int, samplingNum: Int,
    getUserCommodity: List<UserGetCommodities>,
    onExchangeClicked: (id: Int, price: Int) -> Unit
) {

    var commodityOfKt =
        mutableListOf<UserGetCommodities>()

    var commodityOfOthers =
        mutableListOf<UserGetCommodities>()


    getUserCommodity.forEach {
        when (it.commodityPlate) {
            "kt" -> {
                commodityOfKt.add(it)
            }
            "other" -> commodityOfOthers.add(it)
        }
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
                        text = stringResource(id = R.string.sapling_exchange),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.align(Alignment.Center),
                        )
                }
            }
        }
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                SamplingHead(screenWidth, samplingNum)
            }
            item {
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                SamplingKtUnlockBar(
                    screenWidth = screenWidth,
                    stringResource(id = R.string.unlockKt)
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
            items(commodityOfKt) { content ->
                SamplingKtUnlockCard(content, screenWidth) { id, price ->
                    onExchangeClicked(id, price)
                    commodityOfKt.clear()
                    commodityOfOthers.clear()
                }
            }
            item {
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                SamplingKtUnlockBar(
                    screenWidth = screenWidth,
                    stringResource(id = R.string.developedArticleUnlock)
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
            items(commodityOfOthers) { content ->
                SamplingOtherUnlockCard(content, screenWidth) { id, price ->

                    commodityOfKt.clear()
                    commodityOfOthers.clear()
                    onExchangeClicked(id, price)
                }
            }
        }
    }
}

@Composable
fun SamplingKtUnlockCard(
    commodity: UserGetCommodities,
    screenWidth: Int,
    onExchangeClicked: (id: Int, price: Int) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(horizontal = (screenWidth * 0.05).dp)
            .fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = lightGary_l
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(commodity.commodityContent)
                Row(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = commodity.commodityPrice.toString(), color = lightYellow3)

                    Image(
                        painter = painterResource(id = R.drawable.mi_3),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(25.dp)
                            .aspectRatio(1f)
                    )
                }

            }
            Surface(
                color = if (commodity.userUnlockCommodity) lightGary_m else remindGreen1,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(enabled = !commodity.userUnlockCommodity) {
                        onExchangeClicked(
                            commodity.commodityId,
                            commodity.commodityPrice
                        )
                    }) {
                Text(
                    text = stringResource(if (commodity.userUnlockCommodity) R.string.exchanged else R.string.exchange),
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 3.dp)
                )
            }


        }
    }
}


@Composable
fun SamplingKtUnlockBar(screenWidth: Int, text: String) {
    Column(
        modifier = Modifier.padding(
            horizontal = (screenWidth * 0.05).dp
        ), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Divider(color = remindGreen1, thickness = 3.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Surface() {
                    Text(
                        text,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun SamplingHead(screenWidth: Int, samplingNum: Int) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.sampling_1),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier.padding(
                vertical = 50.dp,
                horizontal = (screenWidth * 0.05).dp
            ), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(border = BorderStroke(2.dp, remindGreen1), shape = CircleShape) {
                    Image(
                        painter = painterResource(id = R.drawable.mi_3),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
                            .aspectRatio(1f)
                    )
                }
                Text(text = samplingNum.toString(), style = MaterialTheme.typography.h6)
            }

            Surface(
                modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
                shape = CircleShape,
                color = lightYellow4
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        border = BorderStroke(1.dp, darkGary),
                        modifier = Modifier.aspectRatio(1f),
                        color = Color.Transparent
                    ) {
                        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                            Text(text = "?", fontSize = 12.sp)
                        }
                    }
                    Text(
                        text = stringResource(id = R.string.How_to_get_sapling),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SamplingOtherUnlockCard(
    commodity: UserGetCommodities,
    screenWidth: Int,
    onExchangeClicked: (id: Int, price: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = (screenWidth * 0.05).dp)
            .fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = lightGary_l
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sampling_2),
                contentDescription = null,
                modifier = Modifier.size(130.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = commodity.commodityContent)
                Surface(
                    color = if (commodity.userUnlockCommodity) lightGary_m else remindGreen1,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .height(intrinsicSize = IntrinsicSize.Min)
                        .clickable(enabled = !commodity.userUnlockCommodity) {
                            onExchangeClicked(
                                commodity.commodityId,
                                commodity.commodityPrice
                            )
                        }) {
                    Row(
                        modifier = Modifier
                            .width(110.dp)
                            .height(20.dp)
                            .padding(horizontal = 20.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (!commodity.userUnlockCommodity) {
                                Icon(
                                    painterResource(id = R.drawable.sampling_3),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxHeight(0.8f)
                                        .aspectRatio(1f)
                                )
                                Text(
                                    text = commodity.commodityPrice.toString(),
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = stringResource(if (commodity.userUnlockCommodity) R.string.unlocked else R.string.unlock),
                                color = Color.White, fontSize = 12.sp
                            )
                        }
                    }

                }
            }
        }

    }
}

