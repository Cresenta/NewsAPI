package id.indocyber.common.entity.everything

import com.google.gson.annotations.SerializedName

data class GetEverythingResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)