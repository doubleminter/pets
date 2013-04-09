package up.site.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;

import up.site.constants.Constants;
import up.site.constants.SystemConfig;

public class ModelHelper {

	private static final String DOT = ".";
	
    public static Model initAdmin(Model model, HttpServletRequest request) {
        model.addAttribute("cPath", Constants.ADMIN_CPATH);
        model.addAttribute("cPathWeb", SystemConfig.getCpathWeb());
        model.addAttribute("cPathMobile", SystemConfig.getCpathMobile());

        return model;
    }

    
    public static Model initMobile(Model model, HttpServletRequest request) {

        String uri = request.getRequestURI().replaceFirst(request.getContextPath() + "/", "");

        model.addAttribute("cPath", SystemConfig.getCpathMobile());
        model.addAttribute("prefix", getPrefix(uri));
        
        return model;
    }
    
    public static Model initWeb(Model model, HttpServletRequest request) {

//        String uri = request.getRequestURI().replaceFirst(request.getContextPath() + "/", "");

        model.addAttribute("cPath", SystemConfig.getCpathWeb());
//        model.addAttribute("prefix", getPrefix(uri));
        
        return model;
    }
    
    public static String getCPath(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceFirst(request.getContextPath() + "/", "");
        
        return getHomePath(uri);
    }
    
    public static String getHomePath(String uri) {
        StringBuilder s = new StringBuilder();
        
        boolean firstFlag = true;
        for (int i = 0; i < uri.length(); i++) {
            if (uri.charAt(i) == '/') {
                if (firstFlag) {
                	firstFlag = false;
                	continue;
                }
                s.append("../");
            }
        }
    	
		return s.toString();
    }
    
    /**
     * 获取访问url相对于app/mobile/的相对路径
     * @param uri 访问url
     * @return
     */
    public static String getPrefix(String uri){
    	String s = uri.substring("mobile/".length());
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 0; i < s.length(); i++){
    		if (s.charAt(i) == '/') {
                sb.append("../");
            }
    	}
    	
    	return sb.toString();
    }
    
    /**
     * 获取访问url相对于app/admin/的相对路径
     * @param uri 访问url
     * @return
     */
    public static String getAdminPrefix(String uri){
    	String s = uri.substring("admin/".length());
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 0; i < s.length(); i++){
    		if (s.charAt(i) == '/') {
                sb.append("../");
            }
    	}
    	
		if (StringUtils.isNotEmpty(sb.toString()))
			return sb.deleteCharAt(sb.length() - 1).toString();

		return sb.toString();
    }
    
	public static void routePage(Model model, HttpServletRequest request) {
		Integer curpage;
		try {
			curpage = Integer.parseInt(request.getParameter("p"));
			model.addAttribute("pageno", curpage);
		} catch (Exception e) {
			model.addAttribute("pageno", 1);
		}
	}
	

	public static String redirectAdmin(String uri) {
	    return "redirect:" + Constants.ADMIN_CPATH + uri;
	}
}
