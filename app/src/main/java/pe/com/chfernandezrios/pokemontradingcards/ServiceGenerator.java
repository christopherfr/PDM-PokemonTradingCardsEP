package pe.com.chfernandezrios.pokemontradingcards;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chfernandezrios on 2/10/2016.
 */
public class ServiceGenerator {

    public static final String SERVICIO_POKEMON_URL = "https://pokemonservice.herokuapp.com/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder servicioPokemonBuilder = new Retrofit.Builder().baseUrl(SERVICIO_POKEMON_URL).addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = null;

        retrofit = servicioPokemonBuilder.client(httpClient.build()).build();

        return retrofit.create(serviceClass);
    }
}
