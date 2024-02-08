package com.mustafa.manager;

import com.mustafa.dto.request.UserSaveRequestDto;
import org.apache.catalina.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.mustafa.constants.RestApiUrls.ADD;

/**
 * MicroServisler arasında iletişimi RestAPI üzerinden sağlamak için kullanılır.
 * İletişim kurulacak servisin controller katmanına istek atar.
 * İki adet parateresini özellikle kullanmalıyız:
 * 1- url : istek atılacak controller sınıfına erişim adresini yazıyoruz.
 * 2- name : yazılan her bir manager için bir isim veriyoruz. Zorunlu değildir. Ancak
 * aynı ismi taşıyan birden fazla manager olursa sistem hata verir. Sorunu anlamamız mümkün
 * olmayabilir. Kullanırken dikkatli olun.
 */
@FeignClient(url = "http://localhost:9094/dev/v1/user", name = "userProfileManager")
public interface UserProfileManager {
    /**
     * metoda istek atabilmeyi tanımlıyoruz
     */
    @PostMapping(ADD)
    ResponseEntity<Void> save(@RequestBody UserSaveRequestDto dto);

    }
