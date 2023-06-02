package com.example.submission.ui.screen.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.submission.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.submission.di.Injection
import com.example.submission.ui.ViewModelFactory
import com.example.submission.ui.common.UiState
import com.example.submission.ui.components.FavoriteButton
import com.example.submission.ui.theme.SubmissionTheme

@Composable
fun DetailScreen(
    animeId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToFav: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                    viewModel.getAnimeById(animeId)
                }
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.image,
                    data.title,
                    data.desc,
                    data.rate,
                    onBackClick = navigateBack,
                    onAddToFav = {
                        viewModel.addToFavorite(data)
                        navigateToFav()
                    },
                    data.isFav
                )
            }
            is UiState.Error -> {
                Text(
                    text = stringResource(R.string.error_loading_data),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    image: String,
    title: String,
    desc: String,
    rate: String,
    onBackClick: () -> Unit,
    onAddToFav: () -> Unit,
    isFav: Boolean,
    modifier: Modifier = Modifier,
) {

    val isFavState = remember { mutableStateOf(isFav) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() },
                    tint = Color.White
                )
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                        )
                        Text(
                            text = rate,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = "Synopsis",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            FavoriteButton(
                text = stringResource(R.string.add_to_fav),
                onClick = {
                    onAddToFav()
                    isFavState.value = true
                },
                enabled = !isFavState.value
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    SubmissionTheme {
        DetailContent(
            "",
            "Kimetsu",
            "aa",
            "9.05",
            onBackClick = {},
            onAddToFav = {},
            true
        )
    }
}