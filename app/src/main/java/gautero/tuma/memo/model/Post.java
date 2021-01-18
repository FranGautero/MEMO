package gautero.tuma.memo.model;

import android.graphics.Bitmap;

public class Post {
    String Titulo;
    String Historia;
    String Usiario;
    int Imagen;

    public Post(String titulo, String historia, String usiario, int imagen) {
        Titulo = titulo;
        Historia = historia;
        Usiario = usiario;
        Imagen = imagen;
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
        return Usiario;
    }

    public void setUsiario(String usiario) {
        Usiario = usiario;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }
}
