package com.github.xupie.guildbridge.commands;

import com.github.xupie.guildbridge.config.ModConfig;
import com.github.xupie.guildbridge.features.GuildBridge;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public final class TestBridgeCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            ClientCommandManager.literal("testbridge")
                .executes(TestBridgeCommand::broadcast)
        );
    }

    private static int broadcast(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        GuildBridge.register(Text.literal("§2Guild > §b[MVP§c+§b] IsleofDucks §e[STAFF]§f: ✧ Xupie: b"), true);
        return Command.SINGLE_SUCCESS;
    }
}


