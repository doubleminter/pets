package up.site.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;


/**
 * 系统配置参数类
 */
public class SystemConfig {
    private static Logger log = Logger.getLogger(SystemConfig.class);
    
    private SystemConfig(){}
    
    private static AtomicBoolean refreshing = new AtomicBoolean(false);
    
    private static Map<String, String> configMapping;
    
    private static Map<String, String> backUpConfigMapping;
    
    /**
     * 默认编码
     */
    public static final String KEY_DEFAULT_ENCODING = "default.encoding";

    /**
     * JSON默认日期字符串格式
     */
    public static final String KEY_JSON_DATE_FORMAT = "json.date.format";
    
    /**
     * 管理后台功能权限是否检查开关,'0'表示不验证
     */
    public static final String KEY_FUNC_PERMISSION_FLAG = "func.permission.flag";
    
    /**
     * 默认编码
     */
    public static final String KEY_ZIP_ENCODING = "zipoutputstream.encoding";
    
    /**
     * 后台文件管理功能可访问路径
     */
    public static final String KEY_ADMIN_FILE_VALID_PATHS = "admin.file.valid.paths";
    
    /**
     * 后台允许上传的图片文件类型
     */
    public static final String KEY_ADMIN_FILE_EXT_IMAGE = "admin.file.ext.image";
    
    /**
     * 后台允许上传的flash文件类型
     */
    public static final String KEY_ADMIN_FILE_EXT_FLASH = "admin.file.ext.flash";
    
    /**
     * 后台允许上传的媒体文件类型
     */
    public static final String KEY_ADMIN_FILE_EXT_MEDIA = "admin.file.ext.media";
    
    /**
     * 后台允许上传的附件文件类型
     */
    public static final String KEY_ADMIN_FILE_EXT_ATTACHMENT = "admin.file.ext.attachment";
    
    /**
     * 后台允许用文本框编辑的文件类型
     */
    public static final String KEY_ADMIN_FILE_EXT_TEXT = "admin.file.ext.text";
    
    /**
     * 后台允许上传的附件大小上限(单位:字节B)
     */
    public static final String KEY_ADMIN_ATTACHMENT_MAXLENGTH = "admin.attachment.maxlength";
    
    /**
     * 后台具有个性图标的文件类型
     */
    public static final String KEY_ADMIN_FILE_EXT_ICON = "admin.file.ext.icon";
    
    /**
     * 后台KindEditor文件上传返回路径前缀
     */
    public static final String KEY_ADMIN_KEUPLOAD_URL_PREFIX = "admin.keupload.url.prefix";
    
    /**
     * 后台应用日志文件存放路径
     */
    public static final String KEY_APP_LOG_LOCATION = "app.log.location";
    
    /**
     * 后台应用日志文件单次读取字节数
     */
    public static final String KEY_APP_LOG_BYTE = "app.log.quantity";
    
    /**
     * 反馈响应信息
     */
    public static final String KEY_MOBILE_FEEDBACK_MESSAGE = "mobile.feedback.message";
    
    /**
     * 文章分页标识
     */
    public static final String KEY_MOBILE_PAGING_BREAK = "mobile.article.pagebreak";
    
    /**
     * 客户端文件存放的相对路径
     */
    public static final String KEY_CLIENT_PATH = "admin.file.client.path";
    
    /**
     * 二维码图片存放的相对路径
     */
    public static final String KEY_TWO_DIM_CODE_IMAGE_PATH = "admin.file.2dimcode.path";
    
    /**
     * 常见问题展示位置列表
     */
    public static final String FAQ_POSITIONS = "faq.positions";
    
    public static final String KEY_NUMBER_OF_WORDS_PER_QUESTION = "web.question.words.question";
    
    /**
     * 系统可接受最大图片宽度
     */
    public static final String IMG_MAX_WIDTH = "img.max.width";
    
    /**
     * 系统可接受最大图片高度
     */
    public static final String IMG_MAX_HEIGHT = "img.max.height";
    
    /**
     * 系统可接受最大图片大小
     */
	public static final String IMG_MAX_SIZE = "img.max.size";
	
	/**
     * 导入VI-POS数据系统容忍错误的最大数
     */
    public static final String VIPOS_ERROR_MAX_SIZE = "vipos.error.max.size";
    
    /**
     * 导入VI-POS数据系统一次批量存储的最大条数
     */
    public static final String VIPOS_SECTION_SAVE_MAX_SIZE = "vipos.section.save.max.size";
	
	/**
	 * 系统可接受最多导出的数量
	 */
	public static final String EXPORT_MAX_QUANTITY = "admin.export.maxquantity";
	
	/**
	 * 网页版发布路径
	 */
	public static final String CPATH_WEB = "web.cpath";
	
	/**
	 * 移动版发布路径
	 */
	public static final String CPATH_MOBILE = "mobile.cpath";

	/**cstp routecode */
	public static final String KEY_CSTP_ROUTECODE = "cstp.route.code";

	/** cstp url */
	public static final String KEY_CSTP_URL = "cstp.url";

	   /** sms download template*/
    public static final String SMS_DOWNLOAD_TEMPLATE = "sms.download.template";

    
	public static final String PAYMENT_RETURN_URL_TEMPLATE = "payment.returnurl.template";

	public static final String PAA_CREDIT_SERVICEINFO = "credit.paa.serviceinfo";

	public static final String PAA_CREDIT_CONTENT_PROVIDER = "payment.paa.content.provider";

	public static final String PAA_CREDIT_TYPE = "credit.paa.type";
	
	public static final String PAA_PAYMENT_TYPE = "payment.paa.type";

	public static final String PAA_SPID = "payment.paa.spid";

	public static final String PAA_SYS_PROVIDER = "payment.sys.provider";

	public static final String PAA_USE_TESTMODE = "payment.testmode";

	public static final String PAA_ORDER_CONTENT = "credit.order.content";

	public static final String PAA_CURRENCY = "payment.paa.currency";

	public static final String BANK_CARD_VERIFY_IP = "payment.card.verifyip";

	public static final String DATE_FORMAT = "json.date.format";//SystemConfig.get(SystemConfig.KEY_JSON_DATE_FORMAT);

	public static final String JIAOFEI_RECHARGE_URL = "jiaofei.recharge.url";

	public static final String RECHARGE_BIZ_RULE = "recharge.biz.rule";

	public static final String PAA_MERCHANT_COUNTRY = "payment.merchant.country";

	public static final String PAA_MERCHANT_NAME = "payment.merchant.name";

	public static final String PAA_TERMINAL_ID = "payment.terminal.id";

	public static final String PAA_MERCHANT_ID = "payment.merchant.id";
	
	/** HTI服务器通信字节限制(单位：字节) 10485760*/
	public static final String HTI_READ_LIMIT = "hti.read.limit";
	
	/** HTI服务器IP地址 172.17.248.51*/
	public static final String HTI_IP = "hti.ip";
	
	/** HTI服务器端口号 5180*/
	public static final String HTI_PORT = "hti.port";
	
	/** 统计基础数据录入地址*/
	public static final String TONGJI_TRACK_URL = "tongji.track.url";

	public static final String ORDER_NUMBER_URL = "orderno.url";

	public static final String ALLOWED_CARD_TYPE_LIST = "allowed.cardtype.list";
	
	/** 预约商户申请渠道方*/
	public static final String MCHT_APPLICATION_CHANNEL = "mcht.application.channel";
	
	/** 预约商户申请跟踪人*/
	public static final String MCHT_APPLICATION_TRACKER = "mcht.application.tracker";
	
	/** jiaofeiUrl*/
	public static final String JIAOFEI_URL = "jiaofei.url";
	
	/** jiaofei ResultQuery 超时时间*/
    public static final String JIAOFEI_TIMEOUT = "jiaofei.timeout";
    
    /** jiaofei参数配置-户号(逗号分隔)*/
    public static final String JIAOFEI_VAR_USRNUM = "jiaofei.var.usrnum";
    
    /** jiaofei参数配置-月份(逗号分隔)*/
    public static final String JIAOFEI_VAR_MONTH = "jiaofei.var.querymonth";
    
    /** jiaofei参数配置-账单明细(逗号分隔)*/
    public static final String JIAOFEI_VAR_BILL = "jiaofei.var.bill";
    
    /** unit: (s) */
    public static final String JIAOFEI_CACHE_TIMEOUT = "jiaofei.cache.timeout";

    /** 信用卡还款新控件prepay请求要素模板 **/
	public static final String CREDIT_JIAOFEI_PAY_TEMPLATE = "credit.jiaofei.pay.template";
	
    public static boolean refresh(){
        try{
            refreshing.set(true); //start refreshing
            backUpConfigMapping = configMapping; //back up the config
            configMapping = new HashMap<String,String>();
            
            return true;
        }catch (Exception e) {
            log.warn("Refresh system config failure! Configurations in memory are not changed.", e);
            configMapping = backUpConfigMapping; // recovery the config
        }finally{
            refreshing.set(false); // Set the refresh flag before notify, avoid futher more threads to warting pool.
                                    // If not, the thread will not be notified which call the get method after the notify method.
        }
        return false;
    }
    
    public static void init(){
        configMapping = new HashMap<String,String>();
    }
    
    public static String get(String key) {
        while(refreshing.get()){ // Wait if refreshing
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                log.warn("Waiting was interrupted.", e);
            } catch (Exception e) {
                log.warn("", e);
            }
        }
        return configMapping.get(key);
    }
    
    public static String getCpathWeb() {
        log.debug("Web Cpath:" + configMapping.get(CPATH_WEB));
        //return configMapping.get(CPATH_WEB);
        return "http://172.17.136.158:8080/mpaysite/web/";
    }
    
    public static String getCpathMobile() {
        log.debug("Mobile Cpath:" + configMapping.get(CPATH_MOBILE));
        //return configMapping.get(CPATH_MOBILE);
        return "http://172.17.136.158:8080/mpaysite/mobile/";
    }
    
    public static String getDefaultCharset() {
        return configMapping.get(KEY_DEFAULT_ENCODING);
    }
}
