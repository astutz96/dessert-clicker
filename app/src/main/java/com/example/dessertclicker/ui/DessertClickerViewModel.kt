package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertClickerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DessertClickerUiState())
    val uiState: StateFlow<DessertClickerUiState> = _uiState.asStateFlow()

    private var desserts: MutableSet<Dessert> = mutableSetOf()
    private var currentDessertIndex :  Int = 0;

    init {
        desserts = dessertList.toMutableSet()
        _uiState.value = DessertClickerUiState(revenue = desserts.first().)
    }

    fun userClickedDessert() {
        val dessertToShow = determineDessertToShow()
        
        _uiState.update { currentState ->
            currentState.copy(
                revenue = currentState.currentDessertPrice.plus(currentState.revenue),
                dessertsSold = currentState.dessertsSold.inc(),
                currentDessertImageId = dessertToShow.imageId,
                currentDessertPrice = dessertToShow.price
            )
        }
        
    }
    
    fun determineDessertToShow(): Dessert{
        var dessertToShow = desserts.first()
        for(dessert in desserts){
            if(_uiState.value.dessertsSold >= dessert.startProductionAmount){
                dessertToShow = dessert
            }else{
                break
            }
        }
        return dessertToShow
    }

}