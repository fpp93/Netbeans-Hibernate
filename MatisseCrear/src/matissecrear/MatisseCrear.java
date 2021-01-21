package matissecrear;
import com.matisse.MtDatabase;
import com.matisse.MtException;
import gest_proyectos.*;
/**
*
* @author David
*/
public class MatisseCrear {
    private static MtDatabase db = null;
public static void main(String[] args) {
try{
// Instanciamos un objeto de tipo MtDatabase que da acceso a nuestra BDO
db = new MtDatabase("localhost", "AcDat_BDO");
db.open();
// Comenzamos con la transacción
db.startTransaction();
// Instanciamos un objeto de tipo proyecto vinculado a la BDO
/*
Proyecto p1 = new Proyecto(db);
p1.setNom_proy("PAPEL ELECTRÓNICO");
p1.setF_inicio(new java.util.GregorianCalendar(2018,12,01));
// Instanciamos EmpleadoPlantilla y le asignamos la relación
EmpleadoPlantilla jp1 = new EmpleadoPlantilla(db);
jp1.setDni("78901234X");
jp1.setNom_emp("NADALES");
jp1.setNum_emp("604202");
p1.setJefe_proyecto(jp1);
// Creamos un empleado y lo asignamos al proyecto
Empleado e1 = new Empleado(db);
e1.setDni("56789012B");
e1.setNom_emp("SAMPER");
p1.appendTiene_asignado(e1);
// Creamos un empleadoPlantilla, le asignamos datos profesionales
// y lo vinculamos al proyecto. Como se puede ver, EmpleadoPlantilla
// hereda de Empleado sus atributos y sus relaciones.
EmpleadoPlantilla e2 = new EmpleadoPlantilla(db);
e2.setDni("76543210S");
e2.setNom_emp("SILVA");
e2.setNum_emp("753014");
DatosProfesionales dp2 = new DatosProfesionales(db);
dp2.setDni("76543210S");
dp2.setCategoria("B1");
dp2.setSueldo_bruto_anual(45200F);
e2.setTiene_datos_prof(dp2);
p1.appendTiene_asignado(e2);
Empleado e3 = new Empleado(db);
e3.setDni("89012345E");
e3.setNom_emp("ROJAS");
// Actualizamos todos los objetos nuevos en la BDO
*/
Empleado e2= Empleado.lookupEmpleado_pk(db,"76543210S");

    System.out.println(e2.subeSueldoBruto(10));

db.commit();
}
catch(MtException e){
System.out.println("MtException: " + e.getMessage());
}
finally{
if(db != null){
db.close();
}
}
}
}
