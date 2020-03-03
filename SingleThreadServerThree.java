package service;

import web.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 86181 on 2020/3/3.
 */
public class SingleThreadServerThree extends Thread {
    Socket clientSocket;
    public SingleThreadServerThree(Socket clientSocket) {
        super();
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        Test test = new Test();

            Scanner readFromClient = null;
            PrintStream sendMsgToClient = null;
            try {


                System.out.println("等待客户端建立连接...");

                //一直阻塞直到客户端建立连接

                System.out.println("有新的客户端建立连接，端口号为：" + clientSocket.getPort());
                System.out.println(clientSocket.getLocalPort());//本地端口号

                //获取此连接的输入输出流
                //输入使用Scanner，输出使用打印流
                readFromClient = new Scanner(clientSocket.getInputStream());
                sendMsgToClient = new PrintStream(clientSocket.getOutputStream(), true, "UTF-8");

                //进行数据的输入输出
                if (readFromClient.hasNext()) {
                    String s = readFromClient.nextLine();
                    if (s.equals("LIST")) {
                        sendMsgToClient.println(test.calculate());
                    }else{
                        String sb=s.substring(4);
                        sendMsgToClient.println(test.pull(sb));
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //关闭包装流
                readFromClient.close();
                sendMsgToClient.close();

        }
    }
    public static void main(String[] args) throws IOException {
        //建立TCP连接服务,绑定端口
        ServerSocket tcpServer = new ServerSocket(9090);
        //接受连接,传图片给连接的客户端,每个TCP连接都是一个java线程
        while(true){
            Socket clientSocket = tcpServer.accept();
            new SingleThreadServerThree(clientSocket).start();
        }
    }
}
