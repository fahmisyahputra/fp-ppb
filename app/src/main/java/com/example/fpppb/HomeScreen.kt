package com.example.fpppb

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.layout.ContentScale


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header bar
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

            IconButton(onClick = { /* Drawer */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        }

//        Spacer(modifier = Modifier.height(16.dp))

        // Placeholder image
        Image(
            painter = painterResource(id = R.drawable.banner_home),
            contentDescription = "Banner",
            contentScale = ContentScale.Crop, // ⬅️ agar gambar memenuhi area
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "Welcome to ClassMate!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(26.dp))

        // Grid Buttons
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HomeMenuButton("Jadwal", Color(0xFF448AFF), modifier = Modifier.weight(1f)) {
                    navController.navigate(AppScreen.Schedule.route)
                }
                HomeMenuButton("Tugas", Color.White, Color.Black, modifier = Modifier.weight(1f)) {
                    navController.navigate(AppScreen.Task.route)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HomeMenuButton("Catatan", Color(0xFF448AFF), modifier = Modifier.weight(1f)) {
                    navController.navigate(AppScreen.Notes.route)
                }
                HomeMenuButton("Profil", Color.White, Color.Black, modifier = Modifier.weight(1f)) {
                    navController.navigate(AppScreen.Profile.route)
                }
            }
        }
    }
}

@Composable
fun HomeMenuButton(
    title: String,
    bgColor: Color,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(130.dp) // ⬅️ Lebih tinggi
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = textColor, fontSize = 18.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
