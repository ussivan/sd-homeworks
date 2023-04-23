import com.mongodb.rx.client.MongoClient
import com.mongodb.rx.client.MongoClients
import dao.StoreReactiveDao
import dao.StoreService
import http.RequestHandler
import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServer
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import io.reactivex.netty.protocol.http.server.HttpServerResponse

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val db = createMongoClient().getDatabase("reactive")
            val requestHandler = RequestHandler(
                StoreService(
                    StoreReactiveDao(
                        db.getCollection("users"), db.getCollection("products")
                    )
                )
            )
            HttpServer
                .newServer(8080)
                .start { req: HttpServerRequest<ByteBuf>, resp: HttpServerResponse<ByteBuf> ->
                    val response = requestHandler.getResponse(req)
                    resp.writeString(response)
                }
                .awaitShutdown()
        }

        private fun createMongoClient(): MongoClient {
            return MongoClients.create("mongodb://localhost:27017")
        }
    }
}