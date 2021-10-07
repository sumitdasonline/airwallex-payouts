package api.v1.beneficiaries.createbeneficiaries.bankDetails

import api.v1.beneficiaries.createbeneficiaries.CreateBeneficiarySetUp
import helpers.BankCountryCode
import helpers.Status
import helpers.putValueIntoJsonKey
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@Execution(ExecutionMode.CONCURRENT)
class BankCountryCodeTest : CreateBeneficiarySetUp() {

    @ParameterizedTest
    @EnumSource(BankCountryCode::class)
    fun bankCountryCodeWIthValidValues201(bankCountryCode: BankCountryCode) {
        val modifiedPayload = CreateBeneficiariesFullPayload.putValueIntoJsonKey(
            "beneficiary.bank_details.bank_country_code",
            bankCountryCode.toString()
        ).toString()
            .putValueIntoJsonKey("beneficiary.bank_details.swift_code", bankCountryCode.swiftCode.toString()).toString()
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

    @Test
    fun bankCountryCodeSwiftCodeMismatch() {
        val modifiedPayload = CreateBeneficiariesFullPayload.putValueIntoJsonKey(
            "beneficiary.bank_details.bank_country_code",
            BankCountryCode.AU.toString()
        ).toString()
            .putValueIntoJsonKey(
                "beneficiary.bank_details.swift_code",
                BankCountryCode.CN.swiftCode.toString()
            ).toString()
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
            body("message", equalTo("The swift code is not valid for the given bank country code: AU"))
            body("source", equalTo("beneficiary.bank_details.swift_code"))
        }
    }

    @Test
    fun bankCountryCodeNotOnList() {
        val modifiedPayload =
            CreateBeneficiariesFullPayload.putValueIntoJsonKey("beneficiary.bank_details.bank_country_code", "XX")
                .toString()
                .putValueIntoJsonKey("beneficiary.bank_details.swift_code", "ICBKXXBJ").toString()
        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.BAD_REQUEST.code)
            statusLine(Status.BAD_REQUEST.message)
            body("code", equalTo("invalid_argument"))
            body("message", equalTo("XX is not a valid type"))
            body("source", equalTo("beneficiary.bank_details.bank_country_code"))
        }
    }

}