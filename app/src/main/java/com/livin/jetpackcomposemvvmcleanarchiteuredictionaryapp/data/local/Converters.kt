package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.util.JsonConverter
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.Meaning

@ProvidedTypeConverter
class Converters(private val jsonConverter: JsonConverter) {

    @TypeConverter
    fun fromMeaningJson(json: String): List<Meaning> {
        return jsonConverter.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonConverter.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: "[]"
    }
}