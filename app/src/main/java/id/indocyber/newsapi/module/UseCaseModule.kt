package id.indocyber.newsapi.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.indocyber.api_service.service.GetEverythingService
import id.indocyber.api_service.service.GetSourcesService
import id.indocyber.api_service.usecase.GetCategoriesUseCase
import id.indocyber.api_service.usecase.GetEverythingPagingUseCase
import id.indocyber.api_service.usecase.GetSourcesUseCase

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetCategoryUseCase() =
        GetCategoriesUseCase()

    @Provides
    fun provideGetSourcesUseCase(getSourcesService: GetSourcesService) =
        GetSourcesUseCase(getSourcesService)

    @Provides
    fun provideGetEverythingPagingUseCase(getEverythingService: GetEverythingService) =
        GetEverythingPagingUseCase(getEverythingService)
}