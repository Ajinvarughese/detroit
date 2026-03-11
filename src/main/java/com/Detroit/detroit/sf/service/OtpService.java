package com.Detroit.detroit.sf.service;

import com.Detroit.detroit.exceptions.OtpExpiredException;
import com.Detroit.detroit.sf.entity.OTP;
import com.Detroit.detroit.sf.repository.OtpRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    public OTP createOTP(OTP otp) {
        String generatedOtp = generateOTP();
        Optional<OTP> existingOtp =
                otpRepository.findByEmail(otp.getEmail());

        if (existingOtp.isPresent()) {
            OTP oldOtp = existingOtp.get();
            OTP responseOtp = new OTP(generatedOtp, oldOtp.getEmail());
            oldOtp.setOtp(passwordEncoder.encode(generatedOtp));
            sendOtp(responseOtp.getOtp(), responseOtp.getEmail());
            otpRepository.save(oldOtp);
            return responseOtp;
        }

        OTP responseOtp = new OTP(generatedOtp, otp.getEmail());
        otp.setOtp(passwordEncoder.encode(generatedOtp));
        sendOtp(responseOtp.getOtp(), responseOtp.getEmail());
        otpRepository.save(otp);
        return  responseOtp;
    }

    private void sendOtp(String otp, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code for Detroit");
        message.setText("Your OTP is: \n\n" + otp + "\n\nThis OTP will be valid for 5 minutes.\n\nDo not share it with anyone. If you didn't make this request, you can safely ignore this email.");

        mailSender.send(message);
    }

    @Transactional
    public OTP validateOTP(OTP otp) {
        OTP existing = otpRepository.findByEmail(otp.getEmail())
                .orElseThrow(() -> new OtpExpiredException("Otp Expired"));
        if(passwordEncoder.matches(otp.getOtp(), existing.getOtp())) {
            otpRepository.delete(existing);
            return existing;
        }
        throw new OtpExpiredException("OTP is wrong");
    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Transactional
    @Scheduled(fixedRate = 60000) // runs every 1 minute
    public void deleteExpiredOTPs() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(5);
        otpRepository.deleteByCreatedAtBefore(expiryTime);
    }
}
