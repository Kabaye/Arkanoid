package by.bsu.kulich.game.elements.loader;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class ResourceLoader {
    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public static InputStream getMusicStream(final String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

    }
}
