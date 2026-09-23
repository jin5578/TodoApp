package com.example.design_system.component.dialog.sort_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.design_system.R
import com.example.design_system.component.CustomRadioButton
import com.example.design_system.theme.TodoTheme
import com.example.design_system.utils.getTitleResId
import com.example.model.SortByType

@Composable
fun SortTaskDialog(
    sortByType: SortByType,
    onCloseClick: () -> Unit,
    onSelectClick: (SortByType) -> Unit,
) {
    var selectedSortTaskType by remember { mutableStateOf(value = sortByType) }

    Dialog(
        onDismissRequest = onCloseClick,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(fraction = 1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            Text(
                modifier = Modifier.padding(all = 20.dp),
                text = stringResource(id = R.string.sort_tasks_by),
                style = TodoTheme.typography.bold_20,
                color = MaterialTheme.colorScheme.onBackground,
            )

            SortByType.entries.forEach { type ->
                CustomRadioButton(
                    titleResId = type.getTitleResId(),
                    isSelected = selectedSortTaskType == type,
                    onClick = { selectedSortTaskType = type },
                )
            }

            Text(
                modifier = Modifier.padding(
                    bottom = 24.dp,
                    end = 32.dp,
                ).clickable { onSelectClick(selectedSortTaskType) }
                    .align(alignment = Alignment.End),
                text = stringResource(id = R.string.select),
                style = TodoTheme.typography.medium_16,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SortTaskDialogPreview() {
    TodoTheme {
        SortTaskDialog(
            sortByType = SortByType.DUE_DATE_AND_TIME,
            onCloseClick = {},
            onSelectClick = {},
        )
    }
}
