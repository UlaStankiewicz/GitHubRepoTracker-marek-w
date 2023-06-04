package pl.mwaszczuk.githubrepotracker.design.components.topBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pl.mwaszczuk.githubrepotracker.design.DesignDrawables
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS

@Composable
fun TopBar(
    navController: NavHostController
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        contentPadding = PaddingValues(horizontal = SizeS),
        elevation = 0.dp
    ) {
        AnimatedVisibility(
            visible = LocalTopBar.current.state.value.isBackArrowVisible
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = SizeS)
                    .clickable {
                        navController.navigateUp()
                    },
                painter = painterResource(
                    id = DesignDrawables.ic_arrow_back
                ),
                contentDescription = ""
            )
        }
        Text(
            text = LocalTopBar.current.state.value.title,
            style = MaterialTheme.typography.h3
        )
    }
}
