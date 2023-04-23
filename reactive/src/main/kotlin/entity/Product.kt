package entity

import org.bson.Document

class Product(private val name: String, private val usdPrice: Double) {
    constructor(document: Document) : this(document.getString("name"), document.getDouble("usdPrice"))
    constructor(name: String, price: Long, currency: Currency) : this(name, toUsd(price, currency))

    fun toDocument(): Document {
        return Document()
            .append("name", name)
            .append("usdPrice", usdPrice)
    }

    fun toString(currency: Currency): String {
        return formatProduct(1 / currency.toUsd, currency.toString())
    }

    private fun formatProduct(priceCoeff: Double, currencyStr: String): String {
        return String.format("%s %.2f %s%n", name, usdPrice * priceCoeff, currencyStr)
    }

    companion object {
        private fun toUsd(price: Long, currency: Currency): Double {
            return price * currency.toUsd
        }
    }
}