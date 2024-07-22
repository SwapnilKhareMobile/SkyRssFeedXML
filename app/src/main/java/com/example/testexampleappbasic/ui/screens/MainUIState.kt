package com.example.testexampleappbasic.ui.screens

import com.example.testexampleappbasic.model.Item

sealed class MainUIState{
    data object Loading: MainUIState()
    data object Error: MainUIState()
    data class Success(val data: List<Item>): MainUIState()
    data object Empty: MainUIState()
}