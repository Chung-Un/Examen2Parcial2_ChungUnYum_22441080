/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2parcial2;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author chung
 */
public class PSNUsers {
    RandomAccessFile psn;
    HashTable users; 
    
    public PSNUsers() throws FileNotFoundException, IOException{
        if (psn==null){
            psn = new RandomAccessFile("registro.psn","rw");
            users = new HashTable();
            
            if(psn.length()==0){
                return;
            }
            
            reloadHashTable();
            
        }
    }
    
    private void reloadHashTable() throws IOException{
    if (psn.length() == 0) return;
    
    try{
        psn.seek(0);
            while (psn.getFilePointer() < psn.length()) {
                long pos = psn.getFilePointer();
                String username = psn.readUTF();
                int puntos = psn.readInt();
                int cantTrofeos = psn.readInt();
                boolean activo = psn.readBoolean();
                long posTrofeos = psn.readLong();

                if (activo) {
                    users.add(username, pos);
                }
                if(cantTrofeos>0){
                    psn.seek(posTrofeos);
                    for (int i = 0; i < cantTrofeos; i++) {
                        psn.readUTF(); 
                        psn.readUTF(); 
                        psn.readUTF();
                        psn.readLong();
                    }
                }
            }
        } catch (EOFException e){
            System.out.println("Archivo corrupto");
        } 
    }
    /*
    formato psn
    String username
    int puntos
    int cant trofeos
    boolean activo
    long posicionUltimoTrofeo
    
    String tipo del trofeo
    String nombre del juego
    String nombre del trofeo
    Long fecha en que se gano
    */
    
    void addUser(String username) throws IOException{
        if(users.search(username)!=-1){
            JOptionPane.showMessageDialog(null, "Usuario ya existe", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        long pos = psn.length();
        psn.seek(pos);
        psn.writeUTF(username);
        psn.writeInt(0);
        psn.writeInt(0);
        psn.writeBoolean(true);
        psn.writeLong(psn.getFilePointer());
        users.add(username, pos);
        JOptionPane.showMessageDialog(null, "Usuario registrado con exito", "Error",JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    
    void deactivateUser(String username) throws IOException{
        long pos = users.search(username);
        if(pos!=-1){
            psn.seek(pos);
            psn.readUTF();
            psn.skipBytes(8);
            psn.writeBoolean(false);
            users.remove(username);
            JOptionPane.showMessageDialog(null, "Usuario desactivado", "Exito", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(null, "Usuario no ha sido encontrado",  "Error", JOptionPane.ERROR_MESSAGE);
        
    }
    
    void addTrophieTo(String username, String trophyGame, String trophyName, Trophy type) throws IOException {
    long pos = users.search(username);
    if(pos != -1) {
        psn.seek(pos);
        String currentUser = psn.readUTF();
        int puntos = psn.readInt();
        int cantTrofeos = psn.readInt();
        boolean activo = psn.readBoolean();
        long posUltimoTrofeo = psn.readLong();
        
        long newTrophyPos = psn.length();
        if(cantTrofeos > 0) {
            psn.seek(posUltimoTrofeo);
            psn.readUTF(); 
            psn.readUTF(); 
            psn.readUTF();
            psn.readLong();
            newTrophyPos = psn.getFilePointer();
        } else {
            newTrophyPos = psn.length();
        }

        psn.seek(newTrophyPos);
        psn.writeUTF(type.name());
        psn.writeUTF(trophyGame);
        psn.writeUTF(trophyName);
        psn.writeLong(new Date().getTime());

        cambiarPosicionUltimoTrofeo(username, newTrophyPos);
        
        psn.seek(pos + currentUser.length() + 2); 
        psn.writeInt(puntos + type.puntos);
        psn.writeInt(cantTrofeos + 1);                
        JOptionPane.showMessageDialog(null, "Trofeo agregado exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null, "El usuario no esta activo", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    void cambiarPosicionUltimoTrofeo(String username, long posicionUltimoTrofeo) throws IOException {
        long pos = users.search(username);
        if(pos != -1) {
            psn.seek(pos);
            String currentUser = psn.readUTF();
            long posicionCampoTrofeos = pos + currentUser.length() + 2 + 4 + 4 + 1;
            psn.seek(posicionCampoTrofeos);
            psn.writeLong(posicionUltimoTrofeo);
        }
    }
    
   StringBuilder playerInfo(String username) throws IOException {
    StringBuilder builder = new StringBuilder();
    long pos = users.search(username);
    
    if(pos != -1) {
        psn.seek(pos);
        String user = psn.readUTF();
        int puntos = psn.readInt();
        int cantTrofeos = psn.readInt();
        boolean activo = psn.readBoolean();
        long posTrofeos = psn.readLong();
        
        builder.append("USUARIO: ").append(user)
              .append("\nPUNTOS: ").append(puntos)
              .append("\nTROFEOS: ").append(cantTrofeos)
              .append("\nESTADO: ").append(activo ? "Activo" : "Inactivo").append("\n");
        
        if(cantTrofeos > 0) {
            builder.append("\n** TROFEOS **\n");
            long posActual = psn.getFilePointer(); 
            
            psn.seek(posTrofeos);
            for(int i = 0; i < cantTrofeos; i++) {
                try {
                    String tipoTrofeo = psn.readUTF();
                    String nombreJuego = psn.readUTF();
                    String nombreTrofeo = psn.readUTF();
                    Date fecha = new Date(psn.readLong());
                    
                    builder.append("\nTIPO: ").append(tipoTrofeo)
                          .append("\nJUEGO: ").append(nombreJuego)
                          .append("\nNOMBRE: ").append(nombreTrofeo)
                          .append("\nFECHA: ").append(fecha).append("\n");
                } catch (EOFException e) {
                    break;
                }
            }
            
            psn.seek(posActual); 
        }
    } else {
        builder.append("Usuario no encontrado");
    }
    
    return builder;
    }
    
}
