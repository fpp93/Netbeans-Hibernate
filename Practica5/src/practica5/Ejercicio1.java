/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica5;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Resource;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;



public class Ejercicio1 {

    
    public static void main(String[] args) {
        // TODO code application logic here
//        String driver = “org.exist.xmldb.DatabaseImpl”; // driver para eXist
//        Class c1 = Class.forName(driver); // Cargamos el driver
//        Database database = (Database) c1.newInstance(); // Instancia de la BD
//        DatabaseManager.registerDatabase(database); // Registro del driver
        
        obtenColeccion("pruebas");
        crearCopiaSeguridad("copia_seguridad");
        
    }
    
    public static Collection obtenColeccion(String nomCol){
        Collection col=null;
        try{
            
            String driver = "org.exist.xmldb.DatabaseImpl"; // driver para eXist
            Class c1 = Class.forName(driver); // Cargamos el driver
            Database database = (Database) c1.newInstance(); // Instancia de la BD
            DatabaseManager.registerDatabase( database); // Registro del driver
            String uri = "xmldb:exist://localhost:8090/exist/xmlrpc/db/"+nomCol; // Colec.
            String usu = "admin"; // usuario
            String pass = "admin"; // contraseña
            col = DatabaseManager.getCollection(uri,usu,pass);
            
            return col;
        }catch(Exception e){
            e.printStackTrace();
        }
         return col;
        
        
    }
    public static void crearCopiaSeguridad(String nomCol){
        Collection col= null;
        
        GregorianCalendar calendar= new GregorianCalendar();
        String fecha_sistema=""+calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
       try{
          
           String driver = "org.exist.xmldb.DatabaseImpl"; // driver para eXist
            Class c1 = Class.forName(driver);
            Database database = (Database) c1.newInstance(); // Instancia de la BD
            DatabaseManager.registerDatabase( database);
            String uri = "xmldb:exist://localhost:8090/exist/xmlrpc/db"; // Colec.
            String usu = "admin"; // usuario
            String pass = "admin"; // contraseña
            col = DatabaseManager.getCollection(uri,usu,pass);
            //obtengo la coleccion copia de seguridad de db
            Collection temp=col.getChildCollection(nomCol);
            //si no existe entra
            if(temp==null){
                //creo la coleccion copia de seguridad
                    CollectionManagementService mgtService = (CollectionManagementService)
                    col.getService("CollectionManagementService","1.0");
                    mgtService.createCollection(nomCol);
            }
            //me situo en el hijo pruebas de db
            Collection pruebas=col.getChildCollection("pruebas");
            //creo un array con los hijos de la coleccion pruebas
            String[] hijos=pruebas.listResources();
            //recorro el array que lleva los nombres de las colecciones de pruebas
            for(int i=0;i<hijos.length;i++){
                //guardo el nombre en una cadena pero lleva un .xml que no queremos 
                String cadena=hijos[i];
                //lo separo con expresiones
                Pattern patron = Pattern.compile("^(.+)\\.xml");
                Matcher matcher = patron.matcher(cadena);
                matcher.find();
                //creo en este string el nombre del archivo copia
                String cadena2="COPIA_"+matcher.group(1)+"_"+fecha_sistema+".xml";
                //me situo en el padre de pruebas
                Collection padre=pruebas.getParentCollection();
                //me situo en el hijo copia de seguridad
                Collection copia=padre.getChildCollection(nomCol);
                //creo el recurso uno a uno dentro del for
               Resource recurso_copia=copia.createResource(cadena2,XMLResource.RESOURCE_TYPE);
               //a ese recurso le asigno el contenido de el recurso recorrido en pruebas
               recurso_copia.setContent(pruebas.getResource(cadena).getContent());
               copia.storeResource(recurso_copia);       
            }
            
            
                    
                  
                
            
           
            
            
            
            
       }catch(Exception e){
           e.printStackTrace();
       }
        
    }
    
}
