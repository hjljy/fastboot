package cn.hjljy.fastboot.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author yichaofan
 */
public class RequestUtil {

    /**
     * 获取请求当中body的数据
     * @param inputStream 数据量
     * @return json字符串
     */
    public static String inputStream2String(InputStream inputStream){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            sb.append("body参数获取失败");
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取请求当中的token
     * @param request 请求
     * @return token字符串
     */
    public static String getToken(HttpServletRequest request){
        return request.getHeader("Authorization").substring("Bearer".length()).trim();
    }
}
