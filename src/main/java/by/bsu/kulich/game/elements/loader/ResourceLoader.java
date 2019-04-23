package main.java.by.bsu.kulich.game.elements.loader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ResourceLoader {


    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);

        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public static URL getMusicURL(final String path) throws IOException {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        return url;
    }

    public static InputStream getMusicInputStream(final String path) throws IOException {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}
