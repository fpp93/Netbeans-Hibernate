/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examen.fran.parrapaez;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Francisco
 */
public class LecturaFichero {
    
    
    public static void main(String[] args)  {
    
    String direccion ="src/RecursosHumanos.sql";
		File f1 = new File(direccion);
		
		
	String url = "jdbc:mysql://localhost:3306/francisco-parrapaez" + "?useUnicode=true"+ "&serverTimezone=Europe/Madrid";
	String nombredriver ="com.mysql.cj.jdbc.Driver";
        
         try {
	    	Class.forName(nombredriver);
		Connection con = DriverManager.getConnection(url, "Francisco", "examen1");
		     
	    	BufferedReader br = new BufferedReader(new FileReader(f1));
                int cont=0;
	    	
	    	
			while(true) {
				Statement stat=con.createStatement();
				
				String sql = (br.readLine());
				//este if me ejecuta los insert de una linea
				if(sql!=null && (sql.startsWith("INSERT")&&sql.endsWith(";"))) {
					
					stat.executeUpdate(sql);
			       //pensaba utilizar ese contador para refenrenciar que son dos lienas pero no me ha dado tiempo
				}else if(sql!=null && (sql.startsWith("INSERT")&& sql.endsWith(","))){
                                    cont++;
                                    //si ha entrado en un insert acabado en , entra en el values y lo ejecuta
                                }else if(sql!=null && (sql.startsWith("VALUES")&& sql.endsWith(";")&& cont==1)){
                                    stat.executeUpdate(sql);
                                }else{
                                    break;
                                }
                                //si es 1 lo reinicio a 0
				if(cont==1){
                                    cont=0;
                                }
			}
			br.close();
			con.close();
	    	
	    }catch(Exception e) {
	    	
	    	e.printStackTrace();
	    }
    
    
    }
}
