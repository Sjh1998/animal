package service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 86181 on 2020/3/1.
 */
public class Cheshi1 {
    public static void main(String[] args) throws IOException {
        while (true){
            Socket client = null;
            Scanner readFromServer = null;
            PrintStream writeMsgToServer = null;
            try {

                //尝试与服务器建立连接
                client = new Socket("127.0.0.1",9090);
                //获取此连接的输入输出流
                readFromServer = new Scanner(client.getInputStream());
                writeMsgToServer = new PrintStream(client.getOutputStream());
                //进行数据的输入输出
                //让用户自己输入动作
                System.out.println("请输入动作");
                Scanner sc=new Scanner(System.in);
                String s=sc.next();
                writeMsgToServer.println(s);
                if(readFromServer.hasNext()){
                    System.out.println("服务器发来的消息为："+readFromServer.nextLine());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                client.close();
                readFromServer.close();
                writeMsgToServer.close();
            }
        }
    }
}
