package modelos;

/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 * @since 1.0
 * @version 1.0
 *
 * Utilizada para el adapter de comentarios para luego añadir los comentarios
 * en un ListView y mostrarlos al usuario
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
