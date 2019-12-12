package edu.spbu.server;

import java.io.*;

import java.net.*;



public class Client implements Runnable {

    static private Socket connection;

    static private DataOutputStream output;

    static private DataInputStream input;



    public static void main(String[] args) throws IOException {

        try{

            Client client = new Client("www.239.ru", 80);

            client.sendData("www.239.ru");

            client.receiveData();

        } catch(IOException e){

            e.printStackTrace();

        }

    }

    //конструктор клиента

    public Client(String serverName, int port ) throws IOException {

        connection = new Socket(serverName,port);

        output = new DataOutputStream(connection.getOutputStream());

        System.out.println("DataOutputStream  created");

        input = new DataInputStream(connection.getInputStream());

        System.out.println("DataInputStream created");

    }

    @Override

    public void run(){

    }

    private void sendData(String serverName) throws IOException {

        String s = "GET / HTTP/1.1\r\n" +
                "Host: " + serverName +"\r\n" +
                "\r\n";

        BufferedWriter outputstream = new BufferedWriter(new OutputStreamWriter(output));

        output.write(s.getBytes());

        output.flush();

        System.out.println("Запрос отправлен");

    }

    //получает ответ

    private void receiveData() {

        try{

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            System.out.println("Работаем...\n");

            String s = reader.readLine();

            if(s!=null)

                System.out.println("Ответ: ");

            while(s!=null){

                System.out.println(s);

                s=reader.readLine();

            }

        } catch(IOException e){

            e.printStackTrace();

        }

    }

}