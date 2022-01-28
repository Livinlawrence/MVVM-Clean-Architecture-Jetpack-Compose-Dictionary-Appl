package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.remote.dto

import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.Meaning

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
){
    fun toMeaning(): Meaning {
        return Meaning(
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech
        )
    }
}