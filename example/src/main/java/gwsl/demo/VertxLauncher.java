package gwsl.demo;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertxLauncher extends Launcher {

    private static Logger log = LoggerFactory.getLogger(VertxLauncher.class);

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        log.info("launch vertx: {}", String.join(" ", args));

        Launcher launcher = new VertxLauncher();
        launcher.dispatch(args);

    }

    private static void hookShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("shutdown now");
            if (LogManager.getContext() instanceof LoggerContext) {
                Configurator.shutdown((LoggerContext) LogManager.getContext());
                log.info("Shutting down log4j2");
            } else {
                log.warn("Unable to shutdown log4j2");
            }
        }));
    }

    @Override
    public void afterConfigParsed(JsonObject config) {
    }

    @Override
    public void beforeStartingVertx(VertxOptions options) {
    }

    @Override
    public void afterStartingVertx(Vertx vertx) {
    }

    @Override
    public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
    }

    @Override
    public void beforeStoppingVertx(Vertx vertx) {
        log.info("Prepare to stop vertx.");
    }

    @Override
    public void afterStoppingVertx() {
        log.info("vertx has stopped.");
    }

    @Override
    public void handleDeployFailed(Vertx vertx, String mainVerticle, DeploymentOptions deploymentOptions, Throwable cause) {
        log.error("{} is deploy failed.", mainVerticle, cause);
        vertx.close();
    }
}
