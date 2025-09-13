package com.allenth17.casestudy2.nav

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object MainGraph : Destination

    @Serializable
    data object HomeScreen : Destination

    @Serializable
    data class  UserDetailScreen(
        val id: Int
    ) : Destination

}