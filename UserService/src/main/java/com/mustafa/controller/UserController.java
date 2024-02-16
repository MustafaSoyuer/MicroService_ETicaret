package com.mustafa.controller;

import com.mustafa.domain.User;
import com.mustafa.dto.request.DefaultRequestDto;
import com.mustafa.dto.request.UserSaveRequestDto;
import com.mustafa.dto.request.UserUpdateRequestDto;
import com.mustafa.exception.ErrorType;
import com.mustafa.exception.UserServiceException;
import com.mustafa.service.UserService;
import com.mustafa.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import  static com.mustafa.constants.RestApiUrls.*;

/**
 * http://localhost:9094/dev/v1/user
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
/**
 * Loglama işlemleri için kullanıyoruz.
 */
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    @Value("${userservice.deger2}")
    private String deger2;

    @Value("${userservice.listem.iki}")
    private String iki;


    @GetMapping("get-application-properties")
    public String getApplicationProperties(){
        log.trace("Properties Bilgisi...: "+iki);
        log.debug("Properties Bilgisi...: "+iki);
        log.info("Properties Bilgisi...: "+iki);
        log.warn("Properties Bilgisi...: "+iki);
        log.error("Properties Bilgisi...: "+iki);
        System.out.println("Console cikti...: "+iki);
        return deger2;
    }

    /**
     * /add
     * @param dto
     * @return
     */
    @PostMapping(ADD)
    public ResponseEntity<Void> save(@RequestBody UserSaveRequestDto dto){
        userService.save(dto);
        return  ResponseEntity.ok().build();
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Void> update(UserUpdateRequestDto dto){
        userService.update(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<List<User>> getAll( DefaultRequestDto dto){
        Optional<Long> authId = jwtTokenManager.validateToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserServiceException(ErrorType.INVALID_TOKEN_ERROR);
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/get-string")
    public ResponseEntity<String> getString(String ad){
        return ResponseEntity.ok(userService.getString(ad));
    }


}
