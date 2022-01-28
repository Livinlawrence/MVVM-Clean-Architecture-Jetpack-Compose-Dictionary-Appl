package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.usecase

import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.common.Resource
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.WordInfo
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordDetails(private val wordInfoRepository: WordInfoRepository) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {

        if (word.isBlank()) {
            return flow { }
        }

        return wordInfoRepository.getWordInfo(word)
    }
}