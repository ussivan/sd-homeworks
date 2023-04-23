package by.iuss.account.service

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MarketService(restTemplateBuilder: RestTemplateBuilder) {
    private val restTemplate: RestTemplate

    init {
        restTemplate = restTemplateBuilder.build()
    }

    fun buyStocks(name: String, stocks: Int) {
        restTemplate.postForObject("$HOST/buy-stocks/{name}?stocks={stocks}", null, String::class.java, name, stocks)
    }

    fun sellStocks(name: String, stocks: Int) {
        restTemplate.postForObject("$HOST/add-stocks/{name}?stocks={stocks}", null, String::class.java, name, stocks)
    }

    fun getStockPrice(name: String): Double {
        val response = restTemplate.getForObject(
            "$HOST/get-company/{name}", HashMap::class.java, name
        )
        return response["stockPrice"] as Double
    }

    companion object {
        private const val HOST = "http://localhost:8080/market"
    }
}