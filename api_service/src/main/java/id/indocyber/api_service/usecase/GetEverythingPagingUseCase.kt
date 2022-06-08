package id.indocyber.api_service.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import id.indocyber.api_service.paging_data_source.GetEverythingDataSource
import id.indocyber.api_service.service.GetEverythingService

class GetEverythingPagingUseCase(
    private val getEverythingService: GetEverythingService
) {
    operator fun invoke(sources: String, q: String) = Pager(
        config = PagingConfig(25),
        pagingSourceFactory = {
            GetEverythingDataSource(
                getEverythingService,
                sources,
                q)
        }
    )
}