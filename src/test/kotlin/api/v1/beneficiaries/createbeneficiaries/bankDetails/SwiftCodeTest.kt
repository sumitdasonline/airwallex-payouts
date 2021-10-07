package api.v1.beneficiaries.createbeneficiaries.bankDetails

import api.v1.beneficiaries.createbeneficiaries.CreateBeneficiarySetUp
import helpers.BankCountryCode
import helpers.PaymentMethods
import helpers.Status
import helpers.deleteKeyFromJson
import helpers.putValueIntoJsonKey
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@Execution(ExecutionMode.CONCURRENT)
class SwiftCodeTest : CreateBeneficiarySetUp() {

    @Test
    fun swiftCodeBlankWhenPaymentMethodIsLocal() {
        val modifiedPayload = JSONObject(
            CreateBeneficiariesHappyFlowPayload.putValueIntoJsonKey(
                "beneficiary.bank_details.bank_country_code",
                BankCountryCode.US.toString()
            ).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.swift_code", "").toString()
                .putValueIntoJsonKey("beneficiary.bank_details.account_routing_type1", "aba").toString()
                .putValueIntoJsonKey("beneficiary.bank_details.account_routing_value1", "021000021").toString()
        )
            .put("payment_methods", listOf(PaymentMethods.LOCAL.toString())).toString()

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
    fun swiftCodeValidWhenPaymentMethodIsLocal() {
        val modifiedPayload = JSONObject(
            CreateBeneficiariesHappyFlowPayload.putValueIntoJsonKey(
                "beneficiary.bank_details.bank_country_code",
                BankCountryCode.US.toString()
            ).toString()
                .putValueIntoJsonKey(
                    "beneficiary.bank_details.swift_code",
                    BankCountryCode.US.swiftCode.toString()
                ).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.account_routing_type1", "aba").toString()
                .putValueIntoJsonKey("beneficiary.bank_details.account_routing_value1", "021000021").toString()
        )
            .put("payment_methods", listOf(PaymentMethods.LOCAL.toString())).toString()

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
    fun swiftCodeInvalidWhenPaymentMethodIsLocal() {
        val modifiedPayload = JSONObject(
            CreateBeneficiariesHappyFlowPayload.putValueIntoJsonKey(
                "beneficiary.bank_details.bank_country_code",
                BankCountryCode.US.toString()
            ).toString()
                .putValueIntoJsonKey(
                    "beneficiary.bank_details.swift_code",
                    BankCountryCode.CN.swiftCode.toString()
                ).toString()
                .putValueIntoJsonKey("beneficiary.bank_details.account_routing_type1", "aba").toString()
                .putValueIntoJsonKey("beneficiary.bank_details.account_routing_value1", "021000021").toString()
        )
            .put("payment_methods", listOf(PaymentMethods.LOCAL.toString())).toString()

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

    @Execution(ExecutionMode.SAME_THREAD)
    @Test
    fun swiftCodeRemovedWhenPaymentMethodIsLocal() {
        val modifiedPayload = JSONObject(
            CreateBeneficiariesHappyFlowPayload.putValueIntoJsonKey(
                "beneficiary.bank_details.bank_country_code",
                BankCountryCode.US.toString()
            ).toString()
                .deleteKeyFromJson("beneficiary.bank_details.swift_code").toString()
                .putValueIntoJsonKey("beneficiary.bank_details.account_routing_type1", "aba").toString()
                .putValueIntoJsonKey("beneficiary.bank_details.account_routing_value1", "021000021").toString()
        )
            .put("payment_methods", listOf(PaymentMethods.LOCAL.toString())).toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(201)
            statusLine("HTTP/1.1 201 Created")
        }
    }

    @Test
    fun invalidSwiftCode() {
        val modifiedPayload = CreateBeneficiariesFullPayload.putValueIntoJsonKey(
            "beneficiary.bank_details.bank_country_code",
            BankCountryCode.US.toString()
        ).toString()
            .putValueIntoJsonKey("beneficiary.bank_details.swift_code", "ICBKUSBJE").toString()
        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(400)
            statusLine("HTTP/1.1 400 Bad Request")
            body("code", equalTo("payment_schema_validation_failed"))
            body("message", equalTo("Should be a valid and supported SWIFT code / BIC"))
            body("source", equalTo("beneficiary.bank_details.swift_code"))
        }
    }
}