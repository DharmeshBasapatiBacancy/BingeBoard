package com.example.bingeboard.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.bingeboard.BuildConfig
import com.example.bingeboard.db.MovieDatabase
import com.example.bingeboard.network.ApiService
import com.example.bingeboard.network.models.Movie
import com.example.bingeboard.network.models.RemoteKeys
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val apiService: ApiService,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, Movie>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private val initialPageIndex = 1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = apiService.getAllMovies(BuildConfig.API_KEY, page)
            val endOfList = response.results.isEmpty()
            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.remoteKeysDao().clearAll()
                    movieDatabase.movieDao().clearAll()
                }
                val prevKey = if (page == initialPageIndex) null else page - 1
                val nextKey = if (endOfList) null else page + 1
                val keys = response.results.map {
                    RemoteKeys(it.id, prevKey, nextKey)
                }
                movieDatabase.remoteKeysDao().insertRemote(keys)
                movieDatabase.movieDao().addMovies(response.results)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfList)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Movie>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: initialPageIndex
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey ?: MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> movieDatabase.remoteKeysDao().getRemoteKeys(movie.id) }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie -> movieDatabase.remoteKeysDao().getRemoteKeys(movie.id) }

    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repId ->
                movieDatabase.remoteKeysDao().getRemoteKeys(repId)
            }

        }
    }


}