package com.mustafa.controller;

import com.mustafa.domain.User;
import com.mustafa.dto.request.UserRequestDto;
import com.mustafa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mustafa.constants.RestApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {
    private final UserService userService;

    @PostMapping(ADD)
    @CrossOrigin("*")
    public ResponseEntity<Void> save(@RequestBody UserRequestDto dto){
        userService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<Void> update(@RequestBody UserRequestDto dto){
        userService.update(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<Iterable<User>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }


}
