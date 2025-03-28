/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2parcial2;

/**
 *
 * @author chung
 */
public enum Trophy {
   PLATINO(5),ORO(3),PLATA(2),BRONCE(1);
   
   public final int puntos;
   
   Trophy(int puntos){
       this.puntos= puntos;
   }
}
