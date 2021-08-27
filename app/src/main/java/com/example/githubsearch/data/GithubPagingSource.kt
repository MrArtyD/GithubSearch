package com.example.githubsearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearch.api.GithubApi
import com.example.githubsearch.data.items.User
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val query: String,
    private val githubApi: GithubApi
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val currentPosition = params.key ?: GITHUB_STARTING_PAGE_INDEX

        return try {
            val response = githubApi.searchUsers(query, params.loadSize, currentPosition)
            LoadResult.Page(
                data = response.items,
                prevKey = if (currentPosition == GITHUB_STARTING_PAGE_INDEX) null else currentPosition - 1,
                nextKey = if (response.items.isEmpty()) null else currentPosition + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        TODO("Not yet implemented")
    }
}