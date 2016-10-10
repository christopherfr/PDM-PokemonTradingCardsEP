package pe.com.chfernandezrios.pokemontradingcards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AtraparActivity extends AppCompatActivity {

    private RecyclerView revPokemonesDisponibles;
    private RecyclerView.Adapter revPokemonesDisponiblesAdapter;
    private RecyclerView.LayoutManager revPokemonesDisponiblesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atrapar);



    }
}
