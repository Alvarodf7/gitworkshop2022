/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package snif;


import java.sql.Statement;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Snif {

    public static void main(String[] args) {

        final int PUERTO = 9001;
        byte[] buffer = new byte[1024];

        try {

            DatagramSocket socketUDP = new DatagramSocket(PUERTO);

            while (true) {
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(peticion);
                
                String mensaje = new String(peticion.getData());
                InetAddress IpC = peticion.getAddress();
                int length = peticion.getLength();
                
                System.out.println(mensaje);
                System.out.println(IpC);
                System.out.println(length);
                String[] vector = mensaje.split(" ");
                String Longitud = vector[1];
                String Latitud = vector[2];
                String Fecha = vector[3];        
                String Hora  = vector[4];       
                
                System.out.println(Longitud);
                String user = "admin";
                String clave = "andres102030";
                String url = "jdbc:mysql://database-1.ccarysyysokd.us-east-1.rds.amazonaws.com:3306";
                Statement stmt;
                
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Snif.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    Connection c = DriverManager.getConnection(url, user, clave);
                    stmt = c.createStatement();
                    stmt.executeUpdate("INSERT INTO design.disen VALUES('"+Longitud+"', '"+Latitud+"' ,'"+Fecha+"','"+Hora+"' )");
                    
            }   catch (SQLException ex){
                        Logger.getLogger(Snif.class.getName()).log(Level.SEVERE, null, ex);
                }
            }     
        }   catch (SocketException ex) {
            Logger.getLogger(Snif.class.getName()).log(Level.SEVERE, null, ex);
        
            }   catch (IOException ex) {
            Logger.getLogger(Snif.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


