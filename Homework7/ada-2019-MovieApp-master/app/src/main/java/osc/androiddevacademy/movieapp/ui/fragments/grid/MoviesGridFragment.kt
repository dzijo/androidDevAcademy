package osc.androiddevacademy.movieapp.ui.fragments.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragemnt_movie_grid.*
import osc.androiddevacademy.movieapp.App
import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.common.showFragment
import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.networking.BackendFactory
import osc.androiddevacademy.movieapp.presentation.MoviesGridPresenter
import osc.androiddevacademy.movieapp.ui.fragments.pager.MoviesPagerFragment

class MoviesGridFragment : Fragment(), MoviesGridContract.View {
    private val SPAN_COUNT = 2

    companion object{
        private var showingFavorites = false
    }

    private val gridAdapter by lazy {
        MoviesGridAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    private val presenter: MoviesGridContract.Presenter by lazy {
        MoviesGridPresenter(
            BackendFactory.getMovieInteractor(),
            MoviesDatabase.getInstance(App.getAppContext())
        )
    }

    private val movieList = arrayListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragemnt_movie_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesGrid.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
        presenter.setView(this)
        showButton.setOnClickListener { onShowButtonClicked() }

        if (showingFavorites) requestFavoriteMovies()
        else requestPopularMovies()
    }

    override fun onResume() {
        super.onResume()
        showButton.setOnClickListener { onShowButtonClicked() }
        if (showingFavorites) requestFavoriteMovies()
        else requestPopularMovies()
    }

    private fun requestPopularMovies() {
        presenter.requestPopularMovies()
    }


    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(
            R.id.mainFragmentHolder,
            MoviesPagerFragment.getInstance(
                movieList,
                movie
            ),
            true
        )
    }

    private fun onFavoriteClicked(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        gridAdapter.notifyDataSetChanged()
        presenter.favoriteClicked(movie)
        if (showingFavorites) requestFavoriteMovies()
    }

    private fun onShowButtonClicked() {
        showingFavorites = if (showingFavorites) {
            requestPopularMovies()
            false
        } else {
            requestFavoriteMovies()
            true
        }
    }

    private fun requestFavoriteMovies() {
        val movies = presenter.requestFavoriteMovies()
        movies.forEach { it.isFavorite = true }
        movieList.clear()
        movieList.addAll(movies)
        gridAdapter.setMovies(movieList)
    }

    override fun onSuccess(movies: ArrayList<Movie>) {
        movies.filter { (id) ->
            presenter.requestFavoriteMovies().any { it.id == id }
        }.forEach { it.isFavorite = true }
        movieList.clear()
        movieList.addAll(movies)
        gridAdapter.setMovies(movieList)
    }

    override fun onFailure() =
        Toast.makeText(this.context, "Getting popular movies failed", Toast.LENGTH_SHORT).show()

}