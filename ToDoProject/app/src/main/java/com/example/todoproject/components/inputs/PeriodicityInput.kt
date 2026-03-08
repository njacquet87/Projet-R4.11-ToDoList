package com.example.todoproject.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PeriodicityInput() {

    var isChecked by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var selectedPeriocicity by remember { mutableStateOf("Aucune") }

    val periodicityOptions = listOf("Aucune", "Quotidienne", "Hebdomadaire", "Mensuelle")

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Afficher la sélection", fontSize = 10.sp)

        Checkbox(checked = isChecked, onCheckedChange = { isChecked = it },
            colors = CheckboxDefaults.colors(checkedColor = Color.LightGray,
                uncheckedColor = Color.LightGray, checkmarkColor = Color.Black)) }

    if (isChecked) {
        Row() {
            Text(text = "Sélectionnez le type de périodicitée.", fontSize = 10.sp, modifier = Modifier.width(140.dp))

            Box() {
                Button(onClick = { expanded = true }, modifier = Modifier.width(125.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.LightGray
                    )) {
                    Text(text = selectedPeriocicity, fontSize = 10.sp)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
                    modifier = Modifier.width(125.dp).background(Color.LightGray)) {
                    periodicityOptions.forEach { option ->
                        DropdownMenuItem(text = { Text(text = option, color = Color.Black, fontSize = 10.sp) },
                            onClick = {
                                selectedPeriocicity = option
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}