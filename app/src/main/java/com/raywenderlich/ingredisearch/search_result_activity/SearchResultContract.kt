package com.raywenderlich.ingredisearch.search_result_activity

import com.raywenderlich.ingredisearch.Recipe

class SearchResultContract {

    interface View {
        fun showLoading()
        fun showRecipes(recipes: List<Recipe>)
        fun showEmptyRecipes()
        fun showError()
        fun refreshFavoriteStatus(recipeIndex: Int)
    }
}