package br.com.finalelite.weapons.util;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public class Text {

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> translate(String... text) {
        List<String> output = Arrays.asList(text);
        output.forEach(line -> ChatColor.translateAlternateColorCodes('&', line));
        return output;
    }
}
