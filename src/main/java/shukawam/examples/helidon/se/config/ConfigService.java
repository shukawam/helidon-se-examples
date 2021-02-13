package shukawam.examples.helidon.se.config;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import java.util.Collections;
import java.util.logging.Logger;

public class ConfigService implements Service {

    private static final Logger LOGGER = Logger.getLogger(ConfigService.class.getName());

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private final String configValue;

    public ConfigService(Config config) {
        configValue = config.get("app.config").asString().orElse("config service NOT work");
    }

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/", this::getConfigHandler);
    }

    private void getConfigHandler(ServerRequest req, ServerResponse res) {
        var message = String.format("Config value is %s", configValue);
        LOGGER.info(message);
        var jsonObject = JSON.createObjectBuilder().add("message", configValue).build();
        res.send(jsonObject);
    }
}
