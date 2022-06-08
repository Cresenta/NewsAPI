package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.GetSourcesService
import id.indocyber.common.AppResponse
import kotlinx.coroutines.flow.flow

class GetSourcesUseCase(
    private val getSourcesService: GetSourcesService
) {
    operator fun invoke(category: String) = flow {
        emit(AppResponse.loading())
        val response = getSourcesService.getSources(
            category = category
        )
        if (response.isSuccessful) {
            response.body()?.let { data ->
                this.emit(AppResponse.success(data))
            } ?: this.emit(AppResponse.error(null))
        } else {
            this.emit(AppResponse.error(null))
        }
    }
}