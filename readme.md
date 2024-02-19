# KURULUMLAR VE PROJE TEKNOLOJİLERİ 

## Docker üzerinde postgreSQL kurulumu
        Uygulamamızda Auth servis üzerinde kullanıcı oturum açma işlemlerini ve kayıtlarını ilişkisel bir
    veritabanında tutuyoruz. Veritabanı olarak PostgreSQL kullanıyoruz. postgreSQL i ddocker üzerinde çalıştırmak
    için aşağıdaki kodu kullanabilirsiniz.

```
    docker run --name postgreSQL -e POSTGRES_PASSWORD=root -p 5432:5432 -d postgres
```

    
## Dockor üzerinde mongoDB çalıştırmak
    MongoDB kurulumu yaparken yetkili kullanıcı bilgilerinin girilmesi gereklidir. Bu bilgilerin imajların
    Ortam Değişkenlerine atayarak yapabiliyorsunuz. Docker bu tarz bilgileri ekleyebilmeniz için size
    ek parametreler sunmaktadır.
    EK BİLGİ:
    Docker üzerinde bir imaj eklemek istiyorsak -> docker pull [IMAGE_NAME]
    Docker üzerinde bir imaj çalıştırmak istiyorsak -> docker run [IMAGE_NAME]
    Burada önemli bir nokta vardır. docker run eğer ortamda ilgili imaj yok ise önce imajı indirir sonra çalıştırır.
    yani docker run yapmak için önce imajı pull etmenize gerek yoktur.
    Aşağıdaki kod çalışan bir mongoDB oluşturacaktır.

```bash
    docker run --name java13MongoDB -d -e"MONGO_INITDB_ROOT_USERNAME=admin" -e"MONGO_INITDB_ROOT_PASSWORD=root" -p 27017:27017 mongo:jammy
```

    MongoDB yi yönetebilmek için bir araca ihtiyacımmız var. Bu aracın adı MongoDb Compass tool. bu aracı indirip
    kurmanız gereklidir. Adres:   https://www.mongodb.com/try/download/compass

    Compass kurulumu bittikten sonra, açılan yeni pencerede "New Connection +" butonunu tıklıyorsunuz. Ekranın ortasında
    "> Advanced connection options" butonuna tıklayarak detaylı bağlantı ayarlarını yapıyoruz.
    1- Açılan ekranda "Host" kısmına veritabanınızın ip adresini ve port numarasını giriyorsunuz. yerel bilgisayarınız
    için kullanılacak ise ya da docker desktop üzerinde ise (localhost:27017) şeklinde yazıyorsunuz.
    2- Authantication kısmına geçiş yaparak kurulum sırasında girdiğiniz kullanıcı adı ve şifreyi yazıyorsunuz. Docker
    run komutu ile çalıştırdı iseniz -e (enviroment variable) ile giriş yaptığınız bilgileri yazınız. (admin - root)
    
    NOT: MongoDB yi ilk kurulumları ve kullanımları için admin kullanıcı ile işlemleri yapabilirsiniz. Ancak, 
    veritabanlarını yönetmek ve işlemek için kullanmayınız. her DB için ayrı kullanıcı ve yetkiler oluşturunuz
    root kullanıcısı ve şifreleri sadece ilk kurulum için kullanılmalı ve tekrar kullanılmamalıdır. Sadece gerekli
    olduğu durumlarda müdahele için kullanınız.
    Gerekli dökümantasyonlara :     https://www.mongodb.com/docs/manual/    ulaşabilirsiniz.

    Yetkilendirme işlemleri
    1- MONGOHS a tıklayarak açıyorsunuz
    2- Açılan kısımda test> şeklinde bir yer göreceksiniz, öncelikle test DB den kendi DB nize geçmek için 
    use [DB_ADI] yazıp enter a baınız.
    Örn:
    use UserProfile
    switched to db UserProfile
    UserProfile> şeklinde bir görüntü elde edeceksiniz.
    3- burada kullanıcı oluşturmak için gerekli komutları giriyoruz.
    db.createUser({
        user: "bilgeUser",
        pwd: "bilgeUser*",
        roles: ["readWrite","dbAdmin"]
    })

```
    db.createUser({ user: "bilgeUser", pwd: "bilgeUser*", roles: ["readWrite","dbAdmin"]})
```

## Docker üzerinde Redis Single Node oluşturmak

```bash
    docker run --name java13-redis -p 6379:6379 -d redis
```

```bash 
docker run  --name redis-gui -d -p 8001:8001 redislabs/redisinsight:1.14.0
```

## Redis Spring Boot Configuration
    İlgili bağımlılık eklenir.
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis
    implementation 'org.springframework.boot:spring-boot-starter-data-redis:3.2.2'

    DİKKAT!!!
    Redis repository olarak kulanılabileceği gibi, Cache olarakta kullanılabilir. Bu nedenle Spring te Cache i
    ve Redis Repository aktif etmek için gerekli annotasyonları config e eklemeniz uygun olacaktır.

```java
@Configuration
@EnableRedisRepositories
@EnableCaching
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }

}
```   

    Rediste cache oluşturmak için, istediğiniz methodun üzerinde @Cachable anatasyonu ekliyorsunuz böylelikle bu method a girilen 
    değerler için bir Key oluşturuluyor ve retırn değeri redis üzerinde cache lenmiş oluyor

    DİKKAT!!! Spring Boot üzerinde alınan Cache lerin temizlenmesi
    1- Objects.requireNanNull(cacheManager.getCache("user-find-all"")).clear();
    bu işlem br key e sahip olmayan  cache leri temizlemek için kullanılır.
    2-     1- Objects.requireNanNull(cacheManager.getCache("user-find-all"")).evict(KEY);
    bu işlem dışarıdan değer alan bir methodun cache lenmiş datalarını özel olarak silmek için kullanılır.
    @Cacheable("find-by-ad")
    findByAd("muhammet")-> Redis => find-by-ad::muhammet


## ElasticSearch Kurulumu ve Kullanımı

```bash
    docker network create java13-network
```

```bash
    docker run -d --name elasticsearch --net java13-network -p 9200:9200 -p 9300:9300 -e "xpack.security.enabled=false" -e "xpack.security.transport.ssl.enabled=false" -e "discovery.type=single-node" -e "ELASTIC_USERNAME=admin"  -e "ELASTIC_PASSWORD=root" -e "ES_JAVA_OPTS=-Xms512m -Xmx1024m" elasticsearch:8.12.1
```

```bash
    docker run -d --name kibana --net java13-network -p 5601:5601 kibana:8.12.1
```
    DİKKAT!!!
    Elasticsearch sürümleri ile spirng sürümleri arasında bir uyum olması gerekli çünkü eski sürümleri kullanabilmek için
    belli spring boot sürümlrerini kullanmanız gereklidir.

    Spring Boot ile kullanmak için öncelikle bağımlılık ekliyoruz.
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-elasticsearch
    implementation 'org.springframework.boot:spring-boot-starter-data-elasticsearch:3.2.2'

    İligi elasticsearch e bağlanmak için gerekli olana bağlantı configlerini application.yml içine yazoyoruz.

```yml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: admin
    password: root 
```

## RabbitMq Kurulum ve Kullanım
    RabbitMq iki port ile çalışır. 5672, 15672 bu portlardan;
    1- 5672 olan port Rest isteklerini işlemek için kullanılır, bu nedenle Spring Boot bu porta bağlanır.
    2- 15672 olan port arayüz webUI kısmıdır. yönetim ekranı burasıdır.


```bash
    docker run -d --name java13-rabbit -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=root rabbitmq:3-management
```

    Spring boot ile kullanım için;
    implemantatio 'org.springframework.boot:spring-boot-starter-amqp:3.2.2'

## Servisler arası iletişim 

## Application.yml bilgisini config serverdan almak ve configure etmek
    Application Properties (yml) için gerekli configler.
    https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html
 