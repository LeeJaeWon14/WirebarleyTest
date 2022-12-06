package com.jeepchief.wirebarleytest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeepchief.wirebarleytest.model.CurrencyDateService
import com.jeepchief.wirebarleytest.model.LiveDTO
import com.jeepchief.wirebarleytest.model.RetroClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val service = RetroClient.getInstance().create(CurrencyDateService::class.java)

    private val _exchangeRate: MutableLiveData<LiveDTO> by lazy { MutableLiveData<LiveDTO>() }
    val exchangeRate: LiveData<LiveDTO> get() = _exchangeRate
    fun getExchangeRate() {
        viewModelScope.launch {
            _exchangeRate.value = service.getLive()
        }
    }
}