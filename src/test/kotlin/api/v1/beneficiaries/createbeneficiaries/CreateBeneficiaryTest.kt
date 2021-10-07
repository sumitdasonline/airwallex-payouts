package api.v1.beneficiaries.createbeneficiaries

import helpers.BankCountryCode
import helpers.KeyToRemoveArgument
import helpers.PaymentMethods
import helpers.Status
import helpers.deleteKeyFromJson
import helpers.getToken
import helpers.putValueIntoJsonKey
import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@Execution(ExecutionMode.CONCURRENT)
class CreateBeneficiaryTest : CreateBeneficiarySetUp() {

    @ParameterizedTest(name = "Value being removed {0}")
    @MethodSource
    fun removeNonMandatoryFieldsBankDetails(argument: KeyToRemoveArgument) {
        val modifiedPayload = CreateBeneficiariesFullPayload.deleteKeyFromJson(argument.jsonPathToRemove).toString()

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

    @ParameterizedTest
    @MethodSource
    fun blankValuesInNonMandatoryFieldsBankDetails(argument: KeyToRemoveArgument) {
        val modifiedPayload =
            CreateBeneficiariesFullPayload.putValueIntoJsonKey(argument.jsonPathToRemove, "").toString()

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

    @ParameterizedTest(name = "Value being removed {0}")
    @MethodSource
    fun removeMandatoryFieldsBankDetails(argument: KeyToRemoveArgument) {
        val modifiedPayload = CreateBeneficiariesFullPayload.deleteKeyFromJson(argument.jsonPathToRemove).toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.BAD_REQUEST.code)
            statusLine(Status.BAD_REQUEST.message)
            and()
            body("code", equalTo(argument.errorResponse?.code))
            body("message", equalTo(argument.errorResponse?.message))
            body("source", equalTo(argument.errorResponse?.source))
        }
    }

    @ParameterizedTest
    @MethodSource
    fun blankValuesInMandatoryFieldsBankDetails(argument: KeyToRemoveArgument) {
        val modifiedPayload =
            CreateBeneficiariesFullPayload.putValueIntoJsonKey(argument.jsonPathToRemove, "").toString()

        Given {
            body(modifiedPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.BAD_REQUEST.code)
            statusLine(Status.BAD_REQUEST.message)
            and()
            body("code", equalTo(argument.errorResponse?.code))
            body("message", equalTo(argument.errorResponse?.message))
            body("source", equalTo(argument.errorResponse?.source))
        }
    }

    @Test
    fun happyFlowFullPayload() {
        Given {
            body(CreateBeneficiariesFullPayload)
            headers(headers)
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.CREATED.code)
            statusLine(Status.CREATED.message)
            if (createBeneficiariesRequest != null) {
                body(
                    "beneficiary.bank_details.account_currency",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.account_currency)
                )
                body(
                    "beneficiary.bank_details.account_name",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.account_name)
                )
                body(
                    "beneficiary.bank_details.account_number",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.account_number)
                )
                body(
                    "beneficiary.bank_details.account_routing_type1",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.account_routing_type1)
                )
                body(
                    "beneficiary.bank_details.account_routing_type2",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.account_routing_type2)
                )
                body(
                    "beneficiary.bank_details.account_routing_value1",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.account_routing_value1)
                )
                body(
                    "beneficiary.bank_details.account_routing_value2",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.account_routing_value2)
                )
                body(
                    "beneficiary.bank_details.bank_branch",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.bank_branch)
                )
                body(
                    "beneficiary.bank_details.bank_country_code",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.bank_country_code)
                )
                body(
                    "beneficiary.bank_details.bank_name",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.bank_name)
                )
                body(
                    "beneficiary.bank_details.bank_street_address",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.bank_street_address)
                )
                body(
                    "beneficiary.bank_details.binding_mobile_number",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.binding_mobile_number)
                )
                body(
                    "beneficiary.bank_details.fingerprint",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.fingerprint)
                )
                body("beneficiary.bank_details.iban", equalTo(createBeneficiariesRequest.beneficiary.bank_details.iban))
                body(
                    "beneficiary.bank_details.local_clearing_system",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.local_clearing_system)
                )
                body(
                    "beneficiary.bank_details.swift_code",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.swift_code)
                )
                body(
                    "beneficiary.bank_details.transaction_reference",
                    equalTo(createBeneficiariesRequest.beneficiary.bank_details.transaction_reference)
                )
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("$schemas/CreateBeneficiariesResponse.json"))
            }
        }
    }

    @Test
    fun unhappyFlowErrorInvalidAuthentication() {
        Given {
            body(CreateBeneficiariesHappyFlowPayload)
            header("Content-Type", "application/json")
            header("Authorization", "Bearer invalidToken")
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.UNAUTHORISED.code)
            statusLine(Status.UNAUTHORISED.message)
            and()
            body("code", equalTo("unauthorized"))
            body("message", equalTo("Access denied, authentication failed"))
        }
    }

    @Test
    fun unhappyFlowErrorNoAuthentication() {
        Given {
            body(CreateBeneficiariesHappyFlowPayload)
            header("Content-Type", "application/json")
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.UNAUTHORISED.code)
            statusLine(Status.UNAUTHORISED.message)
            and()
            body("code", equalTo("unauthorized"))
            body("message", equalTo("Access denied, authentication failed"))
        }
    }

    @Test
    fun unhappyFlowErrorNoContentType() {
        Given {
            body(CreateBeneficiariesHappyFlowPayload)
            header("Authorization", "Bearer ${getToken()}")
        } When {
            post(endpoint)
        } Then {
            assertThat()
            statusCode(Status.UNSUPPORTED.code)
            statusLine(Status.UNSUPPORTED.message)
            and()
            body("code", equalTo("unsupported_media_type"))
            body("message", equalTo("Content type 'text/plain;charset=ISO-8859-1' not supported"))
        }
    }

    @Test
    fun invalidAccount_routing_type1() {
        val modifiedPayload =
            JSONObject(
                CreateBeneficiariesHappyFlowPayload
                    .putValueIntoJsonKey("beneficiary.bank_details.bank_country_code",BankCountryCode.US.toString()).toString()
                    .deleteKeyFromJson("beneficiary.bank_details.swift_code").toString()
                    .putValueIntoJsonKey("beneficiary.bank_details.account_routing_type1", "xxx").toString()
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
            body("code", equalTo("invalid_argument"))
            body("message", equalTo("xxx is not a valid type"))
            body("source", equalTo("beneficiary.bank_details.account_routing_type1"))
        }
    }
}