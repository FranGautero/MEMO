package gautero.tuma.memo.model;



public class Post {
    String Titulo;
    String Historia;
    String Usuario;
    String [] Imagenes;

    public Post(String titulo, String historia, String usiario, String [] imagen) {
        Titulo = titulo;
        Historia = historia;
        Usuario = usiario;
        Imagenes = imagen;


    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getHistoria() {
        return Historia;
    }

    public void setHistoria(String historia) {
        Historia = historia;
    }

    public String getUsiario() {
        return Usuario;
    }

    public void setUsiario(String usiario) {
        Usuario = usiario;
    }

    public String[] getImagenes() {
        return Imagenes;
    }

    public void setImagenes(String[] imagenes) {
        Imagenes = imagenes;
    }
}
