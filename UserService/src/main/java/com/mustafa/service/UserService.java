package com.mustafa.service;

import com.mustafa.domain.User;
import com.mustafa.dto.request.UserRequestDto;
import com.mustafa.dto.request.UserSaveRequestDto;
import com.mustafa.dto.request.UserUpdateRequestDto;
import com.mustafa.exception.ErrorType;
import com.mustafa.exception.UserServiceException;
import com.mustafa.manager.ElasticUserManager;
import com.mustafa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    private final ElasticUserManager userManager;

    public void save(UserSaveRequestDto dto){
        User user =userRepository.save(User.builder()
                        .userName(dto.getUserName())
                        .email(dto.getEmail())
                        .authId(dto.getAuthId())
                .build());
        /**
         * Bu işlem exception fırlatabilir. bu nedenle ya try..catch yaparsınız
         * ya da Object null kontrolu yaparsınız.
         */
        Objects.requireNonNull(cacheManager.getCache("user-find-all")).clear();
        UserRequestDto userRequestDto = new UserRequestDto(
                user.getId(),
                user.getAuthId(),
                user.getUserName(),
                user.getEmail(),
                user.getPhoto(),
                user.getName(),
                user.getPhone(),
                user.getAbout()
        );
//        userManager.save(userRequestDto);
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
        Objects.requireNonNull(cacheManager.getCache("user-find-all")).clear();
    }

    @Cacheable(value = "user-find-all")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "get-string")
    public String getString(String ad){
        /**
         * DİKKAT !!!!
         * Aşağıya yazılan kod bloğu bir işlemin uzun sürmesi durumunu simüle etmek
         * için eklenmelidir.
         */
        String createSpring = ad.toUpperCase().concat(" -Hoş geldiniz");
        try {
            Thread.sleep(3000L);
        }catch (InterruptedException exception){
            log.error("Beklenmeyen thread hatası");
        }
        return createSpring;
    }

    /**
     *
     * @param userName
     * @param page -> sayfa numarası
     * @param size -> sayfa boyutu
     * @param sortParameter -> sıralama parametresi yani değişken adı
     * @param sortDirection -> sıralama yönü a..z. ya da z..a.
     * @return
     */
    public Page<User> findAllByUserName(String userName, int page, int size, String sortParameter, String sortDirection) {
        Pageable pageable;
        /**
         * Bir sayfalama oluşturmak için;
         * 1- bir sayfada kaç kayıt görünecek
         * 2- hangi sayfayı görüntülemek istiyorsun
         * 3- Sıralama yapmak istiyor musun? yapacaksan hangi alanı sıralayacaksın?
         * 4-
         */
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortParameter);
        pageable = PageRequest.of(page, size, sort);
        Page<User> result = userRepository.findAllByUserNameContaining(userName, pageable);
        return result;
    }
}
