package com.xidian.flow;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Service;

import java.io.*;


/**
 * Created by WJ on 2015/7/23.
 */
@Service("fileUtilities")
public class FileUtilities {


    public static File saveNetImage(String urlStr, UserManager userManager){

        HttpClient httpClient = HttpClientManager.getInstance();
        HttpGet httpGet = new HttpGet(urlStr);
        httpGet.setHeader("Host", "zyzfw.xidian.edu.cn");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        httpGet.setHeader("Accept", "image/png,image/*;q=0.8,*/*;q=0.5");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Referer", "http://zyzfw.xidian.edu.cn/");
        httpGet.setHeader("Cookie", userManager.getPHPSESSID() + "; " +userManager.getBIGipServerzyzfw());
        httpGet.setHeader("Cache-Control", "max-age=0");

        try {
            HttpResponse response = httpClient.execute(httpGet);
            byte[] data = readInputStream(response.getEntity().getContent());
            File imageFile = new File(Constans.IMAGE_FILE_NAME);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(data);
            outputStream.close();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


//    public static File saveNetImage(String urlStr){
//        try {
//            URL url = new URL(urlStr);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(5*1000);
//            InputStream inputStream = connection.getInputStream();
//            byte[] data = readInputStream(inputStream);
//            inputStream.close();
//            File imageFile = new File(Constans.IMAGE_FILE_NAME);
//            FileOutputStream outputStream = new FileOutputStream(imageFile);
//            outputStream.write(data);
//            outputStream.close();
//            return imageFile;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }


    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0 , len);
        }
        return outputStream.toByteArray();
    }
}
