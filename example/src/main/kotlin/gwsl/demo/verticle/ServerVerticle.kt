package gwsl.demo.verticle

import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle
import me.gwsl.vrouter.RouterProvider

class ServerVerticle : CoroutineVerticle() {

    override suspend fun start() {
         val provider = RouterProvider(vertx)

        vertx.createHttpServer()
            .requestHandler(provider.createRouter())
            .listenAwait(8080)
    }
}