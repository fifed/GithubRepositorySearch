package com.fifed.githubrepositories.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.fifed.githubrepositories.R
import com.fifed.githubrepositories.extension.findLastVisibleItem
import com.fifed.githubrepositories.ui.repository.RepositoryScreenStarter
import com.fifed.githubrepositories.ui.search.data.RepositoryItemUIModel
import com.fifed.githubrepositories.ui.search.data.SearchStateUiModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = getViewModel()) {
    val uiState = viewModel.uiStateFlow.collectAsState(initial = SearchStateUiModel())
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        viewModel.init()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        SearchInput(uiState) { inputText ->
            viewModel.onSearchTextChanged(inputText)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (uiState.value.showCentralProgress) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .offset(y = 100.dp)
                )
            }

            if (uiState.value.showDefaultPlaceHolder) {
                DefaultPlaceHolder()
            }

            if (uiState.value.showCentralError) {
                Error(viewModel::retry)
            }

            if (uiState.value.showNoResults) {
                Text(
                    text = "${stringResource(R.string.no_repository_found)} \"${uiState.value.searchText}\"",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .offset(y = 100.dp)
                )
            }
            if (uiState.value.items.isNotEmpty()) {
                RepositoriesList(items = uiState.value.items,
                                 showBottomProgress = uiState.value.showBottomProgress,
                                 showBottomError = uiState.value.showBottomError,
                                 onScroll = { lastVisiblePosition ->
                                     keyboardController?.hide()
                                     if (lastVisiblePosition > uiState.value.items.size - 3) {
                                         viewModel.handleScrollEnding()
                                     }
                                 }, onItemClick = { repo ->
                        navController.navigate(RepositoryScreenStarter(repo.ownerName, repo.name).getRoute())
                    }, onRetry = { viewModel.retry() })
            }
        }
    }
}

@Composable
fun Error(retry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.error_happend),
            fontSize = 20.sp,
            color = Color.Red,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { retry() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
private fun SearchInput(
    uiState: State<SearchStateUiModel>,
    onInputChanged: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp), elevation = 8.dp
    ) {
        OutlinedTextField(
            value = uiState.value.searchText,
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(all = 8.dp),
            label = { Text(text = stringResource(id = R.string.search_github_repositories)) },
            onValueChange = { text ->
                onInputChanged(text)
            })
    }
}

@Composable
private fun RepositoriesList(
    items: List<RepositoryItemUIModel>,
    showBottomProgress: Boolean,
    showBottomError: Boolean,
    onScroll: (Int) -> Unit,
    onItemClick: (RepositoryItemUIModel) -> Unit,
    onRetry: () -> Unit
) {
    val listState = rememberLazyListState()
    if (listState.isScrollInProgress) {
        onScroll(listState.findLastVisibleItem())
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(count = items.count()) { position ->
            RepositoryItem(items[position], onItemClick)
        }
        if (showBottomProgress) {
            item("BottomProgress") {
                ProgressItem()
            }
        }
        if (showBottomError) {
            item("BottomError") {
                ErrorItem(onRetry)
            }
        }
    }
}

@Composable
private fun ProgressItem() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorItem(onRetry: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.error_happend),
            color = Color.Red,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Button(
            onClick = { onRetry() }, modifier = Modifier
                .weight(0.5f)
                .padding(4.dp)
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
private fun DefaultPlaceHolder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = null
        )
    }
}

@Composable
private fun RepositoryItem(itemData: RepositoryItemUIModel, onItemClick: (RepositoryItemUIModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .clickable { onItemClick(itemData) },
        elevation = 16.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${stringResource(R.string.owner)} :",
                    modifier = Modifier.padding(end = 16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )
                Text(
                    text = itemData.ownerName,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = itemData.ownerAvatar, contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(5.dp))
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(start = 24.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = itemData.name,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                        Text(
                            text = itemData.type,
                            textAlign = TextAlign.Center,
                            color = Color(itemData.typeColor),
                            fontSize = 18.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}