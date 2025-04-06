module org.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.project3 to javafx.fxml;
    exports org.example.project3;
}