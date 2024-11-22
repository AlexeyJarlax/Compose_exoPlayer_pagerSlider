package com.pavlovalexey.startsetupforcomposein2024.ui.eventlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onApply: (type: String?, date: String?, distance: Double?) -> Unit
) {
    var selectedType by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var selectedDistance by remember { mutableStateOf<Double?>(null) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Фильтры") },
        text = {
            Column {
                Text(text = "Тип события")
                DropdownMenuSample(
                    options = listOf("Все", "Концерт", "Мастер-класс"),
                    selectedOption = selectedType ?: "Все",
                    onOptionSelected = {
                        selectedType = if (it == "Все") null else it
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Дата")
                OutlinedTextField(
                    value = selectedDate ?: "",
                    onValueChange = { selectedDate = it },
                    label = { Text("Год-Месяц-День") },
                    placeholder = { Text("Например, 2024-12-31") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Радиус (км)")
                OutlinedTextField(
                    value = selectedDistance?.toString() ?: "",
                    onValueChange = {
                        selectedDistance = it.toDoubleOrNull()
                    },
                    label = { Text("Например, 20") },
                    placeholder = { Text("20") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onApply(selectedType, selectedDate, selectedDistance)
            }) {
                Text("Применить")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Отмена")
            }
        }
    )
}