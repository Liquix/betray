import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Level{

	private byte[] tiles;
	public int width;
	public int height;

	public List<Entity> entities = new ArrayList<Entity>();

	private String imagePath;
	public BufferedImage image;

	public Level(String imagePath){

		if(imagePath != null){
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		}

		else{

			this.width = 64;
			this.height = 64;

			tiles = new byte[width * height];
			this.generateLevel();
		}
	}

	public void generateLevel(){
		for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
            	if(x * y % 10 < 7)
            		tiles[x + y * width] = Tile.GRASS.getId();
            	else
            		tiles[x + y * width] = Tile.STONE.getId();
            }
        }
	}

	public void tick(){
		for(Entity e : entities){
			e.tick();
		}

		for(Tile t : Tile.tiles){
			if(t == null)
				break;
			else
				t.tick();
		}
	}

	public void loadLevelFromFile(){
		try{
			File f = new File(imagePath);
            this.image = ImageIO.read(f);
            this.width = image.getWidth();
            this.height = image.getHeight();

            tiles = new byte[width * height];
            this.loadTiles();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	private void loadTiles(){
		int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);

		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tileCheck: for(Tile t : Tile.tiles){
					if(t != null && t.getLevelColor() == tileColors[x + y * width]){
						this.tiles[x + y * width] = t.getId();
						break tileCheck;
					}
				}
			}
		}
	}

	private void saveLevelToFile(){
		try{
			ImageIO.write(image, "png", new File(imagePath));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void alterTile(int x, int y, Tile newTile){
		this.tiles[x + y * width] = newTile.getId();
		image.setRGB(x, y, newTile.getLevelColor());
	}

	public void renderTiles(Screen screen, int xOffset, int yOffset){
		if(xOffset < 0)
			xOffset = 0;
		if(xOffset > ((width << 3) - screen.width))
			xOffset = ((width << 3) - screen.width);
		if(yOffset < 0)
			yOffset = 0;
		if(yOffset > ((height << 3) - screen.height))
			yOffset = ((width << 3) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				getTile(x, y).render(screen, this, (x << 3), (y <<3));
			}
		}
	}

	public void renderEntities(Screen screen){
		for(Entity e : entities){
			e.render(screen);
		}
	}

	public Tile getTile(int x, int y){
		if(0 > x || x >= width || 0 > y || y >= height)
			return Tile.VOID;

		return Tile.tiles[tiles[x + y * width]];
	}

	public void addEntity(Entity entity){
		this.entities.add(entity);
	}
}