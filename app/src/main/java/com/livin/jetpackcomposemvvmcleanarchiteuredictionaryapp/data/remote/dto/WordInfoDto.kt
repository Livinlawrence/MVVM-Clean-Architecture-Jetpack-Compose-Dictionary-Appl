package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.remote.dto

import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.local.entity.WordInfoEntity

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val origin: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word
        )
    }
}