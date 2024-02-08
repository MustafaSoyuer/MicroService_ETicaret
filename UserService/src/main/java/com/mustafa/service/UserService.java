package com.mustafa.service;

import com.mustafa.domain.User;
import com.mustafa.dto.request.UserSaveRequestDto;
import com.mustafa.dto.request.UserUpdateRequestDto;
import com.mustafa.exception.ErrorType;
import com.mustafa.exception.UserServiceException;
import com.mustafa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(UserSaveRequestDto dto){
        userRepository.save(User.builder()
                        .userName(dto.getUserName())
                        .email(dto.getEmail())
                        .authId(dto.getAuthId())
                .build());
    }

    public void update(UserUpdateRequestDto dto) {
        /**
         * dto içinde gelen user id bilgisi ile repository de parametre geçerek
         * bu id ye sahip bir kullanıcı olup olmadığı bilgisini çektik.
         */
        Optional<User> user = userRepository.findById(dto.getId());
        /**
         * Eğer id si verilen kullanıcı bulunmaz ise hata fırlatır.
         */
        if (user.isEmpty())
            throw new UserServiceException(ErrorType.ERROR_INVALID_USER_ID);
        /**
         * Herşey yolunda ise kullanıcıyı öncelikle optional içinde çıkartırız
         * ve dto içinden gelen parametreleri güncellenecek kullancıcı bilgileri
         * ile değiştiririz.
         */
        User updateUser = user.get();
        updateUser.setAbout(dto.getAbout());
        updateUser.setName(dto.getName());
        updateUser.setPhone(dto.getPhone());
        updateUser.setPhoto(dto.getPhoto());
        userRepository.save(updateUser);

    }
}
