module com.example.productivityassistant {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.productivityassistant to javafx.fxml;
    exports com.example.productivityassistant;
}