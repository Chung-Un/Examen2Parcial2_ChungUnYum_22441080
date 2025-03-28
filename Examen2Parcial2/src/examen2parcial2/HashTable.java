/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2parcial2;

/**
 *
 * @author chung
 */
public class HashTable {
    Entry cabeza;
    
    public HashTable(){
        cabeza = null;
    }
    
    void add(String username, long pos){
        if(cabeza==null){
           cabeza = new Entry(username,pos);
       } 
       else{
           Entry nodoActual = cabeza;
           while(nodoActual.getNext()!=null){
               nodoActual = nodoActual.getNext();
           }
           nodoActual.enlazarNext(new Entry(username,pos));
       }
    }
    
    void remove(String username){
        if(cabeza!=null && cabeza.username.equals(username)){
            cabeza = cabeza.next;
        }
        else{
            Entry entryActual = cabeza;
            while( entryActual!=null && entryActual.getNext() != null){
                if(entryActual.getNext().getUsername().equals(username)){
                    entryActual.enlazarNext(entryActual.getNext().getNext()); 
                }
                entryActual = entryActual.getNext();
            }
            
        }
    }
    
    long search(String username){
        if(cabeza!=null && cabeza.username.equals(username)){
            return cabeza.pos;
        }
        else{
            Entry entryActual = cabeza;
            while( entryActual!=null){
               if(entryActual.username.equals(username)){
                   return entryActual.pos;
               }
               entryActual = cabeza.getNext();
            }
            return -1;
        }
    }
}
