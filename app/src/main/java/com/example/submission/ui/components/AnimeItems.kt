package com.example.submission.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.submission.ui.theme.Shapes
import com.example.submission.ui.theme.SubmissionTheme

@Composable
fun AnimeItem(
    image: String,
    title: String,
    rate: String,
    modifier: Modifier = Modifier,
) {
    Column {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(260.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Row {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Yellow,
            )
            Text(
                text = rate,
                modifier = modifier
                    .padding(start = 4.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RewardItemPreview() {
    SubmissionTheme {
        AnimeItem("", "Fullmetal Alchemist: Brotherhood", "9.10")
    }
}