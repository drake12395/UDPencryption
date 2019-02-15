package socketprog1;

/**
 *
 * @author Drake
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;
public class Client {
    // variable used in encrypt/decrypt methods to include all characters used  
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyz";
    //takes user input and sends to (and recieves from) the server
    public static void main (String[] args) {
        
        System.out.println("Please enter your name");
        // scanner takes user name from keyboard
        Scanner input = new Scanner(System.in);
        String firstName = input.next();
        String lastName = input.next();
        String userName = firstName + " " + lastName;
                  
      try {
        String serverName, sndMessage, rcvMessage;
        int serverPort;
        // set to recieve proper amount of numbers for ssn number                 
        byte[] rcvBuf = new byte[9];
        // declare UDP transmittable Datagrampackets to send/recieve data 
        DatagramSocket clientSocket;
        DatagramPacket sndPacket, rcvPacket;
        // set socket to server port number   
        clientSocket = new DatagramSocket(9000);
        // I.P address, port and packet for server    
        serverName = "127.0.0.1";
        serverPort = 3000;
        sndMessage = encryptData(userName);
        // datagram to send UDP transmission to server
        sndPacket = new DatagramPacket(sndMessage.getBytes(), sndMessage.length(),InetAddress.getByName(serverName), serverPort);
        // UDP socket send method takes the packet containing encrypted user name
        clientSocket.send(sndPacket);
        // set packet to take rcvBuf and its length    
        rcvPacket = new DatagramPacket(rcvBuf, rcvBuf.length);
        clientSocket.receive(rcvPacket);
        rcvMessage = new String(rcvBuf, 0, rcvBuf.length);
        
        // print user ssn    
        System.out.println(decryptData(rcvMessage));
        clientSocket.close();
        }
        catch(Exception e) {
            
        }
    }
    
     
  // method to read file and populate string  
  public static String readFileAsString(String fileName)throws Exception { 
    String data = ""; 
    data = new String(Files.readAllBytes(Paths.get(fileName))); 
    return data; 
  }
  
  // method that takes string and encrypts it by moving each char 3 positions right 
  public static String encryptData (String plainText) {  
    plainText = plainText.toLowerCase();
    String cipherText = "";        
    
    for (int i = 0; i < plainText.length(); i++) {
      if (plainText.charAt(i) != ' ') {
      int pos = ALLCHAR.indexOf(plainText.charAt(i));
      int key = (4 + pos);
      char newChar = ALLCHAR.charAt(key);
      cipherText += newChar;
      }
      else {
          cipherText += ' ';
      }
    }
    return cipherText;       
  }
  
  // opposite of encryption method, moves each char 3 positions left   
  public static String decryptData (String cipherText) {        
    cipherText = cipherText.toLowerCase();
    String plainText = "";
    
    for (int i = 0; i < cipherText.length(); i++) {
      if (cipherText.charAt(i) != ' ') {  
      int pos = ALLCHAR.indexOf(cipherText.charAt(i));
      int key = (pos - 4);
      if (key < 0) {
        key = ALLCHAR.length() + key;
      }
      char newChar = ALLCHAR.charAt(key);
      plainText += newChar;
      }
      else {
          plainText += ' ';
      }
    }
    return plainText;
  }

}
