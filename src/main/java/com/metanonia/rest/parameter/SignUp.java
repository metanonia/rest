package com.metanonia.rest.parameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(value = "SignUp", description = "SignUp")
public class SignUp {
    @ApiModelProperty(value = "Uid", required = true)
    String uid;
    @ApiModelProperty(value = "Password", required = true)
    String password;
    @ApiModelProperty(value = "Name", required = true)
    String name;
}