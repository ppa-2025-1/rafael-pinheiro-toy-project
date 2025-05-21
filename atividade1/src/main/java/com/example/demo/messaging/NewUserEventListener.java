package com.example.demo.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.NewUser;
import com.example.demo.model.business.UserBusiness;

@Component
public class NewUserEventListener {

    private final UserBusiness userBusiness;

    public NewUserEventListener(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }


    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void newUser(NewUser event) {
        userBusiness.criarUsuario(event);
    }
}