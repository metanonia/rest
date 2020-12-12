package com.metanonia.rest.controller;

import com.metanonia.rest.advice.exception.UserNotFoundException;
import com.metanonia.rest.model.User;
import com.metanonia.rest.repository.UserRepository;
import com.metanonia.rest.response.CommonResult;
import com.metanonia.rest.response.SingleResult;
import com.metanonia.rest.security.JwtTokenProvider;
import com.metanonia.rest.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        if(passowrd.matches(user.getPassword()) == false) throw new UserNotFoundException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUid(), user.getRoles()));
    }

    @ApiOperation(value = "SignUp", notes = "Member Join")
    @PostMapping("signup")
    public CommonResult sigun(@ApiParam(value = "Email", required = true) @RequestParam String uid
                            , @ApiParam(value = "password", required = true) @RequestParam String password
                            , @ApiParam(value = "Name", required = true) @RequestParam String name
                          ) {
        userRepository.save(User.builder()
                .uid(uid)
                .password(password)
                .name(name)
                .roles(Collections.singletonList("USER"))
                .build()
        );
        return responseService.getSuccessResult(ResponseService.CommonResponse.SUCCESS);
    }
}
