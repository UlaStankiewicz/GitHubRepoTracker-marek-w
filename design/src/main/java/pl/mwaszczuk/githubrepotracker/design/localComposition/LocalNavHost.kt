package pl.mwaszczuk.githubrepotracker.design.localComposition

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController?> { null }
