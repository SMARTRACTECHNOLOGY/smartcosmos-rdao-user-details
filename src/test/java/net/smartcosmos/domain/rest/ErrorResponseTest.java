package net.smartcosmos.domain.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.junit.*;

import static org.junit.Assert.*;

import static net.smartcosmos.domain.rest.ErrorResponse.CODE;
import static net.smartcosmos.domain.rest.ErrorResponse.MESSAGE;

public class ErrorResponseTest {

    private ObjectMapper mapper = new ObjectMapper();

    // region Serialization

    @Test
    public void thatSerializationIgnoresVersion() throws JsonProcessingException {

        ErrorResponse error = ErrorResponse.builder()
            .code(0)
            .message("someMessage")
            .build();

        assertNotNull(error.getVersion());

        String jsonString = mapper.writeValueAsString(error);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertFalse(jsonObject.has("version"));
    }

    @Test
    public void thatSerializationFieldsArePresent() throws JsonProcessingException {

        final Integer expectedCode = 1;
        final String expectedMessage = "someMessage";

        ErrorResponse error = ErrorResponse.builder()
            .code(expectedCode)
            .message(expectedMessage)
            .build();

        String jsonString = mapper.writeValueAsString(error);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertTrue(jsonObject.has(CODE));
        assertTrue(jsonObject.has(MESSAGE));
    }

    @Test
    public void thatSerializationValuesAreSet() throws JsonProcessingException {

        final Integer expectedCode = 1;
        final String expectedMessage = "someMessage";

        ErrorResponse error = ErrorResponse.builder()
            .code(expectedCode)
            .message(expectedMessage)
            .build();

        String jsonString = mapper.writeValueAsString(error);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertEquals(expectedCode, jsonObject.get(CODE));
        assertEquals(expectedMessage, jsonObject.get(MESSAGE));
    }

    @Test
    public void thatSerializationSucceedsCodeIsAbsentIfNull() throws JsonProcessingException {

        final Integer expectedCode = null;
        final String expectedMessage = "someMessage";

        ErrorResponse error = ErrorResponse.builder()
            .code(expectedCode)
            .message(expectedMessage)
            .build();

        String jsonString = mapper.writeValueAsString(error);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertFalse(jsonObject.has(CODE));
    }

    @Test
    public void thatSerializationSucceedsMessageIsAbsentIfNull() throws JsonProcessingException {

        final Integer expectedCode = 1;
        final String expectedMessage = null;

        ErrorResponse error = ErrorResponse.builder()
            .code(expectedCode)
            .message(expectedMessage)
            .build();

        String jsonString = mapper.writeValueAsString(error);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertFalse(jsonObject.has(MESSAGE));
    }

    @Test
    public void thatSerializationSucceedsMessageIsAbsentIfEmpty() throws JsonProcessingException {

        final Integer expectedCode = 1;
        final String expectedMessage = "";

        ErrorResponse error = ErrorResponse.builder()
            .code(expectedCode)
            .message(expectedMessage)
            .build();

        String jsonString = mapper.writeValueAsString(error);
        JSONObject jsonObject = new JSONObject(jsonString);

        assertFalse(jsonObject.has(MESSAGE));
    }

    // endregion
}
