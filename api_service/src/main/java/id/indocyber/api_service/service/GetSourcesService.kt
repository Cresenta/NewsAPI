package id.indocyber.api_service.service

import id.indocyber.api_service.Constants.API_KEY
import id.indocyber.common.entity.sources.GetSourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSourcesService {
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String = "",
        @Query("language") language: String = "",
        @Query("country") country: String = "",
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<GetSourcesResponse>
}