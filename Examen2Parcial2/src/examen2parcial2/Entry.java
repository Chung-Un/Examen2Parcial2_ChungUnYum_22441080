/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2parcial2;

/**
 *
 * @author chung
 */
public class Entry {
    String username;
    long pos;
    Entry next;
    
    public Entry(String username, long pos){
       this.username = username;
       this.pos = pos;
       next = null;
    }
    
    public void enlazarNext(Entry nodo){
        this.next = nodo;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    public Entry getNext() {
        return next;
    }

    public void setNext(Entry next) {
        this.next = next;
    }
    
   
    
}
