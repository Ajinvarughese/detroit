package com.Detroit.detroit.sf.entity;

import com.Detroit.detroit.library.EntityDetails;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTP extends EntityDetails {
    private String otp;
    private String email;
}
