package api.v1.beneficiaries.createbeneficiaries.bankDetails

import api.v1.beneficiaries.createbeneficiaries.CreateBeneficiarySetUp
import helpers.AccountCurrency
import helpers.Status
import helpers.putValueIntoJsonKey
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource

@Execution(ExecutionMode.CONCURRENT)
class AccountCurrencyTest : CreateBeneficiarySetUp() {

    @ParameterizedTest(name = "{index} - Currency = {0}")
    @EnumSource(AccountCurrency::class)
    fun accountCurrencyWithValidValues201(accountCurrency: AccountCurrency) {
        val modifiedPayload = CreateBeneficiariesFullPayload.putValueIntoJsonKey(
            "beneficiary.bank_details.account_currency",
            accountCurrency.toString()
        ).toString()

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

    @ParameterizedTest(name = "{index} - Currency = {0}")
    @ValueSource(strings = ["abcd", "!@#$", "invalidValue", "123", ""])
    fun accountCurrencyWithInvalidValues(accountCurrency: String) {
        val modifiedPayload = CreateBeneficiariesFullPayload.putValueIntoJsonKey(
            "beneficiary.bank_details.account_currency",
            accountCurrency
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
            body("code", equalTo("invalid_argument"))
            body(
                "message",
                equalTo("beneficiary.bank_details.account_currency is not of the expected type; please refer to our API documentation")
            )
            body("source", equalTo("beneficiary.bank_details.account_currency"))
        }
    }
}