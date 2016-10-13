package pe.com.chfernandezrios.pokemontradingcards.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chfernandezrios on 10/10/2016.
 */
public class PokemonDisponible implements Parcelable {
    private int pokemonId;
    private String pokemonNombre;
    private String pokemonImagenUrl;

    public PokemonDisponible(int pokemonId, String pokemonNombre, String pokemonImagenUrl) {
        this.pokemonId = pokemonId;
        this.pokemonNombre = pokemonNombre;
        this.pokemonImagenUrl = pokemonImagenUrl;
    }

    /***** Parcelable *****/
    private PokemonDisponible(Parcel in) {
        this.pokemonId = in.readInt();
        this.pokemonNombre = in.readString();
        this.pokemonImagenUrl = in.readString();
    }

    public static final Parcelable.Creator<PokemonDisponible> CREATOR = new Creator<PokemonDisponible>() {
        @Override
        public PokemonDisponible createFromParcel(Parcel source) {
            return new PokemonDisponible(source);
        }

        @Override
        public PokemonDisponible[] newArray(int size) {
            return new PokemonDisponible[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pokemonId);
        dest.writeString(pokemonNombre);
        dest.writeString(pokemonImagenUrl);
    }
    /***** Parcelable *****/

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getPokemonNombre() {
        return pokemonNombre;
    }

    public void setPokemonNombre(String pokemonNombre) {
        this.pokemonNombre = pokemonNombre;
    }

    public String getPokemonImagenUrl() {
        return pokemonImagenUrl;
    }

    public void setPokemonImagenUrl(String pokemonImagenUrl) {
        this.pokemonImagenUrl = pokemonImagenUrl;
    }
}
