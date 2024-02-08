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
    


## Servisler arası iletişim 
    