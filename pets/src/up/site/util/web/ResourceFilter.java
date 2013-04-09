package up.site.util.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import up.site.constants.Constants;
import up.site.constants.ContentTypeConfig;
import up.site.constants.SystemConfig;
import up.site.util.HttpUtil;
import up.site.util.ServletUtil;
import up.site.util.StreamUtil;

public class ResourceFilter implements Filter {

    private Logger logger = Logger.getLogger(getClass());
    
	private static final String defaultVid = "00000000-0000-0000-0000-000000000000";

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        if (!(request instanceof HttpServletRequest)) {
            logger.error(getClass().getName()
                    + ": doFilter(): not inspecting non-Http request.");
            response.getWriter().print("Non-Http request is not allowed!");
            
        } else {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            
            if(req.getRequestURI().contains("res/verify.jpg")) {
                chain.doFilter(request, response);
            	return;
            }
            
            String filePath = getFilePath(req);
            
            if(filePath == null) {
            	resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            	return;
            }
            
            FileInputStream fis = null;
            OutputStream os = null;
            try{
            	File file = new File(Constants.APP_REAL_PATH + filePath);
            	
            	if(!file.exists()) {
            		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                	return;
            	}
	            
            	response.reset();
            	
            	if(file.getName().endsWith(".html") || file.getName().endsWith(".htm")){
            		//Not set attachment
            	}else{
	            	String userAgent = req.getHeader("User-Agent").toLowerCase();
		            if (userAgent.indexOf("msie") != -1) {// 是IE浏览器
		                resp.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "utf-8"));
		            } else { //其他浏览器默认使用ISO-8859-1中文编码
		        		resp.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("utf-8"),"ISO-8859-1"));
		            }
            	}
            	os = resp.getOutputStream();
                fis = new FileInputStream(file);
	            
                setContentType(file.getName(),resp);
                response.setContentLength(fis.available());
                
                StreamUtil.copy(fis, os); //write output
                
                if(filePath.contains("/misc/")) { //send track data for misc
                	String ua = req.getHeader("User-Agent");
            		
            		String uri = req.getRequestURI();
            		String host = ServletUtil.getRealHost(req);
            		String params = req.getQueryString();
            		
            		StringBuffer fullUrl = new StringBuffer();
            		fullUrl.append( "http://");
            		fullUrl.append(host);
            		fullUrl.append(clsUri(uri));
            		fullUrl.append("?");
            		fullUrl.append(StringUtils.isEmpty(params)?"":params);
            		
            		String referer = req.getHeader("Referer");
            		
            		String vid = getVid(req.getCookies());
            		
                	sendTrack(ua,fullUrl.toString(),referer,vid,ServletUtil.getRealIp(req));
                }
            }catch(Exception e){
            	System.out.println(e);
            }finally{
            	 StreamUtil.closeWithoutLog(fis);
                 StreamUtil.closeWithoutLog(os);
            }
            
            //resp.setHeader("Cache-Control", "private"); //default value already
            //res.setDateHeader("Expires", 0);
            //res.setHeader("Pragma", "no-cache");
        }
    }

    private void setContentType(String file, HttpServletResponse resp) {
    	int idx = file.lastIndexOf(".");
    	if(idx == -1){
    		return;
    	}
    	String suffix = file.substring(idx).toLowerCase();
    	resp.setContentType(ContentTypeConfig.map(suffix));
    }
    
    private String clsUri(String uri) {
    	if(StringUtils.isEmpty(uri)){
    		return "";
    	}
    	
    	return uri.replaceFirst("mpaysite/mobile/", "").replaceFirst("mpaysite/web/", "");
    }
    
    private void sendTrack(String ua, String url, String referer, String vid, String ip){
		try {
			if(StringUtils.isEmpty(referer)){
	    		referer = "";
	    	}
			StringBuffer trackFullUrl = new StringBuffer();
			trackFullUrl.append(SystemConfig.get(SystemConfig.TONGJI_TRACK_URL));
			trackFullUrl.append("?");
			trackFullUrl.append("ua=");
			trackFullUrl.append(URLEncoder.encode(ua));
			trackFullUrl.append("&url=");
			trackFullUrl.append(URLEncoder.encode(url));
			trackFullUrl.append("&referer=");
			trackFullUrl.append(URLEncoder.encode(referer));
			trackFullUrl.append("&vid=");
			trackFullUrl.append(vid);
			trackFullUrl.append("&ip=");
			trackFullUrl.append(ip);
			HttpUtil.get(trackFullUrl.toString());
		} catch (Exception e) {
			logger.warn("Send client track failure. ->" + e.getMessage());
		}
	}
    
    private String getVid(Cookie[] cookies) {
		if(cookies == null || cookies.length == 0) {
			return defaultVid;
		}
		for(Cookie cookie : cookies) {
			if("vid".equalsIgnoreCase(cookie.getName())){
				return cookie.getValue();
			}
		}
		return defaultVid;
	}
    
    private String getFilePath(HttpServletRequest req) throws UnsupportedEncodingException{
		String uri = req.getRequestURI();
		
		uri = URLDecoder.decode(uri, "utf-8");
		
		if (StringUtils.isEmpty(uri)) {
			return null;
		}

		uri = trimPath(uri);
		
		if (StringUtils.isEmpty(uri)) {
			return null;
		}
		
		int idx = uri.indexOf("res/");

		if (idx == -1) {
			return null;
		}
		
		if(!uri.contains(".")) { //check supported file extension
			return null;
		}
		
		uri = uri.substring(idx);
		
		return uri;
    }
    
    private static String trimPath(String path) {
    	if(path.startsWith("/../")) return null;
    	
    	if(path.contains("../")) {
	    	String[] parts = path.split("/");
	    	if(parts.length == 1) {
	    		return path;
	    	}
	    	
	    	int idx = path.indexOf("../");
	    	String s = path.substring(0,idx-1);
	    	String e = path.substring(idx + 2);
	    	
	    	idx = s.lastIndexOf("/");
	    	s = s.substring(0, idx);
	    	
	    	path = s + e;
	    	
	    	if(path.contains("../")) {
	    		path = trimPath(path);
	    	}
    	}
    	return path;
    }
    
    public void init(FilterConfig arg0) throws ServletException {
        System.setProperty("file.encoding", Constants.DEFAULT_ENCODING);
    }
}