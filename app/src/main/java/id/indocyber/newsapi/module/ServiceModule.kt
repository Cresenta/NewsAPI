package id.indocyber.newsapi.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.indocyber.api_service.RetrofitClient
import id.indocyber.api_service.service.GetEverythingService
import id.indocyber.api_service.service.GetSourcesService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideRetrofit (@ApplicationContext context: Context) =
        RetrofitClient.getClient(context)

    @Provides
    @Singleton
    fun provideGetSourcesService(retrofit: Retrofit) =
        retrofit.create(GetSourcesService::class.java)

    @Provides
    @Singleton
    fun provideGetEverythingService(retrofit: Retrofit) =
        retrofit.create(GetEverythingService::class.java)
}