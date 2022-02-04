package com.nytimes.explorer.articles.ui


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nytimes.explorer.articles.data.repository.ArticlesRepository
import com.nytimes.explorer.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val repository: ArticlesRepository
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(ArticlesState())
    val state: State<ArticlesState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var searchJob: Job? = null

    //debounce search query
    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        viewModelScope.launch {
            delay(500L)
            repository.getArticles(query).onEach { results ->
                when (results) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            articles = results.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            articles = results.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                results.message ?: "Unknown error"
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            articles = results.data ?: emptyList(),
                            isLoading = true
                        )

                    }
                }

            }.launchIn(this)

        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}

