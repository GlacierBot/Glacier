package in.parapengu.glacier.handler.utils;

import in.parapengu.glacier.handler.ChatColor;
import in.parapengu.glacier.handler.logging.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatFormatter {

    public static String parse(String string) {
        try {
            JSONObject json = new JSONObject(string);
            return parse(json);
        } catch (JSONException ex) {
            return string.substring(1, string.length() - 1);
        }
    }

    public static String parse(JSONObject json) {
        StringBuilder message = new StringBuilder();
        for (ChatColor color : ChatColor.format()) {
            if (json.has(color.getName()) && json.getBoolean(color.getName())) {
                message.append(color);
            }
        }

        if (json.has("color")) {
            message.append(ChatColor.valueOf(json.getString("color").toUpperCase()));
        }

        if (json.has("translate")) {
            String text = json.getString("translate");
            if (json.has("with")) {
                JSONArray array = json.getJSONArray("with");
                String[] translationValues = new String[array.length()];
                for (int i = 0; i < translationValues.length; i++) {
                    Object object = array.get(i);

                    String value;
                    if (object instanceof JSONObject) {
                        value = parse((JSONObject) object);
                    } else {
                        value = (String) object;
                    }

                    translationValues[i] = value;
                }

                text = String.format(text, translationValues);
            }
            message.append(text);
        } else if (json.has("text")) {
            message.append(json.get("text"));
        }

        if (json.has("extra")) {
            JSONArray extra = json.getJSONArray("extra");
            for (int i = 0; i < extra.length(); i++) {
                Object object = extra.get(i);
                if (object instanceof JSONObject) {
                    message.append(parse((JSONObject) object));
                } else {
                    message.append(object);
                }
            }
        }

        return message.toString();
    }

    public static JSONObject serialize(String string) {
        String splitter = ChatColor.COLOR_CHAR + "";
        JSONObject object = new JSONObject();

        List<ColorString> message = new ArrayList<>();
        if (string.contains(splitter)) {
            String[] split = string.split(splitter);
            for (String str : split) {
                if (str.length() < 1) {
                    continue;
                }

                char ch = str.charAt(0);
                ChatColor color = ChatColor.getByChar(ch);
                str = str.substring(1);
                Logging.getLogger().debug("Read '" + ch + "': \"" + str + "\"");
                message.add(new ColorString(color, str));
            }

            Logging.getLogger().debug(message);
        } else {
            message.add(new ColorString(null, string));
        }

        JSONObject current = object;
        JSONArray array = null;
        List<ChatColor> format = asList(ChatColor.format());

        List<ChatColor> colors = new ArrayList<>();

        int pos = 1;
        for (ColorString entry : message) {
            ChatColor color = entry.getColor();
            String text = entry.getString();

            if (!format.contains(color)) {
                colors.clear();
            }

            colors.add(color);
            for (ChatColor col : colors) {
                if (col != null) {
                    if (format.contains(col)) {
                        if (!current.has(col.getName())) {
                            current.put(col.getName(), true);
                        }
                    } else {
                        current.put("color", col.getName().toLowerCase());
                    }
                }
            }

            if ((text != null && text.length() > 0) || !format.contains(color)) {
                text = text != null ? text : "";
                current.put("text", text);

                if (message.size() > pos) {
                    if (array == null) {
                        array = new JSONArray();
                        current.put("extra", array);
                    }

                    JSONObject extra = new JSONObject();
                    array.put(extra);
                    current = extra;
                }
            }
            pos++;
        }

        return object;
    }

    private static <T> List<T> asList(T[] array) {
        List<T> list = new ArrayList<>();
        for (T value : array) {
            list.add(value);
        }

        return list;
    }

    public static class ColorString {

        private ChatColor color;
        private String string;

        public ColorString(ChatColor color, String string) {
            this.color = color;
            this.string = string;
        }

        public ChatColor getColor() {
            return color;
        }

        public String getString() {
            return string;
        }

        @Override
        public String toString() {
            return color.name() + ": " + string;
        }

    }

}