package com.mustafa.service;

import com.mustafa.domain.User;
import com.mustafa.dto.request.UserRequestDto;
import com.mustafa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(UserRequestDto dto){
        User user = User.builder()
                .id(dto.id())
                .phone(dto.phone())
                .name(dto.name())
                .userName(dto.userName())
                .about(dto.about())
                .email(dto.email())
                .authId(dto.authId())
                .build();
        userRepository.save(user);
    }

    public void update(UserRequestDto dto){
        User user = User.builder()
                .id(dto.id())
                .phone(dto.phone())
                .name(dto.name())
                .userName(dto.userName())
                .about(dto.about())
                .email(dto.email())
                .authId(dto.authId())
                .build();
        userRepository.save(user);
    }

    public Iterable<User> getAll(){
        return userRepository.findAll();
    }
}
