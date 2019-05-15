package proyecto.appcampeonato.beans;

import java.io.Serializable;

public class SexoBean implements Serializable {

    private int codigo;
    private String nombre;

    public String toString(){
        return nombre;
    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
