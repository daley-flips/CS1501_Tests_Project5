// javac cs1501_p5\*.java; if ($?) { java -cp . cs1501_p5.init_tests }
package cs1501_p5;

import java.util.Map;
import java.util.HashMap;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;


public class init_tests{
    public static void main(String args[]){

        String path = "bmp/test.bmp";

        //String path = "YOUR_PATH_TO/test.bmp";

        System.out.println("\nA test file for project 5\n");
        System.out.println("Good job Daley its running\n");

        test_SquaredEuclideanMetric();
        test_CircularHueMetric();
        test_Bucket(path);

        System.out.println();
    }









//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
    private static void test_SquaredEuclideanMetric(){
        // d^2(p, q) = (1-4)^2 + (2-6)^2 + (3-3)^2 = 9 + 16 + 0 = 25
        // Output: 25

        System.out.println("\n---------- Testing SquaredEuclideanMetric colorDistance... ----------");
        SquaredEuclideanMetric sem = new SquaredEuclideanMetric();
        Pixel p1 = new Pixel(1,2,3);
        Pixel p2 = new Pixel(4,6,3);

        double expectedDistance = 25;
        double actualDistance = sem.colorDistance(p1,p2);
        System.out.println("Basic test:");
        System.out.println("Expected Distance: " + expectedDistance + " | Actual Distance: " + actualDistance + " | " + (actualDistance == expectedDistance ? "SUCCESS!!" : "FAIL********* I'm sorry it didnt work but keep trying you can do it! :)\n"));
        
        System.out.println("Same test:");
        expectedDistance = 0;
        actualDistance = sem.colorDistance(p1,p1);
        System.out.println("Expected Distance: " + expectedDistance + " | Actual Distance: " + actualDistance + " | " + (actualDistance == expectedDistance ? "SUCCESS!!" : "FAIL********* I'm sorry it didnt work but keep trying you can do it! :)\n"));
        
        System.out.println("Another test:");
        Pixel p3 = new Pixel(1,1,1);
        Pixel p4 = new Pixel(4,5,6);
        expectedDistance = 50;
        actualDistance = sem.colorDistance(p3,p4);
        System.out.println("Expected Distance: " + expectedDistance + " | Actual Distance: " + actualDistance + " | " + (actualDistance == expectedDistance ? "SUCCESS!!" : "FAIL********* I'm sorry it didnt work but keep trying you can do it! :)\n"));
        System.out.println();

    }
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
    private static void test_CircularHueMetric(){

        System.out.println("\n---------- Testing CircularHueMetriccolorDistance... ----------");

        System.out.println("Test1: ");
        // Example 1: Hue 1 = 50, Hue 2 = 90, Shortest Distance = 40
        // Direct distance: |50 - 90| = 40
        // Wrap-around distance: 360 - 40 = 320
        Pixel p1 = new Pixel(255,212,0);
        //System.out.println(p1.getHue()); // prints 50
        Pixel p2 = new Pixel(128,255,0);
        //System.out.println(p2.getHue()); // prints 90
        CircularHueMetric chm = new CircularHueMetric();
        double expectedDistance = 40;
        double actualDistance = chm.colorDistance(p1,p2);
        System.out.println("Expected Distance: " + expectedDistance + " | Actual Distance: " + actualDistance + " | " + (actualDistance == expectedDistance ? "SUCCESS!!" : "FAIL********* I'm sorry it didnt work but keep trying you can do it! :)\n"));

        System.out.println("Test2: ");
        // Example 2: Hue 1 = 20, Hue 2 = 340, Shortest Distance = 40
        // Direct distance: |20 - 340| = 320
        // Wrap-around distance: 360 - 320 = 40
        p1 = new Pixel(200, 80, 20);
        //System.out.println(p1.getHue()); // prints 20
        p2 = new Pixel(200, 20, 80);
        //System.out.println(p2.getHue()); // prints 340
        expectedDistance = 40;
        actualDistance = chm.colorDistance(p1,p2);
        System.out.println("Expected Distance: " + expectedDistance + " | Actual Distance: " + actualDistance + " | " + (actualDistance == expectedDistance ? "SUCCESS!!" : "FAIL********* I'm sorry it didnt work but keep trying you can do it! :)\n"));

        System.out.println("Test3: ");
        // Example 3: Hue 1 = 0, Hue 2 = 359, Shortest Distance = 1
        // Direct distance: |0 - 359| = 359
        // Wrap-around distance: 360 - 359 = 1
        p1 = new Pixel(200, 20, 20);
        //System.out.println(p1.getHue()); // prints 0
        p2 = new Pixel(200, 20, 23)  ;
        //System.out.println(p2.getHue()); // prints 359
        expectedDistance = 1;
        actualDistance = chm.colorDistance(p1,p2);
        System.out.println("Expected Distance: " + expectedDistance + " | Actual Distance: " + actualDistance + " | " + (actualDistance == expectedDistance ? "SUCCESS!!" : "FAIL********* I'm sorry it didnt work but keep trying you can do it! :)\n"));
        
        
        System.out.println("Test4: ");
        // Example 4: Hue 1 = 20, Hue 2 = 200, Shortest Distance = 180
        // Direct distance: |20 - 200| = 180
        // Wrap-around distance: 360 - 180 = 180

        p1 = new Pixel(200, 80, 20);
        //System.out.println(p1.getHue()); // prints 20
        p2 = new Pixel(20, 140, 200);
        //System.out.println(p2.getHue()); // prints 200
        expectedDistance = 180;
        actualDistance = chm.colorDistance(p1,p2);
        System.out.println("Expected Distance: " + expectedDistance + " | Actual Distance: " + actualDistance + " | " + (actualDistance == expectedDistance ? "SUCCESS!!" : "FAIL********* I'm sorry it didnt work but keep trying you can do it! :)\n"));
        
    }
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------

private static void test_Bucket(String path) {
    System.out.println("\n---------- Testing Bucket Generate Color Palette... ----------");

    try {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        Pixel[][] pixelArray = Util.convertBitmapToPixelMatrix(image);

        BucketingMapGenerator bmg = new BucketingMapGenerator();
        int numColors = 512;
        Pixel[] actualPalette = bmg.generateColorPalette(pixelArray, numColors);

        Pixel[] expectedPalette = new Pixel[numColors];  
        int totalColors = (int) Math.pow(2, 24);
        int range = totalColors / numColors;

        for (int i = 0; i < numColors; i++) {
            int center = (range * i) + (range / 2);
            int red = (center >> 16) & 0xFF;
            int green = (center >> 8) & 0xFF;
            int blue = center & 0xFF;
            expectedPalette[i] = new Pixel(red, green, blue);
        }

        boolean testPassed = true;
        int not_too_many_prints = 0;
        for (int i = 0; i < numColors; i++) {
            if (actualPalette[i] == null) {
                System.out.println("Error: Actual pixel at index " + i + " is null.");
                testPassed = false;
            } else if (expectedPalette[i] == null) {
                System.out.println("Error: Expected pixel at index " + i + " is null.");
                testPassed = false;
            } else if (!actualPalette[i].equals(expectedPalette[i]) && not_too_many_prints <= 5) {
                testPassed = false;
                System.out.printf("FAIL: Mismatch at index %d: Expected RGB(%d, %d, %d) but got RGB(%d, %d, %d)\n",
                                  i,
                                  expectedPalette[i].getRed(), expectedPalette[i].getGreen(), expectedPalette[i].getBlue(),
                                  actualPalette[i].getRed(), actualPalette[i].getGreen(), actualPalette[i].getBlue());
                not_too_many_prints++;
            }
        }

        if (testPassed) {
            System.out.println("SUCCESS!!");
        } else {
            System.out.println("FAIL********* I'm sorry it didnt work but keep trying you can do it! :)");
        }



        //---------------------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------------------
        //we are gonna test the map
        System.out.println("\n---------- Testing Bucket Generate Color Map... ----------");
        Pixel[][] testPixelArray = {
            { new Pixel(10, 20, 30), new Pixel(40, 50, 60), new Pixel(70, 80, 90) },
            { new Pixel(110, 120, 130), new Pixel(140, 150, 160), new Pixel(170, 180, 190) }
        };
        
     
        int numTestColors = 256;  
        Pixel[] testPalette = bmg.generateColorPalette(testPixelArray, numTestColors);
        
       
        Map<Pixel, Pixel> actualMap = bmg.generateColorMap(testPixelArray, testPalette);

        Map<Pixel, Pixel> expectedMap = new HashMap<>();
        expectedMap.put(new Pixel(10, 20, 30), findClosestPaletteColor(new Pixel(10, 20, 30), testPalette));
        expectedMap.put(new Pixel(40, 50, 60), findClosestPaletteColor(new Pixel(40, 50, 60), testPalette));
        expectedMap.put(new Pixel(70, 80, 90), findClosestPaletteColor(new Pixel(70, 80, 90), testPalette));
        expectedMap.put(new Pixel(110, 120, 130), findClosestPaletteColor(new Pixel(110, 120, 130), testPalette));
        expectedMap.put(new Pixel(140, 150, 160), findClosestPaletteColor(new Pixel(140, 150, 160), testPalette));
        expectedMap.put(new Pixel(170, 180, 190), findClosestPaletteColor(new Pixel(170, 180, 190), testPalette));
        
     
        boolean mapTestPassed = true;
        for (Pixel key : expectedMap.keySet()) {
            Pixel expectedColor = expectedMap.get(key);
            Pixel actualColor = actualMap.get(key);
            if (!actualColor.equals(expectedColor)) {
                mapTestPassed = false;
                System.out.printf("FAIL: Color mismatch for pixel RGB(%d, %d, %d): Expected RGB(%d, %d, %d), but got RGB(%d, %d, %d)\n",
                                  key.getRed(), key.getGreen(), key.getBlue(),
                                  expectedColor.getRed(), expectedColor.getGreen(), expectedColor.getBlue(),
                                  actualColor.getRed(), actualColor.getGreen(), actualColor.getBlue());
            }
        }
        
        if (mapTestPassed) {
            System.out.println("SUCCESS!!");
        } else {
            System.out.println("FAIL********* I'm sorry it didnt work but keep trying you can do it! :)");
        }
        //---------------------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------------------

        

    } catch (IOException e) {
        System.out.println("Error reading image file: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
    }
}
private static Pixel findClosestPaletteColor(Pixel pixel, Pixel[] palette) {
    Pixel closestColor = palette[0];
    double minDistance = Double.MAX_VALUE;
    for (Pixel color : palette) {
        double distance = Math.sqrt(Math.pow(color.getRed() - pixel.getRed(), 2) +
                                    Math.pow(color.getGreen() - pixel.getGreen(), 2) +
                                    Math.pow(color.getBlue() - pixel.getBlue(), 2));
        if (distance < minDistance) {
            minDistance = distance;
            closestColor = color;
        }
    }
    return closestColor;
}


//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------











} // end class
        
