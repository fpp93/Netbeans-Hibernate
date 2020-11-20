/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejercicios;

import Singleton.HibernateUtil;
import dam.Equipos;
import dam.Estadisticas;
import dam.Jugadores;
import static java.lang.reflect.Array.set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Francisco
 */
public class Ejercicio9 {
    
    public static void main(String[] args){
    
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.openSession();
   
    
    Query q=session.createQuery("select e from Equipos e");
    
    List<Equipos> lista_equipos=q.list();
    
    Iterator<Equipos> it = lista_equipos.iterator();
    
    //inicializacion de los objetos que utilizare para guardar informacion
    float puntosminimo=2000;
    Jugadores jugadorminimo=null;
    Jugadores temporal=null;
    Estadisticas est_temporal=null;
    
    //bucles anidades de iteradores para recorrer equiposz, jugadores y estadisticas
    while(it.hasNext()){
        puntosminimo=2000;
         Equipos e_temporal=it.next();
        Set<Jugadores> jugador1=e_temporal.getJugadoreses();
        Iterator<Jugadores> it1=jugador1.iterator();
        
            while(it1.hasNext()){
                temporal=it1.next();
                Set<Estadisticas> estadistica1=temporal.getEstadisticases();
                Iterator<Estadisticas> it2=estadistica1.iterator();
                
                    while(it2.hasNext()){
                        est_temporal=it2.next();
                        float puntos_temporal=est_temporal.getPuntosPorPartido();
                        String temporada=est_temporal.getId().getTemporada();
                        
                        if(puntos_temporal<puntosminimo && temporada.equalsIgnoreCase("07/08")){
                            puntosminimo=puntos_temporal;
                            jugadorminimo=temporal;
                            
                        }
                    }
            }
            //una vez obtengo el peor jugador de cada equipo, recorro sus estadisticas y las borro junto con el jugador
         System.out.println("Nombre:"+jugadorminimo.getNombre()+" ,Puntos:"+puntosminimo+" ,Equipo:"+e_temporal.getNombre()+" >- Borrado");
          Transaction tx = session.beginTransaction();
         for(Estadisticas e:jugadorminimo.getEstadisticases()){
             session.delete(e);   
         }
         session.delete(jugadorminimo);
         tx.commit();
    }
    
    session.close();
  
    
    }
    
}
