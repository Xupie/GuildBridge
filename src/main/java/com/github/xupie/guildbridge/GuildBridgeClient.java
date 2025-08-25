package com.github.xupie.guildbridge;

import com.github.xupie.guildbridge.commands.GuildBridgeCommand;
import com.github.xupie.guildbridge.commands.TestBridgeCommand;
import com.github.xupie.guildbridge.commands.TestGuildCommand;
import com.github.xupie.guildbridge.commands.TestWelcomeCommand;
import com.github.xupie.guildbridge.config.ModConfig;
import com.github.xupie.guildbridge.features.AutoWelcome;
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
            TestWelcomeCommand.register(dispatcher);
        }));
        ClientReceiveMessageEvents.MODIFY_GAME.register(GuildBridge::register);
        ClientReceiveMessageEvents.GAME.register(AutoWelcome::register);
    }
}
