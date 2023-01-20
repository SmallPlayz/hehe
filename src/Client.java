import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.*;

public class Client implements Runnable {

    private static Socket clientSocket = null;
    public static PrintStream os = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    private static String username="";

    private static JPanel chatPanel;
     static  String amog = "";

    public static void main(String []args) {

        int portNumber = 10348; //port
        String host = "10.10.3.242";

        System.out.println("Now using host = " + host + ", portNumber = " + portNumber);

        System.out.println("Welcome To ChatApp");
        Scanner input=new Scanner (System.in);
        Graphics graphics = new Graphics();
        String message="";
        String full="";
        Boolean exit=false;
        //System.out.print("Input your username: ");
        //username=input.nextLine();

        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
            //os.println(username);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Chat");
        frame.setSize(350, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JTextField textField = new JTextField();
        textField.setBounds(10, 420, 230, 25);
        frame.add(textField);

        JButton button = new JButton("Send");
        button.setBounds(250, 420, 75, 25);
        frame.add(button);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.PAGE_AXIS));

        frame.add(chatPanel);
        frame.setVisible(true);

        button.addActionListener(new ActionListener(){ // ActionListerner for sign-in button.
            public void actionPerformed(ActionEvent e){ // Button code starts here.
                if(!textField.getText().equalsIgnoreCase("")){
                    amog = textField.getText();
                    os.println(textField.getText());
                    chatPanel.add(new JLabel("You: " + textField.getText()));
                    chatPanel.revalidate();
                    chatPanel.repaint();
                    textField.setText("");

                    Component[] componentList = chatPanel.getComponents();
                    if(componentList.length > 25){
                        chatPanel.remove(componentList[0]);
                        chatPanel.revalidate();
                        chatPanel.repaint();
                    }
                }
            } // Button code ends here.
        });

        if (clientSocket != null && os != null && is != null) {
            try {
                new Thread(new Client()).start();
                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }

    }
    public void run() {
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                if(!responseLine.equalsIgnoreCase(amog)) {
                    System.out.println(responseLine);

                    chatPanel.add(new JLabel("Stranger: " + responseLine));
                    chatPanel.revalidate();
                    chatPanel.repaint();

                    Component[] componentList = chatPanel.getComponents();
                    if (componentList.length > 25) {
                        chatPanel.remove(componentList[0]);
                        chatPanel.revalidate();
                        chatPanel.repaint();
                    }
                }
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}
class Graphics {
    Graphics(){
        JFrame frame = new JFrame("ChatApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);
        //frame.setVisible(true);

    }
}