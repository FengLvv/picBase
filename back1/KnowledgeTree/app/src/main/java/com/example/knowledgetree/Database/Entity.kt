package com.example.knowledgetree.Database

import androidx.room.*
import com.example.knowledgetree.R

//用户
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int,
    var name: String,
    var headImage: String="https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
    var integral: Int,
    var userDetail: String?,
)

//奖章
@Entity
data class Medal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    var medalId: Int,
    var medalType: String,
    var medalName: String,
)

//用户奖章交互表
@Entity(primaryKeys = ["userId", "medalId"])
data class UserMedalCrossRef(
    val userId: Int,
    @ColumnInfo(index = true)
    val medalId: Int,
)

//查询接受类
data class UserGetMedals(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "medalId",
        associateBy = Junction(UserMedalCrossRef::class)
    )
    val medal: List<Medal>,
)


//商品
@Entity
data class Commodity(
    @PrimaryKey
    var commodityId: Int,
    var commodityContent: String,
    var commodityPrice: Int,
    var commodityPlate:String
)

//用户商品交互
@Entity(primaryKeys = ["userId", "commodityId"])
data class UserCommodityCrossRef(
    var userId: Int,
    @ColumnInfo(index = true)
    var commodityId: Int,
    var userUnlockCommodity: Boolean=false
    )

//用户商品接受类
data class UserGetCommodities(
    var commodityContent: String,
    var commodityPrice: Int,
    var userUnlockCommodity: Boolean,
    var commodityPlate: String,
    var commodityId: Int
)

//文章
@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    var articleId: Int,
    var plateName: String,
    var writeUserId: Int,
    var imageRes: String,
    var articleName: String,
    var articleContent: String,
    var articleAlignGroupId: Int?,
    var articleData: String = "0000,00,00",
    var articleDescription: String = "Article default description",
    var articleGoods: Int = 0,
)

//文章组
@Entity
data class ArticleGroup(
    @PrimaryKey(autoGenerate = true)
    var articleGroupId: Int,
    var articleGroupName: String,
    var plateName: String,
)


@Entity
data class Exercises(
    @PrimaryKey(autoGenerate = true)
    var exercisesId: Int,
    var articleId: Int,
    var exerciseQuestion: String,
    var exerciseSelection: String,
    var exerciseAnswer: Int,
    var exercisePicRes: String="https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
    var exerciseReason: String = "无"
)

//文章被多个用户评论，用户评论多个文章
@Entity(primaryKeys = ["userId", "articleId"])
data class UserCommentArticle(
    var userId: Int,
    val articleId: Int,
    val commentContent: String,
    val commentTime: String,
    var commentGoods: Int,
)

//文章的评论
data class getArticleComments(
    var name: String,
    var headImage: String="https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
    var commentContent: String,
    var commentTime: String,
    var commentGoods: Int,
    var userId: Int,
    var articleId: Int,
    var articleName: String,
    var articleDescription: String,

    )

//收藏
@Entity(primaryKeys = ["userId", "articleId"])
data class Collect(
    var userId: Int,
    val articleId: Int,
    val collectTime: String,
)

//收藏夹内容
data class userCollectContent(
    var collectTime: String,
    val articleContent: String,
    val articleName: String,
)


//最近浏览
@Entity(primaryKeys = ["userId", "articleId"])
data class View(
    var userId: Int,
    val articleId: Int,
    val viewTime: String,
)

//书签
@Entity(primaryKeys = ["userId", "articleId"])
data class Bookmark(
    var userId: Int,
    val articleId: Int,
    val bookmarkRate: Float,
)

data class GetBookMark(
    var bookmarkRate: Float,
    var articleName: String,
    var articleId: Int,
    var imageRes: String,
    var articleDescription: String,
    var plateName: String
)

@Entity(primaryKeys = ["userId", "articleId"])
data class UserReadArticle(
    var userId: Int,
    val articleId: Int,
)





