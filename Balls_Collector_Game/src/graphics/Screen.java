package graphics;

import java.util.Random;

/** keeps track of pixels on the screen */
public class Screen extends Render {
    private Render test;
    
    /** constructor for Screen class 
      * @param width: number of pixels horizontally on the window
      * @param height: number of pixels vertically on the window
      */
    public Screen(int width, int height) {
        super(width, height);
        
        Random random = new Random();
        test = new Render(250, 250);
        
        for (int i = 0; i < 250 * 250; i++) {
            test.pixels[i] = random.nextInt();
        }
    }

    /** renders the pixels */
    public void render() {
        /* refreshes the pixels where the rendering is no longer happening;
         * gets rid of trail
         */
        for (int i = 0; i < width * height; i++) {
            pixels[i] = 0;
        }
        int animation = (int) (Math.sin(System.currentTimeMillis() % 1000.0 / 1000 * Math.PI * .25) * 500);
        draw(test, 100, 100 + animation);
    }
}
