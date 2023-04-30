package com.example.enzospokedex;

import java.util.HashMap;
import java.util.List;
import java.io.Serializable;

public class Pokemon implements Serializable {
    private int id;
    private String name;
    private String spriteUrl;
    private int weight;
    private List<String> abilities;
    private List<String> types;
    private HashMap<String, String> stats;
    private String evolutionChainUrl;

    public Pokemon(int id, String name, String spriteUrl, int weight, List<String> abilities, List<String> types, HashMap<String, String> stats)  {
        this.id = id;
        this.name = name;
        this.spriteUrl = spriteUrl;
        this.weight = weight;
        this.abilities = abilities;
        this.types = types;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public String getSpriteUrl() {
        return spriteUrl;
    }

    public int getWeight() {
        return weight;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public List<String> getTypes() {
        return types;
    }

    public HashMap<String, String> getStats() {
        return stats;
    }

    public int getId() {
        return id;
    }

    public String getEvolutionChainUrl() {
        return evolutionChainUrl;
    }

    public void setEvolutionChainUrl(String evolutionChainUrl) {
        this.evolutionChainUrl = evolutionChainUrl;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", spriteUrl='" + spriteUrl + '\'' +
                ", weight=" + weight +
                ", abilities=" + abilities +
                ", types=" + types +
                ", stats=" + stats +
                '}';
    }
}

