package com.mustafa.controller;

import com.mustafa.domain.User;
import com.mustafa.dto.request.UserSaveRequestDto;
import com.mustafa.dto.request.UserUpdateRequestDto;
import com.mustafa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import  static com.mustafa.constants.RestApiUrls.*;

/**
 * http://localhost:9094/dev/v1/user
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {
    private final UserService userService;

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
}
