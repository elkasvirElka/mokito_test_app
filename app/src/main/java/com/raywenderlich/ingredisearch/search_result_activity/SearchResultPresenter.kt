package com.raywenderlich.ingredisearch.search_result_activity

import com.raywenderlich.ingredisearch.BasePresenter
import com.raywenderlich.ingredisearch.Recipe
import com.raywenderlich.ingredisearch.RecipeRepository

class SearchResultsPresenter(val repository: RecipeRepository) : BasePresenter<SearchResultContract.View>() {
    private var recipes: List<Recipe>? = null

    fun search(query: String) {
        view?.showLoading()
        // 2
        repository.getRecipes(query, object : RecipeRepository.RepositoryCallback<List<Recipe>> {
            // 3
            override fun onSuccess(recipes: List<Recipe>?) {
                this@SearchResultsPresenter.recipes = recipes
                if (recipes != null && recipes.isNotEmpty()) {
                    view?.showRecipes(recipes)
                } else {
                    view?.showEmptyRecipes()
                }
            }

            // 4
            override fun onError() {
                view?.showError()
            }
        })
    }

    fun addFavorite(recipe: Recipe) {
        // 2
        recipe.isFavorited = true
        // 3
        repository.addFavorite(recipe)
        // 4
        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null) {
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }

    fun removeFavorite(recipe: Recipe) {
        repository.removeFavorite(recipe)
        recipe.isFavorited = false
        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null) {
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }
}