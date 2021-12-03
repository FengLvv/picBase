package com.example.knowledgetree.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter



//临时充当数据库
data class interactionData(
    val title : String,
    val browse : Int ,
    val like : Int,
    val collect : Int,
    val date : String,
    val pictureRes : Int,
    val writer : String,
    val profile : Int
)

data class mainBottomNav(
    val pictureRes: Int,
    val stringRes: Int
)

data class Profile(
    val profileId : Int,
    val name : String,
    val medalsRes: List<Int>
)

data class iconText(
    val icon : Int,
    val text : String
)