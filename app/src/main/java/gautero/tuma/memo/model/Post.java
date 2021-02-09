package gautero.tuma.memo.model;


import java.lang.reflect.Array;
import java.util.List;

public class Post {

    String Historia;
    String img0;
    String img1;
    String img2;
    String img3;
    String img4;
    String img5;
    String Titulo;
    String Usuario;

    public Post(String titulo, String historia, String usuario, String imgn0, String imgn1, String imgn2, String imgn3, String imgn4, String imgn5) {

        Historia = historia;
        img0 = imgn0;
        img1 = imgn1;
        img2 = imgn2;
        img3 = imgn3;
        img4 = imgn4;
        img5 = imgn5;
        Titulo = titulo;
        Usuario = usuario;


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

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getImg0() {
        return img0;
    }

    public void setImg0(String img0) {
        this.img0 = img0;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }
}
