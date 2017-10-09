/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip_database_client;

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Ip_database_client {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientsocket = new DatagramSocket();
        InetAddress ipaddress = InetAddress.getLocalHost();
        byte[] senddata = new byte[1024];
        byte[] receivedata = new byte[1024];
        byte[] receivedata2 = new byte[1024];
        int portaddr = 1362;
        System.out.println("\t\t*****DOMAIN NAME RESOLVER*****");
        System.out.println("\nEnter the hostname......");
        String sentence = br.readLine();
        senddata = sentence.getBytes();
        DatagramPacket pack = new DatagramPacket(senddata, senddata.length, ipaddress, portaddr);
        clientsocket.send(pack);
        DatagramPacket recvpack = new DatagramPacket(receivedata, receivedata.length);
        clientsocket.receive(recvpack);
        String ip = new String(recvpack.getData());
        System.out.println("IPAddress is......." + ip);
        DatagramPacket recvpack1 = new DatagramPacket(receivedata, receivedata.length);
        clientsocket.receive(recvpack1);
        String cate = new String(recvpack1.getData());
        System.out.println("category is......." + cate);
        clientsocket.close();
        System.out.println("\n\n\t*****DOMAIN NAME RESOLVED SUCCESSFULLY*****");
        JOptionPane.showMessageDialog(null,"the domain name...."+sentence+"\nthe ip addres is...."+ip+"\nthe category is..."+cate);
        System.exit(0);
     
    }

}
