package com.example.praktikumlocalrestapi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumlocalrestapi.modeldata.DataSiswa
import com.example.praktikumlocalrestapi.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface StatusUiSiswa {
    data class Success(val listSiswa: List<DataSiswa>) : StatusUiSiswa
    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}


class HomeViewModel (private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel(){
    var listSiswa: StatusUiSiswa by mutableStateOf(StatusUiSiswa.Loading)
        private set
    init {
        loadSiswa()
    }
    private fun loadSiswa() {
        viewModelScope.launch {
            listSiswa = StatusUiSiswa.Loading
            listSiswa = try {
                StatusUiSiswa.Success(repositoryDataSiswa.getDataSiswa())
            }catch (e: IOException) {
                StatusUiSiswa.Error
            } catch (e: HttpException){
                StatusUiSiswa.Error
            }
        }
    }
}