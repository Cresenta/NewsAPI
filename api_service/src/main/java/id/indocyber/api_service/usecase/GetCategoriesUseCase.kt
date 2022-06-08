package id.indocyber.api_service.usecase

import id.indocyber.api_service.Constants.NEWS_CATEGORY
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase {
    operator fun invoke() = flow {
        val list = NEWS_CATEGORY
        emit(list)
    }
}