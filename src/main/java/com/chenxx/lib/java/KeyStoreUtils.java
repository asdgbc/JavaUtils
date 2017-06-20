package com.chenxx.lib.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by asdgbc on 20/6/2017.
 */
public class KeyStoreUtils {

    public static void main(String... args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyStore ks = genKeyStore("JKS",
                "/Users/asdgbc/Workspace/java/VerifyApkSig/finance.keystore",
                "finance.tech.netease.com".toCharArray());
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(getPrivateKey(ks,"android.finance.163.com","finance.tech.netease.com".toCharArray()));
        signature.update(StreamUtils.readBytesFromFile("/Users/asdgbc/Workspace/java/VerifyApkSig/app-prod-release/META-INF/CERT.SF"));
        byte[] sign = signature.sign();
        System.out.println(StringUtils.toHexString(sign));
    }

    /**
     *
     * @param keyStoreType
     * @param keyStoreFilePath
     * @param pwd
     * @return
     */
    public static KeyStore genKeyStore(String keyStoreType, String keyStoreFilePath, char[] pwd) {
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(new FileInputStream(keyStoreFilePath), pwd);
            return keyStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param ks
     * @param alias
     * @param pwd
     * @return
     */
    public static PrivateKey getPrivateKey(KeyStore ks, String alias, char[] pwd) {
        try {
            Key key = ks.getKey(alias, pwd);
            if (key instanceof PrivateKey) {
                return (PrivateKey) key;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
