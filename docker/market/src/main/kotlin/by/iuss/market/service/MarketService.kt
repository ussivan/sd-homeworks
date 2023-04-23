package by.iuss.market.service

import by.iuss.market.entity.Company
import by.iuss.market.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class MarketService(private val companyRepository: CompanyRepository) {
    fun addCompany(company: Company): Company {
        if (companyRepository.findByName(company.name!!).isPresent) {
            throw RuntimeException("Company already exists")
        }
        return companyRepository.save(company)
    }

    fun addStocks(name: String, stocks: Int): Company {
        val company = getCompany(name)
        company.addStocks(stocks)
        return companyRepository.save(company)
    }

    fun getCompany(name: String): Company {
        return companyRepository.findByName(name)
            .orElseThrow { RuntimeException("Company not found") }
    }

    fun buyStocks(name: String, stocks: Int): Company {
        val company = getCompany(name)
        company.buyStocks(stocks)
        return companyRepository.save(company)
    }

    fun updateStockPrice(name: String, price: Double): Company {
        val company = getCompany(name)
        company.stockPrice = price
        return companyRepository.save(company)
    }
}