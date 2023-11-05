package com.example.chatu.view.chat_menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chatu.view.chat_menu.ui.theme.Pink80
import com.example.chatu.view.chat_menu.ui.theme.Purple40


@Composable
fun SingleMessage(message: String, isCurrentUser: Boolean) {

    if (isCurrentUser){
        Column(horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp, bottomEnd = 25.dp),
                colors = CardDefaults.cardColors(
                    containerColor =  Purple40
                ),
                elevation = CardDefaults.cardElevation(5.dp)

                ) {
                Text(
                    text = message,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(16.dp),
                    color =  White
                )
            }
        }
    }

    else{
        Box{
            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier.wrapContentSize()
            ) {
                Card(
                    shape = RoundedCornerShape( topEnd = 30.dp, bottomEnd =25.dp, bottomStart = 30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =  Pink80
                    ),
                    elevation = CardDefaults.cardElevation(1.dp),
                    modifier = Modifier.wrapContentSize()


                    ) {
                    Text(
                        text = message,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(16.dp).wrapContentWidth(),
                        maxLines = Int.MAX_VALUE,
                        color = White
                    )
                }
            }
        }

    }
}