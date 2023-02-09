module com.example.comp1008week5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.comp1008week5 to javafx.fxml;
    exports com.example.comp1008week5;
}