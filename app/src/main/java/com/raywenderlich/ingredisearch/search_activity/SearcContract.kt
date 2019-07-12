package com.raywenderlich.ingredisearch.search_activity

class SearcContract{
    // 6
    interface View {
        fun showQueryRequiredMessage()
        fun showSearchResults(query: String)
    }

}