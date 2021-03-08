package com.example.androiddevchallenge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.ZonedDateTime

class MainViewModel : ViewModel() {

    var triggerStartTime by mutableStateOf<ZonedDateTime?>(null)

    fun buttonClicked() {
        triggerStartTime = (if (triggerStartTime == null) ZonedDateTime.now() else null)
    }
}