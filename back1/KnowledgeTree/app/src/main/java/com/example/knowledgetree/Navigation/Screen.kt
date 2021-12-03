package com.example.knowledgetree.Navigation

//密封类Screen（不可实例化），储存导航的“地址”参数为屏幕的名字
sealed class Screen(val route: String) {
    //单例类继承父类，传入名字
    object LoginScreen : Screen("login_screen")
    object KtScreen : Screen("kt_screen")
    object DpScreen : Screen("dp_screen")
    object EdScreen : Screen("ed_screen")
    object DpFragmentScreen : Screen("dp_fragment_screen")
    object DpDocumentScreen : Screen("dp_document_screen")
    object SearchScreen : Screen("search_screen")
    object ArticleScreen : Screen("article_screen")
    object SelfTestScreen : Screen("self_test_screen")
    object NoteScreen : Screen("note_screen")
    object BookMarkScreen : Screen("bookmark_screen")
    object SamplingScreen : Screen("sampling_screen")
    object ArticleNormal : Screen("article_normal_screen")
    object InitialUse : Screen("initial_user_screen")

    fun withArgs(vararg args: String): String {  //传入多个字符串，把他们串在一起
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }
}
