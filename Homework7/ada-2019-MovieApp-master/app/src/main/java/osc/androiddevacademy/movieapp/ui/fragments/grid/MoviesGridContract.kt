package osc.androiddevacademy.movieapp.ui.fragments.grid

import osc.androiddevacademy.movieapp.model.Movie

interface MoviesGridContract {

    interface View{

        fun onSuccess(movies: ArrayList<Movie>)

        fun onFailure()

    }

    interface Presenter{

        fun setView(v: View)

        fun requestPopularMovies()

        fun favoriteClicked(movie: Movie)

        fun requestFavoriteMovies() : ArrayList<Movie>
    }
}