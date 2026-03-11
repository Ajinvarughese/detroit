package com.Detroit.detroit.sf.controller;

import com.Detroit.detroit.sf.entity.OTP;
import com.Detroit.detroit.sf.service.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/otp")
@AllArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @PostMapping
    public ResponseEntity<OTP> getOtp(@RequestBody OTP otp) {
        return ResponseEntity.ok(otpService.createOTP(otp));
    }

    @PostMapping("/validate")
    public ResponseEntity<OTP> validateOTP(@RequestBody OTP otp) {
        return ResponseEntity.ok(otpService.validateOTP(otp));
    }
}