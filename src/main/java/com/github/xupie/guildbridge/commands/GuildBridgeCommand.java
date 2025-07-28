package com.github.xupie.guildbridge.commands;

import com.github.xupie.guildbridge.config.ModConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

@Environment(EnvType.CLIENT)
public final class GuildBridgeCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            literal("guildbridge")
                .executes(GuildBridgeCommand::openConfig)
        );
    }

    private static int openConfig(CommandContext<FabricClientCommandSource> context) {
        Screen configScreen = ModConfig.configScreen(null);
        MinecraftClient.getInstance().setScreen(configScreen);
        return Command.SINGLE_SUCCESS;
    }
}