package pe.com.chfernandezrios.pokemontradingcards.beans.responses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chfernandezrios on 9/10/2016.
 */
public class Pokemon implements Parcelable {

    private String nombre;
    private int nivel;
    private String tipo;
    private String descripcion;
    private String url;

    public Pokemon() {
    }

    public Pokemon(String nombre, int nivel, String tipo, String descripcion, String url) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.url = url;
    }

    /****** Parcelable *****/
    private Pokemon(Parcel in) {
        this.nombre = in.readString();
        this.nivel = in.readInt();
        this.tipo = in.readString();
        this.descripcion = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(nivel);
        dest.writeString(tipo);
        dest.writeString(descripcion);
        dest.writeString(url);
    }
    /****** Parcelable *****/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
