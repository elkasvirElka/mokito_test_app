package com.raywenderlich.ingredisearch

import com.nhaarman.mockito_kotlin.mock
import com.raywenderlich.ingredisearch.search_activity.SearcContract
import com.raywenderlich.ingredisearch.search_activity.SearchPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class SearchTests {

    private lateinit var presenter : SearchPresenter
    private lateinit var view : SearcContract.View

    @Before
    fun setup() {
        presenter = SearchPresenter()
        view = mock()
        presenter.attachView(view)
    }
    // 1
    @Test
    fun search_withEmptyQuery_callsShowQueryRequiredMessage() {

        presenter.search("")

        verify(view).showQueryRequiredMessage()
        verify(view, never()).showSearchResults(anyString())
    }
}