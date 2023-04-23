package dao

import entity.Currency
import entity.Product
import entity.User
import rx.Observable

class StoreService(private val dao: StoreReactiveDao) {
    fun getLocalProductsForUser(userId: Long): Observable<String> {
        val currency = dao.getUser(userId).map { it.currency }
        val products = dao.getProducts()
        return Observable.combineLatest(currency, products) { cur, product ->
            product.toString(
                cur
            )
        }
    }

    fun addUser(userId: Long, currency: Currency): Observable<String> {
        return dao.addUser(User(userId, currency))
    }

    fun addProduct(userId: Long, price: Long, name: String): Observable<String> {
        return dao.getUser(userId)
            .map { it.currency }
            .switchMap { currency ->
                dao.addProduct(
                    Product(
                        name, price, currency
                    )
                )
            }
    }
}