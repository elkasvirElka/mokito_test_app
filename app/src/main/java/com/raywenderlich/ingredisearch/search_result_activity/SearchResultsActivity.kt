/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.ingredisearch.search_result_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.raywenderlich.ingredisearch.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_noresults.*

fun Context.searchResultsIntent(query: String): Intent {
  return Intent(this, SearchResultsActivity::class.java).apply {
    putExtra(EXTRA_QUERY, query)
  }
}

private const val EXTRA_QUERY = "EXTRA_QUERY"

class SearchResultsActivity : ChildActivity(), SearchResultContract.View {


  private val repository: RecipeRepository by lazy { RecipeRepository.getRepository(this) }

  private val presenter: SearchResultsPresenter by lazy { SearchResultsPresenter(RecipeRepository.getRepository(this))}

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)

    presenter.attachView(this)
    val query = intent.getStringExtra(EXTRA_QUERY)
    supportActionBar?.subtitle = query
    presenter.search(query)

    retry.setOnClickListener { presenter.search(query) }
  }


  override fun showLoading() {
    loadingContainer.visibility = View.VISIBLE
    errorContainer.visibility = View.GONE
    list.visibility = View.GONE
    noresultsContainer.visibility = View.GONE
  }

  override fun showError() {
    loadingContainer.visibility = View.GONE
    errorContainer.visibility = View.VISIBLE
    list.visibility = View.GONE
    noresultsContainer.visibility = View.GONE
  }

  override fun showEmptyRecipes() {
    loadingContainer.visibility = View.GONE
    errorContainer.visibility = View.GONE
    list.visibility = View.VISIBLE
    noresultsContainer.visibility = View.VISIBLE
  }

  override fun showRecipes(recipes: List<Recipe>) {
    loadingContainer.visibility = View.GONE
    errorContainer.visibility = View.GONE
    list.visibility = View.VISIBLE
    noresultsContainer.visibility = View.GONE

    setupRecipeList(recipes)
  }

  override fun refreshFavoriteStatus(recipeIndex: Int) {
    list.adapter?.notifyItemChanged(recipeIndex)
  }

  private fun setupRecipeList(recipes: List<Recipe>) {
    list.layoutManager = LinearLayoutManager(this)
    list.adapter = RecipeAdapter(recipes, object : RecipeAdapter.Listener {
      override fun onClickItem(recipe: Recipe) {
        startActivity(recipeIntent(recipe.sourceUrl))
      }

      override fun onAddFavorite(recipe: Recipe) {
        presenter.addFavorite(recipe)
      }

      override fun onRemoveFavorite(recipe: Recipe) {
        presenter.removeFavorite(recipe)
      }
    })
  }

}
