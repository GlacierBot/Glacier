package in.parapengu.glacier.handler.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

public class OtherUtils {

    public static <T> T getRandom(T... objects) {
        return objects[getRandom(0, objects.length - 1)];
    }

    public static <T> T getRandom(List<T> objects) {
        return objects.get(getRandom(0, objects.size() - 1));
    }

    public static <T> List<T> getRandom(List<T> objects, int size) {
        List<T> clone = newArrayList(objects);
        List<T> selected = new ArrayList<>();
        while (selected.size() < size && clone.size() > 0) {
            selected.add(clone.get(getRandom(0, clone.size() - 1)));
        }

        return selected;
    }

    public static int getRandom(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static double getRandom(double min, double max) {
        return new Random().nextInt((int) (max - min) + 1) + min;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> newArrayList(T... array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> newArrayList(Collection<T> collection) {
        List<T> list = new ArrayList<>();
        list.addAll(collection);
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> newArrayList(Collection<T> collection, T... array) {
        List<T> list = newArrayList(collection);
        Collections.addAll(list, array);
        return list;
    }

    public static <T> List<T> asList(T[] array) {
        List<T> list = new ArrayList<>();
        for (T value : array) {
            list.add(value);
        }

        return list;
    }

    public static List<Byte> asList(byte[] array) {
        List<Byte> list = new ArrayList<>();
        for (byte value : array) {
            list.add(value);
        }

        return list;
    }

    public static <T> ArrayList<T> reverse(List<T> list) {
        ArrayList<T> reversed = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversed.add(list.get(i));
        }

        return reversed;
    }

    public static <T> T[] reverse(T[] array) {
        int i2 = 0;
        T[] reversed = create(array, array.length);
        for (int i = array.length - 1; i >= 0; i--, i2++) {
            reversed[i2] = array[i];
        }

        return reversed;
    }

    public static <K, V> HashMap<K, V> reverse(Map<K, V> map) {
        HashMap<K, V> reversed = new HashMap<>();
        List<Entry<K, V>> entries = newArrayList(map.entrySet());
        for (int i = map.size() - 1; i >= 0; i--) {
            Entry<K, V> entry = entries.get(i);
            reversed.put(entry.getKey(), entry.getValue());
        }

        return reversed;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] create(Class<T> cls, int size) {
        return (T[]) Array.newInstance(cls, size);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] create(T[] array, int size) {
        return (T[]) create(array.getClass().getComponentType(), size);
    }

    public static <T> String join(T[] array, String join) {
        return join(array, join, array.length);
    }

    public static <T> String join(T[] array, String join, int limit) {
        String string = "";
        for (int i = 0; i < array.length && i < limit; i++) {
            String line = array[i].toString();
            if (i > 0) {
                string = string + join;
            }

            string = string + line;
        }

        return string;
    }

    public static <T> String join(List<T> list, String join) {
        return join(list, join, list.size());
    }

    public static <T> String join(List<T> list, String join, int limit) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size() && i < limit; i++) {
            builder.append(list.get(i).toString());

            int pos = i + 1;
            boolean last = pos < list.size() && pos < limit;
            if (!last) {
                builder.append(join);
            }
        }

        return builder.toString();
    }

    public static UUID uuidFromString(String s) {
        if (s.contains("-")) {
            return UUID.fromString(s);
        } else {
            return UUID.fromString(s.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
        }
    }

    public static String listToEnglishCompound(Collection<String> list) {
        return listToEnglishCompound(list, "", "");
    }

    public static String listToEnglishCompound(Collection<?> list, String prefix, String suffix) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for(Object str : list) {
            if(i != 0) {
                if(i == list.size() - 1) {
                    builder.append(" and ");
                } else {
                    builder.append(", ");
                }
            }

            builder.append(prefix).append(str).append(suffix);
            i++;
        }

        return builder.toString();
    }

}
