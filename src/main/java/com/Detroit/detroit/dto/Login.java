package com.Detroit.detroit.dto;

import com.Detroit.detroit.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean encrypted = false;
}
