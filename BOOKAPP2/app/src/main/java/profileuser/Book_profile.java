package profileuser;

import com.example.bookapp.Book;

import java.util.List;

public class Book_profile {
    private int imgbook;
    private int eye;
    private int star;
    private int chuong;

    private String tensach;
    private String texteye;
    private String textstar;
    private String textchuong;
    private String mota;


    public Book_profile(int imgbook, int eye, int star, int chuong, String tensach, String texteye, String textstar, String textchuong, String mota) {
        this.imgbook = imgbook;
        this.eye = eye;
        this.star = star;
        this.chuong = chuong;
        this.tensach = tensach;
        this.texteye = texteye;
        this.textstar = textstar;
        this.textchuong = textchuong;
        this.mota = mota;
    }

    public int getImgbook() {
        return imgbook;
    }

    public void setImgbook(int imgbook) {
        this.imgbook = imgbook;
    }

    public int getEye() {
        return eye;
    }

    public void setEye(int eye) {
        this.eye = eye;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getChuong() {
        return chuong;
    }

    public void setChuong(int chuong) {
        this.chuong = chuong;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTexteye() {
        return texteye;
    }

    public void setTexteye(String texteye) {
        this.texteye = texteye;
    }

    public String getTextstar() {
        return textstar;
    }

    public void setTextstar(String textstar) {
        this.textstar = textstar;
    }

    public String getTextchuong() {
        return textchuong;
    }

    public void setTextchuong(String textchuong) {
        this.textchuong = textchuong;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}

