package com.github.xupie.guildbridge.features;

import com.github.xupie.guildbridge.config.ModConfig;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.awt.*;
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

    public static Text register(Text textMessage, boolean b) {
        String msg = textMessage.getString();

        Matcher bridgeMatcher = GUILD_BRIDGE_MESSAGE_PATTERN.matcher(msg);
        if (bridgeMatcher.matches()) {
            String player = bridgeMatcher.group(1);
            String message = bridgeMatcher.group(2);

            String compact = ModConfig.HANDLER.instance().compactGuildMessage ? "§2B>" : "§2Bridge >";
            String rank = ModConfig.HANDLER.instance().addRank ? " " + ModConfig.HANDLER.instance().rank : "";
            String prefix = ModConfig.HANDLER.instance().addPrefix ? " " + ModConfig.HANDLER.instance().prefix : "";

            Color playerNameColor = ModConfig.HANDLER.instance().playerNameColor;
            TextColor textColor = TextColor.fromRgb(playerNameColor.getRGB());

            Text newMessage = Text.literal(compact + rank + " ")
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
            return newMessage;
        }

        Matcher guildMessage = GUILD_MESSAGE_PATTERN.matcher(msg);
        if (guildMessage.matches()) {
            if (ModConfig.HANDLER.instance().compactGuildMessage) {
                msg = msg.replace("§2Guild >", "§2G>");
            }

            String player = guildMessage.group(2);
            Text newMessage = Text.literal(msg)
                .styled(style -> style
                    .withClickEvent(new ClickEvent.RunCommand("/pv " + player))
                    .withHoverEvent(new HoverEvent.ShowText(Text.literal("§eClick here to run /pv " + player)))
                    .withUnderline(true)
                    .withColor(Formatting.AQUA)
                );
            return newMessage;
        }

        Matcher joinMessage = GUILD_JOIN_LEFT.matcher(msg);
        if (joinMessage.matches()) {
            if (ModConfig.HANDLER.instance().compactGuildMessage) {
                Text newMessage = Text.literal(msg.replace("§2Guild >", "§2G>"));
                return newMessage;
            }
        }

        return textMessage;
    }
}
