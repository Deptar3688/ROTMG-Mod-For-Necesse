package rotmg.level.presets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;

public class PresetMap {
    Color[][] map;

    public PresetMap(String filePath){
        setMap(filePath);
    }

    public Color[][] getMap() {
        return map;
    }
    public void setMap(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
//            BufferedImage image = ImageIO.read(new File("resources/map/oryxcastlemap.png"));
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixelData = new int[width * height];
            Color[][] map = new Color[width][height];

            PixelGrabber getPixels = new PixelGrabber(image, 0, 0, width, height, pixelData, 0, width);
            getPixels.grabPixels();

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    map[i][j] = handlesinglepixel(i, j, pixelData[j * width + i]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public Color handlesinglepixel(int x, int y, int pixel) {
//        int alpha = (pixel >> 24) & 0xff;
        int red   = (pixel >> 16) & 0xff;
        int green = (pixel >>  8) & 0xff;
        int blue  = (pixel      ) & 0xff;
//
//        System.out.print("Red = ");
//        System.out.print(red);
//        System.out.print(" | Green = ");
//        System.out.print(green);
//        System.out.print(" | Blue = ");
//        System.out.println(blue);

        Color pixelColor = new Color(red, green, blue);
        return pixelColor;
    }
}