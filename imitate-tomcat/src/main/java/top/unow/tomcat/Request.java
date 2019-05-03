package top.unow.tomcat;

import java.io.IOException;
import java.io.InputStream;

/*
 *  @项目名：  foundation-learning
 *  @包名：    top.unow.tomcat
 *  @文件名:   Request
 *  @创建者:   ouyangxiong
 *  @创建时间:  2019-05-03 14:29
 *  @描述：    TODO
 */
public class Request {
    private String uri;
    private InputStream input;

    public Request(InputStream input){
        this.input = input;
    }

    public void parse(){
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for(int j = 0; j < i; j++){
            request.append((char)buffer[j]);
        }
        System.out.println(request.toString());
        uri = parseUri(request.toString());

    }

    private String parseUri(String requestString) {
        int index1,index2;
        index1 = requestString.indexOf(' ');
        if(index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    public String getUri(){
        return uri;
    }
}
