package com.example.seeker.Database;

import android.util.Log;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public interface PublicKeyDatabase {





    static String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnAHkz9KtobRYf1HuMw2GjZm46rrixcqC" +
            "537PbmdgS0fRA9TsGWKSZMr+/+0yHIne+jHa0DtzaYOQpbB8nzC2Q4nm/XS4t1E0O5/kp4MMG0TC" +
            "jv44LZe/b/Al/RwAelCKQ3lpguu40TaquRTHVBtbL8wKYIDe0BlW1uqAgTuzI/gdB5C26mDnD8Pm" +
            "qZ04dCHbKBXLi+u53YxVGkRRLWwraflm20Bu0w2tlMnCx7RX0PR8D+XuhZVEZt4eZWD6pws9DWAa" +
            "Ky7bI4AY7DhpxyQKVyYIOC8cGS6rf8aNltNz1dp5ZqpeX0iy0rTuuCmQx5xz0EKgVcTqJmmjrCpW" +
            "HYMHhwIDAQAB";


     static String encryptMessage(String plainText) throws Exception {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);



        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }





}

