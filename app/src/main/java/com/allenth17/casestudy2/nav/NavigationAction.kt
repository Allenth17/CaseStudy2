package com.allenth17.casestudy2.nav

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {
    data class Navigate (
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction
    data object NavigateUp : NavigationAction
}
