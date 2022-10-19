package levels;

import graphics.Sprite;
import java.util.Random;

/** creates a layout for the level */
public class Level {
    public Block[] blocks;
    public final int width, height;
    
    /** constructor for the Level class */
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        blocks = new Block[width * height];
        
        Random random = new Random();
        
        /* render blocks */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Block block = null;
                
                if (random.nextInt(8) == 0) { /* likelihood of rendering a wall */
                    block = new SolidBlock();
                } else {
                    block = new Block();
                    
                    if (random.nextInt(5) == 0) { /* places sprite on one out of every 5 open tiles */
                        block.addSprite(new Sprite(0, 0, 0));
                    }
                }
                
                blocks[x + y * width] = block;
            }
        }
    }
    
    /** creates a block */
    public Block create(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Block.solidWall;
        }
        
        return blocks[x + y * width];
    }
}