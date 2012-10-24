import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class SpriteSheet{
    public String path;
    public int width;
    public int height;

    public int[] pixels;

    public SpriteSheet(String imgPath){
        BufferedImage image = null;
        
        try{
            File f = new File(imgPath);
            image = ImageIO.read(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        if(image == null){
            return;
        }

        this.path = path;
        this.width = image.getWidth();
        this.height = image.getHeight();

        pixels = image.getRGB(0, 0, width, height, null, 0, width);

        for(int i = 0; i < pixels.length; i++){
            pixels[i] = (pixels[i] & 0xff) / 64;
        }
    }
}
