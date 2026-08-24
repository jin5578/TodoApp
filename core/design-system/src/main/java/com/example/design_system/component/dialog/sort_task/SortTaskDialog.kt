package com.example.design_system.component.dialog.sort_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import com.example.design_system.theme.TodoTheme
import com.example.model.SortTaskType

@Composable
fun SortTaskDialog(
    sortTaskType: SortTaskType,
    onCloseClick: () -> Unit,
    onSelectClick: (SortTaskType) -> Unit,
) {
    var selectedSortTaskType by remember { mutableStateOf(value = sortTaskType) }

    Dialog(
        onDismissRequest = onCloseClick
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(fraction = 1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        ) {
            Text(
                modifier = Modifier.padding(all = 20.dp),
                text = stringResource(id = R.string.sort_tasks_by),
                style = TodoTheme.typography.bold_18,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            SortTaskType.entries.forEach { type ->
                CustomRadioButton(
                    label = type.title,
                    isSelected = selectedSortTaskType == type,
                    onClick = { selectedSortTaskType = type }
                )
            }

            Text(
                modifier = Modifier.padding(
                    bottom = 24.dp,
                    end = 32.dp
                ).clickable { onSelectClick(selectedSortTaskType) }
                    .align(alignment = Alignment.End),
                text = stringResource(id = R.string.select),
                style = TodoTheme.typography.bold_14,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun CustomRadioButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.onSecondaryContainer),
            selected = isSelected,
            onClick = onClick
        )
        Text(
            text = label,
            style = TodoTheme.typography.medium_16,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SortTaskDialogPreview() {
    TodoTheme {
        SortTaskDialog(
            sortTaskType = SortTaskType.BY_CREATE_TIME_ASCENDING,
            onCloseClick = {},
            onSelectClick = {}
        )
    }
}