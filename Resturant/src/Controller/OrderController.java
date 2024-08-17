package Controller;

import Model.AddOrderModel;

public class OrderController {
    private final AddOrderModel model;

    public OrderController() {
        this.model = new AddOrderModel();
    }

    // Method to save the order by passing the customer and order details to the model
    public void saveOrder(String customerName, String customerPhone, String[][] orderItems) {
        boolean isSaved = model.saveOrder(customerName, customerPhone, orderItems);
        if (isSaved) {
            System.out.println("Order saved successfully");
        } else {
            System.out.println("Failed to save order");
        }
    }
    
    
}
