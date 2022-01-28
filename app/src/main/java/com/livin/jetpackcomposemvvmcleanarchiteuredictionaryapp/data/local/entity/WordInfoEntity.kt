package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.Meaning
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.WordInfo

@Entity
data class WordInfoEntity(
    @PrimaryKey
    val id: Int? = null,
    val word: String,
    val phonetic: String, val origin: String,
    val meanings: List<Meaning>
) {

    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            origin = origin,
            phonetic = phonetic,
            word = word
        )
    }
}