package com.email.recuperacion_email.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDTO {

    private String mailFrom;

    private String mailTo;

    private String subject;

    private String username;

    private String token;

}
