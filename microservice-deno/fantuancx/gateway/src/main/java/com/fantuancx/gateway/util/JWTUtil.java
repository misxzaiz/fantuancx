package com.fantuancx.gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Map;

public class JWTUtil {

    // 签名/密钥
    private static final String sign = "fantuan";

    /**
     * 设置 JWT
     * @param day jwt 过期时间
     * @param map jwt 负载
     * @return
     */
    public static String setJWT(int day, Map<String, Object> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, day);
        JWTCreator.Builder builder = JWT.create();
        for(String key : map.keySet()){
            builder.withClaim(key,(String) map.get(key));
        }
        String jwt = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(sign));
        return jwt;
    }

    /**
     * 解析 jwt
     * @param jwt jwt
     * @return
     */
    public static Map<String, Claim> getJWT(String jwt){
        JWTVerifier build = JWT.require(Algorithm.HMAC256(sign)).build();
        return build.verify(jwt).getClaims();
    }

    /**
     * 判断签名是否正确
     * @param jwt
     * @return
     */
    public static boolean validateSignature(String jwt) {
        String signature = jwt.split("\\.")[2];
        String computedSignature = computeSignature(jwt);
        return signature.equals(computedSignature);

    }

    static String computeSignature(String jwt) {
        // 获取头部和负载
        String[] parts = jwt.split("\\.");
        // 计算 HMAC 值
        String signature = null;
        try {
            Mac hmac256 = Mac.getInstance("HmacSHA256");
            hmac256.init(new SecretKeySpec(sign.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] digest = hmac256.doFinal((parts[0] + "." + parts[1]).getBytes(StandardCharsets.UTF_8));
            signature = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return signature;
    }
}
