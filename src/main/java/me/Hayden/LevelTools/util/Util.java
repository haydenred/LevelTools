package me.Hayden.LevelTools.util;

import com.google.common.base.Strings;
import me.Hayden.LevelTools.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");

    public static String translateHexCodes (String textToTranslate) {

        Matcher matcher = HEX_PATTERN.matcher(textToTranslate);
        StringBuffer buffer = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());

    }

    public static String getProgressBar(int current, int max) {
        Configuration config = Main.getInstance().getConfig();
        char symbol = config.getString("settings.progressbar.filler").charAt(0);
        int totalBars = config.getInt("settings.progressbar.bars");
        ChatColor completedColor = ChatColor.getByChar(config.getString("settings.progressbar.complete_color").charAt(0));
        ChatColor notCompletedColor = ChatColor.getByChar(config.getString("settings.progressbar.incomplete_color").charAt(0));
        if (max == 0) {
            return Strings.repeat("" + completedColor + symbol, totalBars);
        }
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }



}
