package com.github.xupie.guildbridge.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class ModConfig {
    public static ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(FabricLoader.getInstance().getConfigDir().resolve("guildbridge.json"))
            .build())
        .build();

    @SerialEntry
    public String botIGN = "IsleofDucks";

    @SerialEntry
    public boolean compactGuildMessage = false;

    @SerialEntry
    public boolean addRank = true;

    @SerialEntry
    public String rank = "§d[MEOW++]";

    @SerialEntry
    public boolean addPrefix = true;

    @SerialEntry
    public String prefix = "§e[BRIDGE]";

    @SerialEntry
    public Color playerNameColor = Color.CYAN;

    @SerialEntry
    public boolean autoWelcome = true;

    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(HANDLER, (((defaults, config, builder) -> builder
            .title(Text.literal("Guild Bridge Config"))
            .category(ConfigCategory.createBuilder()
                .name(Text.literal("General"))

                // bot IGN
                .option(Option.<String>createBuilder()
                    .name(Text.literal("Bot IGN"))
                    .binding("IsleofDucks", () -> config.botIGN, newVal -> config.botIGN = newVal)
                    .description(OptionDescription.of(Text.literal("")))
                    .controller(StringControllerBuilder::create)
                    .build())

                // Compact Guild Message
                .option(Option.<Boolean>createBuilder()
                    .name(Text.literal("Compact Guild Message"))
                    .binding(false, () -> config.compactGuildMessage, newVal -> config.compactGuildMessage = newVal)
                    .description(OptionDescription.of(Text.literal("Changes \"Guild >\" to \"G>\"")))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                        .coloured(true))
                    .build())

                // Add Rank
                .option(Option.<Boolean>createBuilder()
                    .name(Text.literal("Add Rank"))
                    .binding(true, () -> config.addRank, newVal -> config.addRank = newVal)
                    .description(OptionDescription.of(Text.literal("Adds Rank to bridge's messages")))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                        .coloured(true))
                    .build())

                // Rank
                .option(Option.<String>createBuilder()
                    .name(Text.literal("Rank"))
                    .binding("§d[MEOW++]", () -> config.rank, newVal -> config.rank = newVal)
                    .description(OptionDescription.of(Text.literal("")))
                    .controller(StringControllerBuilder::create)
                    .build())

                // Add Prefix
                .option(Option.<Boolean>createBuilder()
                    .name(Text.literal("Add Prefix"))
                    .binding(true, () -> config.addPrefix, newVal -> config.addPrefix = newVal)
                    .description(OptionDescription.of(Text.literal("Adds Prefix to bridge's messages")))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                        .coloured(true))
                    .build())

                // Prefix
                .option(Option.<String>createBuilder()
                    .name(Text.literal("Prefix"))
                    .binding("§e[BRIDGE]", () -> config.prefix, newVal -> config.prefix = newVal)
                    .description(OptionDescription.of(Text.literal("")))
                    .controller(StringControllerBuilder::create)
                    .build())

                // Player Name Color
                .option(Option.<Color>createBuilder()
                    .name(Text.literal("Player Name Color"))
                    .binding(Color.CYAN, () -> config.playerNameColor, newVal -> config.playerNameColor = newVal)
                    .description(OptionDescription.of(Text.literal("")))
                    .controller(ColorControllerBuilder::create)
                    .build())

                // Compact Guild Message
                .option(Option.<Boolean>createBuilder()
                    .name(Text.literal("Auto Welcome Ducksicle"))
                    .binding(false, () -> config.autoWelcome, newVal -> config.autoWelcome = newVal)
                    .description(OptionDescription.of(Text.literal("")))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                        .coloured(true))
                    .build())

                .build()
            )
        ))).generateScreen(parent);
    }
}
