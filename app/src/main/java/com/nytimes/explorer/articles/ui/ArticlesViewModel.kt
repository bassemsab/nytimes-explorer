package com.nytimes.explorer.articles.ui


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nytimes.explorer.articles.data.repository.ArticlesRepository
import com.nytimes.explorer.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val repository: ArticlesRepository
) : ViewModel() {

    val searchQuery = mutableStateOf("")

    val state = mutableStateOf(ArticlesState())

    val eventFlow = MutableSharedFlow<UIEvent>()

    //debounce search query
    fun onSearch() {
        state.value = state.value.copy(
            isLoading = true
        )

        viewModelScope.launch {
            repository.getArticles(searchQuery.value).onEach { results ->
                when (results) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            articles = results.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        state.value = state.value.copy(
                            articles = results.data ?: emptyList(),
                            isLoading = false
                        )
                        eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                results.message ?: "Unknown error"
                            )
                        )
                    }
                    is Resource.Loading -> {
                        state.value = state.value.copy(
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

