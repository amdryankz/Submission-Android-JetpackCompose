package com.example.submission.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileImage: String,
    name: String,
    email: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = profileImage,
            contentDescription = null,
            modifier = Modifier
                .size(240.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = email,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
        )
    }
}