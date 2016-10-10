package pe.com.chfernandezrios.pokemontradingcards;

import java.util.List;

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
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @POST("usuarios/login")
    Call<LoginResponse> logIn(@Body LoginRequest loginRequest);

    @POST("usuarios")
    Call<Status> registro(@Body RegistroRequest registroRequest);

    @GET("usuarios/{id_usuario}/pokemones")
    Call<List<Integer>> obtenerMisPokemones(@Path("id_usuario") int id);

    @GET("pokemones/{id_pokemon}")
    Call<PokemonResponse> obtenerDatosPokemon(@Path("id_pokmeon") int id);

    @GET("disponibles")
    Call<List<Integer>> obtenerPokemonesDisponibles();

    @POST("pokemones/atrapar")
    Call<Status> atraparPokemon(@Body AtraparRequest atraparRequest);

}
