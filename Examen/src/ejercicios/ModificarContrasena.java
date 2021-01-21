/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejercicios;

import Singleton.HibernateUtil;
import dam.Usuario;
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
public class ModificarContrasena {
    
    public static void main(String[] args){
     
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory() ;
    Session session = sessionFactory.openSession();
    
    Scanner sc=new Scanner(System.in);
    Scanner sc1=new Scanner(System.in);
    Scanner sc2=new Scanner(System.in);
    Scanner sc3=new Scanner(System.in);
     
        System.out.println("Introduce el nombre de usuario");
        String vusuario=sc.nextLine();
        System.out.println("Introduce la contraseña antigua");
        String vclave=sc1.nextLine();
        
         Query q1=session.createQuery("select u from Usuario u where u.usuario=:vusuario and u.clave=:vclave");
         q1.setParameter("vusuario",vusuario);
         q1.setParameter("vclave", vclave);
         
         List<Usuario> lista=q1.list();
         
          if(lista.isEmpty()){
              System.out.println("Ese usuario o contrasena no existe");
              
         }else{
               for(Usuario p  : lista){
                    Usuario temporal=p;
                    System.out.println("introduce la contrasena nueva");
                    String nueva_clave=sc3.nextLine();
                     temporal.setClave(nueva_clave);
             Transaction tx = session.beginTransaction();
             session.update(temporal);
             tx.commit();
                   
                }
               
    
    }
    
}
}
