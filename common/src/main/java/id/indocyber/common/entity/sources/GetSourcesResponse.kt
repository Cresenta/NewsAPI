package id.indocyber.common.entity.sources

import com.google.gson.annotations.SerializedName

data class GetSourcesResponse(
    @SerializedName("sources")
    val sources: List<Source>,
    @SerializedName("status")
    val status: String
)