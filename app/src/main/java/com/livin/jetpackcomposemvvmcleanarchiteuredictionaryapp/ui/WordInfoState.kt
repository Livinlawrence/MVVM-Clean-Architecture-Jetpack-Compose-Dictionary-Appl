package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.ui

import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.WordInfo

data class WordInfoState(
     val data: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)
