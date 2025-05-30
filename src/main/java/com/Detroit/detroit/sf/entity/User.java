package com.Detroit.detroit.sf.entity;


import com.Detroit.detroit.enums.Role;
import com.Detroit.detroit.enums.SubRole;
import com.Detroit.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends EntityDetails {


    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private SubRole subRole;

    @Column(unique = true, nullable = false)
    private String phone;

    private String address;

    private String organization;

}
