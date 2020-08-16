package com.lhsj.police.dingding.sign;

import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.core.text.ReEncodes;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Signs {

    private static Mac mac;

    static {
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            ReLogs.warn(Signs.class.getName(), e);
        }
    }

    /**
     * 参考文档：https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq
     *
     * @param timestamp 时间戳
     * @param secret    需要加密的文本
     */
    public static synchronized String sign(Long timestamp, String secret) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            mac.init(new SecretKeySpec(secret.getBytes(UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(UTF_8));
            return URLEncoder.encode(ReEncodes.encodeBase64(signData), "UTF-8");
        } catch (Throwable e) {
            ReLogs.warn(Signs.class.getName(), e);
            return null;
        }
    }

}
