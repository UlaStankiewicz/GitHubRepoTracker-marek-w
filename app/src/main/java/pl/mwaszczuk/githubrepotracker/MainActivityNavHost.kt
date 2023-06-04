package pl.mwaszczuk.githubrepotracker

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import pl.mwaszczuk.githubrepotracker.design.anims.*
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui.REPOSITORY_DETAILS_ROUTE
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui.RepositoryDetailsScreen
import pl.mwaszczuk.githubrepotracker.reposearch.search.ui.SEARCH_SCREEN_ROUTE
import pl.mwaszczuk.githubrepotracker.reposearch.search.ui.SearchScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainActivityNavigation() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = SEARCH_SCREEN_ROUTE
    ) {
        composable(
            route = SEARCH_SCREEN_ROUTE,
            enterTransition = slideIntoContainerAnimRight(),
            popEnterTransition = slideIntoContainerAnimRight(),
            exitTransition = slideOutOfContainerAnimLeft(),
            popExitTransition = slideOutOfContainerAnimLeft()
        ) {
            SearchScreen(navController = navController)
        }

        composable(
            route = REPOSITORY_DETAILS_ROUTE + "/{repoOwner}/{repoName}/{repoId}",
            enterTransition = slideIntoContainerAnimLeft(),
            popEnterTransition = slideIntoContainerAnimLeft(),
            exitTransition = slideOutOfContainerAnimRight(),
            popExitTransition = slideOutOfContainerAnimRight(),
            arguments = listOf(
                navArgument("repoOwner") {
                    type = NavType.StringType
                },
                navArgument("repoName") {
                    type = NavType.StringType
                },
                navArgument("repoId") {
                    type = NavType.IntType
                }
            )
        ) {
            RepositoryDetailsScreen()
        }
    }
}
