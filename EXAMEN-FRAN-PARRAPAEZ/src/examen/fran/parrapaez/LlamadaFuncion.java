/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examen.fran.parrapaez;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Francisco
 */
public class LlamadaFuncion {
    
     public static void main(String[] args)  {
    
    String url = "jdbc:mysql://localhost:3306/francisco-parrapaez" + "?useUnicode=true"+ "&serverTimezone=Europe/Madrid";
    String nombredriver ="com.mysql.cj.jdbc.Driver";
    
        
         try {
		    Class.forName(nombredriver);
		   
	            Connection con = DriverManager.getConnection(url, "Francisco", "examen1");
		    Statement stat=con.createStatement();
			       
	            String sql = "select francisco-parrapaez.salario_maximo()";
		    ResultSet result = stat.executeQuery(sql);
		      
		    System.out.print(result.getString(1));
		     
	    	
		      con.close();
			      
	 
		    }catch(Exception e) {
		    	
		    	e.printStackTrace();
		    }
    
    }
    
    
}
