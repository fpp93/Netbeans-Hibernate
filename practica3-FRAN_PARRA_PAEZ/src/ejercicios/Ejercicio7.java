/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejercicios;

import Singleton.HibernateUtil;
import dam.Equipos;
import dam.Partidos;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Francisco
 */
public class Ejercicio7 {
    
     public static void main(String[] args){
     
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory() ;
    Session session = sessionFactory.openSession();
    

    Query q2=session.createQuery("select e from Equipos e");
     
    List<Equipos> lista_equipos=q2.list();
    //guardo el tamaño de la lista antes de ir disminuyendo el tamaño de la lista
    int tamaño=lista_equipos.size();
    //par o no
     if(lista_equipos.size()%2==0){
          //for que va hasta la mitad del total de equipos(emparejamientos=)
     for(int i=0;i<tamaño/2;i++){
         Transaction tx = session.beginTransaction();
         
         Query q=session.createQuery("select count(p.codigo) from Partidos p");
          //(guardo el total de codigos que hay)
         long total_codigos =(long) q.uniqueResult();
         //creo dos equipos temporales y su posicion sera un numero aleatoriodentro del rango del size de la lista
         Equipos temporal=lista_equipos.get((int)(Math.random()*lista_equipos.size()));
         //borro esa referencia para que no exista en la lista y asi no pueda salir mas
         lista_equipos.remove(temporal);
         Equipos temporal2=lista_equipos.get((int)(Math.random()*lista_equipos.size()));
         lista_equipos.remove(temporal2);
         //creo un objeto partido y voy seteando
         Partidos partido=new Partidos();
         partido.setCodigo((int)total_codigos+1);
         partido.setEquiposByEquipoLocal(temporal);
         partido.setEquiposByEquipoVisitante(temporal2);
         partido.setPuntosLocal(null);
         partido.setPuntosVisitante(null);
         partido.setTemporada("08/09");  
         
         session.save(partido);
         tx.commit();
     }
         System.out.println("Operacion realizada con exito");
     }else{
          System.out.println("error-Equipos no pares");
     }
     session.close();
     sessionFactory.close();
     }
    
   
}
