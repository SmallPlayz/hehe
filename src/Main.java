import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Chat");
        frame.setSize(350, 500);
        frame.setResizable(false);

        JTextField textField = new JTextField();
        textField.setBounds(10, 420, 230, 25);
        frame.add(textField);

        JButton button = new JButton("Send");
        button.setBounds(250, 420, 75, 25);
        frame.add(button);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.PAGE_AXIS));

        frame.add(chatPanel);
        frame.setVisible(true);

        for(int i = 0;i <40;i ++) {
            chatPanel.add(new JLabel("hello " + i));
            chatPanel.revalidate();
            chatPanel.repaint();
        }
/*
        Component[] componentList = chatPanel.getComponents();
        for(int i = 14;i >=0;i --) {
            chatPanel.remove(componentList[i]);
            Thread.sleep(500);
            chatPanel.revalidate();
            chatPanel.repaint();
        }
        */
    }
}