package com.lucas.passwordvault.controller;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Encrypt {

    public static String encrypt(String plainText) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        SecretKey secretKey = (SecretKey) keyStore.getKey("VaultAESKey", null);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] iv = cipher.getIV();
        byte[] encryption = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + encryption.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryption, 0, combined, iv.length, encryption.length);

        return Base64.encodeToString(combined, Base64.DEFAULT);
    }
}
