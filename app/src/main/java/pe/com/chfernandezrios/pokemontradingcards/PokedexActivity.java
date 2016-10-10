package pe.com.chfernandezrios.pokemontradingcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import android.os.Handler;

import pe.com.chfernandezrios.pokemontradingcards.beans.Pokemon;
import pe.com.chfernandezrios.pokemontradingcards.beans.responses.PokemonResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexActivity extends AppCompatActivity {
    private ImageView iviPokemon;
    private TextView tviNombrePokemon;
    private TextView tviNivelPokemon;
    private TextView tviTipoPokemon;
    private TextView tviDescripcionPokemon;

    private int id;
    private int elemento = 0;
    private List<Integer> misPokemones;
    IPokemonClient client;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mispokemones);

        /******** Views ********/
        iviPokemon = (ImageView) findViewById(R.id.iviPokemon);
        tviNombrePokemon = (TextView) findViewById(R.id.tviNombrePokemon);
        tviNivelPokemon = (TextView) findViewById(R.id.tviNivelPokemon);
        tviTipoPokemon = (TextView) findViewById(R.id.tviTipoPokemon);
        tviDescripcionPokemon = (TextView) findViewById(R.id.tviDescripcionPokemon);
        Button butAnterior = (Button) findViewById(R.id.butAnterior);
        Button butMenu = (Button) findViewById(R.id.butMenu);
        Button butSiguiente = (Button) findViewById(R.id.butSiguiente);

        // Obtener el intent que condujo aquí
        Intent dasboardIntent = getIntent();

        // Obtener el id del usuario mandado en el intent
        id = dasboardIntent.getIntExtra("ID", 0);

        // Handler del hilo principal
        handler = new Handler();

        // Cliente REST Pokemon
        client = ServiceGenerator.createService(IPokemonClient.class);

        // Obtener mis pokemones y cargarlos
        obtenerMisPokemones();

        // Cuando se haga click en <<
        butAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elemento == 0) {
                    elemento = misPokemones.size() - 1;
                } else {
                    elemento--;
                }
                cargarDatosPokemon(misPokemones.get(elemento));
            }
        });

        // Cuando se haga click en Menú
        butMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ID", id);
                intent.setClass(PokedexActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        // Cuando se haga click en >>
        butSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elemento == misPokemones.size() - 1) {
                    elemento = 0;
                } else {
                    elemento++;
                }
                cargarDatosPokemon(misPokemones.get(elemento));
            }
        });
    }

    private void obtenerMisPokemones() {
        /*
        // Conexión remota en nuevo hilo
        new Thread() {
            @Override
            public void run() {*/
        client.obtenerMisPokemones(id).enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                misPokemones = response.body();

                // Si es nula o está vacía
                if (misPokemones == null || misPokemones.size() == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("ID", id);
                    intent.setClass(PokedexActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), "Usted no tiene pokemones", Toast.LENGTH_SHORT).show();
                }

                cargarDatosPokemon(misPokemones.get(elemento));
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Intent intent = new Intent();
                intent.putExtra("ID", id);
                intent.setClass(PokedexActivity.this, DashboardActivity.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "No se pudo obtener sus pokemones", Toast.LENGTH_SHORT).show();
            }
        });
        /*
            }
        }.start();*/
    }

    private void cargarDatosPokemon(final int pokemonId) {
        // Obtener datos

        client.obtenerDatosPokemon(pokemonId).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                final PokemonResponse pokemonResponse = response.body();

                Picasso.with(getApplicationContext()).load(pokemonResponse.getUrl()).into(iviPokemon);

                // UI en nuevo hilo
                handler.post(new Thread() {
                    @Override
                    public void run() {
                        // Cargar views
                        tviNombrePokemon.setText(pokemonResponse.getNombre());
                        tviNivelPokemon.setText(String.valueOf(pokemonResponse.getNivel()));
                        tviTipoPokemon.setText(pokemonResponse.getTipo());
                        tviDescripcionPokemon.setText(pokemonResponse.getDescripcion());
                    }
                });
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Intent intent = new Intent();
                intent.putExtra("ID", id);
                intent.setClass(PokedexActivity.this, DashboardActivity.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "No se pudo cargar los datos del pokemon", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ID", id);
        outState.putInt("ELEMENTO", elemento);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id = savedInstanceState.getInt("ID");
        elemento = savedInstanceState.getInt("ELEMENTO");
        // Obtener mis pokemones y cargarlos
        // Lo ideal sería guardar la lista de pokemones obtenida antes
        // y recuperarla aquí, pero no sé cómo meter un Collection en el Bundle
        obtenerMisPokemones();
    }
}
