package osc.androiddevacademy.movieapp.ui.fragments.details

import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.Review

interface MovieDetailsContract {

    interface View {

        fun onGetReviewSuccess(reviews: List<Review>)

        fun onGetReviewFailure()

    }

    interface Presenter {

        fun setView(v: View)

        fun getReviews(movie: Movie)

    }
}