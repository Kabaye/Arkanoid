package by.bsu.kulich.game.elements.loader;

import java.awt.*;
import java.net.URL;

public class ImageLoader {
    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}
