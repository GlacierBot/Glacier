package in.parapengu.glacier.handler.logging;

import in.parapengu.glacier.handler.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {

    public void write(SimpleDateFormat format, LogLevel level, String message) {
        String prefix = "";
        if (format != null || level != null) {
            String formatPre = format != null ? format.format(new Date()) : "";
            String levelPre = level != null ? level.getColor().toConsole() + level.name() + ChatColor.RESET.toConsole() : null;
            prefix = "[" + formatPre + (formatPre != null && levelPre != null ? " " : "") + levelPre + "] ";
        }

        String msg = ChatColor.getConsoleString(prefix + message);
        write(msg);
    }

    public void write(String string) {
        System.out.println(string + ChatColor.RESET.toConsole());
    }

}
