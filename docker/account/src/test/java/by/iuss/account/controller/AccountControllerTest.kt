package by.iuss.account.controller

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.transaction.Transactional

@Testcontainers
@SpringBootTest
@Transactional
open class AccountControllerTest {
    @Autowired
    lateinit var controller: AccountController
    
    private val restTemplate = TestRestTemplate()
    private var accountId: Long = 0

    @BeforeEach
    fun setUp() {
        market.start()
        addCompany("AAPL", 10000, 20.0)
        accountId = controller.addAccount(1000.0).id
    }

    @AfterEach
    fun cleanUp() {
        market.stop()
    }

    @Test
    fun testRegister() {
        val res = controller.getAccount(accountId)
        Assertions.assertThat(res.balance).isEqualTo(1000.0)
        Assertions.assertThat(res.stocks).isEmpty()
    }

    @Test
    fun testAddMoney() {
        val res = controller.addMoney(accountId, 1000.0)
        Assertions.assertThat(res.balance).isEqualTo(2000.0)
        Assertions.assertThat(res.stocks).isEmpty()
    }

    @Test
    fun testBuyStocks() {
        val res = controller.buyStocks(accountId, "AAPL", 10)
        Assertions.assertThat(res.balance).isEqualTo((1000 - 10 * 20).toDouble())
        Assertions.assertThat(res.stocks).containsEntry("AAPL", 10)
        Assertions.assertThat(controller.inStocks(accountId)).isEqualTo((10 * 20).toDouble())
    }

    @Test
    fun testBuyStocksAfterPriceChange() {
        controller.buyStocks(accountId, "AAPL", 10)
        changePrice("AAPL", 50.0)
        val res = controller.buyStocks(accountId, "AAPL", 10)
        Assertions.assertThat(res.balance).isEqualTo((1000 - 10 * 20 - 10 * 50).toDouble())
        Assertions.assertThat(res.stocks).containsEntry("AAPL", 20)
        Assertions.assertThat(controller.inStocks(accountId)).isEqualTo((20 * 50).toDouble())
    }

    @Test
    fun testBuySellIncrease() {
        controller.buyStocks(accountId, "AAPL", 10)
        changePrice("AAPL", 50.0)
        val res = controller.sellStocks(accountId, "AAPL", 10)
        Assertions.assertThat(res.balance).isEqualTo((1000 - 10 * 20 + 10 * 50).toDouble())
        Assertions.assertThat(res.stocks).containsEntry("AAPL", 0)
        Assertions.assertThat(controller.inStocks(accountId)).isEqualTo(0.0)
    }

    private fun addCompany(name: String, stocks: Int, price: Double) {
        restTemplate.postForObject(
            "$MARKET_HOST/add-company?name={name}&stocks={stocks}&price={price}",
            null, String::class.java, name, stocks, price
        )
    }

    private fun changePrice(name: String, price: Double) {
        restTemplate.postForObject(
            "$MARKET_HOST/update-price/{name}?price={price}",
            null, String::class.java, name, price
        )
    }

    companion object {
        @Container
        var market: FixedHostPortGenericContainer<*> = FixedHostPortGenericContainer("market:1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080)
        private const val MARKET_HOST = "http://localhost:8080/market"
    }
}