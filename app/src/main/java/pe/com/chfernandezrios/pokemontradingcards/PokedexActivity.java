package pe.com.chfernandezrios.pokemontradingcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

import pe.com.chfernandezrios.pokemontradingcards.beans.responses.Pokemon;

public class PokedexActivity extends AppCompatActivity {
    private ImageView iviPokemon;
    private TextView tviNombrePokemon;
    private TextView tviNivelPokemon;
    private TextView tviTipoPokemon;
    private TextView tviDescripcionPokemon;

    private int usuarioId;
    private int elemento = 0;
    private List<Pokemon> misPokemones;
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

        // Obtener el usuarioId del usuario mandado en el intent
        usuarioId = dasboardIntent.getIntExtra("USUARIO_ID", 0);

        // Obtener la lista de pokemones capturados del usuario enviada en el intent
        misPokemones = dasboardIntent.getParcelableArrayListExtra("LISTA_POKEMONES_CAPTURADOS");

        // Handler del hilo principal y carga de elementos gráficos
        handler = new Handler();
        cargarViews();

        // Cuando se haga click en <<
        butAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elemento == 0) {
                    elemento = misPokemones.size() - 1;
                } else {
                    elemento--;
                }
                cargarViews();
            }
        });

        // Cuando se haga click en Menú
        butMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("USUARIO_ID", usuarioId);
                intent.putParcelableArrayListExtra("LISTA_POKEMONES_CAPTURADOS", (ArrayList<Pokemon>) misPokemones);
                intent.putExtra("POKEDEX_ACTIVITY", true);
                intent.setClass(PokedexActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
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
                cargarViews();
            }
        });
    }

    private void cargarViews() {
        final Pokemon pokemonActual = misPokemones.get(elemento);

        Picasso.with(getApplicationContext()).load(pokemonActual.getUrl()).into(iviPokemon);

        // UI en nuevo hilo
        handler.post(new Thread() {
            @Override
            public void run() {
                // Cargar views
                tviNombrePokemon.setText(pokemonActual.getNombre());
                tviNivelPokemon.setText(String.valueOf(pokemonActual.getNivel()));
                tviTipoPokemon.setText(pokemonActual.getTipo());
                tviDescripcionPokemon.setText(pokemonActual.getDescripcion());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("USUARIO_ID", usuarioId);
        outState.putParcelableArrayList("LISTA_POKEMONES_CAPTURADOS", (ArrayList<Pokemon>) misPokemones);
        outState.putInt("ELEMENTO", elemento);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        usuarioId = savedInstanceState.getInt("USUARIO_ID");
        misPokemones = savedInstanceState.getParcelableArrayList("LISTA_POKEMONES_CAPTURADOS");
        elemento = savedInstanceState.getInt("ELEMENTO");
        cargarViews();
    }
}
