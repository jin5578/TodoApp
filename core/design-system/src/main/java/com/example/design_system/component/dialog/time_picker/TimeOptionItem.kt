package com.example.design_system.component.dialog.time_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.TimeOption
import java.time.LocalDateTime

@Composable
internal fun TimeOptionItem(
    modifier: Modifier = Modifier,
    timeOption: TimeOption,
    isClicked: Boolean,
    onClick: (LocalDateTime?) -> Unit,
) {
    Box(
        modifier = modifier.clip(shape = RoundedCornerShape(size = 8.dp))
            .background(
                color =
                    if (isClicked) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surfaceContainer,
            ).clickable {
                onClick(timeOption.time)
            }.padding(
                all = 8.dp
            )
    ) {
        Text(
            text = stringResource(id = timeOption.resId),
            style = TodoTheme.typography.medium_12,
            color = if (isClicked) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeOptionItemPreview() {
    TodoTheme {
        val timeOption = TimeOption(
            resId = R.string.time_00,
            time = LocalDateTime.now(),
        )
        TimeOptionItem(
            timeOption = timeOption,
            isClicked = true,
            onClick = {}
        )
    }
}
