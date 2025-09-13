package com.allenth17.casestudy2.di

import com.allenth17.casestudy2.nav.DefaultNavigator
import com.allenth17.casestudy2.nav.Destination
import com.allenth17.casestudy2.nav.Navigator
import com.allenth17.casestudy2.screen.UserViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::UserViewModel)
    single <Navigator> {
        DefaultNavigator(
            startDestination = Destination.MainGraph
        )
    }
}