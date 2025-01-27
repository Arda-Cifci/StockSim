package com.example.cmpt362_stocksim

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class StockDatabaseViewModel(private val repository: StockDatabaseRepository): ViewModel(), Serializable {
    val allStockLiveData: LiveData<List<Stock>> = repository.allStocks.asLiveData()

    fun insert(stock: Stock){
        repository.insert(stock)
    }

    fun updateStock(stock: Stock) {
        viewModelScope.launch {
            repository.updateStock(stock)
        }
    }


    private val stockByIdLiveData = MutableLiveData<Stock>()
    val stockById: LiveData<Stock> get() = stockByIdLiveData

    fun getStockById(id: Long) {
        viewModelScope.launch {
            val stock = withContext(Dispatchers.IO){
                repository.getStockById(id)
            }
            stockByIdLiveData.value = stock
        }
    }
    private val stockByNameLiveData = MutableLiveData<Stock>()
    val stockByName: LiveData<Stock> get() = stockByNameLiveData

    fun getStockByName(name: String) {
        viewModelScope.launch {
            val stock = withContext(Dispatchers.IO){
                repository.getStockByName(name)
            }
            stockByNameLiveData.value = stock
        }
    }

    private val stockQuantityByIdLiveData = MutableLiveData<Int>()
    val stockQuantityById: LiveData<Int> get() = stockQuantityByIdLiveData

    fun getStockQuantityById(id: Long) {
        viewModelScope.launch {
            val quantity = withContext(Dispatchers.IO){
                repository.getStockQuantity(id)
            }
            stockQuantityByIdLiveData.value = quantity
        }
    }
    private val stockQuantityByNameLiveData = MutableLiveData<Int>()
    val stockQuantityByName: LiveData<Int> get() = stockQuantityByNameLiveData

    fun getStockQuantityByName(name: String) {
        viewModelScope.launch {
            // Perform the database query on the IO dispatcher to avoid main thread blocking
            val quantity = withContext(Dispatchers.IO) {
                repository.getStockQuantity(name)
            }
            stockQuantityByNameLiveData.value = quantity
        }
    }

    private val stockTotalValueByIdLiveData = MutableLiveData<Double>()
    val stockTotalValueById: LiveData<Double> get() = stockTotalValueByIdLiveData

    fun getStockTotalValueById(id: Long) {
        viewModelScope.launch {
            val totalValue = withContext(Dispatchers.IO){
                repository.getStockTotalValue(id)
            }
            stockTotalValueByIdLiveData.value = totalValue
        }
    }
    private val stockTotalValueByNameLiveData = MutableLiveData<Double>()
    val stockTotalValueByName: LiveData<Double> get() = stockTotalValueByNameLiveData

    fun getStockTotalValueByName(name: String) {
        viewModelScope.launch {
            val totalValue = withContext(Dispatchers.IO){
                repository.getStockTotalValue(name)
            }
            stockTotalValueByNameLiveData.value = totalValue
        }
    }

    private val stockCashValueByIdLiveData = MutableLiveData<Double>()
    val stockCashValueById: LiveData<Double> get() = stockCashValueByIdLiveData

    fun getStockCashValueById(id: Long) {
        viewModelScope.launch {
            val cashValue = withContext(Dispatchers.IO){
                repository.getStockCashValue(id)
            }
            stockCashValueByIdLiveData.value = cashValue
        }
    }
    private val stockCashValueByNameLiveData = MutableLiveData<Double>()
    val stockCashValueByName: LiveData<Double> get() = stockCashValueByNameLiveData

    fun getStockCashValueByName(name: String) {
        viewModelScope.launch {
            val cashValue = withContext(Dispatchers.IO){
                repository.getStockCashValue(name)
            }
            stockCashValueByNameLiveData.value = cashValue
        }
    }

    fun setStockQuantity(name: String, newStockQuantity: Int) {
        viewModelScope.launch {
            repository.setStockQuantity(name, newStockQuantity)
        }
    }

    fun setStockTotalValue(name: String, newTotalValue: Double) {
        viewModelScope.launch {
            repository.setStockTotalValue(name, newTotalValue)
        }
    }

    fun setStockCashValue(name: String, newStockCashValue: Double) {
        viewModelScope.launch {
            repository.setStockCashValue(name, newStockCashValue)
        }
    }

    fun deleteAll(){
        val stockList = allStockLiveData.value
        if(stockList != null && stockList.size > 0){
            repository.deleteAll()
        }
    }
    fun deleteStock(id: Long){
        val stockList = allStockLiveData.value
        if(stockList != null && stockList.size > 0){
            repository.deleteStock(id)
        }
    }


    // I ADDED THIS IDK IF WE NEED IT THO
    class stockViewModelFactory(private val repository: StockDatabaseRepository): ViewModelProvider.Factory{
        override  fun <T: ViewModel> create(modelClass:Class<T>): T {
            if (modelClass.isAssignableFrom(StockDatabaseViewModel::class.java))
                return StockDatabaseViewModel(repository) as T
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }


}