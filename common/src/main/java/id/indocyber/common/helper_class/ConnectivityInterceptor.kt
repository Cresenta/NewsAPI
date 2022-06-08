package id.indocyber.common.helper_class

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import id.indocyber.common.entity.ErrorData
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.PrintWriter
import java.io.StringWriter

class ConnectivityInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val builder: Request.Builder = chain.request().newBuilder()
            return chain.proceed(builder.build())
        } catch (e: Exception) {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            Log.e("ConnectionManager", "Not connected to the Internet")
            e.printStackTrace(pw)

            return Response.Builder().code(0)
                .body(
                    Gson().toJson(ErrorData(sw.toString(), 0))
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .message(e.message.orEmpty())
                .build()
        }
    }
}