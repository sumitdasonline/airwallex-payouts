package api.v1.beneficiaries.createbeneficiaries.bankDetails

import api.v1.beneficiaries.createbeneficiaries.CreateBeneficiarySetUp
import helpers.BankCountryCode
import helpers.Status
import helpers.putValueIntoJsonKey
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@Execution(ExecutionMode.CONCURRENT)
class AccountNumberTest : CreateBeneficiarySetUp() {

    @ParameterizedTest(name = "{index} - {0} : {1}")
    @MethodSource
    fun accountNumberInvalidForUS(testName: String, accountNumber: String, errorMessage: String) {
        val modifiedPayload =
            CreateBeneficiariesFullPayload
                .putValueIntoJsonKey("beneficiary.bank_details.account_number", accountNumber).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.bank_country_code", BankCountryCode.US.toString()).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.swift_code", BankCountryCode.US.swiftCode.toString()).toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.BAD_REQUEST.code)
            statusLine(Status.BAD_REQUEST.message)
            body("code", equalTo("payment_schema_validation_failed"))
            body("message", equalTo(errorMessage))
            body("source", equalTo("beneficiary.bank_details.account_number"))
        }
    }

    @ParameterizedTest(name = "{index} - {0} : {1}")
    @MethodSource
    fun accountNumberValidForUS(testName: String, accountNumber: String) {
        val modifiedPayload =
            CreateBeneficiariesFullPayload
                .putValueIntoJsonKey("beneficiary.bank_details.account_number", accountNumber).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.bank_country_code", BankCountryCode.US.toString()).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.swift_code", BankCountryCode.US.swiftCode.toString()).toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.CREATED.code)
            statusLine(Status.CREATED.message)
        }
    }

    @ParameterizedTest(name = "{index} - {0} : {1}")
    @MethodSource
    fun accountNumberInvalidForAU(testName: String, accountNumber: String, errorMessage: String) {
        val modifiedPayload =
            CreateBeneficiariesFullPayload
                .putValueIntoJsonKey("beneficiary.bank_details.account_number", accountNumber).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.bank_country_code", BankCountryCode.AU.toString()).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.swift_code", BankCountryCode.AU.swiftCode.toString()).toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.BAD_REQUEST.code)
            statusLine(Status.BAD_REQUEST.message)
            body("code", equalTo("payment_schema_validation_failed"))
            body("message", equalTo(errorMessage))
            body("source", equalTo("beneficiary.bank_details.account_number"))
        }
    }

    @ParameterizedTest(name = "{index} - {0} : {1}")
    @MethodSource
    fun accountNumberValidForAU(testName: String, accountNumber: String) {
        val modifiedPayload =
            CreateBeneficiariesFullPayload
                .putValueIntoJsonKey("beneficiary.bank_details.account_number", accountNumber).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.bank_country_code", BankCountryCode.AU.toString()).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.swift_code", BankCountryCode.AU.swiftCode.toString()).toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.CREATED.code)
            statusLine(Status.CREATED.message)
        }
    }

    @ParameterizedTest(name = "{index} - {0} : {1}")
    @MethodSource
    fun accountNumberInvalidForCN(testName: String, accountNumber: String, errorMessage: String) {
        val modifiedPayload =
            CreateBeneficiariesFullPayload
                .putValueIntoJsonKey("beneficiary.bank_details.account_number", accountNumber).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.bank_country_code", BankCountryCode.CN.toString()).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.swift_code", BankCountryCode.CN.swiftCode.toString()).toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.BAD_REQUEST.code)
            statusLine(Status.BAD_REQUEST.message)
            body("code", equalTo("payment_schema_validation_failed"))
            body("message", equalTo(errorMessage))
            body("source", equalTo("beneficiary.bank_details.account_number"))
        }
    }

    @ParameterizedTest(name = "{index} - {0} : {1}")
    @MethodSource
    fun accountNumberValidForCN(testName: String, accountNumber: String) {
        val modifiedPayload =
            CreateBeneficiariesFullPayload
                .putValueIntoJsonKey("beneficiary.bank_details.account_number", accountNumber).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.bank_country_code", BankCountryCode.CN.toString()).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.swift_code", BankCountryCode.CN.swiftCode.toString()).toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.CREATED.code)
            statusLine(Status.CREATED.message)
        }
    }

}