package com.example.githubsearch.ui.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.githubsearch.data.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val items = currentQuery.switchMap { query ->
        repository.getSearchResults(query).cachedIn(viewModelScope)
    }

    fun searchItems(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "andris"
    }
}