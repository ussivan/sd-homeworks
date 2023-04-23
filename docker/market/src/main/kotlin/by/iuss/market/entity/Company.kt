package by.iuss.market.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Company {

    @Id
    @GeneratedValue
    var id: Long = 0
    var name: String? = null
    var stocks = 0
    var stockPrice = 0.0

    constructor()
    constructor(name: String, stocks: Int, stockPrice: Double) {
        this.name = name
        this.stocks = stocks
        this.stockPrice = stockPrice
    }

    fun addStocks(stocks: Int) {
        this.stocks += stocks
    }

    fun buyStocks(stocks: Int) {
        if (this.stocks < stocks) {
            throw RuntimeException("Not enough stocks")
        }
        this.stocks -= stocks
    }
}