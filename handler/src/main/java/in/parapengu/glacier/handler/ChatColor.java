package in.parapengu.glacier.handler;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Attribute;

import java.util.regex.Pattern;

public enum ChatColor {

    BLACK('0', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString()),
    DARK_BLUE('1', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString()),
    DARK_GREEN('2', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString()),
    DARK_AQUA('3', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString()),
    DARK_RED('4', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString()),
    DARK_PURPLE('5', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString()),
    GOLD('6', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString()),
    GRAY('7', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString()),
    DARK_GRAY('8', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString()),
    BLUE('9', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString()),
    GREEN('a', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString()),
    AQUA('b', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString()),
    RED('c', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString()),
    LIGHT_PURPLE('d', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString()),
    YELLOW('e', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString()),
    WHITE('f', Ansi.ansi().a(Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString()),
    MAGIC('k', "obfuscated", Ansi.ansi().a(Attribute.BLINK_SLOW).toString()),
    BOLD('l', Ansi.ansi().bold().toString()),
    STRIKETHROUGH('m', Ansi.ansi().a(Attribute.STRIKETHROUGH_ON).toString()),
    UNDERLINE('n', Ansi.ansi().a(Attribute.UNDERLINE).toString()),
    ITALIC('o', Ansi.ansi().a(Attribute.ITALIC).toString()),
    RESET('r', Ansi.ansi().a(Attribute.RESET).toString());

    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");

    private char code;
    private String name;
    private String console;
    private String string;

    ChatColor(char code, String string) {
        this.code = code;
        this.console = string;
        this.name = name().toLowerCase();
        this.string = new String(new char[]{COLOR_CHAR, code});
    }

    ChatColor(char code, String name, String string) {
        this.code = code;
        this.name = name;
        this.console = string;
        this.string = new String(new char[]{COLOR_CHAR, code});
    }

    public char getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String toConsole() {
        return console;
    }

    @Override
    public String toString() {
        return string;
    }

    public static ChatColor getByChar(char code) {
        for (ChatColor color : values()) {
            if (color.code == code) {
                return color;
            }
        }

        return null;
    }

    public static ChatColor getByChar(String code) {
        if (code == null) {
            throw new NullPointerException("Code cannot be null");
        }

        if (code.length() <= 0) {
            throw new IllegalArgumentException("Code must have at least one char");
        }

        return getByChar(code.charAt(0));
    }

    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String translate(char ch, String string) {
        char[] b = string.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == ch && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

    public static String getConsoleString(String input) {
        for (ChatColor color : values()) {
            input = input.replace(color.toString(), color.toConsole());
        }

        return input;
    }

    public static ChatColor[] format() {
        return new ChatColor[]{MAGIC, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC};
    }

}
