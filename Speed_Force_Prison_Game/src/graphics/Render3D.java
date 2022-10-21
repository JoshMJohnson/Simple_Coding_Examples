package graphics;

import input.Controller;
import levels.Block;
import levels.Level;
import main.Game;

/** handles the pixel motion effects */
public class Render3D extends Render {
    public double[] zBuffer;
    public double[] zBufferWall;
    private double renderDistance = 5000;
    private double forwardMovement, rightMovement, cosine, sine, up, walking;
//    private double spriteSheetWidth = 8;
    public static int arenaBorderSize = 1000; /* size of map */
    
    /** constructor for the Render3D class */
    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
        zBufferWall = new double[width];
    }
        
    /** renders the floor and ceiling */
    public void arena(Game game) {
        /* make walls solid */
        for (int x = 0; x < width; x++) {
            zBufferWall[x] = 0;
        }
        
        /* floor and ceiling distances away from center */
        double floorPosition = 8;
        double ceilingPosition = 8;
        
        /* rotations */
        double rotation = game.controls.rotation;
        cosine = Math.cos(rotation);
        sine = Math.sin(rotation);
        
        /* movement */
        forwardMovement = game.controls.z;
        rightMovement =  game.controls.x;
        up = game.controls.y;
        walking = 0;
                       
        /* render effects */
        for (int y = 0; y < height; y++) {
            double ceiling = (double) (y - height / 2) / height;   
            double z = (floorPosition + up) / ceiling;
            
            if (Controller.walk) {
                walking = Math.sin(game.time / 6.0) * 0.4;
                z = (floorPosition + up + walking) / ceiling;
            }
            
            /* adjusts head bobbing amounts */
            if (Controller.crouchWalk && Controller.walk) {
                walking = Math.sin(game.time / 6.0) * 0.15;
                z = (floorPosition + up + walking) / ceiling;
            } else if (Controller.runForward && Controller.walk) {
                walking = Math.sin(game.time / 6.0) * 0.8;
                z = (floorPosition + up + walking) / ceiling;
            }

            /* ensure floor and ceiling are moving in same direction */
            if (ceiling < 0) {
                z = (ceilingPosition - up) / -ceiling;
                
                if (Controller.walk) {
                    z = (ceilingPosition - up - walking) / -ceiling;
                }
            }
                        
            for (int x = 0; x < width; x++) {
                double depth = (double) (x - width / 2) / height;
                depth *= z;
                double xx = depth * cosine + z * sine + rightMovement;
                double yy = z * cosine - depth * sine + forwardMovement;
                int xPix = (int) (xx + rightMovement);
                int yPix = (int) (yy + forwardMovement);
                zBuffer[x + y * width] = z;
                pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 50];
            }
        }
        
        /* create pillars; inner walls */
        Level level = game.level;        
        
        /* creates blocks */
        /* lower half of wall */
//        for (int xBlock = -arenaBorderSize; xBlock <= arenaBorderSize; xBlock++) {
//            for (int zBlock = -arenaBorderSize; zBlock <= arenaBorderSize; zBlock++) {
//                /* creates outer wall */
//                Block block = level.create(xBlock + 1, zBlock + 1);
//                Block eastSide = level.create(xBlock + 2, zBlock + 1);
//                Block southSide = level.create(xBlock + 1, zBlock + 2);
//               
//                /* creates pillars */
//                if (block.solid) {
//                    if (!eastSide.solid) {
//                        renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0);
//                    }
//
//                    if (!southSide.solid) {
//                        renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0);
//                    }
//                } else {
//                    if (eastSide.solid) {
//                        renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0);
//                    }
//
//                    if (southSide.solid) {
//                        renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0);
//                    }
//                }
//            }
//        }
//
//        /* upper half of wall */
//        double upperHalf = 0.5;
//        
//        for (int xBlock = -arenaBorderSize; xBlock <= arenaBorderSize; xBlock++) {
//            for (int zBlock = -arenaBorderSize; zBlock <= arenaBorderSize; zBlock++) {
//                /* creates outer wall */
//                Block block = level.create(xBlock + 1, zBlock + 1);
//                Block eastSide = level.create(xBlock + 2, zBlock + 1);
//                Block southSide = level.create(xBlock + 1, zBlock + 2);
//               
//                /* creates pillars */
//                if (block.solid) {
//                    if (!eastSide.solid) {
//                        renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, upperHalf);
//                    }
//                    
//                    if (!southSide.solid) {
//                        renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, upperHalf);
//                    }
//                } else {
//                    if (eastSide.solid) {
//                        renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, upperHalf);
//                    }
//
//                    if (southSide.solid) {
//                        renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, upperHalf);
//                    }                    
//                }
//            }
//        }
        
        /* sprites */
        for (int xBlock = -arenaBorderSize; xBlock <= arenaBorderSize; xBlock++) {
            for (int zBlock = -arenaBorderSize; zBlock <= arenaBorderSize; zBlock++) {
                Block block = level.create(xBlock, zBlock);
                
                /* sprite walking buffer */
                for (int s = 0; s < block.sprites.size(); s++) {
                    Sprite sprite = block.sprites.get(s);
                    renderSprite(xBlock + sprite.x, sprite.y, zBlock + sprite.z);
                }
            }
        }
    }
    
    /** rendering Sprites */
    public void renderSprite(double x, double y, double z) {
        /* has wall move with users head bobbing while moving */
        double upCorrect = -0.0625;
        double rightCorrect = 0.11;
        double forwardCorrect = 0.11;
        double walkCorrect = 0.0625;
        
        /* sprite calculations */
        double xCalculate = ((x / 2) - (rightMovement * rightCorrect)) * 2;
        double yCalculate = ((y / 2) - (up * upCorrect))+ (walking * walkCorrect) * 2;
        double zCalculate = ((z / 2) - (forwardMovement * forwardCorrect)) * 2;
        
        double rotationX = xCalculate * cosine - zCalculate * sine;
        double rotationY = yCalculate;
        double rotationZ = zCalculate * cosine + xCalculate * sine;
        
        /* center of block */
        double xCenter = width / 2;
        double yCenter = height / 2;

        /* rotation values */
        double xPixel = rotationX / rotationZ * height + xCenter;
        double yPixel = rotationY / rotationZ * height + yCenter;
        
        /* corner pins */
        double xPixelLeft = xPixel - 100 / rotationZ;
        double xPixelRight = xPixel + 100 / rotationZ;
        
        double yPixelLeft = yPixel - 200 / rotationZ;
        double yPixelRight = yPixel + 200 / rotationZ;
        
        /* casting to integers */
        int xPixelLeftInt = (int) xPixelLeft;
        int xPixelRightInt = (int) xPixelRight;
        int yPixelLeftInt = (int) yPixelLeft;
        int yPixelRightInt = (int) yPixelRight;
        
        /* clipping */
        if (xPixelLeftInt < 0) { /* x left side */
            xPixelLeftInt = 0;
        }
        
        if (xPixelRightInt > width) { /* x right side */
            xPixelRightInt = width;
        }
        
        if (yPixelLeftInt < 0) { /* y left side */
            yPixelLeftInt = 0;
        }
        
        if (yPixelRightInt > height) { /* y right side */
            yPixelRightInt = height;
        }
        
        rotationZ *= 2; /* sprite z positioning adjustment */ 
        
        /* render Sprites */
        for (int yPix = yPixelLeftInt; yPix < yPixelRightInt; yPix++) {
//            double pixelRotationY = (yPix - yPixelRight) / (yPixelLeft - yPixelRight);
//            int yTexture = (int) pixelRotationY * 8;
            
            for (int xPix = xPixelLeftInt; xPix < xPixelRightInt; xPix++) {
//                double pixelRotationX = (xPix - xPixelRight) / (xPixelLeft - xPixelRight);
//                int xTexture = (int) pixelRotationX * 8;

                if (zBuffer[xPix + yPix * width] > rotationZ) {
//                    pixels[xPix + yPix * width] = 0xFFEA00; /* texture of sprites */
                    pixels[xPix + yPix * width] = Texture.blur.pixels[(xPix & 7) + (yPix & 7) * 8]; /* texture of sprites */
                    zBuffer[xPix + yPix * width] = rotationZ;
                }
            }
        }
    }
    
    /** render the walls */
    public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) {
        /* has wall move with users head bobbing while moving */
        double upCorrect = 0.0625;
        double rightCorrect = 0.11;
        double forwardCorrect = 0.11;
        double walkCorrect = -0.0625;
        
        /* left side of the wall */
        double xCalculateLeft = ((xLeft / 2) - (rightMovement * rightCorrect)) * 2;
        double zCalculateLeft = ((zDistanceLeft / 2) - (forwardMovement * forwardCorrect)) * 2;
        double rotationLeftSideX = xCalculateLeft * cosine - zCalculateLeft * sine;
        double yCornerTopLeft = (-yHeight - (-up * upCorrect + (walking * walkCorrect))) * 2;
        double yCornerBottomLeft = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
        double rotationLeftSideZ = zCalculateLeft * cosine + xCalculateLeft * sine;
        
        /* right side of the wall */
        double xCalculateRight = ((xRight / 2) - (rightMovement * rightCorrect)) * 2;
        double zCalculateRight = ((zDistanceRight / 2) - (forwardMovement * forwardCorrect)) * 2;
        double rotationRightSideX = xCalculateRight * cosine - zCalculateRight * sine;
        double yCornerTopRight = (-yHeight - (-up * upCorrect + (walking * walkCorrect))) * 2;
        double yCornerBottomRight = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
        double rotationRightSideZ = zCalculateRight * cosine + xCalculateRight * sine;
        
        /* clipping */
        double texture3b = 0;
        double texture4b = 8;
        double clip = 0.5;
        
        if (rotationLeftSideZ < clip && rotationRightSideZ < clip) {
            return;
        }
        
        /* left side of the wall clipping */
        if (rotationLeftSideZ < clip) {
            double clip0 = (clip - rotationLeftSideZ) / (rotationRightSideZ - rotationLeftSideZ);
            rotationLeftSideZ = rotationLeftSideZ + (rotationRightSideZ - rotationLeftSideZ) * clip0;
            rotationLeftSideX = rotationLeftSideX + (rotationRightSideX - rotationLeftSideX) * clip0;
            texture3b = texture3b + (texture4b - texture3b) * clip0;
        }
        
        /* right side of the wall clipping */
        if (rotationRightSideZ < clip) {
            double clip0 = (clip - rotationLeftSideZ) / (rotationRightSideZ - rotationLeftSideZ);
            rotationRightSideZ = rotationLeftSideZ + (rotationRightSideZ - rotationLeftSideZ) * clip0;
            rotationRightSideX = rotationLeftSideX + (rotationRightSideX - rotationLeftSideX) * clip0;
            texture4b = texture3b + (texture4b - texture3b) * clip0;
        }
        
        /* pixel locations for walls */
        double xPixelLeft = (rotationLeftSideX / rotationLeftSideZ * height + width / 2);
        double xPixelRight = (rotationRightSideX / rotationRightSideZ * height + width / 2);
        
        /* skip rendering negative pixels */
        if (xPixelLeft >= xPixelRight) {
            return;
        }
        
        int xPixelLeftInt = (int) xPixelLeft;
        int xPixelRightInt = (int) xPixelRight;
        
        /* skip rendering pixels off screen */
        if (xPixelLeftInt < 0) {
            xPixelLeftInt = 0;
        } 
        
        if (xPixelRightInt > width) {
            xPixelRightInt = width;
        }
        
        /* four corner pins */
        double yPixelLeftTop = yCornerTopLeft / rotationLeftSideZ * height + height / 2.0;
        double yPixelLeftBottom = yCornerBottomLeft / rotationLeftSideZ * height + height / 2.0;
        double yPixelRightTop = yCornerTopRight / rotationRightSideZ * height + height / 2.0;
        double yPixelRightBottom = yCornerBottomRight / rotationRightSideZ * height + height / 2.0;

        /* texture preparation */
        double texture1 = 1 / rotationLeftSideZ;
        double texture2 = 1 / rotationRightSideZ;
        double texture3 = texture3b / rotationLeftSideZ;
        double texture4 = texture4b / rotationRightSideZ - texture3;
        
        /* render walls */
        for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);          
            double zWall = texture1 + (texture2 - texture1) * pixelRotation;
            
            if (zBufferWall[x] > zWall) {
                continue;
            }
            
            zBufferWall[x] = zWall;
                        
            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;          
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;
            
            int yPixelTopInt = (int) yPixelTop;
            int yPixelBottomInt = (int) yPixelBottom;
            
            /* skip rendering pixels off screen */
            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            
            if (yPixelBottomInt > height) {
                yPixelBottomInt = height;
            }
            
            int xTexture = (int) ((texture3 + texture4 * pixelRotation) / zWall);

            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                int yTexture = (int) (8 * pixelRotationY);    
                pixels[x + y * width] = Texture.wall.pixels[(xTexture & 7) + (yTexture & 7) * 8];
                zBuffer[x + y * width] = 1 / (texture1 + (texture2 - texture1) * pixelRotation) * 2; //handles render distance limiting
            }
        }
    }
        
    /** limits the distance away for rendering */
    public void renderDistanceLimiter() {
        for (int i = 0; i < width * height; i++) {
            int color = pixels[i];
            int brightness = (int) (renderDistance / zBuffer[i]);
            
            /* minimum brightness value */
            if (brightness < 0) {
                brightness = 0;
            }
            
            /* maximum brightness value */
            if (brightness > 255) {
                brightness = 255;
            }
            
            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = (color) & 0xff;
            
            r = r * brightness / 255;
            g = g * brightness / 255;
            b = b * brightness / 255;
            
            pixels[i] = r << 16 | g << 8 | b;
        }
    }
}