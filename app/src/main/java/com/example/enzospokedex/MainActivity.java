package com.example.enzospokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Pokedex pokedex;
    private PokemonListAdapter pokemonListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView pokemonList = findViewById(R.id.pokemon_list);
        pokemonListAdapter = new PokemonListAdapter(this, new ArrayList<>());
        pokemonList.setAdapter(pokemonListAdapter);

        pokedex = new Pokedex(this, pokemonListAdapter, this);
        pokedex.loadPokemons();

        // Assuming you have a ListView called "pokemonListView" in your MainActivity
        pokemonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pokemon selectedPokemon = (Pokemon) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, PokemonDetailsActivity.class);
                intent.putExtra(PokemonDetailsActivity.EXTRA_POKEMON, selectedPokemon);
                startActivity(intent);
            }
        });


    }
}
