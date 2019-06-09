package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.App
import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.MoviesResponse
import osc.androiddevacademy.movieapp.networking.interactors.MovieInteractor
import osc.androiddevacademy.movieapp.ui.fragments.grid.MoviesGridContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesGridPresenter(
    private val apiInteractor: MovieInteractor,
    private val appDatabase: MoviesDatabase
) :
    MoviesGridContract.Presenter {
    private lateinit var view: MoviesGridContract.View

    override fun setView(v: MoviesGridContract.View) {
        this.view = v
    }

    override fun requestPopularMovies() {
        apiInteractor.getPopularMovies(popularMoviesCallback())
    }

    private fun popularMoviesCallback(): Callback<MoviesResponse> =
        object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                view.onFailure()
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.movies?.run {
                        view.onSuccess(this)
                    }
                }
            }
        }

    override fun favoriteClicked(movie: Movie) {
        if (appDatabase.moviesDao().getFavoriteMovies().findLast { it == movie } != null) {
            appDatabase.moviesDao().deleteFavoriteMovie(movie)
        } else{
            appDatabase.moviesDao().addFavoriteMovie(movie)
        }
    }

    override fun requestFavoriteMovies() = appDatabase.moviesDao().getFavoriteMovies() as ArrayList<Movie>
}