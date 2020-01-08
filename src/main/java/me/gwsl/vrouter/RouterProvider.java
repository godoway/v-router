package me.gwsl.vrouter;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import me.gwsl.vrouter.spi.BaseRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;
import java.util.function.BiConsumer;
import java.util.stream.StreamSupport;

public class RouterProvider {

    private static Logger log = LoggerFactory.getLogger(RouterProvider.class);

    private Vertx vertx;
    private ServiceLoader<BaseRouter> loader;

    public RouterProvider(Vertx vertx) {
        this.vertx = vertx;
        this.loader = ServiceLoader.load(BaseRouter.class);
    }

    public Router createRouter() {
        log.info("loading router:");
        if (loader == null) {
            return Router.router(vertx);
        }
        return StreamSupport.stream(loader.spliterator(), false)
                .collect(() -> Router.router(vertx), mount, (r1, r2) -> {
                });
    }

    private BiConsumer<Router, BaseRouter> mount = (root, sub) -> {
        String routerName = sub.getClass().getName();
        String path = sub.path();
        sub.init(vertx);
        root.mountSubRouter(sub.path(), sub.create());
        log.info("mount [{}] on path: [{}]", routerName, path);
    };

}
