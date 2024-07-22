package com.example.testexampleappbasic.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testexampleappbasic.domain.RssUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val rssUseCase: RssUseCase):ViewModel() {
    var rssList  = MutableStateFlow<MainUIState>(MainUIState.Empty)
    val mRssList: StateFlow<MainUIState> = rssList
    init {
        fetchRssData()
    }

     fun fetchRssData() {
        viewModelScope.launch {
            rssUseCase()
                .onStart { rssList.value = MainUIState.Loading }
                .catch { rssList.value = MainUIState.Error }
                .collect{
                rssList.value = MainUIState.Success(it)
            }
        }
    }

}