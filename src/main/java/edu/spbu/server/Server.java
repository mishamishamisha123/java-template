package edu.spbu.server;



import java.io.*;

import java.net.ServerSocket;

import java.net.Socket;

import java.util.Arrays;



public class Server {

    static private Socket connection;

    static private DataOutputStream output;

    static private DataInputStream input;

    public static void main(String[] args){

        try{

            int port = 8080;

            ServerSocket server = new ServerSocket(port);

            System.out.println("Waiting for connection...");

            while (true) {

                connection = server.accept();

                System.out.println("Connection accepted.");



                output = new DataOutputStream(connection.getOutputStream());

                System.out.println("DataOutputStream  created");



                input = new DataInputStream(connection.getInputStream());

                System.out.println("DataInputStream created");



                String filePath = receiveData(); //получает запрос клиента

                sendData(filePath); // отправляет ответ клиенту

                server.close();

            }

        }catch(IOException e){

            e.printStackTrace();

        }

    }



    //отправляет ответ

    private static void sendData(String filePath) throws IOException {

        File file = new File(filePath);

        if(file.exists()){

            try(FileReader fileRead = new FileReader(file)){

                output.flush();

                BufferedReader reader = new BufferedReader(fileRead);

                StringBuilder text = new StringBuilder();

                String i,content;

                i=reader.readLine();

                while(i!=null){

                    text.append(i);

                    i=reader.readLine();

                }

                content = text.toString();

                String message="HTTP/1.1 200 OK\r\n" +

                        "Server: 127.0.0.1\r\n" +

                        "Content-Type: text/html\r\n" +

                        "Connection: close\r\n\r\n" +content;

                output.write(message.getBytes());//отправляем ответ

                output.close();

            }

            catch(IOException e){

                e.printStackTrace();

            }

        } else{

            output.writeUTF("Искомого файла не существует");

        }

    }

    private static String receiveData(){



        String filePath;

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        try{

            String line;

            if((line=reader.readLine())!=null) {

                String[] st = line.split(" ");

                System.out.println(Arrays.toString(st));

                if(st[1].length()>0){

                    filePath = st[1].substring(1);

                    System.out.println("Запрос принят. Ищем файл: " + filePath);

                    return filePath;

                }

            }else System.out.println("Что то сломалось");



        }catch(IOException e){

            e.printStackTrace();

        }

        return "empty";

    }

}