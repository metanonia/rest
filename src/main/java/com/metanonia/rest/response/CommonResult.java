package com.metanonia.rest.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {
    @ApiModelProperty(value = "success?")
    private boolean success;

    @ApiModelProperty(value = "code?")
    private int code;

    @ApiModelProperty(value = "message?")
    private String msg;
}
