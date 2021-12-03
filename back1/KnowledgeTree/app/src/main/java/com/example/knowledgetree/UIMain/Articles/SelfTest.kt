package com.example.knowledgetree.UIMain.Articles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import coil.transform.CircleCropTransformation
import com.example.knowledgetree.Database.Exercises
import com.example.knowledgetree.R
import com.example.knowledgetree.ui.theme.lightGary
import com.example.knowledgetree.ui.theme.remindGreen1
import kotlinx.coroutines.launch


@Composable
fun SelfTest(navController: NavController, tests: List<Exercises>, screenWidth: Int) {
    var testCurrentNum by remember {
        mutableStateOf(0)
    }
    val finishTest = stringResource(id = R.string.finish_test)
    val testSumNum = tests.size
    var testSelectionSelectedNum by remember {
        mutableStateOf(-1)
    }
    val scope = rememberCoroutineScope()
    var scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Snackbar(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        shape = CircleShape,
                        elevation = 5.dp,
                        backgroundColor = lightGary
                    ) {
                        Text(
                            text = data.message,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }

                }
            }
        },
        topBar = {
            ArticleTopBar(
                seeCollect = false,
                ifCollect = false,
                text = stringResource(id = R.string.self_test),
                { navController.popBackStack() }, {},
            )
        }) {
        Column(
            modifier = Modifier.padding(
                horizontal = (screenWidth * 0.04).dp,
                vertical = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "${stringResource(id = R.string.question)} ${tests[testCurrentNum].exercisesId}",
                style = MaterialTheme.typography.h5
            )
            Text(text = tests[testCurrentNum].exerciseQuestion)
            Image(
                painter = rememberImagePainter(
                    data = tests[testCurrentNum].exercisePicRes,
                    onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.demo)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Fit
            )
            tests[testCurrentNum].exerciseSelection.split("\n")
                .forEachIndexed { index, selection ->
                    testSelection(
                        if (index == tests[testCurrentNum].exerciseAnswer && testSelectionSelectedNum != -1) {
                            true
                        } else {
                            testSelectionSelectedNum == index
                        },
                        { testSelectionSelectedNum = index },
                        selection,
                        isRight = (index == tests[testCurrentNum].exerciseAnswer)
                    )
                }
            Column(modifier = Modifier) {
                Text(
                    if (testSelectionSelectedNum != -1) {
                        tests[testCurrentNum].exerciseReason
                    } else "", color = remindGreen1
                )

            }

            val finish = stringResource(id = R.string.finish)
            testPreviousAndNext(onPreviousClick = {
                if (testCurrentNum >= 0) {
                    testCurrentNum -= 1
                    testSelectionSelectedNum = -1
                }
            },
                onNextClick = {
                    if (testCurrentNum < testSumNum - 1) {
                        testCurrentNum += 1
                        testSelectionSelectedNum = -1
                    } else {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(message = finish)
                        }
                        navController.popBackStack()
                    }
                })

        }
    }


}

@Composable
fun testSelection(
    testSelectionSelected: Boolean,
    onTestSelectionClicked: () -> Unit,
    selection: String,
    isRight: Boolean,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = testSelectionSelected,
            onClick = { onTestSelectionClicked() },
            colors = RadioButtonDefaults.colors(selectedColor = if (isRight) remindGreen1 else Color.Red)
        )
        Text(text = selection)
    }
}

@Composable
fun testPreviousAndNext(onNextClick: () -> Unit, onPreviousClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.clickable { onPreviousClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.selftest_2),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(text = stringResource(id = R.string.previous))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.clickable { onNextClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.selftest_1),
                contentDescription = null,
                modifier = Modifier.size(40.dp), tint = remindGreen1
            )
            Text(text = stringResource(id = R.string.next))
        }
    }
}