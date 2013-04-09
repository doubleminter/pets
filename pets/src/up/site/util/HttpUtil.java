package up.site.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.Properties;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtil {

	public static String UTF_8;
	
    private static final Log logger = LogFactory.getLog(HttpUtil.class);

    private static final HttpConnectionManager connectionManager;
    
    private static final HttpClient client;

    static {

    	HttpConnectionManagerParams params = loadHttpConfFromFile();
    	
    	connectionManager = new MultiThreadedHttpConnectionManager();

        connectionManager.setParams(params);

        client = new HttpClient(connectionManager);
        
        //client.getHostConfiguration().setProxy("172.16.1.53", 8080);
    }
    
    private static HttpConnectionManagerParams loadHttpConfFromFile(){
        Properties p  = new Properties();
        try {
			p.load(HttpUtil.class.getResourceAsStream(HttpUtil.class.getSimpleName().toLowerCase() + ".properties"));
		} catch (IOException e) {
		}
		
		UTF_8 = p.getProperty("http.content.encoding");
		
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(Integer.parseInt(p.getProperty("http.connection.timeout")));
        params.setSoTimeout(Integer.parseInt(p.getProperty("http.so.timeout")));
        params.setStaleCheckingEnabled(Boolean.parseBoolean(p.getProperty("http.stale.check.enabled")));
        params.setTcpNoDelay(Boolean.parseBoolean(p.getProperty("http.tcp.no.delay")));
        params.setDefaultMaxConnectionsPerHost(Integer.parseInt(p.getProperty("http.default.max.connections.per.host")));
        params.setMaxTotalConnections(Integer.parseInt(p.getProperty("http.max.total.connections")));
        params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		return params;
    }
    
	public static String post(String url, String encoding, String content) throws Exception {
		try {
			return new String(post(url, content.getBytes()), encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
    
    
    public static String post(String url, String content) throws Exception{
    	return post(url, HttpUtil.UTF_8, content);
    }
    
    
    public static byte[] post(String url, InputStream content, int contentLength) throws Exception {
    	return post(url, new InputStreamRequestEntity(content, contentLength));
    }

    public static byte[] post(String url, byte[] content) throws Exception {
		logger.info("Request message:[" + new String(content) + "] to " + url);
		byte[] ret = post(url, new ByteArrayRequestEntity(content));
		if (ret != null) {
		    logger.info("Response message:[" + new String(ret) + "] from " + url);
		}
    	return ret;
    }

    public static byte[] post(String url, RequestEntity requestEntity) throws Exception {

        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Connection", "Keep-Alive");
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        method.setRequestEntity(requestEntity);

        try {

            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                logger.warn("Abnormal HTTP Status: " + method.getStatusLine());
            }

            return method.getResponseBody();

        } catch (SocketTimeoutException e) {
        	logger.warn("Timeout exception happened while sending to url [" + url + "], Error message :\n" + e.getMessage(), e);
        	throw e;
        } catch (Exception e) {
        	logger.warn("Connect exception happened while sending to url [" + url + "], Error message :\n" + e.getMessage(), e);
        	logger.warn("[" + url + "] " + e.getMessage() + " <- " + e.getClass().getName());
        	throw e;
        } finally {
            method.releaseConnection();
        }
    }

    public static byte[] get(String url) {

        GetMethod method = new GetMethod(url);
        method.addRequestHeader("Connection", "Keep-Alive");
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));

        try {

            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                logger.warn("Abnormal HTTP Status: " + method.getStatusLine());
            }

            return method.getResponseBody();

        } catch (Exception e) {
            logger.warn("[" + url + "] " + e.getMessage() + " <- " + e.getClass().getName());
        } finally {
            method.releaseConnection();
        }

        return null;
    }
}
