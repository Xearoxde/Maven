package ftpChecker;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Test {
	 
    public static void main(String[] args) throws UnknownHostException {
        String server = "85.214.38.42";
        int port = 21;
        String user = "anonymous";
        String pass = "";
 
        FTPClient ftpClient = new FTPClient();
        
        class ftpDirectory{
        	@SuppressWarnings("unused")
			int sum;
        	FTPFile file;
        }
        
        ArrayList<ftpDirectory> ftpDirectories = new ArrayList<ftpDirectory>();
        
        ftpDirectory directories = new ftpDirectory();
        
        try {
        	
            ftpClient.connect(server, port);
            showServerReply(ftpClient);
 
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Connect failed");
                return;
            }
            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
 
            if (!success) {
                System.out.println("Could not login to the server");
                return;
            }
            
            FTPFile[] dirs = ftpClient.listDirectories("/pub/mcplugins/xhome");
            ArrayList<String> ftpDirs = new ArrayList<String>();
            
            String majorVersion;
            String minorVersion;
            String buildVersion;
            
            int majorInt, minorInt, buildInt, sum;
            
            
            
            for(int i = 0; i<dirs.length;i++){
            	System.out.println(dirs[i].getName());
            	System.out.println("");
            	ftpDirs.add(dirs[i].getName().replace("-SNAPSHOT", ""));
            	
            	majorVersion = ftpDirs.get(i).substring(0,ftpDirs.get(i).indexOf("."));
            	minorVersion = ftpDirs.get(i).substring(ftpDirs.get(i).indexOf(".")+1,ftpDirs.get(i).lastIndexOf("."));
            	buildVersion = ftpDirs.get(i).substring(ftpDirs.get(i).lastIndexOf(".")+1,ftpDirs.get(i).length());
            	
            	majorInt = Integer.parseInt(majorVersion)*100;
            	minorInt = Integer.parseInt(minorVersion)*10;
            	buildInt = Integer.parseInt(buildVersion)*1;
            	
            	sum = majorInt + minorInt + buildInt;
            	
            	directories.file = dirs[i];
            	directories.sum = sum;
            	
            	ftpDirectories.add(directories);
            	
            	System.out.println("Directory "+i+" Sum "+sum );
            }
            
            String directoryName = "/pub/mcplugins/xhome/"+ftpDirectories.get(ftpDirectories.size()-1).file.getName()+"/";
            
            FTPFile[] files1 = ftpClient.listFiles(directoryName);
            
            printFileDetails(files1);
            
            URL location = Test.class.getProtectionDomain().getCodeSource().getLocation();
            
            for(int i = 0; i<files1.length;i++){
            	System.out.println("FileName = "+files1[i].getName());
            	if(files1[i].getName().contains(".jar")){
            		String fileName = files1[i].getName();
            		downloadFile(directoryName+files1[i].getName(), location.getPath().substring(1)+fileName, ftpClient);
            		break;
            	}
            }
            
            
 
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        } finally {
            // logs out and disconnects from server
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
 
    private static void printFileDetails(FTPFile[] files) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FTPFile file : files) {
            String details = file.getName();
            if (file.isDirectory()) {
                details = "[" + details + "]";
            }
            details += "\t\t" + file.getSize();
            details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
 
            System.out.println(details);
        }
    }
 
    @SuppressWarnings("unused")
	private static void printNames(String files[]) {
        if (files != null && files.length > 0) {
            for (String aFile: files) {
                System.out.println(aFile);
            }
        }
    }
    
    private static void downloadFile(String remoteFile, String downloadLocation, FTPClient ftpClient) throws IOException{
    	String remoteFile1 = remoteFile;
    	File downloadFile1 = new File(downloadLocation);
    	OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
    	boolean sucess = ftpClient.retrieveFile(remoteFile1, outputStream1);
    	outputStream1.close();
    	
    	if(sucess){
    		System.out.println("File #1 has been downloaded successfully");
    	}else{
    		System.out.println("Downloading Failed");
    	}
    }
 
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
}
//gCcs3@30