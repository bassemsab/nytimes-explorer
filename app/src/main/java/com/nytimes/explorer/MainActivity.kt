package com.nytimes.explorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nytimes.explorer.articles.data.remote.NytApi
import com.nytimes.explorer.articles.ui.Article
import com.nytimes.explorer.articles.ui.ArticlesViewModel
import com.nytimes.explorer.ui.theme.ExplorerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExplorerTheme {
                val viewModel: ArticlesViewModel = hiltViewModel()
                val state = viewModel.state.value
                val query = viewModel.searchQuery
                val scaffoldState = rememberScaffoldState()


                LaunchedEffect(key1 = true) {
                    viewModel.onSearch(NytApi.MOST_SHARED)

                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is ArticlesViewModel.UIEvent.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(event.message)
                            }
                        }

                    }
                }
                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colors.background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            val focusManager = LocalFocusManager.current

                            TextField(
                                singleLine = true,
                                value = query.value,
                                onValueChange = {
                                    query.value = it
                                    state.isSearching = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(onSearch = {
                                    viewModel.onSearch("")
                                    focusManager.clearFocus()
                                }),
                                trailingIcon = {
                                    Icon(Icons.Filled.Close, null, modifier = Modifier.clickable {
                                        state.isSearching = false
                                        state.isLoading = false
                                        focusManager.clearFocus()
                                        query.value = ""

                                    })
                                },
                                placeholder = { Text(text = "Search articles here...") },

                                )
                            if (!state.isSearching) {
                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.horizontalScroll(rememberScrollState())
                                ) {
                                    Button(
                                        modifier = Modifier.padding(4.dp),
                                        onClick = {
                                            viewModel.onSearch(NytApi.MOST_VIEWED)
                                        }
                                    ) {
                                        Text("Most viewed")
                                    }
                                    Button(
                                        modifier = Modifier.padding(4.dp),
                                        onClick = {
                                            viewModel.onSearch(NytApi.MOST_SHARED)
                                        }
                                    ) {
                                        Text("Most Shared")
                                    }
                                    Button(
                                        modifier = Modifier.padding(4.dp),
                                        onClick = {
                                            viewModel.onSearch(NytApi.MOST_EMAILED)
                                        }
                                    ) {
                                        Text("Most Emailed")
                                    }
                                }

                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(state.articles.size) { i ->
                                    val article = state.articles[i]
                                    Article(article, modifier = Modifier.fillMaxWidth())
                                    if (i < state.articles.size - 1) {
                                        Divider()
                                    }

                                }
                            }

                        }

                        if (state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                }

            }
        }
    }
}
