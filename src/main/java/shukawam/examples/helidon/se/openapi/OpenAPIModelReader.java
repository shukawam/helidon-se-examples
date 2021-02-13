package shukawam.examples.helidon.se.openapi;

import io.smallrye.openapi.api.models.responses.APIResponsesImpl;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASModelReader;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.Paths;
import org.eclipse.microprofile.openapi.models.responses.APIResponse;
import org.eclipse.microprofile.openapi.models.responses.APIResponses;

import java.util.Map;

public class OpenAPIModelReader implements OASModelReader {

    private static final String MODEL_READER_PATH = "/openapi/test";

    private static final String DESCRIPTION = "A sample endpoint from OASModelReader.";

    @Override
    public OpenAPI buildModel() {
        PathItem pathItem = OASFactory.createPathItem()
                .GET(OASFactory.createOperation()
                        .operationId("test")
                        .description(DESCRIPTION)
                );
        OpenAPI openAPI = OASFactory.createOpenAPI();
        Paths paths = OASFactory.createPaths()
                .addPathItem(MODEL_READER_PATH, pathItem);
        openAPI.paths(paths);
        return openAPI;
    }
}
