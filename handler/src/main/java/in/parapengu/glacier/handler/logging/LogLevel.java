package in.parapengu.glacier.handler.logging;

import in.parapengu.glacier.handler.ChatColor;

import java.util.logging.Level;

public enum LogLevel {

    DEBUG(ChatColor.AQUA, Level.INFO),
    INFO(ChatColor.WHITE, Level.INFO),
    WARNING(ChatColor.GOLD, Level.WARNING),
    SEVERE(ChatColor.RED, Level.SEVERE);

    private ChatColor color;
    private Level level;

    LogLevel(ChatColor color, Level level) {
        this.color = color;
        this.level = level;
    }

    public ChatColor getColor() {
        return color;
    }

    public Level getLevel() {
        return level;
    }

}
