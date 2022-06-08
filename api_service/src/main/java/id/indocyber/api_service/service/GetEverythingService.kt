package id.indocyber.api_service.service

import id.indocyber.api_service.Constants.API_KEY
import id.indocyber.common.entity.everything.GetEverythingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetEverythingService {
    @GET("everything")
    suspend fun getEverything(
        @Query("sources") sources: String,
        @Query("q") q: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<GetEverythingResponse>
}