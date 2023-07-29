module com.example.foodqueuemanagementsystemmaster {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.foodqueuemanagementsystemmaster to javafx.fxml;
    exports com.example.foodqueuemanagementsystemmaster;
}