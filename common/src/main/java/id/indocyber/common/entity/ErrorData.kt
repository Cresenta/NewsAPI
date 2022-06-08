package id.indocyber.common.entity

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("body")
    val body: String,
    @SerializedName("code")
    val code: Int
)