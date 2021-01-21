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
public class InsertarDatos {
    
    public static void main(String[] args)  {
    
    String url = "jdbc:mysql://localhost:3306/francisco-parrapaez" + "?useUnicode=true"+ "&serverTimezone=Europe/Madrid";
    String nombredriver ="com.mysql.cj.jdbc.Driver";
    
    Scanner sc=new Scanner(System.in);
    Scanner sc1=new Scanner(System.in);
    Scanner sc2=new Scanner(System.in);
    
        System.out.println("Introduce el nuevo cargo");
        String nuevocargo=sc.nextLine();
        System.out.println("Introduce el sueldo minimo");
        int sueldo_minimo=sc1.nextInt();
        System.out.println("Introduce el sueldo maximo");
        int sueldo_maximo=sc2.nextInt();
        String ultimo_id;
        
         try {
		    Class.forName(nombredriver);
		   
	            Connection con = DriverManager.getConnection(url, "Francisco", "examen1");
                    String sql1="select count(idcargo) from cargo";
                    Statement stat1=con.createStatement();
                     
		    ResultSet result = stat1.executeQuery(sql1);
		      
		    result.next() ;
		    result.getInt(1);
                    ultimo_id="C"+Integer.toString(result.getInt(1)+1);
		   
			       
	            String sql = "INSERT INTO cargo(idcargo,nombre,sueldo_min,sueldo_max) value(?,?,?,?)";
		    PreparedStatement stat=con.prepareStatement(sql);
			     
		    stat.setString(1,ultimo_id);
		    stat.setString(2,nuevocargo);
		    stat.setInt(3, sueldo_minimo);
		    stat.setInt(4,sueldo_maximo);
			      
	            stat.executeUpdate();
                    System.out.println("Insercion realizada");
		    }catch(Exception e) {
		    	
		    	e.printStackTrace();
		    }
    
    }
    
}
