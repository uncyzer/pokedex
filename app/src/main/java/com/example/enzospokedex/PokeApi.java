package com.example.enzospokedex;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.net.CronetProviderInstaller;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class PokeApi {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private final CronetEngine cronetEngine;
    private final Executor executor;

    public PokeApi(CronetEngine cronetEngine, Executor executor) {
        this.cronetEngine = cronetEngine;
        this.executor = executor;
    }

    public void fetchGeneration(@NonNull String generationId, Consumer<String> onSuccess, Consumer<Throwable> onFailure) {
        String url = BASE_URL + "generation/" + generationId;
        fetchData(url, onSuccess, onFailure);
    }

    public void fetchPokemonSpecies(@NonNull String speciesUrl, Consumer<String> onSuccess, Consumer<Throwable> onFailure) {
        fetchData(speciesUrl, onSuccess, onFailure);
    }

    public void fetchPokemon(@NonNull String pokemonUrl, Consumer<String> onSuccess, Consumer<Throwable> onFailure) {
        fetchData(pokemonUrl, onSuccess, onFailure);
    }

    private void fetchData(String url, Consumer<String> onSuccess, Consumer<Throwable> onFailure) {
        UrlRequest request = cronetEngine.newUrlRequestBuilder(url, new UrlRequest.Callback() {
            final StringBuilder responseBody = new StringBuilder();

            @Override
            public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) {
                request.followRedirect();
            }

            @Override
            public void onResponseStarted(UrlRequest request, UrlResponseInfo info) {
                request.read(ByteBuffer.allocateDirect((int) info.getReceivedByteCount()));
            }

            @Override
            public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
                byteBuffer.flip();
                responseBody.append(StandardCharsets.UTF_8.decode(byteBuffer).toString());
                byteBuffer.clear();
                request.read(byteBuffer);
            }

            @Override
            public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
                onSuccess.accept(responseBody.toString());
            }

            @Override
            public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
                onFailure.accept(error);
            }
        }, executor).build();
        request.start();
    }


}
