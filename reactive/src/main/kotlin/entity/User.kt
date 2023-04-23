package entity

import org.bson.Document

class User(private val id: Long, val currency: Currency) {

    constructor(document: Document) : this(
        document.getLong("id"),
        Currency.valueOf(document.getString("currency"))
    )

    fun toDocument(): Document {
        return Document()
            .append("id", id)
            .append("currency", currency.toString())
    }
}