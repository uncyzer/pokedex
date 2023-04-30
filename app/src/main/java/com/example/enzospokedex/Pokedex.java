package com.example.enzospokedex;
import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.chromium.net.CronetEngine;

public class Pokedex {
    private final PokeApi pokeApi;
    private final PokemonJsonParser pokemonJsonParser;
    private final List<Pokemon> pokemons;
    private PokemonListAdapter pokemonListAdapter;
    private Activity activity;

    public Pokedex(Context context, PokemonListAdapter pokemonListAdapter, Activity activity) {
        Executor executor = Executors.newSingleThreadExecutor();
        CronetEngine cronetEngine = new CronetEngine.Builder(context).build();
        this.pokeApi = new PokeApi(cronetEngine, executor);
        this.pokemonJsonParser = new PokemonJsonParser();
        this.pokemons = new ArrayList<>();
        this.pokemonListAdapter = pokemonListAdapter;
        this.activity = activity;
    }

    public void loadPokemons() {
        pokeApi.fetchGeneration("1", generationResponse -> {
            List<String> speciesUrls = pokemonJsonParser.parseGenerationResponse(generationResponse);
            for (String speciesUrl : speciesUrls) {
                pokeApi.fetchPokemonSpecies(speciesUrl, speciesResponse -> {
                    String[] pokemonUrl = pokemonJsonParser.parsePokemonSpeciesResponse(speciesResponse);
                    pokeApi.fetchPokemon(pokemonUrl[0], pokemonResponse -> {
                        Pokemon pokemon = pokemonJsonParser.parsePokemonResponse(pokemonResponse);
                        pokemon.setEvolutionChainUrl(pokemonUrl[1]);
                        // Notify the adapter that the data has changed
                        activity.runOnUiThread(() -> {
                            pokemons.add(pokemon);
                            pokemonListAdapter.setPokemons(pokemons);
                            pokemonListAdapter.notifyDataSetChanged();
                        });
                    }, error -> System.out.println("Error fetching pokemon: " + error));
                }, error -> System.out.println("Error fetching pokemon species: " + error));
            }
        }, error -> System.out.println("Error fetching generation: " + error));
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}
