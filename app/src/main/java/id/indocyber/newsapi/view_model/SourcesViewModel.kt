package id.indocyber.newsapi.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.GetSourcesUseCase
import id.indocyber.common.AppResponse
import id.indocyber.common.entity.sources.GetSourcesResponse
import id.indocyber.common.entity.sources.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    application: Application,
    private val getSourcesUseCase: GetSourcesUseCase
) : AndroidViewModel(application) {
    val getSourcesState = MutableLiveData<AppResponse<GetSourcesResponse>>()
    val searchText = MutableLiveData<String>()
    var sourceList = listOf<Source>()
    val selectedSourceIds = mutableListOf<String>()

    fun getSources(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            getSourcesUseCase(category).collect {
                runBlocking {
                    getSourcesState.postValue(it)
                }
            }
        }
    }

    fun filterBySearch() {
        val text = searchText.value.orEmpty()
        val sources = getSourcesState.value?.data?.sources
        sourceList = if (text.isNotBlank()) {
            sources?.filter { source ->
                source.name.contains(text.trim(), ignoreCase = true)
            }.orEmpty()
        } else {
            sources.orEmpty()
        }
    }
}