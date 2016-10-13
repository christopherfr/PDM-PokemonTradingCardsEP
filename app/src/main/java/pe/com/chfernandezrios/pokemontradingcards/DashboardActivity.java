package pe.com.chfernandezrios.pokemontradingcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.com.chfernandezrios.pokemontradingcards.beans.PokemonDisponible;
import pe.com.chfernandezrios.pokemontradingcards.beans.responses.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    private List<Pokemon> misPokemones;
    private List<PokemonDisponible> pokemonesDisponibles;
    private int usuarioId;
    private boolean intentProvieneDePokedexActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /******** Views ********/
        Button butMisPokemones = (Button) findViewById(R.id.butMisPokemonesDashboard);
        Button butPokemonesDisponibles = (Button) findViewById(R.id.butPokemonesDisponiblesDashboard);

        // Cliente REST
        final IPokemonClient client = ServiceGenerator.createService(IPokemonClient.class);

        // Obtener el intent que condujo aquí
        Intent anteriorIntent = getIntent();

        // Obtener el usuarioId del usuario enviado en el intent
        usuarioId = anteriorIntent.getIntExtra("USUARIO_ID", 0);

        // Obtener el flag que indica si el intent viene desde PokedexActivity
        intentProvieneDePokedexActivity = anteriorIntent.getBooleanExtra("POKEDEX_ACTIVITY", false);

        if (intentProvieneDePokedexActivity) {
            // Obtener la lista de pokemones capturados enviado en el intent
            misPokemones = anteriorIntent.getParcelableArrayListExtra("LISTA_POKEMONES_CAPTURADOS");
        }

        // Cuando se haga click en el botón Mi Pokedex
        butMisPokemones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (misPokemones == null) {
                    Toast.makeText(getBaseContext(), "Obteniendo Pokedex... esto puede demorar", Toast.LENGTH_LONG).show();
                    client.obtenerMisPokemones(usuarioId).enqueue(new Callback<List<Pokemon>>() {
                        @Override
                        public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                            if (response.isSuccessful()) {
                                misPokemones = response.body();
                                // Si la lista no es nula ni está vacía
                                if (misPokemones != null && !misPokemones.isEmpty()) {
                                    pasarAPokedexActivity();
                                } else {
                                    Toast.makeText(getBaseContext(), "Usted no tiene pokemones", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                            Log.i("DashboardActivity", t.getMessage());
                            Toast.makeText(getBaseContext(), "No se pudo obtener sus pokemones", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    pasarAPokedexActivity();
                }
            }
        });

        // Cuando se haga click en el botón Pokemones Disponibles
        butPokemonesDisponibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Buscando pokemones cercanos...esto puede demorar", Toast.LENGTH_LONG).show();
                client.obtenerPokemonesDisponibles().enqueue(new Callback<List<PokemonDisponible>>() {
                    @Override
                    public void onResponse(Call<List<PokemonDisponible>> call, Response<List<PokemonDisponible>> response) {
                        if (response.isSuccessful()) {
                            pokemonesDisponibles = response.body();
                            // Si la lista no es nula ni está vacía
                            Intent intent = new Intent();
                            intent.putExtra("USUARIO_ID", usuarioId);
                            intent.putParcelableArrayListExtra("LISTA_POKEMONES_DISPONIBLES", (ArrayList<PokemonDisponible>) pokemonesDisponibles);
                            intent.setClass(DashboardActivity.this, CercanosActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PokemonDisponible>> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "No se pudo obtener los pokemones disponibles", Toast.LENGTH_SHORT).show();
                        Log.i("DashboardActivity", t.getMessage());
                    }
                });
            }
        });
    }

    private void pasarAPokedexActivity () {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("LISTA_POKEMONES_CAPTURADOS", (ArrayList<Pokemon>) misPokemones);
        intent.putExtra("USUARIO_ID", usuarioId);
        intent.setClass(DashboardActivity.this, PokedexActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("LISTA_POKEMONES_CAPTURADOS", (ArrayList<Pokemon>) misPokemones);
        outState.putParcelableArrayList("LISTA_POKEMONES_DISPONIBLES", (ArrayList<PokemonDisponible>) pokemonesDisponibles);
        outState.putInt("USUARIO_ID", usuarioId);
        outState.putBoolean("POKEDEX_ACTIVITY", intentProvieneDePokedexActivity);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        misPokemones = savedInstanceState.getParcelableArrayList("LISTA_POKEMONES_CAPTURADOS");
        pokemonesDisponibles = savedInstanceState.getParcelableArrayList("LISTA_POKEMONES_DISPONIBLES");
        usuarioId = savedInstanceState.getInt("USUARIO_ID");
        intentProvieneDePokedexActivity = savedInstanceState.getBoolean("POKEDEX_ACTIVITY");
    }
}
