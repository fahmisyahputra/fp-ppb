package com.example.fpppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.layout.ContentScale

@Composable
fun NotesScreen(viewModel: NoteViewModel = viewModel()) {
    val notes by viewModel.noteList.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var editingNote by remember { mutableStateOf<NoteItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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

            IconButton(onClick = { /* drawer */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (editingNote != null) "Edit Catatan" else "Tambah Catatan",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            singleLine = false,
            maxLines = 8
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (title.isNotBlank() || content.isNotBlank()) {
                    if (editingNote != null) {
                        viewModel.update(editingNote!!.copy(title = title, content = content))
                        editingNote = null
                    } else {
                        viewModel.add(title, content)
                    }
                    title = ""
                    content = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF448AFF))
        ) {
            Text(if (editingNote != null) "Update Catatan" else "Tambah Catatan", fontWeight = FontWeight.Bold)
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            text = "Daftar Catatan",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(note.content)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                            TextButton(onClick = {
                                title = note.title
                                content = note.content
                                editingNote = note
                            }) {
                                Text("Edit", color = Color(0xFF4A90E2))
                            }
                            TextButton(onClick = { viewModel.delete(note) }) {
                                Text("Hapus", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}
