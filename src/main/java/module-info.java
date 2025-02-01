module com.montyhall.mont_yhall {
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.graphics;
    exports ui;
}
