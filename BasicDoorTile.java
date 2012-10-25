import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class BasicDoorTile extends BasicTile{

	public String imgPath;

	public BasicDoorTile(int id, int x, int y, int tileColor, int levelColor, String imgPath){	// imgPath is level on other side of door
		super(id, x, y, tileColor, levelColor, imgPath);

	}


}