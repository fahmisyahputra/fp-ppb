package com.example.fpppb

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
// Untuk background modifier dan shape
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
// Untuk gambar dan clip
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource



@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            // Gambar
            Image(
                painter = painterResource(id = R.drawable.login_header),
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 80.dp))
            )

            // Overlay gradient blur dari bawah ke atas
            Box(
                modifier = Modifier
                    .fillMaxSize() // PENTING: agar tdk nutup seluruh gambar
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
                text = "Existing Users,\nLog In Here",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Email
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Email/Username") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password
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

            Spacer(modifier = Modifier.height(20.dp))

            // Login Button
            Button(
                onClick = {
                    val pref = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    val savedUsername = pref.getString("username", "")
                    val savedPassword = pref.getString("password", "")

                    if (username == savedUsername && password == savedPassword) {
                        Toast.makeText(context, "Login berhasil!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Username/Email atau password salah", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF00C6FF), Color(0xFF6E00FF))
                        )
                    )
            ) {
                Text("Login", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    navController.navigate(Screen.Register.route)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Belum punya akun? Daftar", color = Color.Black)
            }
        }
    }
}
