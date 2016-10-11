package pe.com.chfernandezrios.pokemontradingcards;

import java.util.List;

import pe.com.chfernandezrios.pokemontradingcards.beans.PokemonDisponible;
import pe.com.chfernandezrios.pokemontradingcards.beans.responses.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by chfernandezrios on 2/10/2016.
 */
public interface IPokemonClient {

    @POST("usuarios/login")
    Call<LoginResponse> logIn(@Body LoginRequest loginRequest);

    @POST("usuarios")
    Call<Status> registro(@Body RegistroRequest registroRequest);

    @GET("usuarios/{id_usuario}/pokemones")
    Call<List<Pokemon>> obtenerMisPokemones(@Path("id_usuario") int id);

    /*
    @GET("pokemones/{id_pokemon}")
    Call<Pokemon> obtenerDatosPokemon(@Path("id_pokemon") int id);
    */

    @GET("disponibles")
    Call<List<PokemonDisponible>> obtenerPokemonesDisponibles();

    @POST("pokemones/atrapar")
    Call<Status> atraparPokemon(@Body AtraparRequest atraparRequest);

}
