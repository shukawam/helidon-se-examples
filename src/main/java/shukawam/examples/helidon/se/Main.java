
package shukawam.examples.helidon.se;

import io.helidon.common.LogConfig;
import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.metrics.MetricsSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import shukawam.examples.helidon.se.config.ConfigService;
import shukawam.examples.helidon.se.greet.GreetService;
import shukawam.examples.helidon.se.jdbc.JdbcService;

import java.lang.management.ManagementFactory;

/**
 * The application main class.
 */
public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() {
    }

    /**
     * Application main entry point.
     *
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        startServer();
    }

    /**
     * Start the server.
     *
     * @return the created {@link WebServer} instance
     */
    static WebServer startServer() {
        // load logging configuration
        LogConfig.configureRuntime();

        // By default this will pick up application.yaml from the classpath
        Config config = Config.create();

        // Build server with JSONP support
        WebServer server = WebServer.builder(createRouting(config))
                .config(config.get("server"))
                .addMediaSupport(JsonpSupport.create())
                .build();

        // Try to start the server. If successful, print some info and arrange to
        // print a message at shutdown. If unsuccessful, print the exception.
        server.start()
                .thenAccept(ws -> {
                    System.out.println(
                            String.format("WEB server is up! http://localhost:%s in %s milliseconds (since JVM startup).",
                                    ws.port(),
                                    ManagementFactory.getRuntimeMXBean().getUptime()
                            ));
                    ws.whenShutdown().thenRun(()
                            -> System.out.println("WEB server is DOWN. Good bye!"));
                })
                .exceptionally(t -> {
                    System.err.println("Startup failed: " + t.getMessage());
                    t.printStackTrace(System.err);
                    return null;
                });

        // Server threads are not daemon. No need to block. Just react.

        return server;
    }

    /**
     * Creates new {@link Routing}.
     *
     * @param config configuration of this server
     * @return routing configured with JSON support, a health check, and a service
     */
    private static Routing createRouting(Config config) {
        // Helidon - Metrics
        MetricsSupport metrics = MetricsSupport.create();

        // Default Service
        GreetService greetService = new GreetService(config);

        // Helidon - Health Check
        HealthSupport health = HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())   // Adds a convenient set of checks
                .build();

        // Helidon - Config
        ConfigService configService = new ConfigService(config);

        // Helidon - DBClient
        Config dbConfig = config.get("db");
        System.out.println("ok");
        DbClient dbClient = DbClient.builder(dbConfig).build();
        JdbcService jdbcService = new JdbcService(dbClient);

        return Routing.builder()
                // Health at "/health"
                .register(health)
                // Metrics at "/metrics"
                .register(metrics)
                // Default
                .register("/greet", greetService)
                // Config
                .register("/config", configService)
                // Helidon DBClient
                .register("/jdbc", jdbcService)
                .build();
    }
}
