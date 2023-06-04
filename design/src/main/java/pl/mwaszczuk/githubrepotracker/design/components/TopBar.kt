package pl.mwaszczuk.githubrepotracker.design.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.mwaszczuk.githubrepotracker.design.DesignDrawables
import pl.mwaszczuk.githubrepotracker.design.localComposition.LocalNavController
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXXS

val AppBarHeight = 64.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String
) {
    val navController = LocalNavController.current
    TopAppBar(
        modifier = modifier.height(AppBarHeight),
        backgroundColor = MaterialTheme.colors.surface,
        contentPadding = PaddingValues(horizontal = SizeS),
        elevation = SizeXXXS
    ) {

        Icon(
            modifier = Modifier
                .padding(end = SizeS)
                .clickable {
                    navController?.navigateUp()
                },
            painter = painterResource(
                id = DesignDrawables.ic_arrow_back
            ),
            contentDescription = ""
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h3
        )
    }
}