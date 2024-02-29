# KOMUT SETLERİ

    kubectl - cli aracı

## get komutu - kubernates objelerinin bilgilerini çeker

    kubectl get nodes -> çalışan asıl sanal PC miz
    kubectl get pods -> node lar üzerinde çalıştırılan sanal küçük conteinerlar
    kubectl get deployments
    kubectl get services
    kubectl get cronjob
    kubectl get secrets

    kubectl get --help -> tüm kodlar

    NOT!!! bilgileri daha kapsamlı almak için -o wide eklemelisiniz
    kubectl get nodes -o wide

## Pod nedir? Ne işe yarar?

    kubernates içinde çalışabilir en küçük objemiz, oluşturmak için create komutunu kullanabiliriz ancak bu yöntem hantal ve kullanışsızdır. Bu neden daha kullanışlı ola deployment ebjesini kullanarak bunu yönetme yoluna gideriz. 
    > kubectl run pod-authservice --image=mustafasoyuer/auth-service:v.0.1
    NOT!!! çalışmayan ya da gereksiz olduğunu düşündüğünüzpodlarısilmek istiyorsanız, 
    > kubectl delete pods [POD_NAME] yazmanız yeterlidir

## Deployment objesi  (emir dosyası) 

    podların durumlarını gözlemleyen, verilen emirler doğrultusunda posları oluşturan, güncelleme gerekli olduğunda bu güncellemeleri sisteme zarar vermeden ve aksatmadan güncellenmesini sağlayan k8s(k ubernete s ) objesidir.

    kubectl ile bir deployment objesi create edebilirsiniz , ancak dopru yöntem bir yml dosyası kullanarak bunu yapmaktır. 


### DİKKAT!!!  bir yml dosyasını deploy etmek

    kubectl apply -f [YML_DOSYASI]

    