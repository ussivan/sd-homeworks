package dao

import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoCollection
import com.mongodb.rx.client.Success
import entity.Product
import entity.User
import org.bson.Document
import rx.Observable
import rx.functions.Func1

class StoreReactiveDao(private val users: MongoCollection<Document>, private val products: MongoCollection<Document>) {
    fun addUser(user: User): Observable<String> {
        return addToCollection(users, user.toDocument())
    }

    fun addProduct(product: Product): Observable<String> {
        return addToCollection(products, product.toDocument())
    }

    fun getUser(userId: Long): Observable<User> {
        return users.find(Filters.eq("id", userId)).first()
            .map { document -> User(document) }
    }

    fun getProducts(): Observable<Product> {
        return products.find().toObservable().map { document -> Product(document) }
    }

    private fun addToCollection(collection: MongoCollection<Document>, document: Document): Observable<String> {
        return collection.insertOne(document).map { s: Success? -> if (s == null) "error" else "success" }
    }
}