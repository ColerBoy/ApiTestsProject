package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Assertions {
    public static void assertJsonByName(Response Response, String name, int expectedValue){
        Response.then().assertThat().body("$",hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value,"JSON value is not equal to expected value");
    }
    public static void assertJsonByName(Response Response, String name, String expectedValue){
        Response.then().assertThat().body("$",hasKey(name));

        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value,"JSON value is not equal to expected value");
    }


    public static void assertResponseTextEquals(Response response, String expectedAnswer){
        assertEquals(expectedAnswer,response.asString(),"Response text is not as expected");
    }
    public static void assertResponseCodeEquals(Response response, int expectedStatusCode){
        assertEquals(expectedStatusCode,response.statusCode(),"Response status code is not as expected");
    }
    public static void assertJsonHasField(Response Response, String expectedFieldName){
        Response.then().assertThat().body("$",hasKey(expectedFieldName));
    }
    public static void assertJsonHasFields(Response Response, String[] expectedFieldNames){
        for(String expectedFieldName : expectedFieldNames){
            Assertions.assertJsonHasField(Response, expectedFieldName);
        }
    }
    public static void assertJsonHasNotField(Response Response, String unexpectedFieldName){
        Response.then().assertThat().body("$",not(hasKey(unexpectedFieldName)));
    }

    public static void assertJsonHasNotFields(Response Response, String[] unexpectedFieldNames){
        for(String unexpectedFieldName : unexpectedFieldNames){
            Assertions.assertJsonHasNotField(Response, unexpectedFieldName);
        }
    }

    public static void assertErrorResponseTextEquals(Response response, String expectedError){
        if (response.asString().startsWith("{") && response.asString().endsWith("}")) {
            response.then().assertThat().body("$",hasKey("error"));
            String error = response.jsonPath().getString("error");
            assertEquals(expectedError, error,"Error message is not as expected");
        } else {
            assertEquals(expectedError, response.asString(), "Error message is not as expected");
        }
    }
}
