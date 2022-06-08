package id.indocyber.newsapi.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.GetEverythingPagingUseCase
import id.indocyber.common.entity.everything.Article
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    application: Application,
    private val getEverythingPagingUseCase: GetEverythingPagingUseCase
) : AndroidViewModel(application) {
    val pagingData = MutableLiveData<PagingData<Article>>()
    var searchText = ""

    fun getArticles(sources: String, q: String = ""){
        viewModelScope.launch {
            getEverythingPagingUseCase.invoke(sources, q)
                .flow
                .cachedIn(viewModelScope)
                .collect {
                    pagingData.postValue(it)
                }
        }
    }
}