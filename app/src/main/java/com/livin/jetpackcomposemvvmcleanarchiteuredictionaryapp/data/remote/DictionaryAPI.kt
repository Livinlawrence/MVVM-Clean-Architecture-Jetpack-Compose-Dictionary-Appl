package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.remote

import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryAPI {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(@Path("word") word: String): List<WordInfoDto>

}