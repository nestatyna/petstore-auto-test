package base;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static base.CustomLogger.log;
import static org.testng.Assert.assertEquals;

public abstract class AbstractController {

    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    public static Response isSuccess(Response response, boolean expect) {
        String statusCode = String.valueOf(response.getStatusCode());
        String body = response.asString();
        if (expect) {
            if (statusCode.startsWith("2")) {
                log("SUCCESS: " + response.getStatusCode() + " OK");
                log("body: \n" + (response.asString().isEmpty() ? "empty" : body) + "\n");
                return response;
            }
        } else {
            if (!statusCode.startsWith("2")) {
                log("ERROR: " + response.getStatusCode());
                log("body: " + (response.asString().isEmpty() ? "empty" : "\n" + body + "\n"));
                return response;
            }
        }
        return response;
    }

    public static Response isSuccess(Response response, Integer statusCode) {
        Response result = isSuccess(response, statusCode.toString().startsWith("2"));
        assertEquals(response.getStatusCode(), statusCode.intValue());
        return result;
    }
}
