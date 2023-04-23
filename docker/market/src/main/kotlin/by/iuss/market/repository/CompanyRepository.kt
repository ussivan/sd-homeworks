package by.iuss.market.repository

import by.iuss.market.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository : JpaRepository<Company, Long> {
    fun findByName(name: String): Optional<Company>
}