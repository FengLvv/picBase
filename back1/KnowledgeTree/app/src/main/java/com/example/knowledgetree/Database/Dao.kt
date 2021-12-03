package com.example.knowledgetree.Database

import androidx.room.*

@Dao
interface treeDao {
    //用户
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(item: List<User>)

    //奖章
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMedal(item: List<Medal>)

    //书签
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookMark(item: List<Bookmark>)

    //交叉表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserMedal(item: List<UserMedalCrossRef>)


    //商品
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCommodity(item: List<Commodity>)

    //用户商品交叉
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserCommodity(item: List<UserCommodityCrossRef>)

    //文章
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(item: List<Article>)

    //文章
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticleGroup(item: List<ArticleGroup>)

    //题目
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExercises(item: List<Exercises>)

    //评论
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserCommentArticle(item: List<UserCommentArticle>)

    //阅读记录
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserReadArticle(readRecord: List<UserReadArticle>)

    /*---------------------查询---------------------------------------*/
    //用userid查询用户的奖章
    @Transaction
    @Query("SELECT * FROM USER WHERE UserId LIKE :userId")
    fun getUserWithMedals(userId: Int): List<UserGetMedals>

    //用userid查询用户购买的商品
    @Transaction
    @Query(
        "SELECT Commodity.commodityContent,commodityPrice,UserCommodityCrossRef.userUnlockCommodity,commodityPlate,Commodity.commodityId " +
                "FROM UserCommodityCrossRef,Commodity " +
                "WHERE  UserCommodityCrossRef.commodityId=Commodity.commodityId" +
                " AND UserCommodityCrossRef.userId LIKE :userId"
    )
    fun getUserWithCommodity(userId: Int): List<UserGetCommodities>

    //UserId查询用户信息
    @Query("SELECT * FROM USER WHERE UserId LIKE :userId")
    fun getUserById(userId: Int): User

    @Transaction
    @Query(
        "SELECT user.name,user.headImage,UserCommentArticle.commentContent," +
                "UserCommentArticle.commentTime," +
                "UserCommentArticle.commentGoods," +
                "user.userId,article.articleId," +
                "article.articleName,article.articleDescription" +
                " from User , UserCommentArticle , Article " +
                "where user.userId=UserCommentArticle.userId " +
                "AND UserCommentArticle.articleId=article.articleId " +
                "and article.articleId = :Id" +
                " ORDER BY UserCommentArticle.commentGoods DESC "
    )
    fun getArticleCommentsByArticleId(Id: Int): List<getArticleComments>

    @Transaction
    @Query(
        "SELECT user.name,user.headImage,UserCommentArticle.commentContent," +
                "UserCommentArticle.commentTime," +
                "UserCommentArticle.commentGoods," +
                "user.userId,article.articleId," +
                "article.articleName,article.articleDescription" +
                " from User , UserCommentArticle , Article " +
                "where user.userId=UserCommentArticle.userId " +
                "AND UserCommentArticle.articleId=article.articleId " +
                "and article.articleId = :Id" +
                " ORDER BY UserCommentArticle.commentGoods DESC "
    )
    fun getArticleCommentsByUserId(Id: Int): List<getArticleComments>


    @Transaction
    @Query(
        "SELECT Collect.collectTime,article.articleContent,article.articleName" +
                " from Collect , Article " +
                "where Collect.articleId=Article.articleId " +
                "and collect.userId = :Id "
    )
    fun getUserCollects(Id: Int): List<userCollectContent>

    @Transaction
    @Query("SELECT * FROM Article WHERE articleAlignGroupId=:id ORDER BY articleId")
    fun getArticleByAlignId(id: Int): List<Article>

    @Transaction
    @Query("SELECT * FROM ArticleGroup WHERE plateName=:plateName ORDER BY articleGroupId")
    fun getArticleGroup(plateName: String): List<ArticleGroup>

    @Transaction
    @Query("SELECT * FROM Article WHERE plateName=:plateName ORDER BY plateName")
    fun getArticleByPlateName(plateName: String): List<Article>

    @Transaction
    @Query("SELECT * FROM Article WHERE articleId=:articleId")
    fun getArticleById(articleId: Int): Article

    @Transaction
    @Query("SELECT 1 FROM UserReadArticle WHERE userId=:userId AND articleId=:articleId ")
    fun getIfReadArticle(userId: Int, articleId: Int): Boolean

    @Transaction
    @Query("SELECT COUNT(articleId)  FROM UserReadArticle WHERE userId=:userId AND articleId in (SELECT articleId From ARTICLE WHERE plateName=:plateName )")
    fun getReadHowMany(userId: Int, plateName: String): Int

    @Transaction
    @Query("SELECT COUNT(articleId) FROM ARTICLE WHERE plateName=:plateName")
    fun getSumArticleNum(plateName: String): Int

    @Transaction
    @Query("SELECT COUNT(userId) FROM UserReadArticle WHERE articleId=:articleId")
    fun getViewNumByArticleId(articleId: Int): Int

    @Transaction
    @Query("SELECT * FROM exercises WHERE articleId=:articleId ORDER BY exercisesId")
    fun getTestsByArticleId(articleId: Int): List<Exercises>

    @Transaction
    @Query("SELECT * FROM Article WHERE articleName LIKE '%' || :articleName || '%'")
    fun getArticlesByNameLike(articleName: String): List<Article>


    @Transaction
    @Query("UPDATE UserCommentArticle SET commentGoods=commentGoods+1 WHERE userId=:userId and articleId=:articleId")
    fun addGood(userId: Int, articleId: Int)

    @Transaction
    @Query(
        "SELECT Bookmark.bookmarkRate,articleName,article.articleId,article.imageRes," +
                "articleDescription,article.plateName FROM Bookmark,article WHERE BOOKMARK.userId =:userId and Bookmark.articleId=article.articleId"
    )  //todo 交互表
    fun getBookMark(userId: Int): List<GetBookMark>


    @Query("UPDATE USER SET integral= :newSampling WHERE userId=:userId")
    fun changeUserSampling(newSampling: Int, userId: Int)

    @Query("UPDATE usercommoditycrossref SET userUnlockCommodity=1  WHERE userId=:userId AND commodityId=:commodityId")
    fun changeCommodityUnlocked(userId: Int, commodityId: Int)


    @Transaction
    fun buyCommodity(userId: Int, commodityId: Int, newSampling: Int) {
        changeUserSampling(newSampling, userId)
        changeCommodityUnlocked(userId, commodityId)
    }

    @Query("DELETE FROM Bookmark WHERE Bookmark.articleId=:articleId and userId=:userId")
    fun deleteBookMark(articleId: Int, userId: Int)

    @Transaction
    suspend fun onCollectClicked(userId: Int, articleId: Int, add: Boolean) {
        if (add) addBookMark(listOf(Bookmark(userId, articleId, 100f)))
        else deleteBookMark(userId=userId, articleId = articleId)
    }

    @Query("SELECT 1 FROM Bookmark WHERE articleId=:articleId AND userId=:userId")
    fun ifSelectBookMark(articleId: Int, userId: Int):Boolean?
}

