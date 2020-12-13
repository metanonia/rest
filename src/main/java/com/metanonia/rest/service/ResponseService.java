package com.metanonia.rest.service;

import com.metanonia.rest.response.CommonResult;
import com.metanonia.rest.response.ListResult;
import com.metanonia.rest.response.SingleResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Getter
    public enum CommonResponse {
        SUCCESS(0, "success"),
        Fail(-1, "fail");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    public <T>SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());

        return result;
    }

    public <T>ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());

        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());

        return result;
    }

    public <T>SingleResult<T> getSinglFailResult(String msg) {
        SingleResult<T> result = new SingleResult<>();
        result.setSuccess(false);
        result.setCode(CommonResponse.Fail.getCode());
        result.setMsg(msg);

        return result;
    }

    public CommonResult getCommonFailResult(String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(CommonResponse.Fail.getCode());
        result.setMsg(msg);

        return result;
    }

    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
