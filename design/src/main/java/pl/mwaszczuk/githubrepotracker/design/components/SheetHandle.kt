package pl.mwaszczuk.githubrepotracker.design.components

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import pl.mwaszczuk.githubrepotracker.design.theme.*

@Composable
fun SheetHandle(
    modifier: Modifier = Modifier,
) {
    Divider(
        modifier = modifier
            .requiredWidth(SizeXL)
            .clip(RoundedCornerShape(SizeM)),
        thickness = SizeXXXS,
        color = MaterialTheme.colors.onBackground
    )
}
