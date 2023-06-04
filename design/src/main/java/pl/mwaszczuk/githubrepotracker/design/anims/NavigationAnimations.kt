package pl.mwaszczuk.githubrepotracker.design.anims

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
fun slideIntoContainerAnimRight(): (
AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition
) = {
    slideIntoContainer(
        AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(400)
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun slideIntoContainerAnimLeft(): (
AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition
) = {
    slideIntoContainer(
        AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(400)
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun slideOutOfContainerAnimRight(): (
AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition
) = {
    slideOutOfContainer(
        AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(400)
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun slideOutOfContainerAnimLeft(): (
AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition
) = {
    slideOutOfContainer(
        AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(400)
    )
}
