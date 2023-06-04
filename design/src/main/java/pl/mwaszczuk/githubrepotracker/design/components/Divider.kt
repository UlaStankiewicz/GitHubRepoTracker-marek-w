package pl.mwaszczuk.githubrepotracker.design.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    thickness: Dp = 0.5.dp,
    color: Color = MaterialTheme.colors.primary,
) {
    Divider(
        modifier = modifier
            .clip(CircleShape),
        thickness = thickness,
        color = color
    )
}
