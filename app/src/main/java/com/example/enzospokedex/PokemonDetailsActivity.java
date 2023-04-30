package com.example.enzospokedex;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.chromium.net.CronetEngine;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PokemonDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_POKEMON = "EXTRA_POKEMON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_details);

        // Get the Pokemon from the intent
        Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra(EXTRA_POKEMON);

        ImageView pokemonImage = findViewById(R.id.pokemon_image);
        TextView pokemonNameAndId = findViewById(R.id.pokemon_name_and_id);
        TextView pokemonTypes = findViewById(R.id.pokemon_types);
        TextView pokemonAbilities = findViewById(R.id.pokemon_abilities);
        TextView pokemonStats = findViewById(R.id.pokemon_stats);
        TextView hpValue = findViewById(R.id.hp_text);
        TextView attackValue = findViewById(R.id.atk_text);
        TextView defenseValue = findViewById(R.id.def_text);

        // Set the Pokemon's image
        Picasso.get().load(pokemon.getSpriteUrl()).into(pokemonImage);
        pokemonNameAndId.setText(String.format("%s (#%d)", pokemon.getName(), pokemon.getId()));
        pokemonTypes.setText("Types : " + String.join(", ", pokemon.getTypes()));
        pokemonAbilities.setText("Sorts de base : " + String.join(", ", pokemon.getAbilities()));

        StringBuilder stats = new StringBuilder();
        // For key value in hashmap
        for (String statName : pokemon.getStats().keySet()) {
            String statValue = pokemon.getStats().get(statName);
            if(Objects.equals(statName, "attack")) {
                attackValue.setText(statValue + " HP");
            } else if(Objects.equals(statName, "defense")) {
                defenseValue.setText(statValue + " DEF");
            } else if(Objects.equals(statName, "hp")) {
                hpValue.setText(statValue + " ATK");
            }
        }

        pokemonStats.setText(stats.toString());
    }
}
