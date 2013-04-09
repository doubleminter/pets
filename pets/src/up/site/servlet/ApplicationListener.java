package up.site.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import up.site.constants.Constants;
import up.site.constants.SystemConfig;

public class ApplicationListener implements ServletContextListener {
    Logger logger = Logger.getLogger(getClass());
    
    public static WebApplicationContext context;
    
    public void contextDestroyed(ServletContextEvent event) {
        //Not in use.
    }

    public void contextInitialized(ServletContextEvent event) {

        //Set application real path
        Constants.APP_REAL_PATH = event.getServletContext().getRealPath("");
        if(Constants.APP_REAL_PATH !=null){
            if(Constants.APP_REAL_PATH.charAt(Constants.APP_REAL_PATH.length() - 1) != java.io.File.separatorChar){
                Constants.APP_REAL_PATH += java.io.File.separatorChar;
            }
        }

        logger.debug(Constants.APP_REAL_PATH);
        
        // load path config into memo.
        Properties pathConfig = new Properties();
        FileInputStream pathConfigFile;
        try {
            pathConfigFile = new FileInputStream(Constants.APP_REAL_PATH + "WEB-INF/config/cpath.properties");
            pathConfig.load(pathConfigFile);
            Constants.ADMIN_CPATH = pathConfig.getProperty("admin.cpath", "/site/admin/");
            
        } catch (FileNotFoundException e) {
            logger.error("Path config file is not found!", e);
        } catch (IOException e) {
            logger.error("Error read path config file!", e);
        }
        
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        
        //Read system configs fromd db.
        SystemConfig.init();
        
    }
}
