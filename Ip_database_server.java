package ip_database_server;

import java.sql.*;
import java.net.*;
import java.io.*;

public class Ip_database_server {

    public static void main(String[] args) throws Exception {
        Statement stmt = null;
        DatagramSocket serversocket = new DatagramSocket(1362);
        byte[] senddata = new byte[1021];
        byte[] receivedata = new byte[1021];
        DatagramPacket recvpack = new DatagramPacket(receivedata, receivedata.length);
        serversocket.receive(recvpack);
        String sen = new String(recvpack.getData());
        InetAddress ipaddress = recvpack.getAddress();
        int port = recvpack.getPort();
        System.out.println("Request for host....." + sen);
        String sen1 = sen.trim();
        try {
            String query1 = "SELECT ip_add,category FROM ip_list WHERE domain_name='" + sen1 + "';";
            System.out.println("connecting to database....");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ip_data", "root", "");
            System.out.println("\ndatabase is connected!!!!");
            System.out.println("\nexecuting query!!!!!");
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query1);
            String ip = null, cate = null;
            while (rs.next()) {
                ip = rs.getString("ip_add");
                cate = rs.getString("category");
                senddata = ip.getBytes();
                DatagramPacket pack2 = new DatagramPacket(senddata, senddata.length, ipaddress, port);
                serversocket.send(pack2);
                System.out.println("\nIP Address sent successfully!!!");
                senddata = cate.getBytes();
                DatagramPacket pack3 = new DatagramPacket(senddata, senddata.length, ipaddress, port);
                serversocket.send(pack3);
                System.out.println("\nCategory sent successfully!!!");
                serversocket.close();
            }
            con.close();
        } catch (Exception e) {
            System.out.println("connection failed to database :" + e);
        }
    }
}
