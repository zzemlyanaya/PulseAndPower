package ru.zzemlyanaya.pulsepower.core.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.zzemlyanaya.pulsepower.core.http.AuthCredentialsCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val authCredentialsCache: AuthCredentialsCache
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = authCredentialsCache.authSid?.let {
        chain.proceed(getAuthHeaderedRequest(it, chain.request()))
    } ?: chain.proceed(chain.request())


    private fun getAuthHeaderedRequest(sid: String, request: okhttp3.Request) =
        request.newBuilder()
            .addHeader(HEADER_X_AUTH_SID, sid)
            .build()

    companion object {
        private const val HEADER_X_AUTH_SID = "X-AuthSid"
    }

}