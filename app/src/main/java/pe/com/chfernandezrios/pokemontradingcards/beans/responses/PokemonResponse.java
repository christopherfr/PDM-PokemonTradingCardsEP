package pe.com.chfernandezrios.pokemontradingcards.beans.responses;

/**
 * Created by chfernandezrios on 9/10/2016.
 */
public class PokemonResponse {

    private String nombre;
    private int nivel;
    private String tipo;
    private String descripcion;
    private String url;

    public PokemonResponse() {
    }

    public PokemonResponse(String nombre, int nivel, String tipo, String descripcion, String url) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.url = url;
    }

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
