package http

import dao.StoreService
import entity.Currency
import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import rx.Observable

class RequestHandler(private val service: StoreService) {
    fun getResponse(req: HttpServerRequest<ByteBuf>): Observable<String> {
        if (req.decodedPath == "/products") {
            return handleGetProducts(req)
        }
        if (req.decodedPath == "/register") {
            return handleRegister(req)
        }
        return if (req.decodedPath == "/add-product") {
            handleAddProduct(req)
        } else Observable.just(req.decodedPath)
    }

    private fun handleGetProducts(req: HttpServerRequest<ByteBuf>): Observable<String> {
        val userId = getQueryParam(req, "id").toLong()
        return service.getLocalProductsForUser(userId)
    }

    private fun handleRegister(req: HttpServerRequest<ByteBuf>): Observable<String> {
        val userId = getQueryParam(req, "id").toLong()
        val currency = Currency.valueOf(getQueryParam(req, "currency"))
        return service.addUser(userId, currency)
    }

    private fun handleAddProduct(req: HttpServerRequest<ByteBuf>): Observable<String> {
        val userId = getQueryParam(req, "id").toLong()
        val price = getQueryParam(req, "price").toLong()
        val name = getQueryParam(req, "name")
        return service.addProduct(userId, price, name)
    }

    private fun getQueryParam(req: HttpServerRequest<ByteBuf>, key: String): String {
        val values = req.queryParameters[key]!!
        if (values.isEmpty()) {
            throw RuntimeException("No username provided")
        }
        return values[0]
    }
}