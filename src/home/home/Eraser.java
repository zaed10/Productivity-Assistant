package home;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

public class Eraser implements Strategy{
    /**
     * Draw the specified Object using the GraphicsContext gc, linewidth and colour.
     *
     * @param gc GraphicsContext
     * @param line Line Colour
     * @param fill Fill Colour
     * @param m MouseEvent
     */
    @Override
    public void drawObj(GraphicsContext gc, ColorPicker line, ColorPicker fill, MouseEvent m) {
        double lineWidth = gc.getLineWidth();
        gc.clearRect(m.getX() - lineWidth / 2, m.getY() - lineWidth / 2, lineWidth, lineWidth);
    }
}
