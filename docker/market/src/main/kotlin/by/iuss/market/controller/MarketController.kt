package by.iuss.market.controller

import by.iuss.market.entity.Company
import by.iuss.market.service.MarketService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/market")
class MarketController(private val marketService: MarketService) {

    @PostMapping("/add-company")
    fun addCompany(@RequestParam name: String, @RequestParam stocks: Int, @RequestParam price: Double): Company {
        return marketService.addCompany(Company(name, stocks, price))
    }

    @PostMapping("/add-stocks/{name}")
    fun addStocks(@PathVariable name: String, @RequestParam stocks: Int): Company {
        return marketService.addStocks(name, stocks)
    }

    @GetMapping("/get-company/{name}")
    fun getCompany(@PathVariable name: String): Company {
        return marketService.getCompany(name)
    }

    @PostMapping("/buy-stocks/{name}")
    fun buyStocks(@PathVariable name: String, @RequestParam stocks: Int): Company {
        return marketService.buyStocks(name, stocks)
    }

    @PostMapping("/update-price/{name}")
    fun updateStockPrice(@PathVariable name: String, @RequestParam price: Double): Company {
        return marketService.updateStockPrice(name, price)
    }
}