package com.mustafa.service;

import com.mustafa.dto.request.LoginRequestDto;
import com.mustafa.dto.request.RegisterRequestDto;
import com.mustafa.dto.request.UserSaveRequestDto;
import com.mustafa.entity.Auth;
import com.mustafa.manager.UserProfileManager;
import com.mustafa.rabbitmq.model.CreateUserModel;
import com.mustafa.rabbitmq.producer.CreateUserProducer;
import com.mustafa.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final CreateUserProducer createUserProducer;
//    private final UserProfileManager manager;

    public Boolean register(RegisterRequestDto dto){
        Auth auth = Auth.builder()
                .password(dto.getPassword())
                .email(dto.getEmail())
                .userName(dto.getUserName())
                .createAt(System.currentTimeMillis())
                .updateAt(System.currentTimeMillis())
                .isActive(true)
                .build();
        authRepository.save(auth);
        /**
         * Burada kullanıcıyı authDB ye kaydettikten sonra USerService e Profil oluşturması
         * için bilgilerini iletmemiz gereklidir.
         */
//        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
//                .authId(auth.getId())
//                .userName(auth.getUserName())
//                .email(auth.getEmail())
//                .build();
//        manager.save(userSaveRequestDto)
        /**
         * DİKKAT!!!!
         * Burada userService e kayıt işlemi FeignClient ile yapıyordu ancak bu işlem büyük riskler barındırır.
         * Bu nedenle rabbitmq ile sistemin sorunsuz ve kayıpsız bir şekilde ilerlemesini sağlıyoruz.
         */

        createUserProducer.sendMessage(CreateUserModel.builder()
                        .authId(auth.getId())
                        .email(auth.getEmail())
                        .userName(auth.getUserName())
                .build());

        return  true;
    }

    public Optional<Auth> doLogin(LoginRequestDto dto){
        Optional<Auth> auth = authRepository.findOptionalByUserNameAndPassword(dto.getUserName(), dto.getPassword());
        return auth;
    }
}
