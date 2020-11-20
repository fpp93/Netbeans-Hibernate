/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejercicios;

import Singleton.HibernateUtil;
import dam.Equipos;
import dam.Jugadores;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Francisco
 */
public class Ejercicio8 {
    
    
     public static void main(String[] args){
     
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory() ;
    Session session = sessionFactory.openSession();
    
    Scanner sc = new Scanner(System.in);
    Scanner sc2 = new Scanner(System.in);
    int cont1=0;
    int cont2=0;
    int control_espacios=0;
    int control_mayus=0;
    
    Query q=session.createQuery("select e.nombre from Jugadores e");
    
    List<String> lista_nombres=q.list();
    
    System.out.println("Introduce el nombre del primer jugador");
    String jugador1=sc.nextLine();
    System.out.println("Introduce el nombre del segundo jugador");
    String jugador2=sc2.nextLine();
    
    //control espacios y mayusculas del jugador 1
    for(int i=0;i<jugador1.length();i++){
        
        //con esto controlo las mayusculas del apellido
        if(control_espacios==1 && (jugador1.charAt(i)>=65 && jugador1.charAt(i)<=90 )){
            control_mayus++;
        }
        //control del espacio
        if(jugador1.charAt(i)==32){
            control_espacios++;
        }
        //control de las mayusculas del nombre
        if(i==0 && (jugador1.charAt(i)>=65 && jugador1.charAt(i)<=90 ) ){
            control_mayus++;
        }
    }
    if(control_espacios!=1 || control_mayus!=2){
        System.out.println("Formato no permitido");
        System.exit(0);
    }
    control_espacios=0;
    control_mayus=0;
     //control espacios y mayusculas del jugador 2
    for(int i=0;i<jugador2.length();i++){
        
        //con esto controlo las mayusculas del apellido
        if(control_espacios==1 && (jugador2.charAt(i)>=65 && jugador2.charAt(i)<=90 )){
            control_mayus++;
        }
        //control del espacio
        if(jugador2.charAt(i)==32){
            control_espacios++;
        }
        //control de las mayusculas del nombre
        if(i==0 && (jugador2.charAt(i)>=65 && jugador2.charAt(i)<=90 ) ){
            control_mayus++;
        }
    }
    if(control_espacios!=1 || control_mayus!=2){
        System.out.println("Formato no permitido");
        System.exit(0);
    }
    
    //control existencia jugadores
    for(String s : lista_nombres){
        if(jugador1.equals(s)){
            cont1++;
        }
        if(jugador2.equals(s)){
            cont2++;
        }
    }
    if(cont1==0 || cont2==0){
        System.out.println("Uno de los jugadores no existe");
        System.exit(0);
    }
    
    
    //consulta de los equipos
    Query q1=session.createQuery("select j from Jugadores j where j.nombre=:jugador1");
    q1.setParameter("jugador1",jugador1);
    Jugadores jugador_1=(Jugadores)q1.uniqueResult();
    Equipos equipo1=jugador_1.getEquipos();
    Query q2=session.createQuery("select j from Jugadores j where j.nombre=:jugador2");
     q2.setParameter("jugador2",jugador2);
    Jugadores jugador_2=(Jugadores)q2.uniqueResult();
    Equipos equipo2=jugador_2.getEquipos();
    
    if(equipo1.getNombre().equals(equipo2.getNombre())){
        //si los dos jugadores pertenecen al mismo equipo, no permito el cambio
        System.out.println("Los dos jugadores pertenecen al mismo equipo");
    }else{
        //aqui hago el cambio de equipo
         Transaction tx = session.beginTransaction();
         jugador_1.setEquipos(equipo2);
         jugador_2.setEquipos(equipo1);
         tx.commit();
         session.close();
         
         System.out.println("cambio realizado con exito");
    }
    
    
    
     
     
     }
    
}
