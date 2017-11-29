package modelos;

/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 * @since 1.0
 * @version 1.0
 *
 * Utilizada para el adapter de ReportesAdapter para luego añadir los tickets
 * en un ListView y mostrarlos al usuario
 */

public class Tickets {
    private int folio;
    private String solicitud;
    private String calificacion;
    private String fechaAlta;
    private String fechaCierre;
    private String solictante;
    private int status;
    private String bibliotecario;

    public Tickets(int folio, String solicitud, String calificacion, String fechaAlta,String fechaCierre, String solictante, int status, String bibliotecario){
        this.folio=folio;
        this.solicitud=solicitud;
        this.calificacion=calificacion;
        this.fechaAlta=fechaAlta;
        this.fechaCierre=fechaCierre;
        this.solictante=solictante;
        this.status=status;
        this.bibliotecario=bibliotecario;
    }


    public int getFolio() {
        return folio;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public String getSolictante() {
        return solictante;
    }

    public int getStatus() {
        return status;
    }

    public String getBibliotecario() {
        return bibliotecario;
    }
}
