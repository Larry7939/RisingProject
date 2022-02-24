package com.example.risingproject.src.main.popularity

import com.example.risingproject.src.main.popularity.models.PopLookupResponse


interface PopularityFragmentView {
    fun onGetPopSuccess(response: PopLookupResponse)
    fun onGetPopFailure(message: String)
}