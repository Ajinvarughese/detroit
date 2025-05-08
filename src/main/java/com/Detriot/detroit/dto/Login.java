package com.Detriot.detroit.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    private String email;
    private String password;
}
