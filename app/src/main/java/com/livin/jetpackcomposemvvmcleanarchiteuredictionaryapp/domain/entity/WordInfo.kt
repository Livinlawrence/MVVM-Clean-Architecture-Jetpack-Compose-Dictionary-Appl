package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity

data class WordInfo(val meanings: List<Meaning>,
                    val origin: String,
                    val phonetic: String,
                    val word: String)