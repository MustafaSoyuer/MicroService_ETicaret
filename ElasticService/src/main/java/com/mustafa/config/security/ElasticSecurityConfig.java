package com.mustafa.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@Slf4j
public class ElasticSecurityConfig {

    /**
     * Spring security ortamda gelen istekleri işlemek yani filtrelemek için SecurityFilterChain e ihtiyac
     * duyar. Eğer bunu siz sağlamaz iseniz zaten kendisi bir tane yaratır ve sistemi bunun üzerinden yönetir.
     */

    @Bean // spring conf dosyalarını önce oluşturduğu için bu beani de önce oluşturacak böylelikle bu ilk ayağa kalkacak gibi ilk güvenlik
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /**
         * Bu alan tüm güvenlik işlemlerinin yönetildiği kısımdır. Burada hangi isteklerin kontrol edileceği
         * hangi isteklerin herkese açık olacağı belirlenir.
         */

        /**
         * Spring Boot 3.x öncesi config
         */
        httpSecurity.authorizeRequests()
                        .anyRequest()
                                .authenticated();
        httpSecurity.formLogin();


        log.info("***** Tüm istekler buradan geçecek. *****");

        return httpSecurity.build();
    }

}
