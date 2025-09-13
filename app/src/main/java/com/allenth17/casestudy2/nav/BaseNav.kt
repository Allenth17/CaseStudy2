package com.allenth17.casestudy2.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.allenth17.casestudy2.screen.HomeScreen
import com.allenth17.casestudy2.screen.UserDetailScreen
import com.allenth17.casestudy2.screen.UserViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun BaseNav() {
    val navController = rememberNavController()
    val navigator = koinInject<Navigator>()
    val viewModel = koinViewModel<UserViewModel>()

    ObserveAsEvents(
        events = navigator.navigationActions
    ) { action ->
        when (action) {
            is NavigationAction.Navigate -> {
                navController.navigate(action.destination) { action.navOptions(this) }
            }

            is NavigationAction.NavigateUp -> {
                navController.navigateUp()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startDestination
    ) {
        navigation<Destination.MainGraph>(
            startDestination = Destination.HomeScreen
        ) {
            composable<Destination.HomeScreen> {
                HomeScreen(
                    viewModel,
                    onUserClick = { user ->
                        viewModel.goToDetail(user.id)
                    }
                )
            }
            composable<Destination.UserDetailScreen> { bse ->
                val userId = bse.toRoute<Destination.UserDetailScreen>().id
                UserDetailScreen(
                    id = userId,
                    viewModel = viewModel
                )
            }
        }
    }
}