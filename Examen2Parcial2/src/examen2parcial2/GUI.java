/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2parcial2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author chung
 */
public class GUI extends JFrame{
    private JPanel panelBotones;
    private JPanel panelContenido;
    private PSNUsers psn;
    
    public GUI(PSNUsers psn) {
        this.psn = psn;
        setTitle("PSN");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        panelBotones = new JPanel();
        panelBotones.setPreferredSize(new Dimension(800, 50));
        panelBotones.setLayout(new GridLayout(1, 4));
        panelBotones.setBackground(Color.WHITE);
        
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);
        
        crearBoton("Agregar usuario", Color.BLUE, this::mostrarAddUser);
        crearBoton("Desactivar usuario", Color.RED, this::mostrarDeactivateUser);
        crearBoton("Agregar trofeo", Color.GREEN, this::mostrarAddTrophy);
        crearBoton("Informacion usuario", Color.YELLOW, this::mostrarUserInfo);
        
        JLabel labelMain = new JLabel("------------------->PSN<-------------------");
        labelMain.setFont(new Font("Arial",Font.BOLD,48));
        
        panelContenido.add(labelMain);
        
        panelPrincipal.add(panelBotones, BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        add(panelPrincipal);
        setVisible(true);
    }
    
    private void crearBoton(String texto, Color color, ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.setFocusable(false);
        boton.setBackground(color);
        boton.setForeground(Color.BLACK);
        boton.addActionListener(listener);
        panelBotones.add(boton);
    }
    
    private void mostrarAddUser(ActionEvent e) {
        panelContenido.removeAll();
        
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        JTextField txtUsername = new JTextField();
        
        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        
        JButton btnAdd = new JButton("Agregar Usuario");
        btnAdd.addActionListener(ev -> {
            try {
                psn.addUser(txtUsername.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.add(btnAdd, BorderLayout.SOUTH);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    private void mostrarDeactivateUser(ActionEvent e) {
        panelContenido.removeAll();
        
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        JTextField txtUsername = new JTextField();
        
        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        
        JButton btnDeactivate = new JButton("Desactivar");
        btnDeactivate.addActionListener(ev -> {
            try {
                psn.deactivateUser(txtUsername.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.add(btnDeactivate, BorderLayout.SOUTH);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    private void mostrarAddTrophy(ActionEvent e) {
        panelContenido.removeAll();

        String[] juegos = {"Slime Rancher","Slime Rancher 2", "Sims 4", "GTA V", "Valorant", "Elden Ring", "OverCooked"};
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField fieldUser = new JTextField();
        JComboBox<String> comboGames = new JComboBox<>(juegos);
        JTextField fieldTrophyName = new JTextField();
        JComboBox<Trophy> comboTrophies = new JComboBox<>(Trophy.values());

        panel.add(new JLabel("Username:"));
        panel.add(fieldUser);
        panel.add(new JLabel("Juego:"));
        panel.add(comboGames);
        panel.add(new JLabel("Nombre Trofeo:"));
        panel.add(fieldTrophyName);
        panel.add(new JLabel("Tipo:"));
        panel.add(comboTrophies);

        JButton btnAdd = new JButton("Agregar trofeo");
        btnAdd.addActionListener(ev -> {
            try {
                Trophy selectedTrophy = (Trophy) comboTrophies.getSelectedItem();
                String selectedGame = (String) comboGames.getSelectedItem(); 

                psn.addTrophieTo(
                    fieldUser.getText(), 
                    selectedGame,  
                    fieldTrophyName.getText(), 
                    selectedTrophy
                );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.add(btnAdd, BorderLayout.SOUTH);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    private void mostrarUserInfo(ActionEvent e) {
        panelContenido.removeAll();
        
        JPanel panelForm = new JPanel(new BorderLayout());
        JTextField txtUsername = new JTextField();
        JButton btnSearch = new JButton("Buscar");
        JTextArea txtResult = new JTextArea();
        txtResult.setEditable(false);
        
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(new JLabel("Username:"), BorderLayout.WEST);
        panelTop.add(txtUsername, BorderLayout.CENTER);
        panelTop.add(btnSearch, BorderLayout.EAST);
        
        btnSearch.addActionListener(ev -> {
            try {
                StringBuilder info = psn.playerInfo(txtUsername.getText());
                txtResult.setText(info.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panelForm.add(panelTop, BorderLayout.NORTH);
        panelForm.add(new JScrollPane(txtResult), BorderLayout.CENTER);
        
        panelContenido.add(panelForm);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    
}
