package com.eminokumus.moviesapp.ui.popular_movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eminokumus.moviesapp.data.api.POSTER_BASE_URL
import com.eminokumus.moviesapp.data.repository.NetworkState
import com.eminokumus.moviesapp.data.vo.Movie
import com.eminokumus.moviesapp.databinding.MovieListItemBinding
import com.eminokumus.moviesapp.databinding.NetworkStateItemBinding
import com.eminokumus.moviesapp.ui.single_movie_details.SingleMovieActivity
import java.lang.ClassCastException

class PopularMoviePagingDataAdapter(val context: Context) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCalback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MOVIE_VIEW_TYPE -> {
                val binding =
                    MovieListItemBinding.inflate(
                        LayoutInflater.from(parent.context)
                    )
                MovieItemViewHolder(binding)
            }

            NETWORK_VIEW_TYPE -> {
                val binding =
                    NetworkStateItemBinding.inflate(
                        LayoutInflater.from(parent.context)
                    )
                NetworkStateItemViewHolder(binding)

            }

            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    // If networkState isn't null and LOADED, it means that we have extra row.
    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    // if we have extra row, we increase item count by 1. Otherwise, we don't increase item count.
    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    // If we have extra row and the position equals last position, we return NETWORK_VIEW_TYPE. Otherwise, we return MOVIE_VIEW_TYPE.
    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }


    class MovieDiffCalback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    class MovieItemViewHolder(var movieListItemBinding: MovieListItemBinding) :
        RecyclerView.ViewHolder(movieListItemBinding.root) {
        fun bind(movie: Movie?, context: Context) {
            movieListItemBinding.cvMovieTitle.text = movie?.title
            movieListItemBinding.cvMovieReleaseDate.text = movie?.releaseDate

            val moviePosterUrl = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterUrl)
                .into(movieListItemBinding.cvIvMoviePoster)

            itemView.setOnClickListener {
                val intent = Intent(context, SingleMovieActivity::class.java)
                intent.putExtra("movieId", movie?.id)
                context.startActivity(intent)
            }

        }
    }

    class NetworkStateItemViewHolder(var networkStateItemBinding: NetworkStateItemBinding) :
        RecyclerView.ViewHolder(networkStateItemBinding.root) {
        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                networkStateItemBinding.progressBar.visibility = View.VISIBLE
            } else {
                networkStateItemBinding.progressBar.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                networkStateItemBinding.errorMsg.visibility = View.VISIBLE
                networkStateItemBinding.errorMsg.text = networkState.msg
            } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                networkStateItemBinding.errorMsg.visibility = View.VISIBLE
                networkStateItemBinding.errorMsg.text = networkState.msg
            } else {
                networkStateItemBinding.errorMsg.visibility = View.GONE
            }

        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                                //hadExtraRow is true and hasExtraRow is false
                notifyItemRemoved(super.getItemCount())       //remove the progressbar at the end
            } else {                                          //hasExtraRow is true and hadExtraRow is false
                notifyItemInserted(super.getItemCount())      //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow is true
            notifyItemChanged(itemCount - 1)       //previous state isn't equal new state, this means there is an error.
        }
    }
}