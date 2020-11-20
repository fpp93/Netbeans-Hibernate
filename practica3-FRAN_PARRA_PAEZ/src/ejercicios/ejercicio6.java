

package ejercicios;


import Singleton.HibernateUtil;
import dam.Equipos;
import dam.Jugadores;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Francisco
 */
public class ejercicio6 {
    
    public static void main(String[] args){
    
        Scanner sc = new Scanner(System.in);
        boolean control;
        do{
            control=false;
            System.out.println("Introduce el nombre del equipo");
            String nombreEquipo=sc.nextLine();
            //control para no introducir una cadena vacia
            if(nombreEquipo.equals("")){
               System.out.println("Error-Parametro vacio");
               control=true;
             }else{
                
                SessionFactory sessionFactory = HibernateUtil.getSessionFactory() ;
                Session session = sessionFactory.openSession();
                Query q=session.createQuery("SELECT j.nombre,j.posicion FROM Jugadores j WHERE j.equipos.nombre=:nombreEquipo");
                //con la segunda consulta ya se cual es el ultimo id
                Query q2=session.createQuery("SELECT count(j.codigo) FROM Jugadores j,Equipos e WHERE e.nombre=j.equipos.nombre AND e.nombre=:nombreEquipo");
                q.setParameter("nombreEquipo",nombreEquipo);
                q2.setParameter("nombreEquipo",nombreEquipo);
         //una lista de object[] ya que no se coge el objeto entero, ni un solo elemento
         List<Object[]> lista = q.list();
         
         //control de lista vacia
         if(lista.isEmpty()){
              System.out.println("Ese equipo no existe");
              control=true;
         }else{
               for(Object[] p : lista){
                    System.out.println("nombre:"+p[0]+" ,Posicion:"+p[1]);
                }
         System.out.println("--------------------------------------");
         long f =(long) q2.uniqueResult();
         //guardo en un long el total de codigos que sera igual al total de jugadores
          System.out.println("Total de jugadores:"+ f);
         session.close();
         }
       
                
            }  
            
        }while(control==true);    
    
    }
    
}
