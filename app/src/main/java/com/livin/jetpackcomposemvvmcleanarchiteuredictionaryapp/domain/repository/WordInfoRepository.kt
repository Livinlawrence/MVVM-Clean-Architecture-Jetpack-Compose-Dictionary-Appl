package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.repository

import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.common.Resource
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>

}