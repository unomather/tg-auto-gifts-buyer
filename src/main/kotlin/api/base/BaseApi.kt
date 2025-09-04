package api.base

import api.base.HttpRequestType.*
import api.base.error.HttpClientError.*
import core.extensions.runCatchingApp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

enum class HttpRequestType {
    GET, POST, DELETE
}

data class ApiParameter(
    val name: String,
    val data: Any?
)

abstract class BaseApi: KoinComponent {

    protected val json by inject<Json>()
    protected val client by inject<HttpClient>()

    protected suspend inline fun <reified T> makeHttpRequest(
        route: String,
        type: HttpRequestType,
        parameters: List<ApiParameter> = listOf(),
        bodyString: String = "",
        crossinline headers: HeadersBuilder.() -> Unit = {},
    ): T {
        val httpCallback: HttpRequestBuilder.() -> Unit = {
            url("https://api.telegram.org/bot8359890378:AAEcNGb_Zy5e7ehh1SOBnilzThn5xOcMaqY/$route")
            headers {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                headers()
            }
            if (bodyString.isNotEmpty()) {
                setBody(bodyString)
            }
            parameters.forEach { param ->
                if (param.data != null) {
                    parameter(param.name, param.data)
                }
            }
        }
        return makeApiCall(
            route = route,
            type = type,
            httpCallback = httpCallback
        )
    }

    protected suspend inline fun <reified T> makeApiCall(
        route: String,
        type: HttpRequestType,
        crossinline httpCallback: HttpRequestBuilder.() -> Unit
    ): T {
        return runCatchingApp {
            executeHttpCallback<T>(
                route = route,
                type = type,
                httpCallback = httpCallback
            )
        }.mapCatching { parsedResponseModel ->
            parsedResponseModel
        }.getOrElse { safeApiCallError ->
            safeApiCallError.message.orEmpty().let { errorMessage ->
                error(errorMessage)
            }
        }
    }

    protected suspend inline fun <reified T> executeHttpCallback(
        route: String,
        type: HttpRequestType,
        crossinline httpCallback: HttpRequestBuilder.() -> Unit
    ): T {
        return runCatchingApp {
            when (type) {
                GET -> client.get(httpCallback)
                POST -> client.post(httpCallback)
                DELETE -> client.delete(httpCallback)
            }
        }.onFailure { error ->
            throw ApiRequestError(route, error)
        }.mapCatching { response ->
            val stringResponse = response.body<String>().also { string ->
                println(string)
            }
            parseModelFromStringResponse<T>(
                route = route,
                responseBodyString = stringResponse
            )
        }.getOrElse { error ->
            throw error
        }
    }

    protected inline fun <reified T> parseModelFromStringResponse(
        route: String,
        responseBodyString: String
    ): T {
        return runCatchingApp {
            val env = json.decodeFromString<TgEnvelope<JsonElement>>(responseBodyString)
            if (!env.ok) {
                val ra = env.parameters?.retryAfter
                val msg = buildString {
                    append(env.description ?: "Bot API error")
                    if (ra != null) append(" (retry_after=$ra)")
                }
                throw IllegalStateException("API error [$route]: $msg")
            }
            if (T::class == Unit::class) {
                @Suppress("UNCHECKED_CAST")
                return@runCatchingApp Unit as T
            }
            json.decodeFromJsonElement<T>(env.result)
        }.mapCatching { model ->
            model
        }.getOrElse { error ->
            throw ParsingApiResponseError(
                route = route,
                error = error,
                responseBodyString = responseBodyString
            )
        }
    }
}