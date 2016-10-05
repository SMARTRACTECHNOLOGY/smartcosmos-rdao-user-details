package net.smartcosmos.domain.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for REST code/message responses.
 */
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties({ "version" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    /**
     * General error code for JSON responses.
     */
    public static final int CODE_ERROR = 1;

    public static final String CODE = "code";
    public static final String MESSAGE = "message";

    private static final int VERSION_1 = 1;
    private final int version = VERSION_1;

    @JsonProperty(CODE)
    private Integer code;

    @JsonProperty(MESSAGE)
    private String message;
}
