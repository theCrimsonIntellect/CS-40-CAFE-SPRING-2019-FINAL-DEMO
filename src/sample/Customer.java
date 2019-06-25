package sample;

import javafx.scene.image.Image;

public class Customer
{
    private Image customerImage;
    private String customerName;

    public Customer()
    {
        customerImage = new Image("mellowCustomer.jpg");
        customerName = "Angry Customer";
    }

    public Customer(Image ci, String nm)
    {
        customerImage = ci;
        customerName = nm;
    }

    public void setCustomerImage(Image img)
    {
        customerImage = img;
    }

    public void setCustomerName(String nm)
    {
        customerName = nm;
    }

    public Image getCustomerImage()
    {
        return customerImage;
    }

    public String getCustomerName()
    {
        return customerName;
    }
}
