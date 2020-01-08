package me.gwsl.vrouter;

import io.vertx.core.Vertx;
import me.gwsl.vrouter.spi.BaseRouter;

public abstract class AbstractBaseRouter implements BaseRouter {

    protected Vertx vertx;

    @Override
    public void init(Vertx vertx) {
        this.vertx = vertx;
    }

}
