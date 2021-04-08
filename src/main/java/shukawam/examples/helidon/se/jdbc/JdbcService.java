package shukawam.examples.helidon.se.jdbc;

import io.helidon.common.http.Http;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import io.helidon.webserver.*;

import javax.json.JsonObject;
import java.util.concurrent.CompletionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcService implements Service {
    private static final Logger LOGGER = Logger.getLogger(JdbcService.class.getName());
    private final DbClient dbClient;

    public JdbcService(DbClient dbclient) {
        this.dbClient = dbclient;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/index", this::index)
                .get("/item", this::getAllItem)
                .get("/item/{id}", this::getItemById)
                .get("/item/name/{name}", this::getItemByName)
                .post("/item", Handler.create(Item.class, this::createItem));
    }

    /**
     * @param req ServerRequest
     * @param res ServerResponse
     */
    private void index(ServerRequest req, ServerResponse res) {
        res.send("DB Client(JDBC) Examples: \n"
                + "    GET      /item        - List all item.\n"
                + "    GET      /item/{id}   - Get item by id.\n"
                + "    GET      /type        - List all item type.\n"
                + "    GET      /type/{id}   - Get type by id.\n"
                + "    POST     /item        - Insert new item.\n"
                + "    POST     /type        - Insert new item type.\n"
                + "    PUT      /item        - Update item.\n"
                + "    PUT      /type        - Update item type.\n"
                + "    DELETE   /item        - Delete item.\n"
                + "    DELETE   /type        - Delete item type.\n");
    }

    /**
     * Get all items by named query.
     *
     * @param req ServerRequest
     * @param res ServerResponse
     */
    private void getAllItem(ServerRequest req, ServerResponse res) {
        res.send(dbClient.execute(dbExecute -> dbExecute
                .namedQuery("select-all-item"))
                .map(it -> it.as(JsonObject.class)), JsonObject.class);
    }

    /**
     * Get a single item by id.
     *
     * @param req ServerRequest
     * @param res ServerResponse
     */
    private void getItemById(ServerRequest req, ServerResponse res) {
        var id = Integer.parseInt(req.path().param("id"));
        dbClient.execute(dbExecute -> dbExecute
                .createNamedGet("select-item-by-id")
                .addParam("id", id)
                .execute())
                .thenAccept(dbRow -> dbRow.ifPresentOrElse(
                        row -> sendRow(row, res),
                        () -> sendNotFound(res, "Item: " + id + " NOT found."))
                ).exceptionally(throwable -> sendError(throwable, res)
        );
    }

    /**
     * Get a single item by name.
     *
     * @param req ServerRequest
     * @param res ServerResponse
     */
    private void getItemByName(ServerRequest req, ServerResponse res) {
        var name = req.path().param("name");
        dbClient.execute(dbExecute -> dbExecute
                .createNamedGet("select-item-by-name")
                .addParam("name", name)
                .execute())
                .thenAccept(dbRow -> dbRow.ifPresentOrElse(
                        row -> sendRow(row, res),
                        () -> sendNotFound(res, "Item: " + name + " NOT found."))
                ).exceptionally(throwable -> sendError(throwable, res)
        );
    }

    /**
     * Create a new item.
     *
     * @param req  ServerRequest
     * @param res  ServerResponse
     * @param item item
     */
    private void createItem(ServerRequest req, ServerResponse res, Item item) {
        System.out.println(item.getId());
        dbClient.execute(dbExecute -> dbExecute
                .createNamedInsert("create-item")
                .indexedParam(item)
                .execute())
                .thenAccept(count -> res.send("Created: " + count + "values"))
                .exceptionally(throwable -> sendError(throwable, res));
    }

    private void sendRow(DbRow dbRow, ServerResponse res) {
        res.send(dbRow.as(JsonObject.class));
    }

    /**
     * Response 404 with message.
     *
     * @param res     ServerResponse
     * @param message Message
     */
    private void sendNotFound(ServerResponse res, String message) {
        LOGGER.warning(message);
        res.status(Http.Status.NOT_FOUND_404);
        res.send(message);
    }

    private <T> T sendError(Throwable throwable, ServerResponse res) {
        Throwable realCause = throwable;
        if (throwable instanceof CompletionException) {
            realCause = throwable.getCause();
        }
        res.status(Http.Status.INTERNAL_SERVER_ERROR_500);
        res.send("Failed to process request: " + realCause.getClass().getName() + "(" + realCause.getMessage() + ")");
        LOGGER.log(Level.WARNING, "Failed to process request", throwable);
        return null;
    }
}
