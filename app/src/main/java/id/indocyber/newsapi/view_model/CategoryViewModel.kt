package id.indocyber.newsapi.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.GetCategoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    application: Application,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : AndroidViewModel(application) {
    val getCategoriesState = MutableLiveData<List<String>>()

    init {
        getCategories()
    }

    fun getCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            getCategoriesUseCase().collect {
                runBlocking(Dispatchers.Main) {
                    getCategoriesState.postValue(it)
                }
            }
        }
    }
}