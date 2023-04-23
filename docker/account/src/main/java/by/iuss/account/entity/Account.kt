package by.iuss.account.entity

import javax.persistence.*

@Entity
class Account {
    @Id
    @GeneratedValue
    var id: Long = 0
    var balance = 0.0
        private set

    @ElementCollection
    @CollectionTable(
        name = "account_stock_mapping",
        joinColumns = [JoinColumn(name = "account_id", referencedColumnName = "id")]
    )
    @MapKeyColumn(name = "stock_name")
    var stocks: MutableMap<String, Int> = HashMap()

    constructor(balance: Double) {
        this.balance = balance
    }

    constructor()

    fun setBalance(balance: Int) {
        this.balance = balance.toDouble()
    }

    fun addMoney(money: Double) {
        balance += money
    }

    fun spendMoney(money: Double) {
        balance -= money
    }
}