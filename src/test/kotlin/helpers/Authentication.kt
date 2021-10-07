package helpers

import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When

val baseURL: String = readProperties().getProperty("baseURL")
val loginAuthentication: String = readProperties().getProperty("loginAuthentication")

/**
 * Get token
 * Create a bearer token to use in other airwallex Demo API
 * @return Extracting the actual token as String from the response body
 */
fun getToken(): String{
    val response = Given {
        headers(createAuthenticationHeaders())
        body("{}")
    } When {
        post("$baseURL$loginAuthentication")
    } Then {
    } Extract {
        body()
    }
    return response.path("token")
}