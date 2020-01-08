package me.gwsl.vrouter

import io.vertx.core.Vertx
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.gwsl.vrouter.spi.BaseRouter

@Suppress("MemberVisibilityCanBePrivate")
abstract class AbstractCoroutineRouter : BaseRouter {


    protected lateinit var vertx: Vertx
    protected lateinit var coroutineScope: CoroutineScope

    override fun init(vertx: Vertx) {
        this.vertx = vertx
        this.coroutineScope = CoroutineScope(vertx.dispatcher())
    }

    fun Route.suspendHandler(fn: suspend (RoutingContext) -> Unit) {
        handler { context ->
            coroutineScope.launch {
                try {
                    fn(context)
                } catch (e: Exception) {
                    context.fail(e)
                }
            }
        }
    }
}