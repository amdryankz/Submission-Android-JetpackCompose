package com.example.submission.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submission.R
import com.example.submission.di.Injection
import com.example.submission.model.Anime
import com.example.submission.ui.ViewModelFactory
import com.example.submission.ui.common.UiState
import com.example.submission.ui.components.FavoriteItem

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
            Text(
                text = stringResource(R.string.menu_favorite),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        when (uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                    viewModel.getAddedAnimeFavorite()
                }
            }
            is UiState.Success -> {
                val animeList = uiState.data
                if (animeList.isEmpty()) {
                    Text(
                        text = stringResource(R.string.empty_favorite),
                        modifier = Modifier.padding(top = 70.dp, start = 140.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 17.sp
                    )
                } else {
                    FavoriteContent(animeList, navigateToDetail)
                }
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
fun FavoriteContent(
    anime: List<Anime>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 55.dp)
        ) {
            items(anime) { item ->
                FavoriteItem(
                    animeId = item.id,
                    image = item.image,
                    title = item.title,
                    rate = item.rate,
                    isFavorite = item.isFav,
                    modifier = Modifier.clickable {
                        navigateToDetail(item.id)
                    }
                )
                Divider()
            }
        }
    }
}