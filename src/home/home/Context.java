package home;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

public class Context {
    private Strategy strat; //strategy

    /**
     * sets the correct strategy
     *
     * @param strategy Strategy
     */
    public Context(Strategy strategy){
        this.strat = strategy;
    }

    /**
     * Executes strategy
     *
     * @param gc GraphicsContext
     * @param line Line Colour
     * @param fill Fill Colour
     * @param m MouseEvent
     */
    public void executeStrategy(GraphicsContext gc, ColorPicker line, ColorPicker fill, MouseEvent m){
        strat.drawObj(gc, line, fill, m);
    }
}
