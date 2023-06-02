package com.example.submission.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submission.R
import com.example.submission.di.Injection
import com.example.submission.model.Anime
import com.example.submission.ui.ViewModelFactory
import com.example.submission.ui.common.UiState
import com.example.submission.ui.components.AnimeItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val searchQuery = remember { mutableStateOf("") }

    TextField(
        value = searchQuery.value,
        onValueChange = { newValue ->
            searchQuery.value = newValue
            viewModel.searchAnime(newValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = {
            Text(text = "Search anime")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { /* Perform any action on Done pressed, if needed */ }
        )
    )

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                    viewModel.getAllAnime(searchQuery.value)
                }
            }
            is UiState.Success -> {
                val animeList = uiState.data
                if (animeList.isEmpty()) {
                    Text(
                        text = stringResource(R.string.empty_anime),
                        modifier = Modifier.padding(top = 100.dp, start = 140.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 17.sp
                    )
                } else {
                    HomeContent(
                        orderReward = animeList,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
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
fun HomeContent(
    orderReward: List<Anime>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(top = 70.dp)
            .testTag("AnimeList")
    ) {
        items(orderReward) { data ->
            AnimeItem(
                image = data.image,
                title = data.title,
                rate = data.rate,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                }
            )
        }
    }
}