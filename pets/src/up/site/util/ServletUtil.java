package up.site.util;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {
    /**
     * X-Forwarded-Host: 172.17.248.51:8080, 172.17.248.51:8080, 172.17.233.159:808, 172.17.248.51:8080
     */
    public static String getRealHost(HttpServletRequest req) {

        String host = req.getHeader("X-Forwarded-Host");

        if (host == null) {
        	return req.getHeader("Host");
        }

        if (host.indexOf(',') == -1) {
            return host;
        }

        return host.split(",")[0];
    }

    /**
     * X-Forwarded-For: 172.17.233.159, 172.17.248.51, 172.17.233.159, 172.17.248.51, 172.17.233.159
     */
    public static String getRealIp(HttpServletRequest req) {

        String ip = req.getHeader("X-Forwarded-For");

        if (ip != null) {
            if (ip.indexOf(',') == -1) {
                return ip;
            }
            return ip.split(",")[0];
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }

        return ip;
    }
}
