package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.common.Constants.SEARCH_DELAY
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.common.Resource
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.usecase.GetWordDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordDetails: GetWordDetails
) : ViewModel() {


    private val _wordInfoState = mutableStateOf<WordInfoState>(WordInfoState())
    val wordInfoState: State<WordInfoState> = _wordInfoState

    private val _searchFieldState = mutableStateOf<String>("")
    val searchFieldState: State<String> = _searchFieldState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun searchWord(word: String) {
        _searchFieldState.value = word
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY)
            getWordDetails.invoke(word).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _wordInfoState.value = wordInfoState.value.copy(
                            data = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _wordInfoState.value = wordInfoState.value.copy(
                            data = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(UiEvent.ShowSnackBar(result.message ?: "error"))
                    }
                    is Resource.Loading -> {
                        _wordInfoState.value = wordInfoState.value.copy(
                            data = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }
}
