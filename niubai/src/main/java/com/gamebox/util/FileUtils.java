package com.gamebox.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.FileLock;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;


public class FileUtils {

	/**
	 * 不可实例化
	 */
	private FileUtils(){	
	}
	
	public static String rootPath;

	static {
		File f = new File(FileUtils.class.getResource("/").getPath());
		String p = f.getParentFile().getParentFile().getPath();
		rootPath = new StringBuilder(p.replace('\\', '/')).append("/")
				.toString();
	}
	
	/**
     * 从系统配置文件中读取文件内容
     * @param fileName
     *          文件名
     */
	public static String readFileFromSysResource(String path) {
        File file;
        BufferedReader reader = null;
        String lastStr = "";
        try {
            file = new ClassPathResource(path).getFile();
            reader = new BufferedReader(new FileReader(file));
            String tpStr = null;
            while ((tpStr = reader.readLine()) != null) {
                lastStr = lastStr + tpStr;
            }
            reader.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {

                }
            }
        }

        return lastStr;
    }
	
	
	
	public static void writeLog(HttpServletRequest request, String fileName, String log, Integer userId) { 

        String dateTime = DateUtils.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss", null);
        String onlineIP = HttpUtils.getIp(request);
        String refer = request.getHeader("Referer");
        
        if (refer == null) {
            refer = request.getParameter("refer");
        }
        char split = ';';
        StringBuilder logContent = new StringBuilder();
        logContent.append(dateTime);
        logContent.append(split);
        logContent.append(onlineIP);
        logContent.append(split);
        logContent.append(userId);
        logContent.append(split);
        logContent.append(refer);
        logContent.append(split);
        logContent.append(log.trim().replaceAll("(\r\n|\r|\n)", " "));
        logContent.append('\n');

        String content = logContent.toString();
        System.out.println(content);
        
        com.ad.util.KafkaProducer.sendMsg("newgameboxad", content);
        
        String yearMonth = DateUtils.convertDateToString(new Date(), "yyyyMMdd", "-8");
        String logDir = "/usr/local/webapps/";
        File logDirFile = new File(logDir);
        if (!logDirFile.isDirectory()) {
            logDirFile.mkdir();
        }
        String logFileName = null;
        try {
            logFileName = logDir + yearMonth + "_" + fileName + "_" + getServerIp() + ".log";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        File logFile = new File(logFileName);
        if (logFile.length() > 2048000) {
            File[] files = logDirFile.listFiles();
            int id = 0;
            int maxid = 0;
            for (File file : files) {
                String name = file.getName();
                if (name.matches("^" + yearMonth + "_" + fileName + "_(\\d)*\\.log$")) {
                    id = Integer.valueOf(name.substring(name.lastIndexOf("_") + 1, name.lastIndexOf(".")));
                    if (id > maxid) {
                        maxid = id;
                    }
                }
            }
            files = null;
            logDirFile = null;
            logFile.renameTo(new File(logDir + yearMonth + "_" + fileName + "_" + (maxid + 1) + ".log"));
        }
        writeFile(logFileName, content, true);
        
	}
	
	/**
	 * 写文件
	 * @param path
	 * 			文件路径
	 * @param fileName
	 * 			文件名
	 */
	public static void  writeFile(String path, String fileName, String content, boolean append)throws Exception{
		writeFile(path + fileName, content, append);
	}
	
	/**
	 * 写文件
	 * @param path
	 * 			文件路径
	 * * @param content
	 * 			输入内容
	 * * @param append
	 * 			是否写入文件末尾  
	 */
	public static void writeFile(String path, String content, boolean append) {
	    try {
            File file = new File(path);
            FileOutputStream out = new FileOutputStream(file, append);
            OutputStreamWriter fwout = new OutputStreamWriter(out, "UTF-8");
            BufferedWriter bw = new BufferedWriter(fwout);
            FileLock fl = out.getChannel().tryLock();
            if (fl.isValid()) {
                bw.write(content);
                fl.release();
            }
            bw.flush();
            fwout.flush();
            out.flush();
            bw.close();
            fwout.close();
            out.close();
            bw = null;
            fwout = null;
            out = null;
        } catch (IOException e) {
            System.out.println();
            //e.printStackTrace();
        }
	    /*
		File file = new File(path);
		BufferedWriter output =null;
			//创建文件
			 if(!file.exists()){
				 if(!file.getParentFile().exists()){
					   file.getParentFile().mkdirs();
				 }
				 file.createNewFile();
			 }
			 output= new BufferedWriter(new FileWriter(file,append));
			 output.write(content);
			 output.flush();
			 output.close();
			 */
	}

	/**
	 * 删除文件夹下所有的文件
	 * 
	 * @param path
	 */
	public static void deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return;
		}
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				files[i].delete();
			} else if (files[i].isDirectory()) {
				// 删除子目录
				deleteDirectory(files[i].getAbsolutePath());
				// 删除目录本身
				files[i].delete();
			}
		}
	}
	
	public static String getServerIp(){
	    try {
	        String os = System.getProperty("os.name").toLowerCase();

	        if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {   
	            NetworkInterface ni = NetworkInterface.getByName("eth0");

	            Enumeration<InetAddress> ias = ni.getInetAddresses();

	            InetAddress iaddress;
	            do {
	                iaddress = ias.nextElement();
	            } while(!(iaddress instanceof Inet4Address));

	            return iaddress.getHostAddress().replace("10.0.0.", "");
	        }
	    } catch (Exception e) {
            e.printStackTrace();
        }
	    return "none";
    }

}
