package pl.mwaszczuk.githubrepotracker.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS

@Composable
fun ItemRow(
    modifier: Modifier = Modifier,
    text: String,
    bottomText: String? = null,
    icon: Painter,
    iconDescription: String = "",
    iconTint: Color = MaterialTheme.colors.onBackground
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = iconDescription,
            tint = iconTint
        )
        Column {
            Text(
                modifier = Modifier
                    .padding(start = SizeS),
                text = text,
                style = MaterialTheme.typography.body1
            )
            if (bottomText != null) {
                Text(
                    modifier = Modifier
                        .padding(start = SizeS),
                    text = bottomText,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
