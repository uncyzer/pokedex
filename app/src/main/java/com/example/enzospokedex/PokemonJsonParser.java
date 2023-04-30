package com.example.enzospokedex;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PokemonJsonParser {

    public List<String> parseGenerationResponse(String generationResponse) {
        List<String> speciesUrls = new ArrayList<>();
        JsonObject generationJson = JsonParser.parseString(generationResponse).getAsJsonObject();
        JsonArray speciesArray = generationJson.getAsJsonArray("pokemon_species");
        for (JsonElement speciesElement : speciesArray) {
            JsonObject speciesObject = speciesElement.getAsJsonObject();
            String speciesUrl = speciesObject.get("url").getAsString();
            speciesUrls.add(speciesUrl);
        }
        return speciesUrls;
    }

    public String[] parsePokemonSpeciesResponse(String speciesResponse) {
        JsonObject speciesJson = JsonParser.parseString(speciesResponse).getAsJsonObject();
        JsonObject evolutionChainObject = speciesJson.getAsJsonObject("evolution_chain");
        String evolutionChainUrl = evolutionChainObject.get("url").getAsString();
        JsonArray varietiesArray = speciesJson.getAsJsonArray("varieties");
        JsonObject defaultVariety = varietiesArray.get(0).getAsJsonObject();
        JsonObject pokemonObject = defaultVariety.getAsJsonObject("pokemon");
        String[] pokemonInfo = {pokemonObject.get("url").getAsString(), evolutionChainUrl};
        return pokemonInfo;
    }

    public Pokemon parsePokemonResponse(String pokemonResponse) {
        JsonObject pokemonJson = JsonParser.parseString(pokemonResponse).getAsJsonObject();
        int id = pokemonJson.get("id").getAsInt();
        String name = pokemonJson.get("name").getAsString();
        String spriteUrl = pokemonJson.getAsJsonObject("sprites").get("front_default").getAsString();
        int weight = pokemonJson.get("weight").getAsInt();

        List<String> abilities = new ArrayList<>();
        JsonArray abilitiesArray = pokemonJson.getAsJsonArray("abilities");
        for (JsonElement abilityElement : abilitiesArray) {
            JsonObject abilityObject = abilityElement.getAsJsonObject().getAsJsonObject("ability");
            abilities.add(abilityObject.get("name").getAsString());
        }

        List<String> types = new ArrayList<>();
        JsonArray typesArray = pokemonJson.getAsJsonArray("types");
        for (JsonElement typeElement : typesArray) {
            JsonObject typeObject = typeElement.getAsJsonObject().getAsJsonObject("type");
            types.add(typeObject.get("name").getAsString());
        }

        HashMap<String, String> stats = new HashMap<>();
        JsonArray statsArray = pokemonJson.getAsJsonArray("stats");
        for (JsonElement statElement : statsArray) {
            String baseStat = statElement.getAsJsonObject().get("base_stat").getAsString();
            JsonObject statObject = statElement.getAsJsonObject().getAsJsonObject("stat");
            stats.put(statObject.get("name").getAsString(), baseStat);
        }
        return new Pokemon(id, name, spriteUrl, weight, abilities, types, stats);
    }
}
