package dam;
// Generated 11-nov-2020 11:57:25 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Categorias generated by hbm2java
 */
public class Categorias  implements java.io.Serializable {


     private int id;
     private String nombreCategoria;
     private Set<Productos> productoses = new HashSet<Productos>(0);

    public Categorias() {
    }

	
    public Categorias(int id) {
        this.id = id;
    }
    public Categorias(int id, String nombreCategoria, Set<Productos> productoses) {
       this.id = id;
       this.nombreCategoria = nombreCategoria;
       this.productoses = productoses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNombreCategoria() {
        return this.nombreCategoria;
    }
    
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    public Set<Productos> getProductoses() {
        return this.productoses;
    }
    
    public void setProductoses(Set<Productos> productoses) {
        this.productoses = productoses;
    }




}


