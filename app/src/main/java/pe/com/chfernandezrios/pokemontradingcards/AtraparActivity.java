package pe.com.chfernandezrios.pokemontradingcards;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import pe.com.chfernandezrios.pokemontradingcards.beans.PokemonDisponible;
import pe.com.chfernandezrios.pokemontradingcards.beans.responses.AtraparRequest;
import pe.com.chfernandezrios.pokemontradingcards.beans.responses.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtraparActivity extends AppCompatActivity {

    private TextView tviAtraparlo;
    private ImageView iviPokemonDisponible;

    private int usuarioId;
    private PokemonDisponible pokemonDisponible;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atrapar);

        /******** Views ********/
        tviAtraparlo = (TextView) findViewById(R.id.tviAtraparlo);
        iviPokemonDisponible = (ImageView) findViewById(R.id.iviPokemonAtraparlo);

        // Cliente REST
        final IPokemonClient client = ServiceGenerator.createService(IPokemonClient.class);

        // Obtener el intent que condujo aquí
        Intent anteriorIntent = getIntent();
        // Obtener el id del usuario enviado en el intent
        usuarioId = anteriorIntent.getIntExtra("USUARIO_ID", 0);
        // Obtener la lista de pokemones disponibles enviada en el intent
        pokemonDisponible = anteriorIntent.getParcelableExtra("POKEMON_SELECCIONADO");

        handler = new Handler();
        cargarViews();

        iviPokemonDisponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtraparRequest atraparRequest = new AtraparRequest(pokemonDisponible.getPokemonId(), usuarioId);
                client.atraparPokemon(atraparRequest).enqueue(new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.isSuccessful()) {
                            Status status = response.body();

                            if (status.getCode() == 0) {
                                Toast.makeText(getBaseContext(), pokemonDisponible.getPokemonNombre() + " capturado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), "No se pudo capturar al pokemon", Toast.LENGTH_SHORT).show();
                            }

                            Intent intent = new Intent();
                            intent.putExtra("USUARIO_ID", usuarioId);
                            intent.setClass(AtraparActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "No se pudo capturar al pokemon", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "No se pudo capturar al pokemon", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void cargarViews() {
        Picasso.with(getApplicationContext()).load(pokemonDisponible.getPokemonImagenUrl()).into(iviPokemonDisponible);

        // UI en nuevo hilo
        handler.post(new Thread() {
            @Override
            public void run() {
                // Cargar views
                tviAtraparlo.setText(pokemonDisponible.getPokemonNombre());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("USUARIO_ID", usuarioId);
        outState.putParcelable("POKEMON_SELECCIONADO", pokemonDisponible);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        usuarioId = savedInstanceState.getInt("USUARIO_ID");
        pokemonDisponible = savedInstanceState.getParcelable("POKEMON_SELECCIONADO");
    }
}
