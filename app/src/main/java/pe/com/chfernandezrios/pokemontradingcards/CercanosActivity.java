package pe.com.chfernandezrios.pokemontradingcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pe.com.chfernandezrios.pokemontradingcards.beans.PokemonDisponible;

public class CercanosActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private List<PokemonDisponible> pokemonesDisponibles;
    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cercanos);

        /******** Views ********/
        final RecyclerView revPokemonesDisponibles = (RecyclerView)findViewById(R.id.revPokemonesDisponibles);
        GridLayoutManager revGridLayoutManager = new GridLayoutManager(CercanosActivity.this, 3);
        revPokemonesDisponibles.setLayoutManager(revGridLayoutManager);

        // Obtener el intent que condujo aquí
        Intent anteriorIntent = getIntent();
        // Obtener el id del usuario enviado en el intent
        usuarioId = anteriorIntent.getIntExtra("USUARIO_ID", 0);
        // Obtener la lista de pokemones disponibles enviada en el intent
        pokemonesDisponibles = anteriorIntent.getParcelableArrayListExtra("LISTA_POKEMONES_DISPONIBLES");

        RecyclerView.Adapter revPokemonesDisponiblesAdapter = new CercanosAdapter(getApplicationContext(), pokemonesDisponibles, this);
        revPokemonesDisponibles.setAdapter(revPokemonesDisponiblesAdapter);
    }

    @Override
    public void onItemClick(View childView, int position) {
        Intent intent = new Intent();
        intent.putExtra("POKEMON_SELECCIONADO", pokemonesDisponibles.get(position));
        intent.putExtra("USUARIO_ID", usuarioId);
        intent.setClass(CercanosActivity.this, AtraparActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("LISTA_POKEMONES_DISPONIBLES", (ArrayList<PokemonDisponible>) pokemonesDisponibles);
        outState.putInt("USUARIO_ID", usuarioId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pokemonesDisponibles = savedInstanceState.getParcelableArrayList("LISTA_POKEMONES_DISPONIBLES");
        usuarioId = savedInstanceState.getInt("USUARIO_ID");
    }
}
