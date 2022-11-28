package com.recody.recodybackend.users.features.mail;

import com.recody.recodybackend.users.VerificationCode;

public interface MailManager {
    
    void sendPasswordResetEmail(VerificationCode verificationCode, String to);
    
}
