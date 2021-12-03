package com.example.knowledgetree.Database

import androidx.room.Query
import androidx.room.Transaction


class TreeRepository(private val treeDao: treeDao) {
    suspend fun addUser(item: List<User>) {
        treeDao.addUser(item = item)
    }

    suspend fun addMedal(item: List<Medal>) {
        treeDao.addMedal(item = item)
    }

    suspend fun addBookMark(item: List<Bookmark>) {
        treeDao.addBookMark(item = item)
    }


    suspend fun addUserMedal(item: List<UserMedalCrossRef>) {
        treeDao.addUserMedal(item = item)
    }


    suspend fun addCommodity(item: List<Commodity>) {
        treeDao.addCommodity(item = item)
    }

    suspend fun addUserCommodity(item: List<UserCommodityCrossRef>) {
        treeDao.addUserCommodity(item = item)
    }

    suspend fun addArticle(item: List<Article>) {
        treeDao.addArticle(item = item)
    }


    suspend fun addExercises(item: List<Exercises>) {
        treeDao.addExercises(item = item)
    }

    suspend fun addUserReadArticle(readRecord: List<UserReadArticle>) {
        treeDao.addUserReadArticle(readRecord)
    }

    suspend fun addUserCommentArticle(item: List<UserCommentArticle>) {
        treeDao.addUserCommentArticle(item = item)
    }

    suspend fun addArticleGroup(item: List<ArticleGroup>) {
        treeDao.addArticleGroup(item)
    }

    fun getArticleCommentsByArticleId(articleId: Int): List<getArticleComments> {
        return treeDao.getArticleCommentsByArticleId(articleId)
    }

    fun getArticleCommentsByUserId(userId: Int): List<getArticleComments> {
        return treeDao.getArticleCommentsByUserId(userId)
    }

    fun getUserCollects(Id: Int): List<userCollectContent> {
        return treeDao.getUserCollects(Id)
    }

    fun getUserWithCommodity(userId: Int): List<UserGetCommodities> {
        return treeDao.getUserWithCommodity(userId)
    }

    fun getUserWithMedals(userId: Int): List<UserGetMedals> {
        return treeDao.getUserWithMedals(userId)
    }

    fun getUserById(userId: Int): User {
        return treeDao.getUserById(userId)
    }

    fun getArticleByAlignId(articleAlignGroupId: Int): List<Article> {
        return treeDao.getArticleByAlignId(articleAlignGroupId)
    }

    fun getArticleGroup(plateName: String): List<ArticleGroup> {
        return treeDao.getArticleGroup(plateName)
    }


    fun getArticleByPlateName(plateName: String): List<Article> {
        return treeDao.getArticleByPlateName(plateName)
    }

    fun getIfReadArticle(userId: Int, articleId: Int): Boolean {
        return treeDao.getIfReadArticle(userId, articleId)
    }

    fun getReadHowMany(userId: Int, plateName: String): Int {
        return treeDao.getReadHowMany(userId, plateName)
    }

    fun getSumArticleNum(plateName: String): Int {
        return treeDao.getSumArticleNum(plateName)
    }

    fun getViewNumByArticleId(articleId: Int): Int {
        return treeDao.getViewNumByArticleId(articleId)
    }

    fun getArticlesByNameLike(articleName: String): List<Article> {
        return treeDao.getArticlesByNameLike(articleName)
    }

    fun addGood(userId: Int, articleId: Int) {
        return treeDao.addGood(userId = userId, articleId = articleId)
    }

    fun getArticleById(articleId: Int): Article {
        return treeDao.getArticleById(articleId = articleId)
    }

    fun getTestsByArticleId(articleId: Int): List<Exercises> {
        return treeDao.getTestsByArticleId(articleId = articleId)
    }

    fun getBookMark(userId: Int): List<GetBookMark> {
        return treeDao.getBookMark(userId)
    }

    fun buyCommodity(userId: Int, commodityId: Int, newSampling: Int) {
        treeDao.buyCommodity(userId, commodityId, newSampling)
    }


    fun ifSelectBookMark(userId: Int, articleId: Int):Boolean? {
        return treeDao.ifSelectBookMark(userId = userId,articleId = articleId)
    }




    suspend fun onCollectClicked(userId: Int, articleId: Int, add: Boolean) {
        treeDao.onCollectClicked(userId=userId,articleId = articleId,add=add)
    }


}
