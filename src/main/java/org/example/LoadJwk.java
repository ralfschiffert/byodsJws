package org.example;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.security.interfaces.RSAPublicKey;

public class LoadJwk {

    public static JWK downloadJwkFromUrl(String jwkUrl) throws IOException, ParseException {
        URL url = new URL(jwkUrl);
        JWKSet jwkSet = JWKSet.load(url);

        // Assuming we want the first key in the set
        // You might want to implement logic to select a specific key if there are multiple
        return jwkSet.getKeys().get(0);
    }

    public static boolean verifyJwsSignature(String jwsString, JWK jwk) throws ParseException, JOSEException {
        // Parse the JWS
        JWSObject jwsObject = JWSObject.parse(jwsString);

        // Get the signing algorithm from the header
        JWSAlgorithm algorithm = jwsObject.getHeader().getAlgorithm();

        // Assuming the JWK is an RSA key
        if (jwk instanceof RSAKey) {
            RSAKey rsaKey = (RSAKey) jwk;
            RSAPublicKey publicKey = rsaKey.toRSAPublicKey();

            // Create a verifier
            JWSVerifier verifier = new RSASSAVerifier(publicKey);

            // Verify the signature
            return jwsObject.verify(verifier);
        } else {
            throw new IllegalArgumentException("Unsupported key type. Expected RSA key.");
        }
    }

    public static void main(String[] args) {
        final String jwkUrl = "https://idbroker.webex.com/idb/oauth2/v2/keys/verificationjwk/"; // Replace with your actual JWK URL
        final String jwsString = "eyJraWQiOiIxOWFmMzYxYS0zYWI0LTU0NzEtYTViMC03MmQxODQyOTRjMmYiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJNeUFwcDMiLCJzdWIiOiJNeUFwcHNQdXJwb3NlMyIsImlzcyI6Imh0dHBzOlwvXC9pZGJyb2tlci53ZWJleC5jb21cL2lkYiIsImV4cCI6MTcyOTI4OTc3NywiY29tLmNpc2NvLmRhdGFzb3VyY2UudXJsIjoiaHR0cHM6XC9cL3NjaGlmZmVydC5tZVwvZHMzIiwiY29tLmNpc2NvLmRhdGFzb3VyY2Uuc2NoZW1hLnV1aWQiOiI3OGVmYzc3NS1kY2NiLTQ1Y2EtOWFjZi05ODlhNGE1OWY3ODgiLCJpYXQiOjE3MjkyODYxNzcsImNvbS5jaXNjby5vcmcudXVpZCI6ImNlODYxZmJhLTZlMmYtNDlmOS05YTg0LWIzNTQwMDhmYWM5ZSIsImp0aSI6IjEyMzQ1NiJ9.SJSz1FR70c12TJRYxXMzdVETTV2cZVWOu54YXM6qPeKJoHXoQQ5uH2DnNPN3BJyzcg3US7hFZasUMDGRYIDxm6qC2b52vWVy3Q-v9QFshH6UvksNUyfwCpJwnU7ztpvcDK6Dy5l6_RuDwdU_upXb5-4efFmmBdP6CuhDI3J29YcpxvpntbduUMF0C72uGr3xqXDAdN90X5KPLx7vh8s7fB4h5WuxuV1CY7jjwz7pCS0Uq1bOgp-Ee_GYJi396CFCh69yee1rQoP2vaBh_XATQaJJOhxwC8W1TVhYpTVOMxpdzDu_MYRu3dECk0eGkn7brPsV0GaUXIAxMGBjaWvzog"; // Replace with your actual JWS
                    //              eyJraWQiOiIxOWFmMzYxYS0zYWI0LTU0NzEtYTViMC03MmQxODQyOTRjMmYiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJNeUFwcDMiLCJzdWIiOiJNeUFwcHNQdXJwb3NlMyIsImlzcyI6Imh0dHBzOlwvXC9pZGJyb2tlci53ZWJleC5jb21cL2lkYiIsImV4cCI6MTcyOTI4OTc3NywiY29tLmNpc2NvLmRhdGFzb3VyY2UudXJsIjoiaHR0cHM6XC9cL3NjaGlmZmVydC5tZVwvZHMzIiwiY29tLmNpc2NvLmRhdGFzb3VyY2Uuc2NoZW1hLnV1aWQiOiI3OGVmYzc3NS1kY2NiLTQ1Y2EtOWFjZi05ODlhNGE1OWY3ODgiLCJpYXQiOjE3MjkyODYxNzcsImNvbS5jaXNjby5vcmcudXVpZCI6ImNlODYxZmJhLTZlMmYtNDlmOS05YTg0LWIzNTQwMDhmYWM5ZSIsImp0aSI6IjEyMzQ1NiJ9.SJSz1FR70c12TJRYxXMzdVETTV2cZVWOu54YXM6qPeKJoHXoQQ5uH2DnNPN3BJyzcg3US7hFZasUMDGRYIDxm6qC2b52vWVy3Q-v9QFshH6UvksNUyfwCpJwnU7ztpvcDK6Dy5l6_RuDwdU_upXb5-4efFmmBdP6CuhDI3J29YcpxvpntbduUMF0C72uGr3xqXDAdN90X5KPLx7vh8s7fB4h5WuxuV1CY7jjwz7pCS0Uq1bOgp-Ee_GYJi396CFCh69yee1rQoP2vaBh_XATQaJJOhxwC8W1TVhYpTVOMxpdzDu_MYRu3dECk0eGkn7brPsV0GaUXIAxMGBjaWvzog
        try {
            JWK jwk = downloadJwkFromUrl(jwkUrl);
            System.out.println("Downloaded JWK: " + jwk.toJSONString());

            boolean isValid = verifyJwsSignature(jwsString, jwk);
            System.out.println("JWS Signature is valid: " + isValid);
        } catch (IOException | ParseException | JOSEException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}