package com.github.xupie.guildbridge.features;

import com.github.xupie.guildbridge.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuildBridge {

    //§2Guild > §b[MVP§c+§b] IsleofDucks §e[STAFF]§f: ✧ Xupie: b
    //§2Guild > §a[VIP§6+§a] Xupie §e[EMBER]§f: b
    //§2Guild > §bDOGPLAYZ §ejoined.
    //§2Guild > §6Ducksicle §eleft.
    public static final Pattern GUILD_BRIDGE_MESSAGE_PATTERN = Pattern.compile("^§2Guild > .*?\\w+ §.\\[\\w+]§f: ✧ ✧?(\\w+).*?: (.*)$");
    public static final Pattern GUILD_MESSAGE_PATTERN = Pattern.compile("^§2Guild > (.*?(?: |§f)(\\w+)) §.\\[\\w+]§f: (?!✧)(.*)$");
    public static final Pattern GUILD_JOIN_LEFT = Pattern.compile("^§2Guild > §.\\w+ §.(?:joined|left)\\.$");
    public static final Set<String> REPLACED_MESSAGES = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static void register(Text textMessage, boolean signedMessage) {
        String msg = textMessage.getString();

        Matcher bridgeMatcher = GUILD_BRIDGE_MESSAGE_PATTERN.matcher(msg);
        if (bridgeMatcher.matches()) {
            String player = bridgeMatcher.group(1);
            String message = bridgeMatcher.group(2);

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                String compact = ModConfig.HANDLER.instance().compactGuildMessage ? "§2B>" : "§2Bridge >";
                String rank = ModConfig.HANDLER.instance().addRank ? " " + ModConfig.HANDLER.instance().rank : "";
                String prefix = ModConfig.HANDLER.instance().addPrefix ? " " + ModConfig.HANDLER.instance().prefix : "";

                Color playerNameColor = ModConfig.HANDLER.instance().playerNameColor;
                TextColor textColor = TextColor.fromRgb(playerNameColor.getRGB());

                MutableText newMessage = Text.literal(compact + rank + " ")
                    .append(Text.literal(player + prefix)
                        .styled(style -> style
                            .withColor(textColor)
                            .withUnderline(false)
                        ))
                    .append("§f: " + message)
                    .styled(style -> style
                        .withClickEvent(new ClickEvent.RunCommand("/pv " + player))
                        .withHoverEvent(new HoverEvent.ShowText(Text.literal("§eClick here to run /pv " + player)))
                        .withUnderline(true)
                        .withColor(Formatting.AQUA)
                    );
                REPLACED_MESSAGES.add(msg);
                client.inGameHud.getChatHud().addMessage(newMessage);
            }
        }

        Matcher guildMatcher = GUILD_MESSAGE_PATTERN.matcher(msg);
        if (guildMatcher.matches()) {
            String playerWithPrefixes = guildMatcher.group(1);
            String player = guildMatcher.group(2);
            String message = guildMatcher.group(3);

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                String compact = ModConfig.HANDLER.instance().compactGuildMessage ? "§2G>" : "§2Guild >";
                MutableText newMessage = Text.literal(compact + " §b" + playerWithPrefixes + "§f: " + message)
                    .styled(style -> style
                        .withClickEvent(new ClickEvent.RunCommand("/pv " + player))
                        .withHoverEvent(new HoverEvent.ShowText(Text.literal("§eClick here to run /pv " + player)))
                        .withUnderline(true)
                        .withColor(Formatting.AQUA)
                    );
                REPLACED_MESSAGES.add(msg);
                client.inGameHud.getChatHud().addMessage(newMessage);
            }
        }

        Matcher leaveMatcher = GUILD_JOIN_LEFT.matcher(msg);
        if (leaveMatcher.matches()) {
            if (!ModConfig.HANDLER.instance().compactGuildMessage) return;
            REPLACED_MESSAGES.add(msg);

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                String newMessage = msg.replace("§2Guild >", "§2G>");
                client.inGameHud.getChatHud().addMessage(Text.of(newMessage));
            }
        }
    }
}
