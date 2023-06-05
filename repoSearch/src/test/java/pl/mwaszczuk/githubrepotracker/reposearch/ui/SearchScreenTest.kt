package pl.mwaszczuk.githubrepotracker.reposearch.ui

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pl.mwaszczuk.githubrepotracker.design.theme.GitHubRepoTrackerTheme
import pl.mwaszczuk.githubrepotracker.reposearch.search.model.RepoSearchHistoryItem
import pl.mwaszczuk.githubrepotracker.reposearch.search.ui.SearchScreenLayout

@RunWith(RobolectricTestRunner::class)
// workaround for https://github.com/robolectric/robolectric/issues/6593
@Config(instrumentedPackages = ["androidx.loader.content"])
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSearchInputsState() {
        composeTestRule.setContent {
            GitHubRepoTrackerTheme {
                SearchScreenLayout(
                    history = emptyList(),
                    searchRepoOwnerInput = "ownerInput",
                    searchRepoNameInput = "nameInput",
                    onItemClicked = {},
                    onSearchInputChanged = { _, _ -> },
                    onSearchClicked = { _, _ -> }
                )
            }
        }
        composeTestRule.onNodeWithTag("input_owner")
            .assertExists()
            .assertTextContains("ownerInput")
        composeTestRule.onNodeWithTag("input_name")
            .assertExists()
            .assertTextContains("nameInput")
    }

    @Test
    fun testSearchHistoryDisplay() {
        composeTestRule.setContent {
            GitHubRepoTrackerTheme {
                SearchScreenLayout(
                    history = listOf(
                        RepoSearchHistoryItem("owner1", "name1", 1),
                        RepoSearchHistoryItem("owner2", "name2", 2)
                    ),
                    searchRepoOwnerInput = "ownerInput",
                    searchRepoNameInput = "nameInput",
                    onItemClicked = {},
                    onSearchInputChanged = { _, _ -> },
                    onSearchClicked = { _, _ -> }
                )
            }
        }
        composeTestRule.onNodeWithText("owner1").assertExists()
        composeTestRule.onNodeWithText("name1").assertExists()
        composeTestRule.onNodeWithText("owner2").assertExists()
        composeTestRule.onNodeWithText("name2").assertExists()
    }
}
