package personas;

public class persona {
    int cedula;
    String Nombre;
    String Fecha;
    String zodiaco;

    public persona(int cedula, String nombre, String fecha, String zodiaco) {
        this.cedula = cedula;
        Nombre = nombre;
        Fecha = fecha;
        this.zodiaco = zodiaco;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getZodiaco() {
        return zodiaco;
    }

    public void setZodiaco(String zodiaco) {
        this.zodiaco = zodiaco;
    }
}
