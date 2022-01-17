module com.example.a3_381 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.a3_381 to javafx.fxml;
    exports com.example.a3_381;
}