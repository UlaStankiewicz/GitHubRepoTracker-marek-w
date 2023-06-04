package pl.mwaszczuk.githubrepotracker.reposearch.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.mwaszczuk.githubrepotracker.design.DesignDrawables
import pl.mwaszczuk.githubrepotracker.design.components.CustomOutlinedTextField
import pl.mwaszczuk.githubrepotracker.design.theme.*

private val AppBarHeight = 64.dp

@Composable
fun SearchTopBar(
    repoOwnerInput: String,
    repoNameInput: String,
    onSearchInputChanged: (String, String) -> Unit,
    onSearchClicked: (String, String) -> Unit
) {
    TopAppBar(
        modifier = Modifier.requiredHeight(AppBarHeight),
        backgroundColor = MaterialTheme.colors.surface,
        contentPadding = PaddingValues(horizontal = SizeS),
        elevation = SizeXXXS
    ) {
        CustomOutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(top = SizeXS)
                .padding(bottom = SizeXXS)
                .align(Alignment.CenterVertically),
            value = repoOwnerInput,
            onValueChange = { onSearchInputChanged(it, repoNameInput) },
            textStyle = MaterialTheme.typography.h5,
            singleLine = true,
            label = {
                Text(
                    text = "owner",
                    style = MaterialTheme.typography.caption)
            }
        )

        Spacer(modifier = Modifier.width(SizeXS))

        CustomOutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(top = SizeXS)
                .padding(bottom = SizeXXS)
                .align(Alignment.CenterVertically),
            value = repoNameInput,
            onValueChange = { onSearchInputChanged(repoOwnerInput, it) },
            textStyle = MaterialTheme.typography.h5,
            singleLine = true,
            label = { Text(text = "name", style = MaterialTheme.typography.caption) }
        )

        Icon(
            modifier = Modifier
                .padding(start = SizeXXS)
                .requiredSize(SizeM)
                .clickable { onSearchClicked(repoOwnerInput, repoNameInput) }
                .align(Alignment.CenterVertically),
            painter = painterResource(
                id = DesignDrawables.ic_search
            ),
            contentDescription = "search"
        )
    }
}

@Preview
@Composable
fun SearchTopBarPreview() {
    GitHubRepoTrackerTheme {
        SearchTopBar(
            repoOwnerInput = "mwaszczuk",
            repoNameInput = "githubrepotracker",
            onSearchInputChanged = { _, _ -> },
            onSearchClicked = { _, _ -> }
        )
    }
}
