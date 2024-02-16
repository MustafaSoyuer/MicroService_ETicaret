package com.mustafa.utility;

import com.mustafa.dto.request.UserRequestDto;
import com.mustafa.manager.ElasticUserManager;
import com.mustafa.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component //uygulama ayağa kalkarken bu sınıftan bir nesne ekle
@RequiredArgsConstructor
public class TestAndRun {
    private final UserService userService;
    private final ElasticUserManager userManager;

//    @PostConstruct // metod constructor gibi davransın direk çalıştırsın
    public void start(){
        userService.findAll().forEach(user -> {
            UserRequestDto dto = new UserRequestDto(
                    user.getId(),
                    user.getAuthId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getPhoto(),
                    user.getName(),
                    user.getPhone(),
                    user.getAbout()
            );
            userManager.save(dto);
        });
    }

}
