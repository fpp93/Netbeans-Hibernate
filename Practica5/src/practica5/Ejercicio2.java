/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica5;

import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import javax.xml.xquery.XQSequence;
import net.xqj.exist.ExistXQDataSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;




public class Ejercicio2 {
    
       public static void main(String[] args) {
           Scanner sc= new Scanner(System.in);
           System.out.println("introduce el titulo");
           String title = sc.nextLine();
           System.out.println("introduce el artista");
           String artist = sc.nextLine();
           System.out.println("introduce el pais");
           String country = sc.nextLine();
           System.out.println("introduce la discografica");
           String company = sc.nextLine();
           System.out.println("introduce el precio");
           String price = sc.nextLine();
           System.out.println("introduce el anio");
           String year = sc.nextLine();
           
          String[] etiquetas=new String[6];
          
          etiquetas[0]=title;
          etiquetas[1]=artist;
          etiquetas[2]=country;
          etiquetas[3]=company;
          etiquetas[4]=price;
          etiquetas[5]=year;
        
        XQConnection con=obtenConexion();
        nuevoCD(con,etiquetas);
        
        
    }
    
    public static XQConnection obtenConexion(){
        try{
            XQDataSource server = new ExistXQDataSource();
            server.setProperty("serverName","localhost");
            server.setProperty("port","8090");
            server.setProperty("user","admin");
            server.setProperty("password","admin");
        
            XQConnection conexion = server.getConnection("admin","admin");
            System.out.println("conexion realizada");
        return conexion;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static void nuevoCD(XQConnection con,String[] etiquetas){
        try{
            XQPreparedExpression expr1 = con.prepareExpression
          ("count(doc('db/pruebas/catalogo.xml')/CATALOG/CD)");
            XQResultSequence result1 = expr1.executeQuery();
            result1.next();
            int id=result1.getInt()+1;
            
            XQExpression expresion= con.createExpression();
            
            expresion.executeCommand("update insert<CD><ID>"+id+"</ID><TITLE>"+etiquetas[0]+"</TITLE><ARTIST>"+etiquetas[1]+"</ARTIST><COUNTRY>"+etiquetas[2]+"</COUNTRY><COMPANY>"+etiquetas[3]+"</COMPANY><PRICE>"+etiquetas[4]+"</PRICE><YEAR>"+etiquetas[5]+"</YEAR></CD>into doc('db/pruebas/catalogo.xml')/CATALOG");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
