package misc;

import com.runemate.game.api.hybrid.Environment;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ScreenshotSaver implements Consumer<Image> {

    private final String name;

    public ScreenshotSaver(String name) {
        this.name = name;
    }

    @Override
    public void accept(Image image) {
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();
        try {
            System.out.println("Saving to: " + Environment.getSharedStorageDirectory().toString());
            File img = new File(Environment.getSharedStorageDirectory().toString(),
                    File.separator + Environment.getAccountAlias() + File.separator + "screenshots" + File.separator + name + ".png");
            if (!img.exists() && !img.mkdirs() && !img.createNewFile()) {
                return;
            }

            ImageIO.write(bi, "png", img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//private final Queue<ScreenshotSaver> screenshotQueue = new LinkedList<>();
//Interfaces.getLoaded(i -> i.getText().contains("Click here to continue"));
//    private void takeScreenshot() {
//        String name = me.getName() + "Cooking" + String.valueOf(Skills.getCurrentLevel(Skill.COOKING));
//        screenshotQueue.add(new ScreenshotSaver(name));
//    }