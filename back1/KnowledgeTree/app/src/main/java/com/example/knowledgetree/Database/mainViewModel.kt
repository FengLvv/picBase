package com.example.knowledgetree.Database.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.knowledgetree.Database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//
class TreeViewModel(application: Application) : AndroidViewModel(application) {
    //实时获取用户奖章
    private val _readUserMedal = MutableLiveData<List<UserGetMedals>>()
    var readUserMedal: LiveData<List<UserGetMedals>> = _readUserMedal

    //实时获取用户奖章
    private val _readSelfTest = MutableLiveData<List<Exercises>>()
    var readSelfTest: LiveData<List<Exercises>> = _readSelfTest

    //实时获取用户商品
    private val _readUserCommodity = MutableLiveData<List<UserGetCommodities>>()
    var readUserCommodity: LiveData<List<UserGetCommodities>> = _readUserCommodity

    //实时获取文章评论
    private val _readArticleComments = MutableLiveData<List<getArticleComments>>()
    var readArticleComments: LiveData<List<getArticleComments>> = _readArticleComments

    //实时获取收藏夹
    private val _readUserCollect = MutableLiveData<List<userCollectContent>>()
    var readUserCollect: LiveData<List<userCollectContent>> = _readUserCollect

    //是否收藏
    private val _readIfCollect = MutableLiveData(false)
    var readIfCollect = _readIfCollect


    //实时获取用户信息
    private val _userInformation = MutableLiveData<User>()
    var userInformation: LiveData<User> = _userInformation

    //实时获取文章信息
    private val _articleInformation = MutableLiveData<List<Article>>()
    var articleInformation: LiveData<List<Article>> = _articleInformation

    //实时获取文章信息
    private val _bookMarks = MutableLiveData<List<GetBookMark>>()
    var bookMarks: LiveData<List<GetBookMark>> = _bookMarks

    //清空文章信息
    fun clearArticleInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            _articleInformation.postValue(listOf())
        }
    }

    //实时获取文章组信息
    private val _articleGroupInformation = MutableLiveData<List<ArticleGroup>>()
    var articleGroupInformation: LiveData<List<ArticleGroup>> = _articleGroupInformation

    //清空文章组信息
    fun clearArticleGroupInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            _articleGroupInformation.postValue(listOf())
        }
    }

    //是否读过文章
    val ifRead = MutableLiveData<Boolean>(false)

    //读过的文章总数
    val readNum = MutableLiveData<Int>(0)

    //板块文章总数
    val allNum = MutableLiveData<Int>(0)

    //文章阅读总数
    val articleReadNum = MutableLiveData<List<Int>>(listOf())

    val read = readNum.value ?: 0
    val all = allNum.value ?: 0
    val percent = if (all == 0) 100f else {
        read.toFloat() / all.toFloat()
    }


    //初始化repository
    private val repository: TreeRepository

    init {
        val treeDao = TreeDatabase.getInstance(application).treeDao()
        repository = TreeRepository(treeDao)
    }

    //用户
    fun addUser(item: List<User>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(item = item)
        }
    }

    //奖章
    fun addMedal(item: List<Medal>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMedal(item = item)
        }
    }


    //用户奖章交叉表
    fun addUserMedal(item: List<UserMedalCrossRef>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserMedal(item = item)
        }
    }

    //用户奖章交叉表
    fun addBookMark(item: List<Bookmark>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBookMark(item = item)
        }
    }


    //用户奖章交互查询
    fun getUserWithMedals(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _readUserMedal.postValue(repository.getUserWithMedals(userId = userId))
        }
    }


    //商品
    fun addCommodity(item: List<Commodity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCommodity(item = item)
        }
    }


    //商品
    fun buyCommodity(userId: Int, commodityId: Int, newSampling: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.buyCommodity(userId, commodityId, newSampling)
        }
    }


    //用户商品交叉表
    fun addUserCommodity(item: List<UserCommodityCrossRef>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserCommodity(item = item)
        }
    }

    //文章
    fun addArticle(item: List<Article>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addArticle(item = item)
        }
    }

    //文章
    fun addArticleGroup(item: List<ArticleGroup>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addArticleGroup(item = item)
        }
    }

    //用户阅读文章
    fun addUserReadArticle(readRecord: List<UserReadArticle>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserReadArticle(readRecord)
        }
    }

    //评论
    fun addUserCommentArticle(item: List<UserCommentArticle>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserCommentArticle(item = item)
        }
    }

    //题目
    fun addExercises(item: List<Exercises>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExercises(item = item)
        }
    }

    //用户笔记交互查询
    fun getUserWithCommodity(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _readUserCommodity.postValue(repository.getUserWithCommodity(userId = userId))
        }
    }

    //文章评论
    fun getArticleCommentsByArticleId(articleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _readArticleComments.postValue(repository.getArticleCommentsByArticleId(articleId))
        }
    }

    //文章评论
    fun getArticleCommentsByUserId(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _readArticleComments.postValue(repository.getArticleCommentsByUserId(userId))
        }
    }

    //用户收藏
    fun getUserCollects(Id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _readUserCollect.postValue(repository.getUserCollects(Id))
        }
    }

    //自测
    fun getTestsByArticleId(articleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _readSelfTest.postValue(repository.getTestsByArticleId(articleId))
        }
    }

    //获取用户信息
    fun getUserById(Id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _userInformation.postValue(repository.getUserById(Id))
        }
    }

    //由文章组获取子文章信息
    fun getArticleByAlignId(articleGroupId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _articleInformation.postValue(repository.getArticleByAlignId(articleGroupId))
        }
    }

    //由板块获取文章组
    fun getArticleGroup(plateName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _articleGroupInformation.postValue(repository.getArticleGroup(plateName))
        }
    }

    //由板块获取文章信息
    fun getArticleByPlateName(plateName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _articleInformation.postValue(repository.getArticleByPlateName(plateName))
        }
    }

    //查询是否已经阅读
    fun getIfReadArticle(userId: Int, articleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            ifRead.postValue(repository.getIfReadArticle(userId, articleId))
        }
    }

    //查询阅读数量
    fun getReadHowMany(userId: Int, plateName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            readNum.postValue(repository.getReadHowMany(userId, plateName))
        }
    }

    //查询板块总数
    fun getSumArticleNum(plateName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            allNum.postValue(repository.getSumArticleNum(plateName))
        }
    }

    //字符串查询
    fun getArticlesByNameLike(articleName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _articleInformation.postValue(repository.getArticlesByNameLike(articleName))
        }
    }

    //根据文章id获取阅读数量
    fun getViewNumByArticleId(articleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            articleReadNum.postValue(
                articleReadNum.value!! +
                        repository.getViewNumByArticleId(articleId)
            )
        }
    }

    //添加评论点赞数量
    fun addGood(userId: Int, articleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGood(userId = userId, articleId = articleId)
        }
    }


    //获取文章
    fun getArticleById(articleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _articleInformation.postValue(listOf(repository.getArticleById(articleId = articleId)))
        }
    }

    //获取文章
    fun getBookMark(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _bookMarks.postValue(repository.getBookMark(userId = userId))
        }
    }


    //获取文章
    fun onCollectClicked(userId: Int, articleId: Int, add: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.onCollectClicked(userId = userId, articleId = articleId, add = add)
        }
    }


    //获取是否收藏
    fun ifSelectBookMark(userId: Int, articleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _readIfCollect.postValue(
                repository.ifSelectBookMark(
                    userId = userId,
                    articleId = articleId
                )
            )
        }
    }
}

class TreeViewModelFactory(
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TreeViewModel::class.java)) {
            return TreeViewModel(application) as T
        }
        throw IllegalStateException("Unknown ViewModel class")
    }
}
