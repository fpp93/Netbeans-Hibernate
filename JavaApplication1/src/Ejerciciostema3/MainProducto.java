/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ejerciciostema3;
import dam.Categorias;
import dam.Productos;
import java.time.Clock;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import singleton.HibernateUtil;

/**
 *
 * @author Francisco
 */
public class MainProducto {
    
    public static void main(String[] args){
        
        Scanner sc=new Scanner(System.in);
        System.out.println("1,insertar producto, 2 modificar precio,3 obtener todos los productos,4 consultar precio ");
        int option=sc.nextInt();
       
        
        switch(option){
            case 1:
                //obtenemos el sessionfactory de singleton
        SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
        
        //obtenemos la session
        Session session = sessionFactory.openSession() ;
        
        //crear un producto
        //Consulta HQL-> obtener la categoria 1 (frutas)
        String queryString ="SELECT c FROM Categorias c WHERE c.id=1";
        
        //variables dque tendra nuestro producto
        String nombreProducto = "Melocoton";
        float precio = 1.6f;
        Query query = session.createQuery(queryString);
        
        //creamos un objeto categorias y le metemos el resultado de la query casteada
        Categorias categoria= (Categorias)query.uniqueResult();
        
        //comprobamos si la categoria existe
        
        
        if(categoria!=null){
            
            Productos producto = new Productos(categoria,nombreProducto,precio);
            //comenzamos la transaccion
            Transaction tx = session.beginTransaction();
            session.save(producto);
            tx.commit();
            
        }else{
            System.out.println("esta categoria no existe");
        }
        session.close();
            break;
            case 2:
                Productos.obtenerYModificarProducto();
            break;
            case 3:
                Productos.obtenerTodo();
                break;
            case 4:
                Productos.consultarPrecio();
                break;
        }
        
        
        
    }
    
    

   
}
