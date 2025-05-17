package com.Detriot.detroit.dto;

import com.Detriot.detroit.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
