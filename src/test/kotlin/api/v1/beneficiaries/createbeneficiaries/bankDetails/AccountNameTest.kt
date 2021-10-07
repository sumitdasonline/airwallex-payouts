package api.v1.beneficiaries.createbeneficiaries.bankDetails

import api.v1.beneficiaries.createbeneficiaries.CreateBeneficiarySetUp
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
class AccountNameTest : CreateBeneficiarySetUp() {

    @ParameterizedTest(name = "{index} - Name = {0}")
    @MethodSource
    fun accountNameInvalid(testName: String, accountName: String) {
        val modifiedPayload = CreateBeneficiariesFullPayload
            .putValueIntoJsonKey("beneficiary.bank_details.account_name", accountName).toString()

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
            body("message", equalTo("Should contain 2 to 200 characters"))
            body("source", equalTo("beneficiary.bank_details.account_name"))
        }
    }

    @ParameterizedTest(name = "{index} - Account Name = {0}")
    @MethodSource
    fun accountNameValid(accountName: String) {
        val modifiedPayload = CreateBeneficiariesFullPayload
            .putValueIntoJsonKey("beneficiary.bank_details.account_name", accountName).toString()

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