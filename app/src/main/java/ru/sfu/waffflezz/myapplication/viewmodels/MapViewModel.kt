package ru.sfu.waffflezz.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.sfu.waffflezz.myapplication.data.entities.MarkerCoordinate

class MapViewModel() : ViewModel() {
    private val apiKey = "AIzaSyCf5Ts3IPlgNUmsWahPR5cOOxWVko6y_0w"

    private val _markers = MutableStateFlow<List<MarkerCoordinate>>(listOf(
        MarkerCoordinate(56.02504619778095, 92.84746186721047),
        MarkerCoordinate(56.02988478324212, 92.79319120853661),
        MarkerCoordinate(55.983530168263215, 92.89092208414334),
        MarkerCoordinate(56.0040098197374, 92.93290657148485),
        MarkerCoordinate(56.04089876943792, 92.91557133375656),
        MarkerCoordinate(56.068259516698525, 92.92138204933644),
        MarkerCoordinate(56.06375617220559, 92.93087873270193),
        MarkerCoordinate(56.06448903017168, 92.9472212258949),
    ))
    val markers: StateFlow<List<MarkerCoordinate>> = _markers



    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return MapViewModel() as T
            }
        }
    }
}