package by.bsu.kulich.game.elements.loader;

import java.awt.*;
import java.io.IOException;
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
}
