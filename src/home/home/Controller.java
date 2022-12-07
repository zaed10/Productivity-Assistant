package home;

// import javafx.embed.swing.SwingFXUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private BorderPane sketch;

    @FXML
    private BorderPane pnlPackages;

    @FXML
    private BorderPane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;

    @FXML
    private TextField priority;

    @FXML
    private TextField taskName;

    @FXML
    private Button addBtn;

    @FXML
    ListView<home.Task> eventList;
    ObservableList<Task> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[10];
        /*
        for (int i = 0; i < nodes.length; i++) {
            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));

                //give the items some effect
                //0A0E3F,02030A
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #05071F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #05071F");
                });
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         */
    }

    // on click func -> call function from To Do class inside (i.e. create new task)
    public void btnNewTask(ActionEvent actionEvent) {
        home.Task t1 = new home.Task(taskName.getText(), priority.getText());
        list.add(t1);
        eventList.setItems(list);
    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            //buttons
            ToggleButton drowbtn = new ToggleButton("Draw");
            ToggleButton eraser = new ToggleButton("Eraser");
            ToggleButton linebtn = new ToggleButton("Line");
            ToggleButton rectangle = new ToggleButton("Rectange");
            ToggleButton circle = new ToggleButton("Circle");
            ToggleButton ellipse = new ToggleButton("Ellipse");
            ToggleButton textbtn = new ToggleButton("Text");

            ToggleButton[] toolsArr = {drowbtn, eraser, linebtn, rectangle, circle, ellipse, textbtn};

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
            line_color.setStyle("-fx-text-fill: #b7c3d7;");
            Label fill_color = new Label("Fill Color");
            fill_color.setStyle("-fx-text-fill: #b7c3d7;");
            Label line_width = new Label("3.0");
            line_width.setStyle("-fx-text-fill: #b7c3d7;");

            for (ToggleButton btn : toolsArr) {
                btn.setCursor(Cursor.HAND);
                btn.setTextFill(Color.WHITE);
                btn.setStyle("-fx-background-color: #05071F;");
            }

            VBox btns = new VBox(10);
            btns.getChildren().addAll(drowbtn, eraser, linebtn, rectangle, circle, ellipse,
                    textbtn, text, line_color, cpLine, fill_color, cpFill, line_width, slider);
            btns.setPadding(new Insets(5));
            btns.setStyle("-fx-background-color: #02030A");
            btns.setPrefWidth(100);

            //make drawing area
            Canvas canvas = new Canvas(1080, 790);
            GraphicsContext gc;
            gc = canvas.getGraphicsContext2D();
            gc.setLineWidth(1);

            //shapes
            Line line = new Line();
            Rectangle rect = new Rectangle();
            Circle circ = new Circle();
            Ellipse elps = new Ellipse();

            canvas.setOnMousePressed(e -> {
                if (drowbtn.isSelected()) {
                    Context cont = new Context(new Drawer());
                    cont.executeStrategy(gc, cpLine, cpFill, e);
                } else if (eraser.isSelected()) {
                    Context cont = new Context(new Eraser());
                    cont.executeStrategy(gc, cpLine, cpFill, e);
                } else if (linebtn.isSelected()) {
                    Context cont = new Context(new Liner());
                    cont.executeStrategy(gc, cpLine, cpFill, e);
                    line.setStartX(e.getX());
                    line.setStartY(e.getY());
                } else if (rectangle.isSelected()) {
                    Context cont = new Context(new Rectangler());
                    cont.executeStrategy(gc, cpLine, cpFill, e);
                    rect.setX(e.getX());
                    rect.setY(e.getY());
                } else if (circle.isSelected()) {
                    Context cont = new Context(new Circler());
                    cont.executeStrategy(gc, cpLine, cpFill, e);
                    circ.setCenterX(e.getX());
                    circ.setCenterY(e.getY());
                } else if (ellipse.isSelected()) {
                    Context cont = new Context(new Ellipser());
                    cont.executeStrategy(gc, cpLine, cpFill, e);
                    elps.setCenterX(e.getX());
                    elps.setCenterY(e.getY());
                } else if (textbtn.isSelected()) {
                    gc.setLineWidth(1);
                    gc.setFont(Font.font(slider.getValue()));
                    Context cont = new Context(new Texter());
                    cont.executeStrategy(gc, cpLine, cpFill, e);
                    gc.fillText(text.getText(), e.getX(), e.getY());
                    gc.strokeText(text.getText(), e.getX(), e.getY());
                }
            });

            canvas.setOnMouseDragged(e -> {
                if (drowbtn.isSelected()) {
                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                } else if (eraser.isSelected()) {
                    double lineWidth = gc.getLineWidth();
                    gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
                }
            });

            canvas.setOnMouseReleased(e -> {
                if (drowbtn.isSelected()) {
                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                    gc.closePath();
                } else if (eraser.isSelected()) {
                    double lineWidth = gc.getLineWidth();
                    gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
                } else if (linebtn.isSelected()) {
                    line.setEndX(e.getX());
                    line.setEndY(e.getY());
                    gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                } else if (rectangle.isSelected()) {
                    rect.setWidth(Math.abs((e.getX() - rect.getX())));
                    rect.setHeight(Math.abs((e.getY() - rect.getY())));
                    if (rect.getX() > e.getX()) {
                        rect.setX(e.getX());
                    }
                    if (rect.getY() > e.getY()) {
                        rect.setY(e.getY());
                    }

                    gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                    gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                } else if (circle.isSelected()) {
                    circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);

                    if (circ.getCenterX() > e.getX()) {
                        circ.setCenterX(e.getX());
                    }
                    if (circ.getCenterY() > e.getY()) {
                        circ.setCenterY(e.getY());
                    }

                    gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                    gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                } else if (ellipse.isSelected()) {
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
                }
            });

            // color picker
            cpLine.setOnAction(e -> {
                gc.setStroke(cpLine.getValue());
            });
            cpFill.setOnAction(e -> {
                gc.setFill(cpFill.getValue());
            });

            // slider to change size
            slider.valueProperty().addListener(e -> {
                double sliderValue = slider.getValue();
                if (textbtn.isSelected()) {
                    gc.setLineWidth(1);
                    gc.setFont(Font.font(slider.getValue()));
                    line_width.setText(String.format("%.1f", sliderValue));
                    return;
                }
                line_width.setText(String.format("%.1f", sliderValue));
                line_width.setStyle("-fx-text-fill: #b7c3d7;");
                gc.setLineWidth(sliderValue);
            });

            //BorderPane Set Side Bar Buttons
            sketch.setLeft(btns);
            //BorderPane Set Canvas
            sketch.setCenter(canvas);

            //set background to white
            sketch.setStyle("-fx-background-color : #FFFFFF");
            sketch.toFront();
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
            //variables to hold the initial values of the text
            ColorPicker cpLine = new ColorPicker(Color.BLACK);
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


            //basically sets everything in
            pnlOrders.setLeft(box);

            //background and everything to the front ezpz
            pnlOrders.setStyle("-fx-background-color : #FFFFFF");
            pnlOrders.toFront();
        }
    }
}
