/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matissemodifborrar;
import com.matisse.MtDatabase;
import com.matisse.MtException;
import gest_proyectos.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 *
 * @author Francisco
 */
public class MatisseModifBorrar {
    private static MtDatabase db = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       try{
           db = new MtDatabase("localhost", "AcDat_BDO");
        db.open();
        db.startTransaction();
        
        //punto1
        Proyecto p1 = new Proyecto(db);
        p1.setNom_proy("AI EN 5G");
        p1.setF_inicio(new GregorianCalendar(2019,05,11));
        
        //punto2
        EmpleadoPlantilla e2 = new EmpleadoPlantilla(db);
        e2.setDni("45543210R");
        e2.setNom_emp("INIESTA");
        e2.setNum_emp("30119");
        p1.setJefe_proyecto(e2);
        //punto3
        Empleado em2= Empleado.lookupEmpleado_pk(db,"89012345E");
        em2.setNom_emp("VERDES");
        //punto4
        p1.appendTiene_asignado(em2);
        //punto5
        EmpleadoPlantilla em3= EmpleadoPlantilla.lookupEmpleadoPlantilla_i_dni(db,"76543210S");
        //borro relaciones
        DatosProfesionales d1 = em3.getTiene_datos_prof();
            d1.remove();
        
        em3.clearTiene_datos_prof();
        //borro el objeto
        em3.deepRemove();
        //punto6
        Empleado em4= Empleado.lookupEmpleado_pk(db,"56789012B");
        DatosProfesionales d2 = em4.getTiene_datos_prof();
        Proyecto[] p2= em4.getAsignado_a();
        
        for(int i=0;i<p2.length;i++){
            p2[i].remove();
        }
        if(d2!=null){
            d2.remove();
        }
        
        em4.clearAsignado_a();
        em4.clearTiene_datos_prof();
        //punto7
        Iterator proyectitos= Proyecto.ownInstanceIterator(db);
        
        while(proyectitos.hasNext()){
            Proyecto pro=(Proyecto)proyectitos.next();
            
            muestraProyecto(pro);
            System.out.println("");
        }
        
        
        db.commit();
        db.close();
       }catch(MtException e){
            System.out.println("MtException: " + e.getMessage());
        }
      
        
        
  
    }
public static void muestraProyecto(Proyecto p){
    
    
    // Queremos imprimir lo siguiente...
    // Nombre del proyecto + OID (identificador único del objeto)
    System.out.println(p.getNom_proy()+" "+p.getMtOid());
    
    // Jefe del proyecto: DNI + Nombre
     EmpleadoPlantilla emp=p.getJefe_proyecto();
     System.out.println("Jefe de proyecto: "+emp.getDni()+", "+emp.getNom_emp());
     
    // Empleados del proyecto (DNI + Nombre)
        Empleado[] emp2=p.getTiene_asignado();
        
        for(int i=0;i<emp2.length;i++){
            System.out.println(emp2[i].getDni()+","+emp2[i].getNom_emp());
        }
        
        
}
    
}
