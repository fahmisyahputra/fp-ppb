package com.example.fpppb

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            // Gambar
            Image(
                painter = painterResource(id = R.drawable.register_header),
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 80.dp))
            )

            // Overlay gradient blur dari bawah ke atas
            Box(
                modifier = Modifier
                    .fillMaxSize() // PENTING: Biar nutup seluruh gambar
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.9f),
                                Color.White.copy(alpha = 0.7f),
                                Color.Transparent
                            ),
                            startY = 1000f, // Bagian bawah gambar
                            endY = 0f      // Menuju atas
                        )
                    )
                    .clip(RoundedCornerShape(bottomStart = 80.dp))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 240.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "New User?\nRegister Here",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Email input
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Password input
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Confirm password input
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Signup button with gradient
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Email & password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(context, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                    } else {
                        val pref = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                        pref.edit()
                            .putString("username", email)
                            .putString("password", password)
                            .apply()
                        Toast.makeText(context, "Berhasil daftar! Silakan login.", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF00C6FF), Color(0xFF6E00FF))
                        )
                    )
            ) {
                Text("Signup", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Sudah punya akun? Login", color = Color.Black)
            }
        }
    }
}
