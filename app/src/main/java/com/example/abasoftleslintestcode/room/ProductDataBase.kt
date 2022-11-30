package com.example.abasoftleslintestcode.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ProductList::class,Cart::class], version = 1, exportSchema = false)
abstract class ProductDataBase: RoomDatabase() {

    abstract fun productDao(): ProductDao


    companion object {
        val DATABASE_NAME: String = "product_db"

        @Volatile
        private var INSTANCE: ProductDataBase? = null


        fun getDataBase(context: Context, scope: CoroutineScope): ProductDataBase {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDataBase::class.java, DATABASE_NAME
                )
                        .addCallback(ProductDatabaseCallBack(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }


    }

    private class ProductDatabaseCallBack(val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE.let { database ->
                scope.launch {
                    if (database != null) {
                        populateDatabase(database.productDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(dao: ProductDao) {

        }
    }
}




//@Database(entities = arrayOf(Member::class), version = 1, exportSchema = false)
//abstract class MemberDatabase : RoomDatabase() {
//
//    abstract fun memberDao(): MemberDao
//    companion object {
//        @Volatile
//        private var INSTANCE: MemberDatabase? = null
//
//        fun getDataBase(context: Context, scope: CoroutineScope) : MemberDatabase
//        {
//            return INSTANCE ?: synchronized(this)
//            {
//                val instance= Room.databaseBuilder(context.applicationContext,
//                    MemberDatabase::class.java,"member_database")
//                    .addCallback(MemberDatabaseCallBack(scope))
//                    .build()
//
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//
//    private class MemberDatabaseCallBack(val scope: CoroutineScope) : RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//
//            INSTANCE.let { database ->
//                scope.launch {
//                    if (database != null) {
//                        populateDatabase(database.memberDao())
//                    }
//                }
//            }
//        }
//
//        suspend fun populateDatabase(memberDao: MemberDao) {
//
////            memberDao.deleteAll()
////
////            var member = Member("1","test","test","","","","","","",
////                "","","","","")
////            memberDao.insert(member)
//
//        }
//    }