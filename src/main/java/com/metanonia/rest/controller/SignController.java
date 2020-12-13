package com.metanonia.rest.controller;

import com.metanonia.rest.advice.exception.UserNotFoundException;
import com.metanonia.rest.model.User;
import com.metanonia.rest.parameter.SignUp;
import com.metanonia.rest.repository.UserRepository;
import com.metanonia.rest.response.CommonResult;
import com.metanonia.rest.response.SingleResult;
import com.metanonia.rest.security.JwtTokenProvider;
import com.metanonia.rest.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1.Sign"})
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1")
public class SignController {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value="login", notes = "email id")
    @PostMapping("/signin")
    public SingleResult<String> signin(@ApiParam(value = "email", required = true) @RequestParam String uid
                                     , @ApiParam(value = "password", required = true) @RequestParam String passowrd
                                   ) {

        User user = Optional.ofNullable(userRepository.findByUid(uid)).orElseThrow(UserNotFoundException::new);

        if(passowrd.matches(user.getPassword()) == false) {
            return responseService.getSinglFailResult("UserId/Password not found");
        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUid(), user.getRoles()));
    }

    @ApiOperation(value = "SignUp", notes = "Member Join")
    @PostMapping(value = "/signup", consumes = {"application/json"})
    public CommonResult sigun(@RequestBody SignUp signUp) {
        ArrayList<String>roles = new ArrayList<>();
        roles.add("ROLE_USER");
        try {
            userRepository.save(User.builder()
                    .uid(signUp.getUid())
                    .password(signUp.getPassword())
                    .name(signUp.getName())
                    .roles(roles)
                    .build()
            );
        }
        catch (Exception e) {
            log.info(e.getMessage());
            return responseService.getCommonFailResult(e.getMessage());
        }

        return responseService.getSuccessResult();
    }
}
