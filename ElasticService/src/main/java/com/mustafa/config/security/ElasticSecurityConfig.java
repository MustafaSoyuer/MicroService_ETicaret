package com.mustafa.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
         * Spring Boot 3.x öncesi config.
         */
//        httpSecurity.authorizeRequests()
//                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() //bunun haricinde kalan tüm anyrequestler
//                        .anyRequest()
//                                .authenticated();
//        httpSecurity.formLogin();

        /**
         * Spring Boot 3.x sonrası config.
         * Spring Security gelen isteklerin ayruştırmak ve yönetmek için HttpSecurity içinde
         * belli methodlarla işlemler yapar.
         * 1- requestMatchers ->gelen isteklerden filtrelenecek olanları eklemek için kullanılır.
         * 2- permitAll -> belirlenen isteklere erişimi aç.
         * 3- anyRequest -> olabilecek tüm istekler tüm end-point kullanımları anlamına gelir.
         * Dikkat burada konu olan kendinden önce belirlenen end-pointler dışında kalanları
         * dahil etmemektedir.
         * 4- authenticated -> belirlenen isteklere erişimde oturum açmayı zorunlu kıl.
         */
        httpSecurity.authorizeHttpRequests(req->
                req.requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "dev/v1/elastic/user/**"
                ).permitAll()
                        .anyRequest()
                        .authenticated()

        );

        /**
         * _csrf kullanımı genel olarak MVC ve Web projelerinde kullanılır.
         *
         * WAF ->Web Application Firewall
         * genellikle, api gateway üzerinde aktif edilir ve saldrıları engellemek için kullanılır.
         *
         *
         * CSRF restAPI kullanımlarından kapatılır. Çünkü istek atmak için bir sayfaya erişmemize
         * gerek yoktur. Direkt olarak bir end-oint e erişim sağlıyoruz.
         */
//        httpSecurity.csrf().disable(); Spring Boot 3.x öncesi kullanımı
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        log.info("***** Tüm istekler buradan geçecek. *****");
//        httpSecurity.addFilterBefore(null,null);


        return httpSecurity.build();
    }

}
