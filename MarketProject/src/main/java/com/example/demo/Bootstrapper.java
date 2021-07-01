package com.example.demo;

import com.example.demo.activity.ActivityRepository;
import com.example.demo.item.ItemRepository;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.smsmessage.MessageSMS;
import com.example.demo.smsmessage.MessageSMSService;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.twilio.twiml.Sms;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final ItemRepository itemRepository;

    private final ActivityRepository activityRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            itemRepository.deleteAll();
            userRepository.deleteAll();
            activityRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            authService.register(SignupRequest.builder()
                    .email("cristi@email.com")
                    .username("cristi")
                    .password("Password1!")
                    .phone("+40748650589")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("employee@email.com")
                    .username("employee")
                    .password("Password1!")
                    .phone("+40748650589")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
        }
    }
}
