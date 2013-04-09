package up.site.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class StreamUtil {
    
    private static Logger logger = Logger.getLogger(StreamUtil.class);
    
	/**
     * Read bytes with any given input stream
     * 
     * @param is
     * @return
     */
    public static final byte[] readBytes(InputStream is) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int data;
            while ((data = is.read()) != -1) {
                buffer.write(data);
            }

            return buffer.toByteArray();
        } catch (IOException e) {
            logger.warn("",e);
        }finally {
            closeQuietly(is);
        }
        return new byte[] {};
    }
    
    /**
     * Read bytes from the socket input stream
     * 
     * @param is
     * @return
     */
    public static final byte[] readBytes(InputStream is, int contentLen) {
        if(contentLen > 0){
            int readLen = 0;
            
            int readLengthThisTime = 0;
            
            byte[] message = new byte[contentLen];
            
            try {
                
                while (readLen != contentLen) {
    
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
    
                    if (readLengthThisTime == -1) {//Should not happen.
                        break;
                    }
    
                    readLen += readLengthThisTime;
                }
                
                return message;
            } catch (IOException e) {
            } finally{
                closeQuietly(is);
            }
        }
        
        return new byte[] {};
    }

    public static void copy(InputStream input, OutputStream output) throws IOException {
        copy(input, output, 1024);
    }
    
    public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {

        byte[] buf = new byte[bufferSize];

        int bytesRead = input.read(buf);
        while (bytesRead != -1) {
            output.write(buf, 0, bytesRead);
            bytesRead = input.read(buf);
            output.flush();
        }

        output.flush();
    }
    
    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                logger.error("",e);
            }
        }
    }
    
    public static void closeWithoutLog(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                logger.debug("Close stream failure ->"+e);
            }
        }
    }
}
