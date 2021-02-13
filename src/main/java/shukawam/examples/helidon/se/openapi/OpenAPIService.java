package shukawam.examples.helidon.se.openapi;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import shukawam.examples.helidon.se.config.ConfigService;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import java.util.Collections;
import java.util.logging.Logger;

public class OpenAPIService implements Service {

    private static final Logger LOGGER = Logger.getLogger(ConfigService.class.getName());

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    public OpenAPIService() {
        // default constructor.
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/test", this::sampleService);
    }

    private void sampleService(ServerRequest req, ServerResponse res) {
        LOGGER.info("OpenAPI sample endpoint.");
        var jsonObject = JSON.createObjectBuilder().add("message", "For OpenAPI sample endpoint").build();
        res.send(jsonObject);
    }
}
