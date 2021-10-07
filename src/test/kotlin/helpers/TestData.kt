package helpers

import org.json.JSONObject

data class KeyToRemoveArgument(val name: String, val jsonPathToRemove: String, val errorResponse: ErrorResponse? = null)
data class ErrorResponse(val code: String, val message: String, val source: String)

data class CreateBeneficiaries(val beneficiary: beneficiary)
data class beneficiary(val bank_details: bank_details)
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

enum class AccountCurrency {
    AUD, CAD, CNY, EUR, GBP, HKD, IDR, INR, JPY, MYR, NZD, PHP, SGD, THB, USD, VND, CHF, KRW, BDT, LKR, TRY, PKR, NPR, DKK, NOK, SEK, CZK, HUF, HRK, RON, PLN, MXN, ZAR, ILS
}

enum class BankCountryCode(val swiftCode: SwiftCode) {
    US(SwiftCode.ABBVUS44), AU(SwiftCode.AAYBAU2S), CN(SwiftCode.BKCHCNBJ110)
}

enum class SwiftCode {
    ABBVUS44, AAYBAU2S, BKCHCNBJ110
}

enum class EntityType {
    PERSONAL, COMPANY
}

enum class PaymentMethods {
    SWIFT, LOCAL
}

enum class Status(val code: Int, val message: String) {
    CREATED(201, "HTTP/1.1 201 Created"), BAD_REQUEST(400, "HTTP/1.1 400 Bad Request"), UNAUTHORISED(
        401,
        "HTTP/1.1 401 Unauthorized"
    )
}

fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

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