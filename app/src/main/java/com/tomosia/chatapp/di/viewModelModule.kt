package com.tomosia.chatapp.di

import com.tomosia.chatapp.viewmodel.LoginRegistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginRegistViewModel() }
}