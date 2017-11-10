package modelos;

/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 */

public class Comentarios {

    private String fecha;
    private String comentario;
    private String nombreCompleto;


    public Comentarios(String fecha,String comentario, String nombreCompleto){

        this.fecha=fecha;
        this.comentario=comentario;
        this.nombreCompleto=nombreCompleto;

    }

    public String getFecha() {
        return fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}
