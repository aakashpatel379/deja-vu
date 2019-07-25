package com.developer.dejavu.util;

import com.developer.dejavu.model.Guest;
import com.developer.dejavu.cpu.Cpu;
import com.developer.dejavu.model.Player;
import com.developer.dejavu.cpu.PlayerType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class PlayerGson {
    RuntimeTypeAdapterFactory<Player> typeAdapterFactory = RuntimeTypeAdapterFactory.of(Player.class, "playerType", true)
            .registerSubtype(Cpu.class, PlayerType.CPU.name())
            .registerSubtype(Guest.class, PlayerType.GUEST.name());

    private Gson playerGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .registerTypeAdapterFactory(typeAdapterFactory)
            .create();
    public Gson getPlayerGson() {
        return playerGson;
    }
}
