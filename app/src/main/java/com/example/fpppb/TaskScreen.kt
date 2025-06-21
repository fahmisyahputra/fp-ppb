package com.example.fpppb

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun TaskScreen(viewModel: TaskViewModel = viewModel()) {
    val context = LocalContext.current
    val taskList by viewModel.taskList.collectAsState()

    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    var editingTaskId by remember { mutableStateOf<Int?>(null) }

    val calendar = Calendar.getInstance()
    val deadlineMillis = remember { mutableStateOf(calendar.timeInMillis) }
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }
    val deadlineText = remember(deadlineMillis.value) {
        dateFormat.format(Date(deadlineMillis.value))
    }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        calendar.set(year, month, day, hour, minute)
                        deadlineMillis.value = calendar.timeInMillis
                        showDatePicker = false
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    val activeTasks = taskList.filter { !it.isDone }.sortedBy { it.deadline }
    val doneTasks = taskList.filter { it.isDone }.sortedBy { it.deadline }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {

        Text(
            text = if (isEditing) "Edit Tugas" else "Tambah Tugas",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Nama Tugas") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text("Deadline: $deadlineText", modifier = Modifier.weight(1f))
            Button(
                onClick = { showDatePicker = true },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Text("Pilih Tanggal & Waktu")
            }
        }

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Catatan (opsional)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    if (isEditing && editingTaskId != null) {
                        viewModel.update(TaskItem(editingTaskId!!, title, notes, deadlineMillis.value, false))
                        isEditing = false
                        editingTaskId = null
                    } else {
                        viewModel.add(title, notes, deadlineMillis.value)
                    }
                    title = ""
                    notes = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(if (isEditing) "Simpan Perubahan" else "Tambah Tugas", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (activeTasks.isNotEmpty()) {
            Text(
                "Tugas Aktif",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            activeTasks.forEach { task ->
                TaskCard(
                    task = task,
                    dateFormat = dateFormat,
                    onDelete = { viewModel.delete(task) },
                    onEdit = {
                        title = task.title
                        notes = task.notes
                        deadlineMillis.value = task.deadline
                        isEditing = true
                        editingTaskId = task.id
                    },
                    onToggleDone = { viewModel.update(task.copy(isDone = true)) }
                )
            }
        }

        if (doneTasks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Tugas Selesai",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            doneTasks.forEach { task ->
                TaskCard(
                    task = task,
                    dateFormat = dateFormat,
                    onDelete = { viewModel.delete(task) },
                    onEdit = {},
                    onToggleDone = { viewModel.update(task.copy(isDone = false)) },
                    isDoneCard = true
                )
            }
        }
    }
}

@Composable
fun TaskCard(
    task: TaskItem,
    dateFormat: SimpleDateFormat,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onToggleDone: () -> Unit,
    isDoneCard: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = { onToggleDone() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text("Deadline: ${dateFormat.format(Date(task.deadline))}")
            if (task.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text("Catatan: ${task.notes}")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                if (!isDoneCard) {
                    TextButton(onClick = onEdit) {
                        Text("Edit", color = Color(0xFF4A90E2))
                    }
                }
                TextButton(onClick = onDelete) {
                    Text("Hapus", color = Color.Red)
                }
            }
        }
    }
}
