package com.metanonia.rest.controller;

import com.metanonia.rest.advice.exception.UserNotFoundException;
import com.metanonia.rest.model.User;
import com.metanonia.rest.repository.UserRepository;
import com.metanonia.rest.response.CommonResult;
import com.metanonia.rest.response.SingleResult;
import com.metanonia.rest.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.NoSuchObjectException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "login ok"
                    , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "find User", notes = "Search User by uid")
    @GetMapping("/user/{uid}")
    public SingleResult<User> findByUid(
            @ApiParam(value = "uid", required = true) @PathVariable String uid
        ) {
        return responseService.getSingleResult(
                Optional.ofNullable(userRepository.findByUid(uid)).orElseThrow(UserNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "login ok"
                    , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "save user", notes = "Save User")
    @PostMapping("/user")
    public SingleResult<User> saveUser(
           @ApiParam(value = "Uid", required = true) @RequestParam String uid
         , @ApiParam(value = "Name", required = true) @RequestParam String name
         , @ApiParam(value = "Password", required = true) @RequestParam String password
        ) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .password(password)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "login ok"
                    , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "modify user", notes = "Modify user")
    @PutMapping("/user")
    public SingleResult<User> modifyUser(
            @ApiParam(value = "Uid", required = true) @RequestParam String uid
          , @ApiParam(value = "Name", required = false) @RequestParam(required = false) String name
          , @ApiParam(value = "Password", required = false) @RequestParam(required = false) String password
        ) {
        SingleResult<User> result = new SingleResult<>();

        User user = userRepository.findByUid(uid);
        if(user == null) {
            result.setSuccess(false);
            result.setCode(ResponseService.CommonResponse.Fail.getCode());
            result.setMsg(ResponseService.CommonResponse.Fail.getMsg());
            return result;
        }
        else {
            if (name != null) user.setName(name);
            if (password != null) user.setPassword(password);

            return responseService.getSingleResult(userRepository.save(user));
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "login ok"
                    , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "delete user", notes = "Romove user")
    @DeleteMapping("/user/{uid}")
    public CommonResult delete(
        @ApiParam(value="uid", required = true) @PathVariable String uid    
        ) {
        userRepository.delete(userRepository.findByUid(uid));

        return responseService.getSuccessResult(ResponseService.CommonResponse.SUCCESS);
    }
}
