package com.example.demo.smsmessage;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageSMS {
    private String receiver;
    private String message;
}
