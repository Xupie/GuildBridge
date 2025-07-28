package com.github.xupie.guildbridge;

import com.github.xupie.guildbridge.commands.GuildBridgeCommand;
import com.github.xupie.guildbridge.commands.TestBridgeCommand;
import com.github.xupie.guildbridge.commands.TestGuildCommand;
import com.github.xupie.guildbridge.config.ModConfig;
import com.github.xupie.guildbridge.features.GuildBridge;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class GuildBridgeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.HANDLER.load();

        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            GuildBridgeCommand.register(dispatcher);
            TestBridgeCommand.register(dispatcher);
            TestGuildCommand.register(dispatcher);
        }));
        ClientReceiveMessageEvents.MODIFY_GAME.register(GuildBridge::register);
    }
}
