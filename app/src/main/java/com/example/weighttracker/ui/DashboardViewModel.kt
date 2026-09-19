package com.example.weighttracker.ui

import androidx.lifecycle.*
import com.example.weighttracker.data.local.WeightEntry
import com.example.weighttracker.data.local.repository.WeightRepository
import kotlinx.coroutines.launch

// Dashboard's view model, handling logic and keeping data if screen rotates
class DashboardViewModel(private val repo: WeightRepository) : ViewModel() {
    private val _history = MutableLiveData<List<WeightEntry>>()
    // Read only version visible for the User Interface to display progress bar
    val history: LiveData<List<WeightEntry>> = _history
    // Holds goal to prefill the input field
    private val _goal = MutableLiveData<Double>()
    val goal: LiveData<Double> = _goal

    // Load user's data by fetching their history and goal (For version 2.0 the default is 220.0)
    fun load(username: String) {
        viewModelScope.launch {
            _history.value = repo.getHistory(username)
            _goal.value = repo.getLastTarget(username) ?: 220.0
        }
    }

    // Saves weight entry and reloads the data so the progress bar gets updated
    fun addWeight(username: String, weight: Double, goal: Double) {
        viewModelScope.launch {
            repo.addWeight(username, weight, goal)
            load(username)
        }
    }
}