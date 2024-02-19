package com.mustafa.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    /**
     * Bir kuyruk yapısında, gönderilecek olan ileti
     * bir exchange üzerinden kuyruğa iletilir. İletimin gerçekleşmesi için
     * queue ile exchange arasında bir köprü olmalıdır. Bu köprüde
     * bindingkey dir. Exchange->kuyruk-> ikisini birbirine bağlayan bindingkey
     */

    private final String EXCHANGE_AUTH = "auth-exchange";
    private final String QUEUE_AUTH = "auth-queue";
    private final String BINDING_KEY_AUTH = "auth-binding-key";

    /**
     * DİKKAT!!!!!
     * Özellikle yapılması gerekenler ortamd Queue , Exchange, Binding nesnelerinin olması gerekmektedir.
     */

    /**
     * 1. Exchange nesnesi ortama sağlanmalıdır. (Direct, Fanout, Topic gibi)
     * Lütfen bunun düşünün, Spring çalışma yapısında spring application context içine nesneleri yaratarak ekler
     * bu nesne yaratma işlemi Bean olarak adlandırılır. Burada eklediğimiz methodlar nesnedöndüğü için onları
     * Bean olarak ortama ekleriz.
     */

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_AUTH);
    }

    @Bean
    Queue queueAuth() {
        return new Queue(QUEUE_AUTH);
    }

    @Bean
    Binding bindingAuth(final DirectExchange directExchange, final Queue queueAuth) {
        return BindingBuilder.bind(queueAuth).to(directExchange).with(BINDING_KEY_AUTH);
    }


}
