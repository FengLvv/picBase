package com.example.knowledgetree.Navigation

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.knowledgetree.Database.*
import com.example.knowledgetree.Database.viewModel.TreeViewModel
import com.example.knowledgetree.Database.viewModel.TreeViewModelFactory
import com.example.knowledgetree.R
import com.example.knowledgetree.UIMain.Articles.ANMain
import com.example.knowledgetree.UIMain.Articles.ArticleMain
import com.example.knowledgetree.UIMain.Articles.SelfTest
import com.example.knowledgetree.UIMain.BookMark.BookmarkMain
import com.example.knowledgetree.UIMain.DesignPerformance.documentMain
import com.example.knowledgetree.UIMain.DesignPerformance.dpMain
import com.example.knowledgetree.UIMain.DesignPerformance.fragmentContent
import com.example.knowledgetree.UIMain.ExampleDevelopment.EdMain
import com.example.knowledgetree.UIMain.InitialUser.IUContent
import com.example.knowledgetree.UIMain.InitialUser.IUMain
import com.example.knowledgetree.UIMain.Login.loginMain
import com.example.knowledgetree.UIMain.MyInfomation.KtMain
import com.example.knowledgetree.UIMain.Note.NoteMain
import com.example.knowledgetree.UIMain.SaplingMain.SamplingMain
import com.example.knowledgetree.UIMain.Search.SearchMain
import java.util.Collections.list


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Navigation(
    pageName: (String) -> Unit,
    navController: NavHostController,
    onSearchIconClicked: () -> Unit,
    onHeadImageClicked: () -> Unit,
    sendUserInfo: (User) -> Unit,
    ifFirstUse:Boolean
) {
    //切换至阅读
    fun onArticleClicked(article: Article, normal: Boolean = false) {
        if (!normal)
            navController.navigate("${Screen.ArticleScreen.route}/${article.articleId}") {
                launchSingleTop = true
            }
        else
            navController.navigate("${Screen.ArticleNormal.route}/${article.articleId}") {
                launchSingleTop = true
            }
    }

    fun onArticleClicked(articleId: Int, normal: Boolean = false) {
        if (!normal)
            navController.navigate("${Screen.ArticleScreen.route}/${articleId}") {
                launchSingleTop = true
            }
        else
            navController.navigate("${Screen.ArticleNormal.route}/${articleId}") {
                launchSingleTop = true
            }
    }
    //viewModel实例化
    val context = LocalContext.current
    val treeViewModel: TreeViewModel = viewModel(
        factory = TreeViewModelFactory(context.applicationContext as Application)
    )
    //初始化数据库demo
    initialDatabase(treeViewModel)
    //获取屏幕数据
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    //获取viewmodel数据
    //是否阅读
    val ifRead by treeViewModel.ifRead.observeAsState(false)
    //阅读数量
    val readNum by treeViewModel.readNum.observeAsState()
    //是否收藏
    val ifCollect by treeViewModel.readIfCollect.observeAsState()
    //板块总数
    val allNum by treeViewModel.allNum.observeAsState()
    //用户信息（default）
    val userInfo by treeViewModel.userInformation.observeAsState(
        User(
            1,
            "default",
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            -1,
            "default"
        )
    )
    //用户商品
    val getUserCommodity by treeViewModel.readUserCommodity.observeAsState(
        listOf(
            UserGetCommodities("null", 0, false, "null", -1)
        )
    )
    //文章评论
    val articleComments by treeViewModel.readArticleComments.observeAsState(listOf())
    //文章组信息（default）
    val articleGroupInformation by treeViewModel.articleGroupInformation.observeAsState(
        listOf(
            ArticleGroup(1, "default", "default")
        )
    )
    val articleInformation by treeViewModel.articleInformation.observeAsState(
        listOf(
            Article(
                1,
                "default",
                -1,
                "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
                "default",
                "default",
                -1,
                articleGoods = 0
            )
        )
    )
    val bookMarks by treeViewModel.bookMarks.observeAsState(
        listOf(
            GetBookMark(
                0f,
                "null",
                -1,
                "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
                "null",
                "null"
            )
        )
    )
    //获取自测题目
    val getSelfTest = treeViewModel.readSelfTest.observeAsState(
        listOf(
            Exercises(
                0,
                -1,
                "null",
                "null",
                0,
                "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp"
            )
        )
    ).value
    //初始化用户信息
    treeViewModel.getUserById(1)
    sendUserInfo(userInfo)




    NavHost(
        navController = navController,
        startDestination = if (ifFirstUse) Screen.InitialUse.route else Screen.LoginScreen.route
    ) {  //索引集, 初始载入的界面

        //登录界面
        composable(route = Screen.LoginScreen.route) {
            pageName("login") //返回页面名称
            loginMain(
                navController = navController,
                screenWidth = screenWidth
            )
        }

        //知识树
        composable(route = Screen.KtScreen.route) {
            pageName("kt")
            //获取信息阅读百分比
            treeViewModel.getReadHowMany(userInfo.userId, "kt")
            treeViewModel.getSumArticleNum("kt")
            //获取小组标题
            treeViewModel.getArticleGroup("kt")
            KtMain(
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                user = userInfo,
                percentage = treeViewModel.percent,
                recentLearn = "最近学习的内容",
                ktGroup = articleGroupInformation,
                //获取文章
                ktArticles = {
                    //从数据库获取是否阅读
                    treeViewModel.getArticleByAlignId(it)
                    articleInformation
                },
                read = {
                    treeViewModel.getIfReadArticle(userInfo.userId, it)
                    ifRead
                },
                onSearchIconClicked = onSearchIconClicked,
                onHeadImageClicked = onHeadImageClicked,
                onArticleClicked = { onArticleClicked(it, true) })
        }

        //设计表现
        composable(route = Screen.DpScreen.route) {
            pageName("dp")

            treeViewModel.getArticleByPlateName("dp")

            dpMain(
                navController = navController,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                headImageRes = userInfo.headImage,
                articles = articleInformation,
                onSearchIconClicked = onSearchIconClicked,
                onHeadImageClicked = onHeadImageClicked,
                onArticleClicked = { onArticleClicked(it) }
            )
        }

        //框架样式
        composable(route = Screen.DpFragmentScreen.route) {
            pageName("dpFragment")

            treeViewModel.getArticleByPlateName("dpFragment")

            fragmentContent(
                navController,
                screenWidth,
                articleInformation
            )
        }

        //设计字典
        composable(route = Screen.DpDocumentScreen.route) {
            pageName("dpDocument")

            treeViewModel.getArticleByPlateName("dpDocument")
            documentMain(
                navController,
                screenWidth,
                articleInformation
            )
        }

        //ed
        composable(route = Screen.EdScreen.route) {
            pageName("ed")
            treeViewModel.getArticleByPlateName("ed")
            EdMain(
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                articles = articleInformation,
                onSearchButtonClicked = onSearchIconClicked,
                onArticleClicked = { onArticleClicked(it) })
        }

        //普通文章
        composable(
            "${Screen.ArticleNormal.route}/{key}",
            arguments = listOf(navArgument("key") { type = NavType.StringType })
        ) {
            pageName("an")
            val articleId = it.arguments?.getString("key")!!.toInt()
            treeViewModel.getArticleById(articleId)
            fun refreshBookMark() {
                treeViewModel.ifSelectBookMark(userInfo.userId, articleId)
            }
            ANMain(
                ifCollected = false,
                onCollectClicked = {
                    treeViewModel.onCollectClicked(
                        userId = userInfo.userId,
                        articleId = articleId,
                        add = ifCollect == null
                    )
                    refreshBookMark()
                },
                onBackClick = { navController.popBackStack() },
                screenWidth
            )
        }


        //文章
        composable(  //通过route找到对应的screen
            //类似url查询，'/{key}'为参数，和路径一起传进去，通过下面“key”传递
            "${Screen.ArticleScreen.route}/{key}",
            arguments = listOf(navArgument("key") { type = NavType.StringType })
        ) {
            pageName("article")
            val articleId = it.arguments?.getString("key")!!.toInt()
            treeViewModel.getArticleById(articleId)
            treeViewModel.getArticleCommentsByArticleId(articleId)

            fun refreshBookMark() {
                treeViewModel.ifSelectBookMark(userInfo.userId, articleId)
                Log.d("bug1", "刷新：${ifCollect.toString()}")
            }
            refreshBookMark()
            ArticleMain(
                screenWidth = screenWidth,
                navController = navController,
                comments = articleComments,
                onAddNoteConfirmClicked = { comment ->
                    treeViewModel.addUserCommentArticle(
                        listOf(
                            UserCommentArticle(
                                userId = userInfo.userId,
                                articleId = articleId,
                                commentContent = comment,
                                commentTime = "0000.00.00",
                                commentGoods = 0
                            )
                        )
                    )
                    treeViewModel.getArticleCommentsByArticleId(articleId)
                },
                onGoodNoteClicked = { userId ->
                    treeViewModel.addGood(userId = userId, articleId = articleId)
                    treeViewModel.getArticleCommentsByArticleId(articleId)
                },
                article = articleInformation[0], //todo 替换为真实的article，并且添加修改分解语句
                ifCollected = ifCollect != null,
                onCollectClicked = {
                    treeViewModel.onCollectClicked(
                        userId = userInfo.userId,
                        articleId = articleId,
                        add = ifCollect == null
                    )
                    refreshBookMark()
                },
                refresh = { onArticleClicked(articleId) }
            )
        }

        //搜索
        composable(route = Screen.SearchScreen.route) {
            val recentSearch = remember { mutableStateListOf<String>() }
            fun search(name: String) {
                //复写article的值
                treeViewModel.getArticlesByNameLike(name)
            }
            SearchMain(
                screenHeight = screenHeight,
                screenWidth = screenWidth,
                navController = navController,
                onArticleClicked = { onArticleClicked(it) },
                onSearchClicked = { search(it) },
                articles = articleInformation,
                recentSearch = recentSearch,
                clearRecentSearch = { recentSearch.clear() },
                onKeyBoardClick = { recentSearch.add(it) }
            )
        }

        //自测
        composable(
            route = "${Screen.SelfTestScreen.route}/{key}",
            arguments = listOf(navArgument("key") { type = NavType.StringType })
        ) {
            pageName("selfTest")
            val articleId = it.arguments?.getString("key")!!.toInt()
            treeViewModel.getTestsByArticleId(articleId)
            SelfTest(
                navController = navController,
                tests = getSelfTest,
                screenWidth = screenWidth,
            )
        }

        //笔记
        composable(route = Screen.NoteScreen.route) {
            pageName("note")
            treeViewModel.getArticleCommentsByUserId(userInfo.userId)
            NoteMain(navController = navController,
                articleComments,
                screenWidth,
                onArticleClicked = { onArticleClicked(it) })
        }

        //商品
        composable(route = Screen.SamplingScreen.route) {
            treeViewModel.getUserWithCommodity(userInfo.userId)
            pageName("sampling")
            fun onExchangeClicked(commodityId: Int, price: Int) {
                treeViewModel.buyCommodity(
                    userId = userInfo.userId,
                    commodityId,
                    userInfo.integral - price
                )
                treeViewModel.getUserWithCommodity(userInfo.userId)
                treeViewModel.getUserById(1)
            }
            SamplingMain(
                navController,
                screenWidth,
                userInfo.integral,
                getUserCommodity,
            ) { id, price ->
                onExchangeClicked(id, price)
            }
        }

        //书签
        composable(route = Screen.BookMarkScreen.route) {
            pageName("bookMark")
            //bookMark的东东
            treeViewModel.getBookMark(userInfo.userId)
            val bookMarksOfOthers = mutableListOf<GetBookMark>()
            val bookMarksOfKt = mutableListOf<GetBookMark>()
            bookMarks.forEach { bookMark ->
                when (bookMark.plateName) {
                    "kt" -> {
                        bookMarksOfKt.add(bookMark)
                    }
                    else -> {
                        bookMarksOfOthers.add(bookMark)
                    }
                }
            }
            BookmarkMain(
                navController = navController,
                bookMarksOfKt.distinct(),
                bookMarksOfOthers.distinct(),
                screenWidth,
                onArticleClicked = { onArticleClicked(it) }
            )
        }


        //初始登录界面
        composable(route = Screen.InitialUse.route) {
            pageName("iu")

            IUMain(
                screenWidth = screenWidth,
                onStartClick = {
                    navController.navigate(Screen.LoginScreen.route)

                }
            )
        }
    }
}


//主界面@composable
/*  composable(
      //通过route找到对应的screen
      //类似url查询，'/{key}'为参数，和路径一起传进去，通过下面“key”传递
      route = Screen.DetailScreen.route + "/{key}",
      //这几行用来规范初始值
      arguments = listOf(
          //name:索引key并设置参数
          navArgument("key") {
              type = NavType.StringType  //参数类型
              defaultValue = "none"  //默认
              nullable = true  //可空
          }
      )
  ) {
      //it:NavBackStackEntry  打开detail_Screen页面，并传入从route传进来的数据
      //   detail_Screen(it.arguments?.getString("key"))
  }*/


fun initialDatabase(treeViewModel: TreeViewModel) {
    val addUser = listOf(
        User(
            1,
            "name1",
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            1,
            null
        ),
        User(
            2,
            "name2",
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            1,
            null
        ),
        User(
            3,
            "name3",
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            1,
            null
        ),
        User(
            4,
            "name4",
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            1,
            null
        )
    )
    val addMedal = listOf(
        Medal(1, "1", "1"),
        Medal(2, "2", "2"),
        Medal(3, "3", "3")
    )
    val addUserMedal = listOf(
        UserMedalCrossRef(1, 1),
        UserMedalCrossRef(1, 2),
        UserMedalCrossRef(1, 3),
        UserMedalCrossRef(2, 1),
        UserMedalCrossRef(3, 2),
    )
    val addCommodity = listOf(
        Commodity(1, "commodity1", 1, "kt"),
        Commodity(2, "commodity2", 1, "kt"),
        Commodity(3, "commodity3", 2, "kt"),
        Commodity(4, "commodity4", 3, "kt"),
        Commodity(5, "commodity5", 20, "other"),
        Commodity(6, "commodity6", 20, "other"),
        Commodity(7, "commodity7", 20, "other"),
        Commodity(8, "commodity8", 20, "other"),
    )
    val addUserCommodity = listOf(
        UserCommodityCrossRef(1, 1, false),
        UserCommodityCrossRef(1, 2, false),
        UserCommodityCrossRef(1, 3, false),
        UserCommodityCrossRef(1, 4, true),
        UserCommodityCrossRef(1, 5, true),
        UserCommodityCrossRef(1, 6, true),
        UserCommodityCrossRef(1, 7, false),
        UserCommodityCrossRef(1, 8, false),
    )
    val addBookMark = listOf<Bookmark>(
/*        Bookmark(1, 1, 100f),
        Bookmark(1, 2, 0f),
        Bookmark(1, 3, 37f),
        Bookmark(1, 4, 10f),
        Bookmark(1, 26, 40f),
        Bookmark(1, 35, 15f),
        Bookmark(1, 33, 780f),*/
    )
    val addArticle = listOf(
        Article(
            1,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article1",
            "articleContent1",
            1
        ),
        Article(
            2,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article2",
            "articleContent2",
            1
        ),
        Article(
            3,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article3",
            "articleContent3",
            1
        ),
        Article(
            4,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article4",
            "articleContent4",
            2
        ),
        Article(
            5,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article5",
            "articleContent5",
            2
        ),
        Article(
            6,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article6",
            "articleContent6",
            null
        ),
        Article(
            7,
            "dp",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article7",
            "articleContent7",
            null
        ),
        Article(
            8,
            "dp",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article8",
            "articleContent8",
            null
        ),
        Article(
            9,
            "dp",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article9",
            "articleContent9",
            null
        ),
        Article(
            10,
            "dp",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article10",
            "articleContent10",
            null
        ),
        Article(
            11,
            "dp",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article11",
            "articleContent11",
            null
        ),
        Article(
            12,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article12",
            "articleContent12",
            null
        ),
        Article(
            13,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article13",
            "articleContent13",
            null
        ),
        Article(
            14,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article14",
            "articleContent14",
            null
        ),
        Article(
            15,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article15",
            "articleContent15",
            null
        ),
        Article(
            16,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article16",
            "articleContent16",
            null
        ),
        Article(
            17,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article17",
            "articleContent17",
            null
        ),
        Article(
            18,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article18",
            "articleContent18",
            null
        ),
        Article(
            19,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article19",
            "articleContent19",
            null
        ),
        Article(
            20,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article20",
            "articleContent20",
            null
        ),
        Article(
            21,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article21",
            "articleContent21",
            null
        ),
        Article(
            22,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article22",
            "articleContent22",
            null
        ),
        Article(
            23,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article23",
            "articleContent23",
            null
        ),
        Article(
            24,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article24",
            "articleContent24",
            null
        ),
        Article(
            25,
            "dpFragment",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article25",
            "articleContent25",
            null
        ),
        Article(
            26,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article26",
            "articleContent26",
            4
        ),
        Article(
            27,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article27",
            "articleContent27",
            4
        ),
        Article(
            28,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article28",
            "articleContent28",
            4
        ),
        Article(
            29,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article29",
            "articleContent29",
            4
        ),
        Article(
            30,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article30",
            "articleContent30",
            5
        ),
        Article(
            31,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article31",
            "articleContent31",
            5
        ),
        Article(
            32,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article32",
            "articleContent32",
            5
        ),
        Article(
            33,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article33",
            "articleContent33",
            5
        ),
        Article(
            34,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article34",
            "articleContent34",
            5
        ),
        Article(
            35,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article35",
            "articleContent35",
            5
        ),
        Article(
            36,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article36",
            "articleContent36",
            6
        ),
        Article(
            37,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article37",
            "articleContent37",
            6
        ),
        Article(
            38,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article38",
            "articleContent38",
            6
        ),
        Article(
            39,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article39",
            "articleContent39",
            6
        ),
        Article(
            41,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article41",
            "articleContent41",
            7
        ),
        Article(
            42,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article42",
            "articleContent42",
            7
        ),
        Article(
            43,
            "ed",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article43",
            "articleContent43",
            7
        ),
        Article(
            44,
            "ed",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article44",
            "articleContent44",
            7
        ),
        Article(
            45,
            "ed",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article45",
            "articleContent45",
            7
        ),
        Article(
            46,
            "ed",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article46",
            "articleContent46",
            7
        ),
        Article(
            47,
            "ed",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article47",
            "articleContent47",
            7
        ),
        Article(
            48,
            "ed",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article48",
            "articleContent48",
            7
        ),
        Article(
            49,
            "ed",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article49",
            "articleContent49",
            7
        ),
        Article(
            50,
            "ed",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "article50",
            "articleContent50",
            7
        ),
        Article(
            51,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search1other",
            "articleContent41",
            7
        ),
        Article(
            52,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search2other",
            "articleContent42",
            7
        ),
        Article(
            53,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search3other",
            "articleContent43",
            7
        ),
        Article(
            54,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search4other",
            "articleContent44",
            7
        ),
        Article(
            55,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search5other",
            "articleContent45",
            7
        ),
        Article(
            56,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search6kt",
            "articleContent46",
            7
        ),
        Article(
            57,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search7kt",
            "articleContent47",
            7
        ),
        Article(
            58,
            "kt",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search8kt",
            "articleContent48",
            7
        ),
        Article(
            59,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search9other",
            "articleContent49",
            7
        ),
        Article(
            60,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search5other",
            "articleContent50",
            7
        ),
        Article(
            61,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search5other",
            "articleContent51",
            7
        ),
        Article(
            62,
            "dpDocument",
            1,
            "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
            "search5other",
            "articleContent52",
            7
        ),
    )

    val addArticleGroup = listOf(
        ArticleGroup(1, "Group1", "kt"),
        ArticleGroup(2, "Group2", "kt"),
        ArticleGroup(3, "Group3", "kt"),
        ArticleGroup(4, "tips", "dpDocument"),
        ArticleGroup(5, "goodArticles", "dpDocument"),
        ArticleGroup(6, "elements", "dpDocument"),
        ArticleGroup(7, "handbooks", "dpDocument"),
        ArticleGroup(8, "Group8", "dpDocument"),
        ArticleGroup(9, "Group9", "dpDocument"),
    )
    val addExercises = listOf(
        Exercises(
            1,
            1,
            "111",
            "selection1\nselection2\nselection3\nselection4",
            0,

            ),
        Exercises(
            2,
            1,
            "222",
            "selection1\nselection2\nselection3\nselection4",
            0,

            ),
        Exercises(
            3,
            2,
            "333",
            "selection1\nselection2\nselection3\nselection4",
            0,

            ),
        Exercises(
            exercisesId = 3,
            articleId = 1,
            exerciseQuestion = "444",
            exerciseSelection = "selection1\nselection2\nselection3\nselection4",
            exerciseAnswer = 0,

            )
    )
    val addUserCommentArticle = listOf(
        UserCommentArticle(1, 1, "111111", "2021", 50),
        UserCommentArticle(2, 1, "222222", "2021", 50),
        UserCommentArticle(3, 1, "333333", "2021", 50),
    )


    treeViewModel.addUser(addUser)
    treeViewModel.addMedal(addMedal)
    treeViewModel.addUserMedal(addUserMedal)

    treeViewModel.addCommodity(addCommodity)
    treeViewModel.addUserCommodity(addUserCommodity)
    treeViewModel.addArticle(addArticle)
    treeViewModel.addExercises(addExercises)
    treeViewModel.addUserCommentArticle(addUserCommentArticle)
    treeViewModel.addArticleGroup(addArticleGroup)
    treeViewModel.addBookMark(addBookMark)


}
