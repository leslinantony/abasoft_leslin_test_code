package com.example.abasoftleslintestcode.application

import android.app.Application
import com.example.abasoftleslintestcode.productList.ProductListRepository
import com.example.abasoftleslintestcode.retrofit.ApiInterface
import com.example.abasoftleslintestcode.room.ProductDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ProductDataBase.getDataBase(this, applicationScope) }
    val productListRepository by lazy { ProductListRepository(database.productDao(),)  }
//    val loanDetailsRepository by lazy {
//        com.finwin.doorstep.digicob.home.jlg.jlg_loan_creation.jlg_loan_details.JlgLoanDetailsRepository(
//            database.memberDao()
//        )
//    }
}