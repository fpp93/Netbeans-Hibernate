/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matisseconsultaconjdbcmodifconjavabinding;

import com.matisse.MtDatabase;
import com.matisse.MtException;
import java.sql.SQLException;
import gest_proyectos.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
*
* @author francisco
*/
public class MatisseConsultaConJDBCModifConJavaBinding {
private static MtDatabase db = null;
public static void muestraErrorSQL(SQLException e) {
    
System.err.println("SQL ERROR message: " + e.getMessage());
System.err.println("SQL State: " + e.getSQLState());
System.err.println("SQL código específico: " + e.getErrorCode());
}
public static void main(String[] args) {
try {
MtDatabase db = new MtDatabase("localhost", "ActDat_BDO");
db.open();
db.startTransaction();
Empleado e = new Empleado(db);
e.setDni("65432109F");
e.setNom_emp("LUQUE");
Connection jdbcCon = db.getJDBCConnection();
Statement st = jdbcCon.createStatement();
String query = "SELECT REF(p) FROM gest_proyectos.Proyecto p WHERE "
+ "p.jefe_proyecto.dni='78901234X'";
ResultSet rs = st.executeQuery(query);
Proyecto p;
while (rs.next()) {
p = (Proyecto) rs.getObject(1);
// Imprime nombre del proyecto y jefe + DNI
System.out.println(p.getNom_proy()+", "+p.getJefe_proyecto().getDni()+","+p.getJefe_proyecto().getNom_emp());
// Si el jefe del proyecto es NADALES, añade como empleado a LUQUE
}
rs.close();
st.close();
db.commit();
} catch (SQLException e) {
muestraErrorSQL(e);
} catch (MtException e) {
System.out.println("MtException: " + e.getMessage());
} finally {
if (db != null) {
db.close();
}
}
}
}

