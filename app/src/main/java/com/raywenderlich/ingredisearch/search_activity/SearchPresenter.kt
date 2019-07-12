package com.raywenderlich.ingredisearch.search_activity

import com.raywenderlich.ingredisearch.BasePresenter

class SearchPresenter : BasePresenter<SearcContract.View>(){

    fun search(query: String) {
        if (query.trim().isBlank()) {
            view?.showQueryRequiredMessage()
        } else {
            view?.showSearchResults(query)
        }
    }


}