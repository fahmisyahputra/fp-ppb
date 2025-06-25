package com.example.fpppb

import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel = viewModel()) {
    val calendar = Calendar.getInstance()

    var subject by remember { mutableStateOf("") }
    val dayOptions = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")
    var selectedDay by remember { mutableStateOf(dayOptions[0]) }
    var expanded by remember { mutableStateOf(false) }

    var selectedHour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }
    var timeText by remember { mutableStateOf("%02d:%02d".format(selectedHour, selectedMinute)) }

    var showTimePicker by remember { mutableStateOf(false) }

    var editingItem by remember { mutableStateOf<ScheduleItem?>(null) }
    val schedules by viewModel.scheduleList.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // ✅ Header Bar (seperti Home)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_horizontal),
                contentDescription = "Logo",
                modifier = Modifier.size(130.dp)
            )

            IconButton(onClick = { /* drawer menu */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            if (editingItem == null) "Tambah Jadwal" else "Edit Jadwal",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp)
        )

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Mata Kuliah") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)) {
            OutlinedTextField(
                value = selectedDay,
                onValueChange = {},
                label = { Text("Hari") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand")
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dayOptions.forEach { day ->
                    DropdownMenuItem(
                        text = { Text(day) },
                        onClick = {
                            selectedDay = day
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jam: $timeText", style = MaterialTheme.typography.bodyLarge)
            Button(
                onClick = { showTimePicker = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF448AFF)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Pilih Jam")
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                LocalContext.current,
                { _, hour, minute ->
                    selectedHour = hour
                    selectedMinute = minute
                    timeText = "%02d:%02d".format(hour, minute)
                    showTimePicker = false
                },
                selectedHour, selectedMinute, true
            ).show()
        }

        Button(
            onClick = {
                if (editingItem == null) {
                    viewModel.add(subject, selectedDay, timeText)
                } else {
                    viewModel.update(
                        editingItem!!.copy(
                            subject = subject,
                            day = selectedDay,
                            time = timeText
                        )
                    )
                    editingItem = null
                }
                subject = ""
                selectedDay = dayOptions[0]
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                selectedHour = hour
                selectedMinute = minute
                timeText = "%02d:%02d".format(hour, minute)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF448AFF)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(if (editingItem == null) "Tambah" else "Simpan Perubahan")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(schedules) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = item.subject,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Hari: ${item.day}", style = MaterialTheme.typography.bodyMedium)
                        Text("Jam: ${item.time}", style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(onClick = {
                                subject = item.subject
                                selectedDay = item.day
                                timeText = item.time
                                editingItem = item
                            }) {
                                Text("Edit", color = Color(0xFF4A90E2))
                            }
                            TextButton(onClick = {
                                viewModel.delete(item)
                            }) {
                                Text("Hapus", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}
