package gwsl.demo.router;


import io.vertx.ext.web.Router;
import me.gwsl.vrouter.AbstractBaseRouter;
import me.gwsl.vrouter.annotation.ARouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ARouter
public class HelloRouter extends AbstractBaseRouter {
    private static Logger log = LoggerFactory.getLogger(HelloRouter.class);


    @Override
    public Router create() {
        Router router = Router.router(vertx);
        router.route("/").handler(ctx -> {
            ctx.response().setChunked(true);
            ctx.response().write("hello router spi");
            ctx.end();
        });
        return router;
    }

    @Override
    public String path() {
        return "/";
    }
}
