package com.example.enzospokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PokemonListAdapter extends BaseAdapter {
    private final Context context;
    private final List<Pokemon> pokemons;

    public PokemonListAdapter(Context context, List<Pokemon> pokemons) {
        this.context = context;
        this.pokemons = pokemons;
    }

    @Override
    public int getCount() {
        return pokemons.size();
    }

    @Override
    public Object getItem(int position) {
        return pokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons.clear();
        // Sort the pokemons by ID
        pokemons.sort((pokemon1, pokemon2) -> pokemon1.getId() - pokemon2.getId());
        this.pokemons.addAll(pokemons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_pokemon, parent, false);
        }

        ImageView pokemonSprite = convertView.findViewById(R.id.pokemon_sprite);
        TextView pokemonName = convertView.findViewById(R.id.pokemon_name);
        TextView pokemonId = convertView.findViewById(R.id.pokemon_id);
        TextView pokemonTypes = convertView.findViewById(R.id.pokemon_types);

        Pokemon pokemon = pokemons.get(position);
        pokemonName.setText(pokemon.getName());
        Picasso.get().load(pokemon.getSpriteUrl()).into(pokemonSprite);
        pokemonId.setText(String.valueOf(pokemon.getId()));
        pokemonTypes.setText(String.join("\n", pokemon.getTypes()));

        return convertView;
    }
}
