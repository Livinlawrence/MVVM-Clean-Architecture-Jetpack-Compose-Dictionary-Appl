package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfo(info: List<WordInfoEntity>)


    @Query("DELETE FROM wordinfoentity WHERE word IN(:words)")
    suspend fun deleteWordInfos(words: List<String>)


    @Query("SELECT * FROM wordinfoentity WHERE word LIKE '%' || :word || '%'")
    suspend fun searchWords(word: String): List<WordInfoEntity>
}