package com.nytimes.explorer.articles.ui


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nytimes.explorer.articles.data.model.search.Article
import com.nytimes.explorer.articles.data.repository.ArticlesRepository
import com.nytimes.explorer.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 10

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val repository: ArticlesRepository
) : ViewModel() {

    val searchQuery = mutableStateOf("")

    val state = mutableStateOf(ArticlesState())

    val eventFlow = MutableSharedFlow<UIEvent>()

    private var articleListScrollPosition = 0

    private fun appendArticles(articles: List<Article>): List<Article> {
        val current = ArrayList(state.value.articles)
        current.addAll(articles)
        return current
    }

    fun incrementPage() {
        state.value.page += 1
    }

    fun onChangeScrollPosition(p: Int) {
        articleListScrollPosition = p
    }

    fun onSearch(media: String, page: Int? = 1) {
        state.value = state.value.copy(
            isLoading = true,
        )

        viewModelScope.launch {
            var p = if (page != null) page - 1 else 0
            repository.getArticles(searchQuery.value, media, p).onEach { results ->
                val res = results.data ?: emptyList()
                var finalResults = if (state.value.isSearching && (page != null)) appendArticles(res) else res
                when (results) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            articles = finalResults,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        state.value = state.value.copy(
                            articles = finalResults,
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
                            articles = finalResults,
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

