package com.example.testexampleappbasic.network

import com.example.testexampleappbasic.model.RssResponse
import com.example.testexampleappbasic.util.GET_UK_FEED
import retrofit2.http.GET

interface APIHelper {
    //Hard coding the url for now, should come dynamically based on different feed type
    @GET(GET_UK_FEED)
    suspend fun getRssFeed(): RssResponse
}