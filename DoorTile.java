import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class DoorTile extends BasicTile{

	public BufferedImage image;

	public DoorTile(int id, int x, int y, int tileColor, int levelColor, String imgPath){	// imgPath is level on other side of door
		super(id, x, y, tileColor, levelColor);

		try{
			ImageIO.write(image, "png", new File(imgPath));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}