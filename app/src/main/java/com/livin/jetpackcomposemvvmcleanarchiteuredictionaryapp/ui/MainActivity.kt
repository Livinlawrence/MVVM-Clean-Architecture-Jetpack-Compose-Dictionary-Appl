package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.ui.theme.DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryTheme {
                val viewModel by viewModels<WordInfoViewModel>()
                val wordState = viewModel.wordInfoState.value
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest {
                        when (it) {
                            is WordInfoViewModel.UiEvent.ShowSnackBar -> {
                                scaffoldState.snackbarHostState.showSnackbar(message = it.message)
                            }
                        }
                    }
                }

                Scaffold(scaffoldState = scaffoldState) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colors.background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            TextField(
                                value = viewModel.searchFieldState.value,
                                onValueChange = viewModel::searchWord,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(text = "Search here")
                                }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items(wordState.data.size) { i ->
                                    val item = wordState.data[i]
                                    if (i > 0) {
                                        Spacer(modifier = Modifier.height(20.dp))
                                    }

                                    WordInfoItem(wordInfo = item)

                                    if (i < wordState.data.size - 1){
                                        Divider()
                                    }
                                }
                            }
                        }
                    }

                    if(wordState.isLoading) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize() ){
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}