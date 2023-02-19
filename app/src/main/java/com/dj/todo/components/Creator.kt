package com.dj.todo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.todo.util.CreatorState

@Composable
fun IntroductionPage2(
    creatorState: (CreatorState) -> Unit,
) {
    MaterialTheme {
        Column() {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {

                AlertDialog(

                    onDismissRequest = {
                        openDialog.value = false
                    }, title = {
                        Text(
                            text = "Meet the Editor", style = TextStyle(
                                fontWeight = FontWeight.Bold, fontSize = 18.sp
                            )
                        )
                    }, text = {
                        Column(
                        ) {
                            Text(
                                text = "Lee, Dongjin", style = TextStyle(
                                    fontWeight = FontWeight.SemiBold, fontSize = 14.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Lee, Dongjin is a talented software engineer with a passion for developing innovative and user-friendly mobile applications. Dongjin always has been eager to learn more to deliver high-quality, cutting-edge apps that meet the needs of users.",
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal, fontSize = 14.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Contact: Lee, Dongjin", style = TextStyle(
                                    fontWeight = FontWeight.Bold, fontSize = 18.sp
                                )
                            )
                            Text(
                                text = "Email: djdw2657@gmail.com", style = TextStyle(
                                    fontWeight = FontWeight.SemiBold, fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "Phone: +82 (010) 2804-2657", style = TextStyle(
                                    fontWeight = FontWeight.SemiBold, fontSize = 14.sp
                                )
                            )
                        }

                    }, confirmButton = {}, dismissButton = {
                        Button(onClick = {
                            openDialog.value = false
                            creatorState(CreatorState.CLOSED)

                        }) {
                            Text("닫기")
                        }
                    })
            }
        }

    }
}

@Preview
@Composable
fun PreviewMyDialog() {
    IntroductionPage2(creatorState = {})
}







