package helpers

import io.restassured.http.Header
import io.restassured.http.Headers

/**
 * Create authentication headers
 * Authentication header required to create Authentication Token
 * @return Headers
 */
fun createAuthenticationHeaders() = Headers(
    listOf(
        Header("Content-Type", "application/json"),
        Header("x-client-id",readProperties().getProperty("x-client-id")),
        Header("x-api-key",readProperties().getProperty("x-api-key"))
    )
)

/**
 * Create beneficiary headers
 * Headers for Create Beneficiary Endpoint
 * @param loginToken
 * @return Headers
 */
fun createBeneficiaryHeaders(loginToken : String) = Headers(
    listOf(
        Header("Content-Type", "application/json"),
        Header("Authorization","Bearer $loginToken")
    )
)