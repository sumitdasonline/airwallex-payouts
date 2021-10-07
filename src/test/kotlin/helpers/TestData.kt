package helpers

import org.json.JSONObject

/**
 * Key to remove argument
 * To hold the arguments in a much more readable way.
 * This is used in some Parameterized Tests.
 * @property name
 * @property jsonPathToRemove
 * @property errorResponse
 * @constructor Create empty Key to remove argument
 */
data class KeyToRemoveArgument(val name: String, val jsonPathToRemove: String, val errorResponse: ErrorResponse? = null)

/**
 * Error response
 *
 * @property code
 * @property message
 * @property source
 * @constructor Create empty Error response
 */
data class ErrorResponse(val code: String, val message: String, val source: String)

/**
 * Create beneficiaries
 * Object to hold Create Beneficiary request
 * @property beneficiary
 * @constructor Create empty Create beneficiaries
 */
data class CreateBeneficiaries(val beneficiary: beneficiary)

/**
 * Beneficiary
 * Object To hold Beneficiary details for Create beneficiary JSON
 * @property bank_details
 * @constructor Create empty Beneficiary
 */
data class beneficiary(val bank_details: bank_details)

/**
 * Bank_details
 * Object to hold Bank details for Create Beneficiary JSON
 * @property account_currency
 * @property account_name
 * @property account_number
 * @property account_routing_type1
 * @property account_routing_type2
 * @property account_routing_value1
 * @property account_routing_value2
 * @property bank_branch
 * @property bank_country_code
 * @property bank_name
 * @property bank_street_address
 * @property binding_mobile_number
 * @property fingerprint
 * @property iban
 * @property local_clearing_system
 * @property swift_code
 * @property transaction_reference
 * @constructor Create empty Bank_details
 */
data class bank_details(
    val account_currency: String,
    val account_name: String,
    val account_number: String,
    val account_routing_type1: String,
    val account_routing_type2: String,
    val account_routing_value1: String,
    val account_routing_value2: String,
    val bank_branch: String,
    val bank_country_code: String,
    val bank_name: String,
    val bank_street_address: String,
    val binding_mobile_number: String,
    val fingerprint: String,
    val iban: String,
    val local_clearing_system: String,
    val swift_code: String,
    val transaction_reference: String
)

/**
 * Account currency
 *
 * @constructor Create empty Account currency
 */
enum class AccountCurrency {
    AUD, CAD, CNY, EUR, GBP, HKD, IDR, INR, JPY, MYR, NZD, PHP, SGD, THB, USD, VND, CHF, KRW, BDT, LKR, TRY, PKR, NPR, DKK, NOK, SEK, CZK, HUF, HRK, RON, PLN, MXN, ZAR, ILS
}

/**
 * Bank country code
 *
 * @property swiftCode Holds a default valid Swift Code for that country
 * @constructor Create empty Bank country code
 */
enum class BankCountryCode(val swiftCode: SwiftCode) {
    US(SwiftCode.ABBVUS44), AU(SwiftCode.AAYBAU2S), CN(SwiftCode.BKCHCNBJ110)
}

enum class SwiftCode {
    ABBVUS44, AAYBAU2S, BKCHCNBJ110
}

/**
 * Payment methods
 *
 * @constructor Create empty Payment methods
 */
enum class PaymentMethods {
    SWIFT, LOCAL
}

/**
 * Status
 * Status codes and lines for REST API response codes.
 * @property code
 * @property message
 * @constructor Create empty Status
 */
enum class Status(val code: Int, val message: String) {
    CREATED(201, "HTTP/1.1 201 Created"),
    BAD_REQUEST(400, "HTTP/1.1 400 Bad Request"),
    UNAUTHORISED(401, "HTTP/1.1 401 Unauthorized"),
    UNSUPPORTED(415,"HTTP/1.1 415 Unsupported Media Type")
}

/**
 * Get Random String
 * Generated an Alphanumeric String of given length
 * @param length
 * @return alpha Numeric String Of Desired Length
 */
fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

/**
* Put Value Into JSON Key
* Puts a value into a JSON key path. Chainable form the String, The string has to be a JSON.
 * Also this will not work with JSONArray elements. Since we are not using that in this assignment , skipped it.
* @param length
* @return JSONObject after putting the value to that Key.
*/
fun String.putValueIntoJsonKey(path: String, value: String): JSONObject {
    val splitPath = path.split(".", limit = 2)
    return if (splitPath.size == 2) {
        JSONObject(this).put(
            splitPath[0],
            JSONObject(this).getJSONObject(splitPath[0]).toString().putValueIntoJsonKey(splitPath[1], value)
        )
    } else
        JSONObject(this).put(splitPath[0], value)
}

/**
 * Delete key from JSON
 * Deletes the given key. Chainable with the String.
 * Also this will not work with JSONArray elements. Since we are not using that in this assignment , skipped it.
 * @param length
 * @return JSONObject after deleting the key
 */
fun String.deleteKeyFromJson(path: String): JSONObject {
    val splitPath = path.split(".", limit = 2)
    return if (splitPath.size == 2) {
        JSONObject(this).put(
            splitPath[0],
            JSONObject(this).getJSONObject(splitPath[0]).toString().deleteKeyFromJson(splitPath[1])
        )
    } else {
        val jsonObject = JSONObject(this)
        jsonObject.remove(splitPath[0])
        jsonObject
    }
}