package com.example.knowledgetree

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.media.audiofx.BassBoost
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.knowledgetree.Database.*
import com.example.knowledgetree.Database.viewModel.TreeViewModel
import com.example.knowledgetree.Database.viewModel.TreeViewModelFactory
import com.example.knowledgetree.Navigation.initialDatabase
import com.example.knowledgetree.UIMain.Articles.ArticleUI
import com.example.knowledgetree.UIMain.Articles.SelfTest

import com.example.knowledgetree.UIMain.DesignPerformance.documentMain
import com.example.knowledgetree.UIMain.MainUI
import com.example.knowledgetree.UIMain.Note.ArticleInKtCard
import com.example.knowledgetree.UIMain.Search.searchTextField


import com.example.knowledgetree.ui.theme.KnowledgeTreeTheme

import kotlinx.coroutines.launch

//获取屏幕大小和context
val articlesDemo = listOf(
    Article(
        1,
        "kt",
        1,
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        "article1",
        "articleContent1",
        1,
        "0000",
        "",
        0
    ),
    Article(
        2,
        "kt",
        1,
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        "article2",
        "articleContent2",
        1,
        "0000",
        "",
        0
    ),
    Article(
        3,
        "kt",
        1,
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        "article3",
        "articleContent3",
        1,
        "0000",
        "",
        0
    ),
    Article(
        4,
        "kt",
        1,
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        "article4",
        "articleContent4",
        2,
        "0000",
        "",
        0
    ),
    Article(
        5,
        "kt",
        1,
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        "article5",
        "articleContent4",
        2,
        "0000",
        "",
        0
    ),
    Article(
        6,
        "ed",
        1,
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        "article6",
        "articleContent4",
        2,
        "0000",
        "",
        0
    ),
    Article(
        7,
        "ed",
        1,
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        "article7",
        "articleContent4",
        2,
        "0000",
        "",
        0
    ),
    Article(
        8,
        "ed",
        1,
        "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
        "article8",
        "articleContent4",
        2,
        "0000",
        "",
        0
    ),
)

class MainActivity : ComponentActivity() {

    var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("com.example.knowledgetree", MODE_PRIVATE)
        var ifFirstUse= prefs!!.getBoolean("firstrun",true)
        setContent {
            //   testDatabase()
            KnowledgeTreeTheme {
                val configuration = LocalConfiguration.current
                val screenHeight = configuration.screenHeightDp
                val screenWidth = configuration.screenWidthDp
                MainUI(ifFirstUse =ifFirstUse)


                //PagerSampleItem()

                /* ArticleUI({}, screenWidth = screenWidth, comments = listOf(
                     getArticleComments("name",
                         commentContent = "1. 在manifest文件中通过android:parentActivityName属性为Activity配置parent activity\n" +
                                 "2. 在代码中通过ActionBar.setDisplayHomeAsUpEnabled(true)方法使能导航按钮\n" +
                                 "3. 在代码中通过ActionBar.setDisplayHomeAsUpEnabled(true)方法使能导航按钮",
                         commentTime = "0000.00.00",
                         commentGoods = 50)
                 ), {},{})*/
                //  SelfTest(tests = addExercises, screenWidth = screenWidth,navController = )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (prefs!!.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs!!.edit().putBoolean("firstrun", false).apply()
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KnowledgeTreeTheme {
        Greeting("Android")
    }
}