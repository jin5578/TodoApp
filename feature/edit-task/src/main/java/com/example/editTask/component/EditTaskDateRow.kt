package com.example.editTask.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designSystem.theme.TodoTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.designSystem.R as DesignSystemR

@Composable
internal fun EditTaskDateRow(
    modifier: Modifier = Modifier,
    date: LocalDate,
    locale: Locale,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() }
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(size = 16.dp),
            imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_calendar),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            modifier = Modifier.weight(weight = 1f),
            text = stringResource(id = DesignSystemR.string.due_date),
            style = TodoTheme.typography.medium_16,
            color = MaterialTheme.colorScheme.onBackground,
        )

        val dateFormat = DateTimeFormatter.ofPattern(
            "yyyy/MM/dd",
            locale,
        )
        val dateContent = date.format(dateFormat)
        Box(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp),
            ).padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            ),
        ) {
            Text(
                text = dateContent,
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskDateRowPreview() {
    TodoTheme {
        EditTaskDateRow(
            date = LocalDate.now(),
            locale = Locale.KOREA,
            onClick = {},
        )
    }
}
