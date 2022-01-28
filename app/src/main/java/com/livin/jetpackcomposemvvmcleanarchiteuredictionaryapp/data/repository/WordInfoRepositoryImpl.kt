package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.repository

import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.common.Resource
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.local.WordInfoDao
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.remote.DictionaryAPI
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.WordInfo
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.repository.WordInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(private val api: DictionaryAPI, private val dao: WordInfoDao) :
    WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow<Resource<List<WordInfo>>> {
        emit(Resource.Loading())
        val wordInfos = dao.searchWords(word).map {
            it.toWordInfo()
        }
        emit(Resource.Loading(wordInfos))
        try {
            val response = api.getWordInfo(word)
            dao.deleteWordInfos(response.map { it.word })
            dao.insertWordInfo(response.map { it.toWordInfoEntity() })
            val newWordInfos = dao.searchWords(word).map { it.toWordInfo() }
            emit(Resource.Success(newWordInfos))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    data = wordInfos,
                    message = "Something went wrong with network call"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(data = wordInfos, message = "No internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?:"errror"))
        }
    }.flowOn(Dispatchers.IO)

}