package shukawam.examples.helidon.se.jdbc;

import io.helidon.common.reactive.Multi;
import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.JsonObject;

public class JdbcService implements Service {

    private final DbClient dbClient;

    public JdbcService(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/item", this::getAllItem)
                .get("/named/item", this::getAllItemByNamedQuery)
                .get("/item", this::getItemById)
                .post("/item", this::createItem)
                .put("/item", this::updateItem)
                .delete("/item", this::deleteItem);
    }

    private void getAllItem(ServerRequest req, ServerResponse res) {
        Multi<JsonObject> rows = dbClient.execute(exec -> exec
                .namedQuery("select-all-items")
                .map(it -> it.as(JsonObject.class))
        );
        res.send(rows, JsonObject.class);
    }

    private void getAllItemByNamedQuery(ServerRequest req, ServerResponse res) {

    }

    private void getItemById(ServerRequest req, ServerResponse res) {

    }

    private void createItem(ServerRequest req, ServerResponse res) {

    }

    private void updateItem(ServerRequest req, ServerResponse res) {

    }

    private void deleteItem(ServerRequest req, ServerResponse res) {

    }
}
