package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.entity.WordInfo

@Composable
fun WordInfoItem(wordInfo: WordInfo, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = wordInfo.word,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = wordInfo.phonetic,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = wordInfo.origin
        )

        wordInfo.meanings.forEach {
            Text(text = it.partOfSpeech, fontWeight = FontWeight.Bold)
            it.definitions.forEachIndexed { i, definition ->
                Text(text = "${i + 1}. ${definition.definition}")
                Spacer(modifier = Modifier.height(10.dp))
                definition.example?.let { example ->
                    Text(text = "E: $example")
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}