package com.example.knowledgetree.UIMain
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.knowledgetree.Database.User
import com.example.knowledgetree.Navigation.Navigation
import com.example.knowledgetree.Navigation.Screen
import com.example.knowledgetree.R
import com.example.knowledgetree.UIMain.MyInfomation.miMain
import com.example.knowledgetree.data.mainBottomNav
import com.example.knowledgetree.ui.theme.lightGary
import com.example.knowledgetree.ui.theme.remindGreen1
import kotlinx.coroutines.launch

@Composable
fun MainUI(ifFirstUse:Boolean) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    var userInfo = User(
        1,
        "default",
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        -1,
        "default"
    )
    val navController = rememberNavController()//控制器
    var pageName by remember {
        mutableStateOf("")
    }
    val bottomBarScreen = arrayOf("kt", "dp", "ed")
    var currentIndex by remember {
        mutableStateOf(
            when (pageName) {
                "kt" -> 0
                "ed" -> 1
                "dp" -> 2
                else -> 0
            }
        )
    }
    val scaffoldState = rememberScaffoldState()
    val scaffoldScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier,
        bottomBar = {
            if (pageName in bottomBarScreen) MainBottom(
                screenHeight,
                navController,
                currentIndex = currentIndex
            ) { currentIndex = it }
        },
        drawerContent = {
            miMain(userInfo, screenWidth) {
                scaffoldScope.launch {
                    scaffoldState.drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
                navController.navigate(
                    when (it) {
                        R.string.notes -> Screen.NoteScreen.route
                        R.string.bookmarks -> Screen.BookMarkScreen.route
                        R.string.setting -> ""
                        R.string.feedback -> ""
                        else -> Screen.KtScreen.route
                    }
                )
            }
        },
        drawerGesturesEnabled = !scaffoldState.drawerState.isClosed,
    ) {
        Navigation(
            {
                pageName = it
                Log.d("bug", pageName)
            },
            navController,
            onSearchIconClicked = { navController.navigate(Screen.SearchScreen.route) },
            {
                scaffoldScope.launch {
                    scaffoldState.drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            },
            { userInfo = it },ifFirstUse=ifFirstUse)  //todo 点击跳转文章
    }
}


@Composable
fun MainBottom(
    height: Int,
    navController: NavController,
    currentIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    //todo : MainBottom尺寸

    BottomAppBar(
        Modifier.height((height.toFloat() * 0.065f).dp),
        backgroundColor = Color.White,
        contentPadding = PaddingValues(),
        elevation = 0.dp
    ) {
        val mainBottomStringId = listOf(
            mainBottomNav(R.drawable.ic_knowledge_tree, R.string.knowledge_tree),
            mainBottomNav(R.drawable.ic_example_development, R.string.example_development),
            mainBottomNav(R.drawable.ic_design_performance, R.string.design_performance)

        )



        TabRow(
            selectedTabIndex = currentIndex,
            backgroundColor = Color.Transparent,
            indicator = {},

            )
        {  //控制那个被选中（下面的小横线）
            mainBottomStringId.forEachIndexed { index, content ->
                IconBottomTab(
                    picRes = content.pictureRes,
                    textRes = content.stringRes,
                    selected = index == currentIndex,
                    onSelected = {
                        onTabSelected(index)
                        when (index) {
                            0 -> navController.navigate(Screen.KtScreen.route)
                            1 -> {
                                navController.navigate(Screen.EdScreen.route)
                            }
                            2 -> navController.navigate(Screen.DpScreen.route)
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun IconBottomTab(
    textRes: Int,
    picRes: Int,
    selected: Boolean = false,
    onSelected: () -> Unit,
//    bottomButtonOnclick: () -> Unit,
) {
    //todo：底部nav的selected

    Surface(contentColor = if (selected) remindGreen1 else lightGary,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onSelected() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painterResource(picRes),
                "",
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .padding(2.dp)
            )
            Text(
                text = stringResource(id = textRes), fontSize = 10.sp,
            )
        }

    }

}
