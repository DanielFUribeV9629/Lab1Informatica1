/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Christian
 */
public class Hilos implements Runnable{
    private Thread t;
    private String threadName;
    
    Hilos(String name){
        threadName = name;
        System.out.println(threadName + " Creado" );
    }
    
    @Override
    public void run() {
        System.out.println(threadName + " Corriendo" );
        try {
         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // Let the thread sleep for a while.
            Thread.sleep(50);
         }
      } catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
    }
    
    
}
