package pe.com.chfernandezrios.pokemontradingcards;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pe.com.chfernandezrios.pokemontradingcards.beans.PokemonDisponible;

/**
 * Created by chfernandezrios on 10/10/2016.
 */

public class CercanosAdapter extends RecyclerView.Adapter<CercanosAdapter.CercanosViewHolder> {

    private Context context;
    private List<PokemonDisponible> pokemonDisponibleElemento;
    private static RecyclerViewClickListener itemListener;

    public static class CercanosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iviPokemon;
        public TextView tviNombrePokemon;

        public CercanosViewHolder(View itemView) {
            super(itemView);
            iviPokemon = (ImageView)itemView.findViewById(R.id.iviPokemon);
            tviNombrePokemon = (TextView)itemView.findViewById(R.id.tviNombrePokemon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.onItemClick(view, getLayoutPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CercanosAdapter(Context context, List<PokemonDisponible> pokemonDisponibleElemento, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.pokemonDisponibleElemento = pokemonDisponibleElemento;
        this.itemListener = itemListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CercanosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview, null);
        return new CercanosViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(CercanosViewHolder holder, int position) {
        Picasso.with(context).load(pokemonDisponibleElemento.get(position).getPokemonImagenUrl()).into(holder.iviPokemon);
        holder.tviNombrePokemon.setText(pokemonDisponibleElemento.get(position).getPokemonNombre());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pokemonDisponibleElemento.size();
    }
}