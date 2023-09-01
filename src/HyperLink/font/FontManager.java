package HyperLink.font;

import HyperLink.HyperLink;

import java.awt.*;
import java.io.InputStream;

public class FontManager {
    public CFontRenderer Regular18;

    public void loadFonts() {
        Regular18 = new CFontRenderer(createFont("Baloo.ttf", 18), true, true, false, 0, 0);
    }


    private Font createFont(String name, int size) {
        Font font;
        try {
            InputStream is = HyperLink.class.getResourceAsStream("/tools/fonts/" + name);
            font = Font.createFont(0, is);
            font = font.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            System.out.println("Error loading font");
            font = new Font("default", Font.PLAIN, size);
        }
        return font;
    }
}
