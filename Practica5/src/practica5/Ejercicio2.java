/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
           Scanner sc1= new Scanner(System.in);
//           System.out.println("introduce el titulo");
//           String title = sc.nextLine();
//           System.out.println("introduce el artista");
//           String artist = sc.nextLine();
//           System.out.println("introduce el pais");
//           String country = sc.nextLine();
//           System.out.println("introduce la discografica");
//           String company = sc.nextLine();
//           System.out.println("introduce el precio");
//           String price = sc.nextLine();
//           System.out.println("introduce el anio");
//           String year = sc.nextLine();
           
           XQConnection con=obtenConexion();
           
//            if(title.equals("")||artist.equals("")||country.equals("")||company.equals("")||price.equals("")||year.equals("")){
//                System.out.println(" ADVERTENCIA : uno de los campos esta vacio");
//                System.exit(0);
//            }else{
//                String[] etiquetas=new String[6];
//          
//                etiquetas[0]=title;
//                etiquetas[1]=artist;
//                etiquetas[2]=country;
//                etiquetas[3]=company;
//                etiquetas[4]=price;
//                etiquetas[5]=year;
//                
//                 nuevoCD(con,etiquetas);
//            }
           
        
        
        
      
        
        System.out.println("introduce un DNI");
        String DNI = sc.nextLine();
        System.out.println("introduce un Codigo Postal");
        int CP = sc1.nextInt();
        
        if(DNI.equals("") || CP==0){
            System.out.println(" ADVERTENCIA : Introduce un dni o codigo postal en los campos");
            System.exit(0);
        }else{
            modificarCP(con,DNI,CP);
        }
        //numeroCompras(con,DNI);
        
        
        
    }
    
       //metodo de obtener conexion
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
            //consulta para saber el ultimo id
            XQPreparedExpression expr1 = con.prepareExpression
          ("count(doc('/db/pruebas/catalogo.xml')/CATALOG/CD)");
            XQResultSequence result1 = expr1.executeQuery();
            result1.next();
            //el ultimo id mas 1 para el nuevo cd
            int id=result1.getInt()+1;
            
            XQExpression expresion= con.createExpression();
            
            expresion.executeCommand("update insert<CD><ID>"+id+"</ID><TITLE>"+etiquetas[0]+"</TITLE><ARTIST>"+etiquetas[1]+"</ARTIST><COUNTRY>"+etiquetas[2]+"</COUNTRY><COMPANY>"+etiquetas[3]+"</COMPANY><PRICE>"+etiquetas[4]+"</PRICE><YEAR>"+etiquetas[5]+"</YEAR></CD>into doc('/db/pruebas/catalogo.xml')/CATALOG");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public static void numeroCompras(XQConnection con,String DNI){
        try{
            XQPreparedExpression expr = con.prepareExpression(" let $cont := count(doc('/db/pruebas/clientes.xml')/clientes/cliente[@DNI='"+DNI+"'])\n" +
            " return $cont");
             XQResultSequence result=expr.executeQuery();
              result.next();
              if(result.getInt()>0){
                   try{
                        XQPreparedExpression expr1 = con.prepareExpression("let $cli := doc('/db/pruebas/clientes.xml')/clientes/cliente[@DNI='"+DNI+"']\n" +
                           "let $cont := count(doc('/db/pruebas/compras.xml')/compras/producto/cliente[@DNI=$cli/@DNI])\n" +
                            "return $cont");
                        XQResultSequence result1=expr1.executeQuery();
                        result1.next();
                        System.out.println(result1.getInt());
            
                    }catch(Exception e){
                        e.printStackTrace();
                    }
              }else{
                  System.out.println("El usuario con DNI "+DNI+ " no se encuentra en la base de datos");
              }
        }catch(Exception e){
            e.printStackTrace();
        }
       
    }
    
    public static void modificarCP(XQConnection con,String DNI,int CP){
        Date fecha = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String direccion ="src/archivo_seguridad/registro_cambios.txt";	
	File f1 = new File(direccion);
       // BufferedWriter wr = new BufferedWriter(new FileWriter(f1));
        try{
             XQPreparedExpression expr = con.prepareExpression(" let $cont := count(doc('/db/pruebas/clientes.xml')/clientes/cliente[@DNI='"+DNI+"'])\n" +
            " return $cont");
             XQResultSequence result=expr.executeQuery();
              result.next();
              if(result.getInt()>0){
                    try{
            
                        XQPreparedExpression expr2 = con.prepareExpression("let $cp := doc('/db/pruebas/clientes.xml')/clientes/cliente[@DNI='"+DNI+"']/CP\n" +
                         " return $cp");
                        XQResultSequence result1 = expr2.executeQuery();
                        result1.next();
                        //he ido probano funciones para recoger string y este es el que me ha funcionado
                        String antiguoCP= result1.getItemAsString(null);
                        
                         XQExpression expresion= con.createExpression();
                         expresion.executeCommand("update value doc('/db/pruebas/clientes.xml')/clientes/cliente[@DNI='"+DNI+"']/CP with " +CP);
             
                         String mensaje = "-------------------------------------------------------------------------\n\n"
                        + "MODIFICACIÓN CLIENTE DNI = " + DNI + " FECHA = " + dateFormat.format(fecha) + "\n\n"
                        + "CP anterior = " + antiguoCP + "\n\n"
                        + "CP nuevo = " + CP + "\n\n"
                        + "-----------------------------------------------------------------------------------------\n\n";
                         // dos casos:si existe ya el archivo o si no y hay que crearlo
                         if(!f1.exists()){
                             try{
                                f1.createNewFile();
                                //actualiza no sobreescribe
                                FileWriter filewriter = new FileWriter(f1, true);
                                BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
                                bufferedwriter.write(mensaje);
                                bufferedwriter.close();
                                
                             }catch(Exception e){
                                 e.printStackTrace();
                             }
                             
                         }else{
                             try{
                                FileWriter filewriter = new FileWriter(f1, true);
                                BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
                                bufferedwriter.write(mensaje);
                                bufferedwriter.close();
                             }catch(Exception e){
                                 e.printStackTrace();
                             }
                         }
                         
                    }catch(Exception e){
                        e.printStackTrace();
                    }
              }else{
                  System.out.println("Este cliente no existe");
              }
        }catch(Exception e){
            e.printStackTrace();
        }
      
    }
}
