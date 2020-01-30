package dn13;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.*;

public class DN13 {
public static void main(String[] args) {
    JFrame okno = new JFrame("Moje prvo okno");
    okno.setSize(600,300);
    okno.setLocation(500,500);

    JPanel p = new JPanel();

    JTextField vpisnoPolje = new JTextField();
    p.add(vpisnoPolje);
    
    JButton gumb = new JButton("Isci");
    p.add(gumb);
    
    JTextArea jta = new JTextArea();
    JScrollPane jsp = new JScrollPane(jta);        
    
    okno.add(p, BorderLayout.PAGE_END);
    okno.add(jsp);
                
    okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    okno.setVisible(true);
  }
}