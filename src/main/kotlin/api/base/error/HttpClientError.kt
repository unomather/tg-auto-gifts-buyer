package api.base.error

sealed class HttpClientError(
    override val message: String
): Throwable(message) {
    data class ApiRequestError(
        val route: String,
        val error: Throwable
    ): HttpClientError(message = buildApiRequestErrorMessage(route, error))

    data class ParsingApiResponseError(
        val route: String,
        val error: Throwable,
        val responseBodyString: String
    ): HttpClientError(
        message = buildParsingApiRequestErrorMessage(route, error, responseBodyString)
    )
}

/**
 * API REQUEST ERROR
 */
private fun buildApiRequestErrorMessage(route: String, error: Throwable): String {
    return """
        There is an API error on a route = $route. 
        Error message = ${error.message}
    """.trimIndent()
}

/**
 * PARSING API REQUEST ERROR
 */
private fun buildParsingApiRequestErrorMessage(
    route: String,
    error: Throwable,
    responseBodyString: String
): String {
    return """
        We can't parse ResponseJson on a route = $route.
        Error message = ${error.message}
        ResponseBodyString = $responseBodyString
    """.trimIndent()
}