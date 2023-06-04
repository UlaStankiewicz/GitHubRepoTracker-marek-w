package pl.mwaszczuk.githubrepotracker.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = LightBlue,
    primaryVariant = LightBlue,
    secondary = LightBlue,
    background = Gray249,
    surface = White,
    onBackground = Black,
    error = LightRed,
)

@Composable
fun GitHubRepoTrackerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = GithubRepoTrackerTypography,
        content = content
    )
}
