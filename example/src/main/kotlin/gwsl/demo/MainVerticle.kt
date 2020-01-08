package gwsl.demo

import gwsl.demo.verticle.ServerVerticle
import io.vertx.kotlin.core.deployVerticleAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle

class MainVerticle: CoroutineVerticle() {

    override suspend fun start() {
        vertx.deployVerticleAwait(ServerVerticle::class.java.name)
    }
}