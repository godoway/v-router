package gwsl.demo.router

import io.vertx.ext.web.Router
import me.gwsl.vrouter.AbstractCoroutineRouter
import me.gwsl.vrouter.annotation.ARouter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ARouter
class HelloKtRouter : AbstractCoroutineRouter() {
    override fun path(): String = "/kt"

    override fun create(): Router {
        val router = Router.router(vertx)

        router.route("/").suspendHandler {
            log.info("hello kt router spi")
            it.response().end("hello kt router spi")
        }

        return router
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(HelloKtRouter::class.java)
    }
}