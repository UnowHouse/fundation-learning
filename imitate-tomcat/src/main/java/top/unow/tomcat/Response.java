package top.unow.tomcat;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
 *  @项目名：  foundation-learning
 *  @包名：    top.unow.tomcat
 *  @文件名:   Response
 *  @创建者:   ouyangxiong
 *  @创建时间:  2019-05-03 14:29
 *  @描述：    TODO
 */
public class Response {
    private static final int BUFFER_SIZE=1024;
    Request request;
    OutputStream out;
    public Response(OutputStream out){
        this.out = out;
    }
    public void setRequest(Request request){
        this.request = request;
    }
    public void sendStaticResource() throws IOException{
        byte []bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try{
            File file = new File(HttpServer.WEB_ROOT,request.getUri());

            if(file.exists()){

                out.write(("HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html; charset=utf-8\r\n" +
                        "\r\n" ).getBytes());
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while(ch != -1){
                    out.write(bytes,0,ch);
                    ch = fis.read(bytes,0,BUFFER_SIZE);
                }
            }else{
                String errorMessage = "HTTP/1.1 404 File Not Found\n" +
                        "Content-Type:text/html\n" +
                        "Content-Length:23\n" +
                        "\n" +
                        "<h1>File not Found</h1>";
                out.write(errorMessage.getBytes());
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }finally{
            if(fis != null)
                fis.close();
        }
    }
}
