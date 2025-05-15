package com.Detriot.detroit.sf.entity;


import com.Detriot.detroit.enums.Role;
import com.Detriot.detroit.enums.SubRole;
import com.Detriot.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends EntityDetails {


    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private SubRole subRole;

    private String phone;

    private String address;

    private String organization;

}
