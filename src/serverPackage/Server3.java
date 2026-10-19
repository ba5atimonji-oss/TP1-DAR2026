package serverPackage;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class Server3 {

        public static void main(String[] args) throws IOException {
            System.out.println("Je suis un serveur en attente de connexion…");

            InetAddress ip = InetAddress.getByName("10.14.186.115");
            int port = 1234;
            InetSocketAddress adresse = new InetSocketAddress(ip, port);

            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(adresse);
            System.out.println("Serveur à l'écoute sur " + ip.getHostAddress() + ":" + port);

            Socket socket = serverSocket.accept();
            InetSocketAddress client = (InetSocketAddress) socket.getRemoteSocketAddress();
            System.out.println("Un client s'est connecté : " + client.getAddress().getHostAddress());

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            int choix;
            do {
                choix = dis.readInt();               
                if (choix != 0) {
                    int x = dis.readInt();
                    int y = dis.readInt();
                    System.out.println("Reçu : choix = " + choix + ", x = " + x + ", y = " + y);

                    boolean ok = true;
                    double resultat = 0;
                    if (choix == 1) {
                        resultat = (double) x + y;
                    } else if (choix == 2) {
                        resultat = (double) x - y;
                    } else if (choix == 3) {
                        resultat = (double) x * y;
                    } else if (choix == 4) {
                        if (y == 0) {
                            ok = false;              
                        } else {
                            resultat = (double) x / y;
                        }
                    } else {
                        ok = false;                   
                    }

                    dos.writeBoolean(ok);             
                    if (ok) {
                        dos.writeDouble(resultat);    
                        System.out.println("Résultat envoyé : " + resultat);
                    } else {
                        System.out.println("Erreur : opération impossible");
                    }
                    dos.flush();
                }
            } while (choix != 0);

            System.out.println("Le client a quitté, fermeture du serveur.");
            dis.close();
            dos.close();
            socket.close();
            serverSocket.close();
        }
    }

