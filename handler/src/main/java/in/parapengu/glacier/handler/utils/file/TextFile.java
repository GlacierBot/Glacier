package in.parapengu.glacier.handler.utils.file;

import in.parapengu.glacier.handler.logging.Logging;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TextFile {

    private File file;
    private boolean append;
    private boolean compressed;
    private List<String> lines;

    public TextFile(File file) {
        this(file, false);
    }

    public TextFile(File file, boolean append) {
        this(file, append, false);
    }

    public TextFile(File file, boolean append, boolean compressed) {
        this.file = file;
        this.append = append;
        this.compressed = compressed;
        load();
    }

    public File getFile() {
        return file;
    }

    public boolean isAppend() {
        return append;
    }

    public List<String> getLines() {
        return lines;
    }

    public void addLine(String line) {
        String[] split = line.contains("\n") ? line.split("\n") : new String[]{line};
        for (String str : split) {
            if (!str.equals("")) {
                lines.add(str);
            }
        }
    }

    public void save() {
        file.mkdirs();
        file.delete();

        try {
            file.createNewFile();
            OutputStream output = new FileOutputStream(file, false);
            if (compressed) {
                output = new GZIPOutputStream(output);
            }

            PrintWriter printer = new PrintWriter(output);
            for (String line : lines) {
                printer.printf("%s" + "%n", line);
            }

            printer.close();
            close(output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
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

                lines = IOUtils.readLines(input);
                close(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            lines = new ArrayList<>();
        }
    }

    public String asString() {
        String string = "";
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (i > 0) {
                string = string + "\n";
            }

            string = string + line;
        }

        return string;
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
