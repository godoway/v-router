package me.gwsl.vrouter.spi;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public interface BaseRouter {

    void init(Vertx vertx);

    Router create();

    String path();
}
