package com.example.editTask.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.designSystem.R as DesignSystemR

@Composable
internal fun EditTaskMemoRow(
    modifier: Modifier = Modifier,
    id: Long,
    memoTitle: String,
    memoContent: String,
    navigateMemo: (Long) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().clickable {
            navigateMemo(id)
        }.padding(horizontal = 16.dp, vertical = 24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(size = 16.dp),
                imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_comment),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier.weight(weight = 1f),
                text = stringResource(id = DesignSystemR.string.memo),
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                text = stringResource(id = DesignSystemR.string.edit),
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        if (memoTitle.isNotEmpty()) {
            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                modifier = Modifier.padding(start = 24.dp, end = 30.dp),
                text = memoTitle,
                style = TodoTheme.typography.regular_12,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        if (memoContent.isNotEmpty()) {
            Spacer(modifier = Modifier.height(height = 4.dp))

            Text(
                modifier = Modifier.padding(start = 24.dp, end = 30.dp),
                text = memoContent,
                style = TodoTheme.typography.regular_12,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskMemoRowPreview() {
    TodoTheme {
        EditTaskMemoRow(
            id = -1L,
            memoTitle = "",
            memoContent = "",
            navigateMemo = {},
        )
    }
}
