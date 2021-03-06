package LightsOut;

import java.awt.Point;

import hsa2.GraphicsConsole;
import java.util.ArrayList;

/** 
 * A mesh2D of rects
 */
public class Grid {
    int size;
    int rSize;

    SimpleRect[][] mesh;

    /**
     * Create a Grid with a rect size, width, and height
     * 
     * @param size Width of the contained SimpleRects
     * @param w Width of the window in px
     * @param h Height of the window in px
     */
    public Grid(int size, int w, int h) {
        this.size = size;

        this.mesh = new SimpleRect[size][size];

        // calc rect size based on grid and window
        rSize = (int) (w / size);

        // Fill the mesh with objects
        int i = 0;
        for (SimpleRect[] row : mesh) {
            int j = 0;
            for (SimpleRect square : row) {
                // Find starting coords
                int x = j * rSize;
                int y = i * rSize;

                // Construct the SimpleRect
                Point loc = new Point(x, y);
                mesh[i][j] = new SimpleRect(loc, rSize, rSize);
                j++;
            }
            i++;
        }

        // Set the centre point
        mesh[(int)mesh.length / 2][(int)mesh.length / 2].set(true);

        System.out.println("[Grid] A grid has been successfully built with a size of: "+ w +" x "+ h +". With a spacing of: "+ size);
    }
    
    /**
     * Calls through to all SimpleRects, and shows them on gc
     * 
     * @param gc The GraphicsConsole to show the SimpleRacts on
     */
    public void display(GraphicsConsole gc) {
        // Iterate through mesh and draw
        synchronized (gc) {
            gc.clear();
            for (SimpleRect[] row : mesh) {
                for (SimpleRect square : row) {

                    // Draw the SimpleRect
                    square.draw(gc);
                }
            }
        }
    }

    /**
     * Is the board entirely true?
     */
    public boolean isFull() {
        for (SimpleRect[] row : mesh) {
            for (SimpleRect square : row) {
                if (!square.get()) {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * Convert a mouse location into an array position, then call flipCross with the new location
     * 
     * @param loc Mouse Point
     */
    public void addressPoints(Point loc) {
        // Get X and Y
        int x = (int) loc.getX();
        int y = (int) loc.getY();

        // Convert to col and row
        int col = x / rSize;
        int row = y / rSize;

        // Call drawing method
        flipCross(col, row);
    }
    
    /**
     * Flips all valid SimpleRacts around a location
     * 
     * @param x Base grid X to address
     * @param y Base grid Y to address
     */
    private void flipCross(int x, int y) {
        // List of grids to flip
        ArrayList<Integer> xsections = new ArrayList<Integer>();
        ArrayList<Integer> ysections = new ArrayList<Integer>();

        // Add current point by default
        xsections.add(x);
        ysections.add(y);

        // Check surrondings
        if (x != 0) {
            xsections.add(x - 1);
            ysections.add(y);
        }
        
        if (x != mesh.length - 1) {
            xsections.add(x + 1);
            ysections.add(y);
        }

        if (y != 0) {
            xsections.add(x);
            ysections.add(y - 1);
        }
        
        if (y != mesh.length - 1) {
            xsections.add(x);
            ysections.add(y + 1);
        }

        // Iterate through valid sections and flip
        int i = 0;
        for (int vx : xsections) {
            mesh[ysections.get(i)][vx].set(!mesh[ysections.get(i)][vx].get());

            i++;
        }
    }
}