package com.example.submission.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.submission.R
import com.example.submission.di.Injection
import com.example.submission.ui.ViewModelFactory
import com.example.submission.ui.screen.favorite.FavoriteViewModel
import com.example.submission.ui.theme.Shapes
import com.example.submission.ui.theme.SubmissionTheme

@Composable
fun FavoriteItem(
    animeId: Long,
    image: String,
    title: String,
    rate: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    val isFavoriteState = remember { mutableStateOf(isFavorite) }

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Row(
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                )
                Text(
                    text = rate,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
        }
        IconButton(
            onClick = {
                viewModel.updateAnimeFavorite(animeId)
                isFavoriteState.value = !isFavoriteState.value
            },
        ) {
            Icon(
                imageVector = if (isFavoriteState.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.menu_favorite),
                tint = if (isFavoriteState.value) Color.Red else LocalContentColor.current.copy(
                    alpha = ContentAlpha.medium
                ),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    SubmissionTheme {
        FavoriteItem(
            4, "", "Fullmetal Alchemist: Brotherhood", "9.10", true
        )
    }
}