/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen2parcial2;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author chung
 */
public class Examen2Parcial2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {
        PSNUsers psn = new PSNUsers();
        GUI gui = new GUI(psn);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    
}
