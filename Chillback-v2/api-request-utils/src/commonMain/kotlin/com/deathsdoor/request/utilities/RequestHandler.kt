package com.deathsdoor.request.utilities

import com.deathsdoor.request.utilities.extensions.httpClientEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

abstract class RequestHandler {
    protected open val client : HttpClient by lazy {
        if(apiKey != null) HttpClient(httpClientEngine)
        else HttpClient(httpClientEngine){
           defaultRequest {
               header("X-RapidAPI-Key",rapidApiKey!!)
               header("X-RapidAPI-Host",rapidApiHost!!)
           }
        }
    }

    protected open val json : Json by lazy { Json }

    protected abstract val baseURL : String


    protected open val apiKey : Pair<String,String>? = null
    protected open val rapidApiKey : String? = null
    protected open val rapidApiHost : String? = null

    var onError : suspend (response: HttpResponse) -> Unit = {}

    protected open suspend fun HttpResponse.responseMessage(): JsonObject? = json.decodeFromString<JsonObject>(this.bodyAsText())
    protected open suspend fun HttpResponse.responseHeader(): JsonElement? = this.responseMessage()?.jsonObject?.get("header")
    protected open suspend fun HttpResponse.responseStatusCode(): Int = this.responseHeader()?.jsonObject?.get("status_code").toString().toInt()
    protected open suspend fun HttpResponse.responseBody(): JsonObject? = this.responseMessage()?.jsonObject?.get("body")?.jsonObject


    protected open fun concentrateBaseUndEndpoint(endpoint: String) = "$baseURL$endpoint?"

    protected open suspend fun requestHttpRequest(
        endpoint: String,
        parameters: Map<String,Any?>,
    ): HttpResponse = client.get(concentrateBaseUndEndpoint(endpoint)){
        apiKey?.let { parameter(it.first,it.second) }
        parameters.forEach { parameter(it.key,it.value) }
    }

    protected open suspend fun <T> performDefaultAction(
        endpoint: String,
        parameters: Map<String,Any?> = mapOf(),
        deserializer : (jsonObject: JsonObject?) -> T
    ): T? {
        val response = requestHttpRequest(endpoint,parameters)
        if(!response.status.isSuccess()){
            onError(response)
            return null
        }
        return deserializer(response.responseBody())
    }
}