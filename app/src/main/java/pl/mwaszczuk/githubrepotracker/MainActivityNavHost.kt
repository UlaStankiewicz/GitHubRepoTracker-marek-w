package pl.mwaszczuk.githubrepotracker

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import pl.mwaszczuk.githubrepotracker.design.anims.*
import pl.mwaszczuk.githubrepotracker.design.localComposition.LocalNavController
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.REPOSITORY_DETAILS_SCREEN_ID_ARG
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.REPOSITORY_DETAILS_SCREEN_NAME_ARG
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.REPOSITORY_DETAILS_SCREEN_OWNER_ARG
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui.REPOSITORY_DETAILS_ROUTE
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui.RepositoryDetailsScreen
import pl.mwaszczuk.githubrepotracker.reposearch.search.ui.SEARCH_SCREEN_ROUTE
import pl.mwaszczuk.githubrepotracker.reposearch.search.ui.SearchScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainActivityNavigation() {
    val navController = rememberAnimatedNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
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
                SearchScreen()
            }

            composable(
                route = REPOSITORY_DETAILS_ROUTE +
                        "/{$REPOSITORY_DETAILS_SCREEN_OWNER_ARG}/{$REPOSITORY_DETAILS_SCREEN_NAME_ARG}/{$REPOSITORY_DETAILS_SCREEN_ID_ARG}",
                enterTransition = slideIntoContainerAnimLeft(),
                popEnterTransition = slideIntoContainerAnimLeft(),
                exitTransition = slideOutOfContainerAnimRight(),
                popExitTransition = slideOutOfContainerAnimRight(),
                arguments = listOf(
                    navArgument(REPOSITORY_DETAILS_SCREEN_OWNER_ARG) {
                        type = NavType.StringType
                    },
                    navArgument(REPOSITORY_DETAILS_SCREEN_NAME_ARG) {
                        type = NavType.StringType
                    },
                    navArgument(REPOSITORY_DETAILS_SCREEN_ID_ARG) {
                        type = NavType.IntType
                    }
                )
            ) {
                RepositoryDetailsScreen()
            }
        }
    }
}
