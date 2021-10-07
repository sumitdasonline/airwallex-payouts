package api.v1.beneficiaries.createbeneficiaries

import com.beust.klaxon.Klaxon
import helpers.BankCountryCode
import helpers.CreateBeneficiaries
import helpers.ErrorResponse
import helpers.KeyToRemoveArgument
import helpers.SwiftCode
import helpers.beneficiary
import helpers.createBeneficiaryHeaders
import helpers.getRandomString
import helpers.getToken
import helpers.readProperties
import io.restassured.http.Headers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.provider.Arguments
import java.io.File
import java.io.StringReader

abstract class CreateBeneficiarySetUp {

    companion object {
        private val baseURL: String = readProperties().getProperty("baseURL")
        private val createBeneficiaries: String = readProperties().getProperty("createBeneficiaries")
        val endpoint = "$baseURL$createBeneficiaries"

        private val payloads: String = readProperties().getProperty("payloads")
        val schemas: String = readProperties().getProperty("schemas")

        val CreateBeneficiariesFullPayload =
            File("$payloads/CreateBeneficiariesFull.json").readText()
        val CreateBeneficiariesHappyFlowPayload =
            File("$payloads/CreateBeneficiariesHappyFlow.json").readText()
        val createBeneficiariesRequest = Klaxon().parse<CreateBeneficiaries>(StringReader(CreateBeneficiariesFullPayload))
        var headers = createBeneficiaryHeaders(getToken())

        @JvmStatic
        fun removeNonMandatoryFieldsBankDetails() = listOf(
            KeyToRemoveArgument("Account Routing Type 1", "beneficiary.bank_details.account_routing_type1"),
            KeyToRemoveArgument("Account Routing Type 2", "beneficiary.bank_details.account_routing_type2"),
            KeyToRemoveArgument("Account Routing Value 1", "beneficiary.bank_details.account_routing_value1"),
            KeyToRemoveArgument("Account Routing Value 2", "beneficiary.bank_details.account_routing_value2"),
            KeyToRemoveArgument("Bank Branch", "beneficiary.bank_details.bank_branch"),
            KeyToRemoveArgument("Bank Street Address", "beneficiary.bank_details.bank_street_address"),
            KeyToRemoveArgument("Fingerprint", "beneficiary.bank_details.fingerprint"),
            KeyToRemoveArgument("Binding Mobile Number", "beneficiary.bank_details.binding_mobile_number"),
            KeyToRemoveArgument("iban", "beneficiary.bank_details.iban"),
            KeyToRemoveArgument("Local Clearing System", "beneficiary.bank_details.local_clearing_system"),
            KeyToRemoveArgument("Transaction Reference", "beneficiary.bank_details.transaction_reference")
        )

        @JvmStatic
        fun blankValuesInNonMandatoryFieldsBankDetails() = listOf(
            KeyToRemoveArgument("Account Routing Value 1", "beneficiary.bank_details.account_routing_value1"),
            KeyToRemoveArgument("Account Routing Value 2", "beneficiary.bank_details.account_routing_value2"),
            KeyToRemoveArgument("Bank Branch", "beneficiary.bank_details.bank_branch"),
            KeyToRemoveArgument("Bank Street Address", "beneficiary.bank_details.bank_street_address"),
            KeyToRemoveArgument("Fingerprint", "beneficiary.bank_details.fingerprint"),
            KeyToRemoveArgument("Binding Mobile Number", "beneficiary.bank_details.binding_mobile_number"),
            KeyToRemoveArgument("iban", "beneficiary.bank_details.iban"),
            KeyToRemoveArgument("Local Clearing System", "beneficiary.bank_details.local_clearing_system"),
            KeyToRemoveArgument("Transaction Reference", "beneficiary.bank_details.transaction_reference")
        )

        @JvmStatic
        fun removeMandatoryFieldsBankDetails() = listOf(
            KeyToRemoveArgument(
                "Account Currency",
                "beneficiary.bank_details.account_currency",
                ErrorResponse("field_required", "must not be null", "beneficiary.bank_details.account_currency")
            ),
            KeyToRemoveArgument(
                "Account Name",
                "beneficiary.bank_details.account_name",
                ErrorResponse(
                    "payment_schema_validation_failed",
                    "This field is required",
                    "beneficiary.bank_details.account_name"
                )
            ),
            KeyToRemoveArgument(
                "Account Number",
                "beneficiary.bank_details.account_number",
                ErrorResponse(
                    "payment_schema_validation_failed",
                    "This field is required",
                    "beneficiary.bank_details.account_number"
                )
            ),
            KeyToRemoveArgument(
                "Bank Country Code",
                "beneficiary.bank_details.bank_country_code",
                ErrorResponse("field_required", "must not be null", "beneficiary.bank_details.bank_country_code")
            ),
            KeyToRemoveArgument(
                "Bank Name",
                "beneficiary.bank_details.bank_name",
                ErrorResponse(
                    "payment_schema_validation_failed",
                    "This field is required",
                    "beneficiary.bank_details.bank_name"
                )
            ),
            KeyToRemoveArgument(
                "Swift Code",
                "beneficiary.bank_details.swift_code",
                ErrorResponse(
                    "payment_schema_validation_failed",
                    "This field is required",
                    "beneficiary.bank_details.swift_code"
                )
            ),
        )

        @JvmStatic
        fun blankValuesInMandatoryFieldsBankDetails() = listOf(
            KeyToRemoveArgument("Account Routing Type 1", "beneficiary.bank_details.account_routing_type1",
                ErrorResponse("invalid_argument"," is not a valid type","beneficiary.bank_details.account_routing_type1")
            ),
            KeyToRemoveArgument("Account Routing Type 2", "beneficiary.bank_details.account_routing_type2",
                ErrorResponse("invalid_argument"," is not a valid type","beneficiary.bank_details.account_routing_type2")
            ),
            KeyToRemoveArgument(
                "Account Currency",
                "beneficiary.bank_details.account_currency",
                ErrorResponse("invalid_argument", "beneficiary.bank_details.account_currency is not of the expected type; please refer to our API documentation", "beneficiary.bank_details.account_currency")
            ),
            KeyToRemoveArgument(
                "Account Name",
                "beneficiary.bank_details.account_name",
                ErrorResponse(
                    "payment_schema_validation_failed",
                    "This field is required",
                    "beneficiary.bank_details.account_name"
                )
            ),
            KeyToRemoveArgument(
                "Account Number",
                "beneficiary.bank_details.account_number",
                ErrorResponse(
                    "payment_schema_validation_failed",
                    "This field is required",
                    "beneficiary.bank_details.account_number"
                )
            ),
            KeyToRemoveArgument(
                "Bank Country Code",
                "beneficiary.bank_details.bank_country_code",
                ErrorResponse("invalid_argument", " is not a valid type", "beneficiary.bank_details.bank_country_code")
            ),
            KeyToRemoveArgument(
                "Bank Name",
                "beneficiary.bank_details.bank_name",
                ErrorResponse(
                    "payment_schema_validation_failed",
                    "This field is required",
                    "beneficiary.bank_details.bank_name"
                )
            ),
            KeyToRemoveArgument(
                "Swift Code",
                "beneficiary.bank_details.swift_code",
                ErrorResponse(
                    "payment_schema_validation_failed",
                    "This field is required",
                    "beneficiary.bank_details.swift_code"
                )
            ),
        )

        @JvmStatic
        fun accountNameInvalid() = listOf(
            Arguments.of("Account Name with 1 letter", getRandomString(1)),
            Arguments.of("Account Name with 201 letters", getRandomString(201)),
        )

        @JvmStatic
        fun accountNameValid() = listOf(
            "!@#$5^&*()",
            "李张黄",
            "xìngshì-sung-yun",
            getRandomString(200),
            getRandomString(2)
        )

        @JvmStatic
        fun accountNumberInvalidForUS() = listOf(
            Arguments.of(
                "Account number with 18 char",
                getRandomString(18),
                "Length of account_number should be between 1 and 17 when bank_country_code is 'US'"
            ),
            Arguments.of(
                "Account number with 35 char",
                getRandomString(35),
                "Length of account_number should be between 1 and 17 when bank_country_code is 'US'"
            ),
            Arguments.of("Account number with special char", "!@$%^^^&&", "Should contain alphanumeric characters only")
        )

        @JvmStatic
        fun accountNumberValidForUS() = listOf(
            Arguments.of("Account Number with 1 char", getRandomString(1)),
            Arguments.of("Account Number with 17 char", getRandomString(17)),
        )

        @JvmStatic
        fun accountNumberInvalidForAU() = listOf(
            Arguments.of(
                "Account number with 5 char",
                getRandomString(5),
                "Length of account_number should be between 6 and 9 when bank_country_code is 'AU'"
            ),
            Arguments.of(
                "Account number with 10 char",
                getRandomString(10),
                "Length of account_number should be between 6 and 9 when bank_country_code is 'AU'"
            ),
            Arguments.of(
                "Account number with 35 char",
                getRandomString(35),
                "Length of account_number should be between 6 and 9 when bank_country_code is 'AU'"
            ),
            Arguments.of("Account number with special char", "!@$%^^^&&", "Should contain alphanumeric characters only")
        )

        @JvmStatic
        fun accountNumberValidForAU() = listOf(
            Arguments.of("Account Number with 6 char", getRandomString(6)),
            Arguments.of("Account Number with 9 char", getRandomString(9)),
        )

        @JvmStatic
        fun accountNumberInvalidForCN() = listOf(
            Arguments.of(
                "Account number with 7 char",
                getRandomString(7),
                "Length of account_number should be between 8 and 20 when bank_country_code is 'CN'"
            ),
            Arguments.of(
                "Account number with 21 char",
                getRandomString(21),
                "Length of account_number should be between 8 and 20 when bank_country_code is 'CN'"
            ),
            Arguments.of(
                "Account number with 35 char",
                getRandomString(35),
                "Length of account_number should be between 8 and 20 when bank_country_code is 'CN'"
            ),
            Arguments.of("Account number with special char", "!@$%^^^&&", "Should contain alphanumeric characters only")
        )

        @JvmStatic
        fun accountNumberValidForCN() = listOf(
            Arguments.of("Account Number with 8 char", getRandomString(8)),
            Arguments.of("Account Number with 20 char", getRandomString(20)),
        )

        @JvmStatic
        fun invalidBankName() = listOf(
            Arguments.of("Bank Name with 201 chars", getRandomString(201))
        )

        @JvmStatic
        fun validBankName() = listOf(
            Arguments.of("Bank Name with 200 chars", getRandomString(200)),
            Arguments.of("Bank Name with 1 char", getRandomString(1)),
            Arguments.of("Bank Name with special chars", "!@#$%^&*()"),
            Arguments.of("Bank Name with space", "A Normal Bank Name"),
            Arguments.of("Bank name using chinese letters", "中国银行")
        )

        @JvmStatic
        fun validSwiftCode() = listOf(
            Arguments.of("11 char swift code", SwiftCode.BKCHCNBJ110, BankCountryCode.CN),
            Arguments.of("Special char in swift code", "IC$#US J", BankCountryCode.US),
            Arguments.of("8 char swift code for AU", SwiftCode.AAYBAU2S)
        )
    }
}