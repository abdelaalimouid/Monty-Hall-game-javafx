module com.montyhall.mont_yhall {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.montyhall.mont_yhall to javafx.fxml;
    exports com.montyhall.mont_yhall;
}