package com.eminokumus.moviesapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.eminokumus.moviesapp.data.api.FIRST_PAGE
import com.eminokumus.moviesapp.data.api.TheMovieDBInterface
import com.eminokumus.moviesapp.data.vo.Movie
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val apiService: TheMovieDBInterface,
) : RxPagingSource<Int, Movie>() {

    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        networkState.postValue(NetworkState.LOADING)
        page = params.key ?: FIRST_PAGE
        return apiService.getPopularMovie(page)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Movie>>
            { result ->
                LoadResult.Page(
                    data = result.movieList,
                    prevKey = if (page == FIRST_PAGE) null else page.minus(1),
                    nextKey = if (result.movieList.isEmpty()) null else page.plus(1)
                )
            }
            .onErrorReturn {
                networkState.postValue(NetworkState.ERROR)
                LoadResult.Error(it)
            }


    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}