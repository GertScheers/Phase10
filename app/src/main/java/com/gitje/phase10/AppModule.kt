package com.gitje.phase10

import com.gitje.phase10.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    this.viewModel { MainViewModel(androidApplication()) }
}