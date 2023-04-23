package by.iuss.account.controller

import by.iuss.account.entity.Account
import by.iuss.account.service.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountService: AccountService) {
    @PostMapping("/add-account")
    fun addAccount(@RequestParam balance: Double): Account {
        return accountService.addAccount(Account(balance))
    }

    @PostMapping("/add-money/{id}")
    fun addMoney(@PathVariable id: Long, @RequestParam money: Double): Account {
        return accountService.addMoney(id, money)
    }

    @GetMapping("/get-account/{id}")
    fun getAccount(@PathVariable id: Long): Account {
        return accountService.getAccount(id)
    }

    @PostMapping("/buy-stocks/{id}")
    fun buyStocks(@PathVariable id: Long, @RequestParam company: String, @RequestParam stocks: Int): Account {
        return accountService.buyStocks(id, company, stocks)
    }

    @PostMapping("/sell-stocks/{id}")
    fun sellStocks(@PathVariable id: Long, @RequestParam company: String, @RequestParam stocks: Int): Account {
        return accountService.sellStocks(id, company, stocks)
    }

    @GetMapping("/stock-balance/{id}")
    fun inStocks(@PathVariable id: Long): Double {
        return accountService.stockBalance(id)
    }
}