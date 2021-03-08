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
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import java.time.ZonedDateTime
import java.time.temporal.TemporalUnit
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val mainViewModel by viewModels<MainViewModel>()

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

            if (startedCounterTime == null) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h3,
                    text = "00:01:00.000"
                )
            } else {
//                val infiniteTransition = rememberInfiniteTransition()
//                val count = startedCounterTime.plusMinutes(1).second - ZonedDateTime.now().second
//                val anim = remember {
//                    TargetBasedAnimation(
//                        animationSpec = tween(200),
//                        typeConverter = Float.VectorConverter,
//                        initialValue = 200f,
//                        targetValue = 1000f
//                    )
//                }
//                var playTime by remember { mutableStateOf(0L) }
//
//                scope.launch {
//                    val startTime = withFrameNanos { it }
//
//                    do {
//                        playTime = withFrameNanos { it } - startTime
//                        val animationValue = anim.getValueFromNanos(playTime)
//                    } while (someCustomCondition())
//                }
//
//
//                Text(
//                    modifier = Modifier.align(Alignment.CenterHorizontally),
//                    style = MaterialTheme.typography.h3,
//                    text = time
//                )
            }

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
