package by.iuss.account.service

import by.iuss.account.entity.Account
import by.iuss.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountRepository: AccountRepository, private val marketService: MarketService) {
    fun addAccount(account: Account): Account {
        return accountRepository.save(account)
    }

    fun addMoney(accountId: Long, money: Double): Account {
        val account = getAccount(accountId)
        account.addMoney(money)
        return accountRepository.save(account)
    }

    fun buyStocks(accountId: Long, company: String, stocks: Int): Account {
        val account = getAccount(accountId)
        val stockPrice = marketService.getStockPrice(company)
        if (account.balance < stockPrice * stocks) {
            throw RuntimeException("Not enough money")
        }
        marketService.buyStocks(company, stocks)
        account.stocks[company] = account.stocks.getOrDefault(company, 0) + stocks
        account.spendMoney(stockPrice * stocks)
        return accountRepository.save(account)
    }

    fun sellStocks(accountId: Long, company: String, stocks: Int): Account {
        val account = getAccount(accountId)
        val availableStocks = account.stocks.getOrDefault(company, 0)
        if (availableStocks < stocks) {
            throw RuntimeException("Not enough stocks")
        }
        val stockPrice = marketService.getStockPrice(company)
        marketService.sellStocks(company, stocks)
        account.stocks[company] = account.stocks[company]!! - stocks
        account.addMoney(stocks * stockPrice)
        return accountRepository.save(account)
    }

    fun getAccount(accountId: Long): Account {
        return accountRepository.findById(accountId)
            .orElseThrow { RuntimeException("Account not found") }
    }

    fun stockBalance(accountId: Long): Double {
        val account = getAccount(accountId)
        var balance = 0.0
        for ((key, value) in account.stocks) {
            val stockPrice = marketService.getStockPrice(key)
            balance += stockPrice * value
        }
        return balance
    }
}