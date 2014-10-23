package in.parapengu.glacier.handler.utils.file;

import in.parapengu.glacier.handler.logging.Logging;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.zip.GZIPInputStream;

public class JSONFile {

    private File file;
    private boolean append;
    private JSONObject json;
    private boolean compressed;

    public JSONFile(File file) {
        this(file, true);
    }

    public JSONFile(File file, boolean append) {
        this(file, append, false);
    }

    public JSONFile(File file, boolean append, boolean compressed) {
        this(file, new JSONObject(), append, compressed);
    }

    public JSONFile(File file, JSONObject json) {
        this(file, json, false);
    }

    public JSONFile(File file, JSONObject json, boolean append) {
        this(file, json, append, false);
    }

    public JSONFile(File file, JSONObject json, boolean append, boolean compressed) {
        this.file = file;
        this.append = append;
        this.json = json;
        this.compressed = compressed;
        load();
    }

    public File getFile() {
        return file;
    }

    public JSONObject getJSON() {
        return json;
    }

    public void setJSON(JSONObject json) {
        if (json == null) {
            throw new NullPointerException("json");
        }

        this.json = json;
    }

    public void save() {
        save(4);
    }

    public void save(int indent) {
        String string = json.toString(indent);
        TextFile text = new TextFile(file, false, compressed);
        text.addLine(string);
        text.save();
    }

    public void load() {
        if (file.exists() && file.isDirectory()) {
            Logging.getLogger().warning("Could not load '" + file.getName() + "' because it was a directory");
            return;
        }

        if (append && file.exists()) {
            try {
                InputStream input = new FileInputStream(file);
                if (compressed) {
                    input = new GZIPInputStream(input);
                }

                json = new JSONObject(IOUtils.toString(input));
                close(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            json = new JSONObject();
        }
    }

    public String asString() {
        return json.toString();
    }

    public String asString(int indent) {
        return json.toString(indent);
    }

    public void close(OutputStream stream) throws IOException {
        if (stream == null) {
            return;
        }

        stream.close();

        if (stream instanceof FilterOutputStream) {
            FilterOutputStream filter = (FilterOutputStream) stream;

            try {
                Field field = FilterOutputStream.class.getDeclaredField("out");
                field.setAccessible(true);
                close((OutputStream) field.get(filter));
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                // impossible
            }
        }
    }

    public void close(InputStream stream) throws IOException {
        if (stream == null) {
            return;
        }

        stream.close();

        if (stream instanceof FilterInputStream) {
            FilterInputStream filter = (FilterInputStream) stream;

            try {
                Field field = FilterInputStream.class.getDeclaredField("in");
                field.setAccessible(true);
                close((InputStream) field.get(filter));
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                // impossible
            }
        }
    }

}
