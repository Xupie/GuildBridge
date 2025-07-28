package com.github.xupie.guildbridge.commands;

import com.github.xupie.guildbridge.features.GuildBridge;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public final class TestGuildCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            ClientCommandManager.literal("testguild")
                .executes(TestGuildCommand::broadcast)
        );
    }

    private static int broadcast(CommandContext<FabricClientCommandSource> context) {
        GuildBridge.register(Text.literal("§2Guild > §a[VIP§6+§a] Xupie §e[EMBER]§f: b"), true);
        return Command.SINGLE_SUCCESS;
    }
}