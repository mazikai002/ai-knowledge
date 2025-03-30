package com.itheima.backend.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RestResult<T> {

    @Schema(description = "Request successful or not", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean success = true;

    @Schema(description = "Prompt message, not return content", requiredMode = Schema.RequiredMode.REQUIRED)
    private String msg = "";

    @Schema(description = "Default business code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code = "0000";

    @Schema(description = "Return content")
    private T content;

    /**
     * Quick return for normal invocation when no response body is needed
     */
    public static <T> RestResult<T> buildSuccessResult() {
        return new RestResult<>();
    }

    /**
     * Encapsulation of the result for a normal return call
     */
    public static <T> RestResult<T> buildSuccessResult(T content) {
        RestResult<T> result = new RestResult<>();
        result.setContent(content);
        return result;
    }

    public void setContent(T content) {
        this.content = content;
    }

}
