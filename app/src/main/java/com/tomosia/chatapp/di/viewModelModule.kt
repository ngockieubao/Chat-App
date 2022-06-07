package com.tomosia.chatapp.di

import com.tomosia.chatapp.ui.home.chat.ChatViewModel
import com.tomosia.chatapp.ui.home.contact.ContactViewModel
import com.tomosia.chatapp.ui.login.LoginRegistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginRegistViewModel() }
    viewModel { ChatViewModel() }
    viewModel { ContactViewModel() }
}