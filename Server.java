package socketprog1;
import java.net.*;
import java.nio.file.*;
/**
 *
 * @author Drake
 */
public class Server {

  /**
   * @param args the command line arguments
   */
    
  public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyz";
    
  public static void main(String[] args) {
        

        
    try {
      // find file on computer and save to a string called data      
      String data = readFileAsString("C:\\Users\\Drake\\Desktop\\cs401\\programming assignments\\socketProg1Text.txt");            
      String[] temp;
      // split string into name and ssn with comma as delimiter
      temp = data.split(",");
      String name = temp[0];
      String ssn = temp[1];
      // declare UDP transmittable Datagrampackets to send/recieve data  
      DatagramSocket serverSocket;
      DatagramPacket rcvPacket;           
      DatagramPacket sndPacket;
            
      String clientName, sndMessage;
      int clientPort;
      // set socket to client port number      
      serverSocket = new DatagramSocket(3000);
      // delcare and set amount of bytes being recieved from client      
      byte[] recvBuf = new byte[13];
      // set packet to take recvBuf and its length
      rcvPacket = new DatagramPacket(recvBuf, recvBuf.length);     
      serverSocket.receive(rcvPacket);           
      String rcvMessage = new String(recvBuf, 0, recvBuf.length);
      
      
      // output to show encrypted name made it to server from client
      System.out.println(rcvMessage);
      // shows that decryption method works properly
      System.out.println(decryptData(rcvMessage));
            
      // compare user input to name in file      
      if(decryptData(rcvMessage).equalsIgnoreCase(name)){
        // call method to encrypt social security number  
        String cipherSsn = encryptData(ssn);       
        // I.P address, port and packet for client    
        clientName = "127.0.0.1";
        clientPort = 9000;
        sndMessage = "Hello";
        // datagram to send UDP transmission to client
        sndPacket = new DatagramPacket(cipherSsn.getBytes(), cipherSsn.length(),InetAddress.getByName(clientName), clientPort);
        // UDP socket send method takes the packet containing encrypted ssn
        serverSocket.send(sndPacket);
        // close socket connection
        serverSocket.close();
      }
    }
    catch(Exception e){
    }     
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
    // method to read file and populate string
  public static String readFileAsString(String fileName)throws Exception { 
    String data = ""; 
    data = new String(Files.readAllBytes(Paths.get(fileName))); 
    return data; 
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
