/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.delay
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(
                    onClickedOnButton = mainViewModel::buttonClicked,
                    startedCounterTime = mainViewModel.triggerStartTime
                )
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(
    onClickedOnButton: () -> Unit = {},
    startedCounterTime: ZonedDateTime? = null
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Timer",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            var timeText by remember { mutableStateOf("00:00:59.000") }

            if (startedCounterTime != null) {
                val targetTime = startedCounterTime.plusSeconds(59)

                LaunchedEffect("test") {
                    do {
                        val secondLeft = ChronoUnit.SECONDS.between(ZonedDateTime.now(), targetTime)
                        timeText = "00:00:$secondLeft.000"
                        delay(900)
                    } while (secondLeft > 0)
                }
            }

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h3,
                text = timeText
            )

            val contentColor = contentColorFor(MaterialTheme.colors.primary)
            Surface(
                modifier = Modifier
                    .clickable(
                        onClick = onClickedOnButton,
                        role = Role.Button
                    )
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 60.dp),
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
                color = MaterialTheme.colors.primary,
                contentColor = contentColor,
                elevation = 6.dp
            ) {
                CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(32.dp)
                            .height(32.dp),
                        imageVector = if (startedCounterTime == null) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                        tint = Color.White,
                        contentDescription = stringResource(id = if (startedCounterTime == null) R.string.play else R.string.pause)
                    )
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
