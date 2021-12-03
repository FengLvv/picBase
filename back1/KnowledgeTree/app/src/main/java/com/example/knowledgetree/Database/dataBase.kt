package com.example.knowledgetree.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class,
        Medal::class,
        UserMedalCrossRef::class,
        Commodity::class,
        UserCommodityCrossRef::class,
        Article::class,
        Exercises::class,
        UserCommentArticle::class,
        Collect::class,
        Bookmark::class,
        ArticleGroup::class,
        UserReadArticle::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TreeDatabase : RoomDatabase() {
    abstract fun treeDao(): treeDao
    companion object {
        @Volatile
        private var INSTANCE: TreeDatabase? = null
        fun getInstance(context: Context): TreeDatabase {
            val treeInstance = INSTANCE
            if (treeInstance != null) {
                return treeInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TreeDatabase::class.java,
                    "tree_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}