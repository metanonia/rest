package com.metanonia.rest.parameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(value = "SignIn", description = "SignIn")
public class SigIn {
    @ApiModelProperty(value = "Uid", required = true)
    private String uid;
    @ApiModelProperty(value = "Password", required = true)
    private String password;
}
