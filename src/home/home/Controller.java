package home;

// import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class Controller implements Initializable {

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private BorderPane pnlCustomer;

    @FXML
    private BorderPane pnlPackages;

    @FXML
    private BorderPane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            //new stuff
            Stack<Shape> undoHistory = new Stack();
            Stack<Shape> redoHistory = new Stack();

            /* ----------btns---------- */
            ToggleButton drowbtn = new ToggleButton("Draw");
            ToggleButton rubberbtn = new ToggleButton("Rubber");
            ToggleButton linebtn = new ToggleButton("Line");
            ToggleButton rectbtn = new ToggleButton("Rectange");
            ToggleButton circlebtn = new ToggleButton("Circle");
            ToggleButton elpslebtn = new ToggleButton("Ellipse");
            ToggleButton textbtn = new ToggleButton("Text");

            ToggleButton[] toolsArr = {drowbtn, rubberbtn, linebtn, rectbtn, circlebtn, elpslebtn, textbtn};

            ToggleGroup tools = new ToggleGroup();

            for (ToggleButton tool : toolsArr) {
                tool.setMinWidth(90);
                tool.setToggleGroup(tools);
                tool.setCursor(Cursor.HAND);
            }

            ColorPicker cpLine = new ColorPicker(Color.BLACK);
            ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);

            TextArea text = new TextArea();
            text.setPrefRowCount(1);

            Slider slider = new Slider(1, 50, 3);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);

            Label line_color = new Label("Line Color");
            Label fill_color = new Label("Fill Color");
            Label line_width = new Label("3.0");

            Button undo = new Button("Undo");
            Button redo = new Button("Redo");
            Button save = new Button("Save");
            Button open = new Button("Open");

            Button[] basicArr = {undo, redo, save, open};

            for (Button btn : basicArr) {
                btn.setMinWidth(90);
                btn.setCursor(Cursor.HAND);
                btn.setTextFill(Color.WHITE);
                btn.setStyle("-fx-background-color: #666;");
            }
            save.setStyle("-fx-background-color: #80334d;");
            open.setStyle("-fx-background-color: #80334d;");

            VBox btns = new VBox(10);
            btns.getChildren().addAll(drowbtn, rubberbtn, linebtn, rectbtn, circlebtn, elpslebtn,
                    textbtn, text, line_color, cpLine, fill_color, cpFill, line_width, slider, undo, redo, open, save);
            btns.setPadding(new Insets(5));
            btns.setStyle("-fx-background-color: #999");
            btns.setPrefWidth(100);

            /* ----------Drow Canvas---------- */
            Canvas canvas = new Canvas(1080, 790);
            GraphicsContext gc;
            gc = canvas.getGraphicsContext2D();
            gc.setLineWidth(1);

            Line line = new Line();
            Rectangle rect = new Rectangle();
            Circle circ = new Circle();
            Ellipse elps = new Ellipse();

            canvas.setOnMousePressed(e -> {
                if (drowbtn.isSelected()) {
                    gc.setStroke(cpLine.getValue());
                    gc.beginPath();
                    gc.lineTo(e.getX(), e.getY());
                } else if (rubberbtn.isSelected()) {
                    double lineWidth = gc.getLineWidth();
                    gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
                } else if (linebtn.isSelected()) {
                    gc.setStroke(cpLine.getValue());
                    line.setStartX(e.getX());
                    line.setStartY(e.getY());
                } else if (rectbtn.isSelected()) {
                    gc.setStroke(cpLine.getValue());
                    gc.setFill(cpFill.getValue());
                    rect.setX(e.getX());
                    rect.setY(e.getY());
                } else if (circlebtn.isSelected()) {
                    gc.setStroke(cpLine.getValue());
                    gc.setFill(cpFill.getValue());
                    circ.setCenterX(e.getX());
                    circ.setCenterY(e.getY());
                } else if (elpslebtn.isSelected()) {
                    gc.setStroke(cpLine.getValue());
                    gc.setFill(cpFill.getValue());
                    elps.setCenterX(e.getX());
                    elps.setCenterY(e.getY());
                } else if (textbtn.isSelected()) {
                    gc.setLineWidth(1);
                    gc.setFont(Font.font(slider.getValue()));
                    gc.setStroke(cpLine.getValue());
                    gc.setFill(cpFill.getValue());
                    gc.fillText(text.getText(), e.getX(), e.getY());
                    gc.strokeText(text.getText(), e.getX(), e.getY());
                }
            });

            canvas.setOnMouseDragged(e -> {
                if (drowbtn.isSelected()) {
                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                } else if (rubberbtn.isSelected()) {
                    double lineWidth = gc.getLineWidth();
                    gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
                }
            });

            canvas.setOnMouseReleased(e -> {
                if (drowbtn.isSelected()) {
                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                    gc.closePath();
                } else if (rubberbtn.isSelected()) {
                    double lineWidth = gc.getLineWidth();
                    gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
                } else if (linebtn.isSelected()) {
                    line.setEndX(e.getX());
                    line.setEndY(e.getY());
                    gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

                    undoHistory.push(new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
                } else if (rectbtn.isSelected()) {
                    rect.setWidth(Math.abs((e.getX() - rect.getX())));
                    rect.setHeight(Math.abs((e.getY() - rect.getY())));
                    //rect.setX((rect.getX() > e.getX()) ? e.getX(): rect.getX());
                    if (rect.getX() > e.getX()) {
                        rect.setX(e.getX());
                    }
                    //rect.setY((rect.getY() > e.getY()) ? e.getY(): rect.getY());
                    if (rect.getY() > e.getY()) {
                        rect.setY(e.getY());
                    }

                    gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                    gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

                    undoHistory.push(new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));

                } else if (circlebtn.isSelected()) {
                    circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);

                    if (circ.getCenterX() > e.getX()) {
                        circ.setCenterX(e.getX());
                    }
                    if (circ.getCenterY() > e.getY()) {
                        circ.setCenterY(e.getY());
                    }

                    gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                    gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());

                    undoHistory.push(new Circle(circ.getCenterX(), circ.getCenterY(), circ.getRadius()));
                } else if (elpslebtn.isSelected()) {
                    elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                    elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));

                    if (elps.getCenterX() > e.getX()) {
                        elps.setCenterX(e.getX());
                    }
                    if (elps.getCenterY() > e.getY()) {
                        elps.setCenterY(e.getY());
                    }

                    gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                    gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());

                    undoHistory.push(new Ellipse(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY()));
                }
                redoHistory.clear();
                Shape lastUndo = undoHistory.lastElement();
                lastUndo.setFill(gc.getFill());
                lastUndo.setStroke(gc.getStroke());
                lastUndo.setStrokeWidth(gc.getLineWidth());

            });
            // color picker
            cpLine.setOnAction(e -> {
                gc.setStroke(cpLine.getValue());
            });
            cpFill.setOnAction(e -> {
                gc.setFill(cpFill.getValue());
            });

            // slider
            slider.valueProperty().addListener(e -> {
                double width = slider.getValue();
                if (textbtn.isSelected()) {
                    gc.setLineWidth(1);
                    gc.setFont(Font.font(slider.getValue()));
                    line_width.setText(String.format("%.1f", width));
                    return;
                }
                line_width.setText(String.format("%.1f", width));
                gc.setLineWidth(width);
            });

            /*------- Undo & Redo ------*/
            // Undo
            undo.setOnAction(e -> {
                if (!undoHistory.empty()) {
                    gc.clearRect(0, 0, 1080, 790);
                    Shape removedShape = undoHistory.lastElement();
                    if (removedShape.getClass() == Line.class) {
                        Line tempLine = (Line) removedShape;
                        tempLine.setFill(gc.getFill());
                        tempLine.setStroke(gc.getStroke());
                        tempLine.setStrokeWidth(gc.getLineWidth());
                        redoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));

                    } else if (removedShape.getClass() == Rectangle.class) {
                        Rectangle tempRect = (Rectangle) removedShape;
                        tempRect.setFill(gc.getFill());
                        tempRect.setStroke(gc.getStroke());
                        tempRect.setStrokeWidth(gc.getLineWidth());
                        redoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                    } else if (removedShape.getClass() == Circle.class) {
                        Circle tempCirc = (Circle) removedShape;
                        tempCirc.setStrokeWidth(gc.getLineWidth());
                        tempCirc.setFill(gc.getFill());
                        tempCirc.setStroke(gc.getStroke());
                        redoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                    } else if (removedShape.getClass() == Ellipse.class) {
                        Ellipse tempElps = (Ellipse) removedShape;
                        tempElps.setFill(gc.getFill());
                        tempElps.setStroke(gc.getStroke());
                        tempElps.setStrokeWidth(gc.getLineWidth());
                        redoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                    }
                    Shape lastRedo = redoHistory.lastElement();
                    lastRedo.setFill(removedShape.getFill());
                    lastRedo.setStroke(removedShape.getStroke());
                    lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
                    undoHistory.pop();

                    for (int i = 0; i < undoHistory.size(); i++) {
                        Shape shape = undoHistory.elementAt(i);
                        if (shape.getClass() == Line.class) {
                            Line temp = (Line) shape;
                            gc.setLineWidth(temp.getStrokeWidth());
                            gc.setStroke(temp.getStroke());
                            gc.setFill(temp.getFill());
                            gc.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
                        } else if (shape.getClass() == Rectangle.class) {
                            Rectangle temp = (Rectangle) shape;
                            gc.setLineWidth(temp.getStrokeWidth());
                            gc.setStroke(temp.getStroke());
                            gc.setFill(temp.getFill());
                            gc.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                            gc.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                        } else if (shape.getClass() == Circle.class) {
                            Circle temp = (Circle) shape;
                            gc.setLineWidth(temp.getStrokeWidth());
                            gc.setStroke(temp.getStroke());
                            gc.setFill(temp.getFill());
                            gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                            gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                        } else if (shape.getClass() == Ellipse.class) {
                            Ellipse temp = (Ellipse) shape;
                            gc.setLineWidth(temp.getStrokeWidth());
                            gc.setStroke(temp.getStroke());
                            gc.setFill(temp.getFill());
                            gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                            gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                        }
                    }
                } else {
                    System.out.println("there is no action to undo");
                }
            });

            // Redo
            redo.setOnAction(e -> {
                if (!redoHistory.empty()) {
                    Shape shape = redoHistory.lastElement();
                    gc.setLineWidth(shape.getStrokeWidth());
                    gc.setStroke(shape.getStroke());
                    gc.setFill(shape.getFill());

                    redoHistory.pop();
                    if (shape.getClass() == Line.class) {
                        Line tempLine = (Line) shape;
                        gc.strokeLine(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY());
                        undoHistory.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));
                    } else if (shape.getClass() == Rectangle.class) {
                        Rectangle tempRect = (Rectangle) shape;
                        gc.fillRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                        gc.strokeRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());

                        undoHistory.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
                    } else if (shape.getClass() == Circle.class) {
                        Circle tempCirc = (Circle) shape;
                        gc.fillOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                        gc.strokeOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());

                        undoHistory.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
                    } else if (shape.getClass() == Ellipse.class) {
                        Ellipse tempElps = (Ellipse) shape;
                        gc.fillOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                        gc.strokeOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());

                        undoHistory.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
                    }
                    Shape lastUndo = undoHistory.lastElement();
                    lastUndo.setFill(gc.getFill());
                    lastUndo.setStroke(gc.getStroke());
                    lastUndo.setStrokeWidth(gc.getLineWidth());
                } else {
                    System.out.println("there is no action to redo");
                }
            });


            /*------- Save & Open ------*/
            // Open
            /*
            open.setOnAction((e)->{
                FileChooser openFile = new FileChooser();
                openFile.setTitle("Open File");
                File file = openFile.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        InputStream io = new FileInputStream(file);
                        Image img = new Image(io);
                        gc.drawImage(img, 0, 0);
                    } catch (IOException ex) {
                        System.out.println("Error!");
                    }
                }
            });

            // Save
            save.setOnAction((e)->{
                FileChooser savefile = new FileChooser();
                savefile.setTitle("Save File");

                File file = savefile.showSaveDialog(primaryStage);
                if (file != null) {
                    try {
                        WritableImage writableImage = new WritableImage(1080, 790);
                        canvas.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        System.out.println("Error!");
                    }
                }

            });

             */

            /* ----------STAGE & SCENE---------- */
            //BorderPane pane = new BorderPane();
            pnlCustomer.setLeft(btns);
            pnlCustomer.setCenter(canvas);

            //Scene scene = new Scene(pnlOverview, 1200, 800);

            /*
            primaryStage.setTitle("Paint");
            primaryStage.setScene(scene);
            primaryStage.show();
            //new stuff
             */
            pnlCustomer.setStyle("-fx-background-color : #FFFFFF");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenu) {
            pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if (actionEvent.getSource() == btnOrders) {
            //sets title for notepad
            System.out.println("Yoo");
            // primaryStage.setTitle("Untitled");

            //sets title for notepad
            // primaryStage.setTitle("Untitled");

            //variables to hold the initial values of the text
            final StringBuilder color = new StringBuilder("-fx-text-inner-color: black;");
            final StringBuilder txtSize = new StringBuilder("-fx-font-size:12;");
            final StringBuilder style = new StringBuilder("-fx-font-style:normal;");
            final StringBuilder font = new StringBuilder("-fx-font-style:normal;");
            final StringBuilder themeColor = new StringBuilder("-fx-control-inner-background:white;");
            final StringBuilder underlineStatus = new StringBuilder("-fx-underline: false;");

            //creates a text area and sets its height and width
            TextArea text = new TextArea();
            text.setPrefHeight(1000);
            text.setPrefWidth(500);

            //creates menus
            Menu fileMenu = new Menu("File");
            Menu fontType = new Menu("Font Type");
            Menu fontSizes = new Menu("Font Sizes");
            Menu colorMenu = new Menu("Font Color");
            Menu fontStyle = new Menu("Font Style");
            Menu themeMenu = new Menu("Themes");

            //creates menu items
            MenuItem newItem = new MenuItem("New");
            MenuItem openItem = new MenuItem("Open");
            MenuItem saveItem = new MenuItem("Save");

            MenuItem serif = new MenuItem("Serif");
            MenuItem monspace = new MenuItem("Monospace");
            MenuItem impact = new MenuItem("Impact");
            MenuItem sans_serif = new MenuItem("Sans-Serif");
            MenuItem chiller = new MenuItem("Chiller");
            MenuItem tahoma = new MenuItem("Tahoma");

            MenuItem fontSize0 = new MenuItem("Size 5");
            MenuItem fontSize1 = new MenuItem("Size 12");
            MenuItem fontSize2 = new MenuItem("Size 14");
            MenuItem fontSize3 = new MenuItem("Size 20");
            MenuItem fontSize4 = new MenuItem("Size 30");
            MenuItem fontSize5 = new MenuItem("Size 50");

            MenuItem blackTxt = new MenuItem("Black");
            MenuItem redTxt = new MenuItem("Red");
            MenuItem blueTxt = new MenuItem("Blue");
            MenuItem greenTxt = new MenuItem("Green");
            MenuItem pinkTxt = new MenuItem("Pink");
            MenuItem purpleTxt = new MenuItem("Purple");

            MenuItem defaultStyle = new MenuItem("Default");
            MenuItem italicItem = new MenuItem("Italics");
            MenuItem boldItem = new MenuItem("Bold");
            MenuItem underlineItem = new MenuItem("Underline");
            MenuItem strikethroughItem = new MenuItem("Strikethrough");

            MenuItem whiteTheme = new MenuItem("white");
            MenuItem blackTheme = new MenuItem("black");

            //adds menu items to the appropriate menus
            fileMenu.getItems().add(newItem);
            fileMenu.getItems().add(openItem);
            fileMenu.getItems().add(saveItem);

            fontType.getItems().add(serif);
            fontType.getItems().add(sans_serif);
            fontType.getItems().add(monspace);
            fontType.getItems().add(impact);
            fontType.getItems().add(chiller);
            fontType.getItems().add(tahoma);

            fontSizes.getItems().add(fontSize0);
            fontSizes.getItems().add(fontSize1);
            fontSizes.getItems().add(fontSize2);
            fontSizes.getItems().add(fontSize3);
            fontSizes.getItems().add(fontSize4);
            fontSizes.getItems().add(fontSize5);

            colorMenu.getItems().add(blackTxt);
            colorMenu.getItems().add(redTxt);
            colorMenu.getItems().add(blueTxt);
            colorMenu.getItems().add(greenTxt);
            colorMenu.getItems().add(pinkTxt);
            colorMenu.getItems().add(purpleTxt);

            fontStyle.getItems().add(defaultStyle);
            fontStyle.getItems().add(italicItem);
            fontStyle.getItems().add(boldItem);
            fontStyle.getItems().add(underlineItem);
            fontStyle.getItems().add(strikethroughItem);

            themeMenu.getItems().add(whiteTheme);
            themeMenu.getItems().add(blackTheme);

            //creates a bar and adds menus to that bar
            MenuBar bar = new MenuBar();
            bar.getMenus().add(fileMenu);
            bar.getMenus().add(fontType);
            bar.getMenus().add(fontSizes);
            bar.getMenus().add(colorMenu);
            bar.getMenus().add(fontStyle);
            bar.getMenus().add(themeMenu);

            //creates a vbox and adds the elements of the bar and text to it
            VBox box = new VBox(bar, text);

            Scene scene = new Scene(box, 750, 500);
            String css = Main.class.getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);


            //makes a set on action method for newItem to create a new blank canvas to type on
            newItem.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {

                            //clears the canvas
                            text.clear();

                            //deletes the current string in themeColor
                            themeColor.delete(0, color.length());

                            //appends the themeColor string with the css for the background color of white
                            themeColor.append("-fx-control-inner-background:white;");

                            txtSize.delete(0, txtSize.length());

                            txtSize.append("-fx-font-size:12;");

                            //checks if the current text color is white
                            if (color.toString().equals("-fx-text-inner-color: white;")) {

                                //if it is then it deletes the current string in color
                                color.delete(0, color.length());

                                //and then it appends the css for black colored text
                                color.append("-fx-text-inner-color: black;");
                            }

                            //enables black text
                            blackTxt.setDisable(false);

                            //sets title back to Untitled
                            // primaryStage.setTitle("Untitled");

                            //sets a white background color with all other css variables
                            text.setStyle("-fx-control-inner-background:white;" + font.toString() + style.toString() + txtSize.toString() + color.toString());
                        }
                    });
            /*
            //declares a file chooser for the choose file option to choose a file from the file menu
            final FileChooser chooser = new FileChooser();
            //makes a set on action method for openItem
            openItem.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {

                            //make a file equal to what is chosen from the open dialog of the file menu
                            File f = chooser.showOpenDialog(primaryStage);

                            //checks that the file isn't null
                            if (f != null) {

                                //sets the name of the canvas to the file's name
                                primaryStage.setTitle(f.getName());

                                //gets all the lines in the text file that is being opened
                                ArrayList<String> txt = read(f);

                                //clears the canvas
                                text.clear();

                                //prints the lines from the text file onto the canvas
                                for (String line : txt) {
                                    text.appendText(line + "\n");
                                }

                            }
                        }
                    }
            );

            //declares another file chooser for the save option to save the file using the file menu
            FileChooser saver = new FileChooser();

            //makes a set on action method for saveItem
            saveItem.setOnAction((ActionEvent a) -> {

                //creates a filter for the FileChooser so that only the txt extension can be used
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                saver.getExtensionFilters().add(extFilter);

                //makes a file equal to what is choose from the save dialog of the file menu
                File f = saver.showSaveDialog(primaryStage);

                //checks that the file isn't null
                if (f != null) {

                    //sets the title of the canvas to the file's name
                    primaryStage.setTitle(f.getName());

                    //calls a function to save the current text on the canvas to the file
                    saveFile(text.getText(), f);
                }
            });
            */
            //makes a set on action method for black text
            blackTxt.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in color
                            color.delete(0, color.length());

                            //appends the color string with the css for the black colored text
                            color.append("-fx-text-inner-color: black;");

                            //sets the text style to have the text color black along with all other css variables
                            text.setStyle("-fx-text-inner-color: black;" + font.toString() + themeColor.toString() + style.toString() + txtSize.toString());
                        }
                    });

            //makes a set on action method for red text
            redTxt.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in color
                            color.delete(0, color.length());

                            //appends the color string with the css for the red colored text
                            color.append("-fx-text-inner-color: red;");

                            //sets the text style to have the text color red along with all other css variables
                            text.setStyle("-fx-text-inner-color: red;" + font.toString() + themeColor.toString() + style.toString() + txtSize.toString());
                        }
                    });

            //makes a set on action method for blue text
            blueTxt.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in color
                            color.delete(0, color.length());

                            //appends the color string with the css for blue colored text
                            color.append("-fx-text-inner-color: blue;");

                            //sets the text style to have the text color blue along with all other css variables
                            text.setStyle("-fx-text-inner-color: blue;" + font.toString() + themeColor.toString() + style.toString() + txtSize.toString());
                        }
                    });

            //makes a set on action method for green text
            greenTxt.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in color
                            color.delete(0, color.length());

                            //appends the color string with the css for green colored text
                            color.append("-fx-text-inner-color: green;");

                            //sets the text style to have the text color green along with all other css variables
                            text.setStyle(themeColor.toString() + style.toString() + color.toString() + txtSize.toString() + font.toString());
                        }
                    });

            //makes a set on action method for pink text
            pinkTxt.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in color
                            color.delete(0, color.length());

                            //appends the color string with the css for pink colored text
                            color.append("-fx-text-inner-color: pink;");

                            //sets the text style to have the text color pink along with all other css variables
                            text.setStyle(themeColor.toString() + style.toString() + color.toString() + txtSize.toString() + font.toString());
                        }
                    });

            //makes a set on action method for purple text
            purpleTxt.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in color
                            color.delete(0, color.length());

                            //appends the color string with the css for purple colored text
                            color.append("-fx-text-inner-color: purple;");

                            //sets the text style to have the text color purple along with all other css variables
                            text.setStyle(themeColor.toString() + style.toString() + color.toString() + txtSize.toString() + font.toString());
                        }
                    });

            //makes a set on action method for black theme
            blackTheme.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in themeColor
                            themeColor.delete(0, color.length());

                            //appends the themeColor string with the css for the background color of black
                            themeColor.append("-fx-control-inner-background:black;");

                            //checks if the current text color is black
                            if (color.toString().equals("-fx-text-inner-color: black;")) {

                                //if it is then it deletes the current string in color
                                color.delete(0, color.length());

                                //and then it appends the css for white colored text
                                color.append("-fx-text-inner-color: white;");
                            }

                            //disables black text
                            blackTxt.setDisable(true);

                            //sets a black background color with all other css variables
                            text.setStyle("-fx-control-inner-background:black;" + font.toString() + style.toString() + txtSize.toString() + color.toString());
                        }
                    });

            //makes a set on action method for white theme
            whiteTheme.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in themeColor
                            themeColor.delete(0, color.length());

                            //appends the themeColor string with the css for the background color of white
                            themeColor.append("-fx-control-inner-background:white;");

                            //checks if the current text color is white
                            if (color.toString().equals("-fx-text-inner-color: white;")) {

                                //if it is then it deletes the current string in color
                                color.delete(0, color.length());

                                //and then it appends the css for black colored text
                                color.append("-fx-text-inner-color: black;");
                            }

                            //enables black text
                            blackTxt.setDisable(false);

                            //sets a white background color with all other css variables
                            text.setStyle("-fx-control-inner-background:white;" + font.toString() + style.toString() + txtSize.toString() + color.toString());
                        }
                    });

            //makes a set on action method for fontSize0
            fontSize0.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in txtSize
                            txtSize.delete(0, txtSize.length());

                            //appends the txtSize string with the css for the font size of 5
                            txtSize.append("-fx-font-size:5;");

                            //sets the text style to have size 12 font along with all other css variables
                            text.setStyle(color.toString() + style.toString() + font.toString() + themeColor.toString() + "-fx-font-size:5");
                        }

                    }
            );

            //makes a set on action method for fontSize1
            fontSize1.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in txtSize
                            txtSize.delete(0, txtSize.length());

                            //appends the txtSize string with the css for the font size of 12
                            txtSize.append("-fx-font-size:12;");

                            //sets the text style to have size 12 font along with all other css variables
                            text.setStyle(color.toString() + style.toString() + font.toString() + themeColor.toString() + "-fx-font-size:12");
                        }

                    }
            );

            //makes a set on action method for fontSize2
            fontSize2.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in txtSize
                            txtSize.delete(0, txtSize.length());

                            //appends the txtSize string with the css for the font size of 14
                            txtSize.append("-fx-font-size:14;");

                            //sets the text style to have size 14 font along with all other css variables
                            text.setStyle(color.toString() + style.toString() + font.toString() + themeColor.toString() + "-fx-font-size:14");
                        }

                    }
            );

            //makes a set on action method for fontSize3
            fontSize3.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in txtSize
                            txtSize.delete(0, txtSize.length());

                            //appends the txtSize string with the css for the font size of 20
                            txtSize.append("-fx-font-size:20;");

                            //sets the text style to have size 20 font along with all other css variables
                            text.setStyle(color.toString() + style.toString() + font.toString() + themeColor.toString() + "-fx-font-size:20");
                        }

                    }
            );

            //makes a set on action method for fontSize4
            fontSize4.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in txtSize
                            txtSize.delete(0, txtSize.length());

                            //appends the txtSize string with the css for the font size of 30
                            txtSize.append("-fx-font-size:30;");

                            //sets the text style to have size 30 font along with all other css variables
                            text.setStyle(color.toString() + style.toString() + font.toString() + themeColor.toString() + "-fx-font-size:30");
                        }

                    }
            );

            //makes a set on action method for fontSize5
            fontSize5.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in txtSize
                            txtSize.delete(0, txtSize.length());

                            //appends the txtSize string with the css for the font size of 50
                            txtSize.append("-fx-font-size:50;");

                            //sets the text style to have size 12 font along with all other css variables
                            text.setStyle(color.toString() + style.toString() + font.toString() + themeColor.toString() + "-fx-font-size:50");
                        }

                    }
            );

            //makes a set on action method for serif
            serif.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in font
                            font.delete(0, font.length());

                            //appends the font string with the css for the serif font
                            font.append("-fx-font-style:serif;");

                            //sets text style to have the serif font along with all other css related variables
                            text.setStyle(color.toString() + txtSize.toString() + style.toString() + themeColor.toString() + "-fx-font-family:serif");
                        }

                    }
            );

            //makes a set on action method for monospace
            monspace.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in font
                            font.delete(0, font.length());

                            //appends the font string with the css for the monospace font
                            font.append("-fx-font-style:monospace;");

                            //sets text style to have the monospace font along with all other css related variables
                            text.setStyle(color.toString() + txtSize.toString() + style.toString() + themeColor.toString() + "-fx-font-family:monospace");
                        }

                    }
            );

            //makes a set on action method for impact
            impact.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in font
                            font.delete(0, font.length());

                            //appends the font string with the css for the impact font
                            font.append("-fx-font-style:impact;");

                            //sets text style to have the impact font along with all other css related variables
                            text.setStyle(color.toString() + txtSize.toString() + style.toString() + themeColor.toString() + "-fx-font-family:impact");
                        }

                    }
            );

            //makes a set on action method for sans_serif
            sans_serif.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in font
                            font.delete(0, font.length());

                            //appends the font string with the css for the sans-serif font
                            font.append("-fx-font-style:sans-serif;");

                            //sets text style to have the sans-serif font along with all other css related variables
                            text.setStyle(color.toString() + txtSize.toString() + style.toString() + themeColor.toString() + "-fx-font-family:sans-serif");
                        }

                    }
            );

            //makes a set on action method for chiller
            chiller.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in font
                            font.delete(0, font.length());

                            //appends the font string with the css for the chiller font
                            font.append("-fx-font-family:chiller;");

                            //sets text style to have the chiller font along with all other css related variables
                            text.setStyle(themeColor.toString() + style.toString() + color.toString() + txtSize.toString() + "-fx-font-family:chiller;");
                        }

                    }
            );

            //makes a set on action method for tahoma
            tahoma.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in font
                            font.delete(0, font.length());

                            //appends the font string with the css for the tahoma font
                            font.append("-fx-font-family:tahoma;");

                            //sets text style to have the tahoma font along with all other css related variables
                            text.setStyle(themeColor.toString() + style.toString() + color.toString() + txtSize.toString() + "-fx-font-family:tahoma;");
                        }

                    }
            );

            //makes a set on action method for defaultStyle
            defaultStyle.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string in style
                            style.delete(0, txtSize.length());

                            //appends the style string with the css for the default style
                            style.append("-fx-font-style:normal;");

                            //sets text style to have the deafult style along with all other css related variables
                            text.setStyle(color.toString() + txtSize.toString() + font.toString() + themeColor.toString() + "-fx-font-style:normal");
                        }

                    }
            );

            //makes a set on action method for italicItem
            italicItem.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string of the style string
                            style.delete(0, txtSize.length());

                            //appends the style string with the css for italics text
                            style.append("-fx-font-style:italic;");

                            //sets text style to have the italics font along with all other css related variables
                            text.setStyle(color.toString() + txtSize.toString() + "-fx-font-style:italic;" + themeColor.toString() + font.toString() + underlineStatus.toString());
                        }

                    }
            );


            //makes a set on action method for boldItem
            boldItem.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {

                            //deletes the current string for the style string
                            style.delete(0, txtSize.length());

                            //appends the style string with the css for bold text
                            style.append("-fx-font-style:bold;");

                            //sets text style to have the bold font and all the other css related variables
                            text.setStyle(color.toString() + txtSize.toString() + "-fx-font-weight:bold;" + themeColor.toString() + font.toString() + underlineStatus.toString());
                        }

                    }
            );

            underlineItem.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {
                            if (text.getStyleClass().contains("underline")) {
                                text.getStyleClass().remove("underline");
                            } else {
                                text.getStyleClass().add("underline");
                            }
                        }

                    }
            );

            strikethroughItem.setOnAction(
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionevent) {
                            if (text.getStyleClass().contains("strikethrough")) {
                                text.getStyleClass().remove("strikethrough");
                            } else {
                                text.getStyleClass().add("strikethrough");
                            }
                        }

                    }
            );


            //sets the primary stage with the box, which holds the textArea
            //primaryStage.setScene(new Scene(box, 500, 500));
            pnlOrders.setLeft(box);
            //pnlPackages.setCenter(scene.getFocusOwner());
            //pnlCustomer.setLeft(btns);
            //pnlCustomer.setCenter(canvas);
            //shows primary stage on the javafx canvas
            //primaryStage.show();
            pnlOrders.setStyle("-fx-background-color : #FFFFFF");
            pnlOrders.toFront();

        }

        }
    public ArrayList<String> read(File file){

        //ArrayList to hold lines from the file
        ArrayList<String> txtLine = new ArrayList<String>();

        //string to keep position as the function loops through the strings in the file
        String currentLine;
        try {

            //a BufferedReader to read through a file
            BufferedReader br = new BufferedReader(new FileReader(file));

            //loops through the file until currentLine hits null
            while((currentLine = br.readLine()) != null) {

                //adds the currentLine in the file to the ArrayList
                txtLine.add(currentLine);
            }

            //closes the BufferedReader
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //returns the ArrayList
        return txtLine;
    }

    public void saveFile(String txt, File file){
        try {
            FileWriter f = new FileWriter(file);
            f.write(txt);
            f.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    }



