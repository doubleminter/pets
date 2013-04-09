package up.site.constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ContentTypeConfig {
	private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static Logger log = Logger.getLogger(ContentTypeConfig.class);

	private static Properties p;
	
	public static void refresh(){
		p = null;
	}
	
	public static String map(String suffix) {
		 if(p == null) {
			 synchronized(ContentTypeConfig.class) {
				p = new Properties();
				FileInputStream fin;
				try {
					fin = new FileInputStream(Constants.APP_REAL_PATH + "WEB-INF/config/content.type.properties");
					p.load(fin);
				} catch (Exception e) {
					log.warn("",e);
				}
			 }
		 }
		
		return p.getProperty(suffix, DEFAULT_CONTENT_TYPE);
	}
	
	public static void main(String[] args) throws IOException {
		FileInputStream fin = new FileInputStream("D:\\svn\\dev\\system\\bz\\src\\site\\web\\WEB-INF\\config\\content.type.properties");
		
		Properties p = new Properties();
		p.load(fin);
		System.out.println(p.getProperty("", DEFAULT_CONTENT_TYPE));
		System.out.println(p.getProperty(".exe", DEFAULT_CONTENT_TYPE));

		System.out.println(p.getProperty(".js", DEFAULT_CONTENT_TYPE));
		System.out.println(p.getProperty(".css", DEFAULT_CONTENT_TYPE));

		System.out.println(p.getProperty(".jpg", DEFAULT_CONTENT_TYPE));
		
		System.out.println(p.getProperty(".png", DEFAULT_CONTENT_TYPE));

		/*BufferedReader br = new BufferedReader(new InputStreamReader(fin));
		String line = br.readLine();
		Set<String> lst = new HashSet<String>();
		while(!StringUtils.isEmpty(line)) {
			lst.add(line);
			line = br.readLine();
		}
		br.close();
		
		String[] arr = new String[lst.size()];
		arr = lst.toArray(arr);
		Arrays.sort(arr);
		
		FileOutputStream fos = new FileOutputStream("D:\\svn\\dev\\system\\bz\\src\\site\\web\\WEB-INF\\config\\content.type.properties");

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		for(String s : arr) {
			bw.write(s);
			bw.newLine();
		}
		
		bw.close();*/
	}
}
