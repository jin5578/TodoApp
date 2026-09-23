package com.example.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme

@Composable
internal fun CustomRadioButton(
    modifier: Modifier = Modifier,
    titleResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
            selected = isSelected,
            onClick = onClick
        )
        Text(
            text = stringResource(id = titleResId),
            style = TodoTheme.typography.medium_16,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomRadioButtonPreview() {
    TodoTheme {
        CustomRadioButton(
            titleResId = R.string.due_date_and_time,
            isSelected = true,
            onClick = {}
        )
    }
}