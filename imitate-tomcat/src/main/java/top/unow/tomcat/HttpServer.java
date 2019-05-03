package top.unow.tomcat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *  @项目名：  foundation-learning
 *  @包名：    top.unow.tomcat
 *  @文件名:   HttpServer
 *  @创建者:   ouyangxiong
 *  @创建时间:  2019-05-03 14:09
 *  @描述：    TODO
 */
public class HttpServer {
    public static final String WEB_ROOT=System.getProperty("user.dir") + File.separatorChar + "webRoot";
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;
    private static final int PORT = 8080;

    public static void main(String [] args){

        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while(!shutdown){
            Socket socket = null;
            InputStream in = null;
            OutputStream out = null;

            try {
                socket = serverSocket.accept();
                in = socket.getInputStream();
                out = socket.getOutputStream();
                Request request = new Request(in);
                request.parse();
                Response response = new Response(out);
                response.setRequest(request);
                response.sendStaticResource();
                System.out.println(request.getUri());
                socket.close();
                shutdown = request.getUri().equals("/shutdown");
                System.out.println(shutdown);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
