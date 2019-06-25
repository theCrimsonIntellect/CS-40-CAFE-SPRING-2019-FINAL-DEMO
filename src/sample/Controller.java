/*
    The Controller class contains all the methods, actions needed to play the game.
    theRealGameStarter() starts the game. It is all the way down.

 */

package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class Controller
{
    public static final int NUMBER_OF_CHAIRS = 8;

    public Label lb1;

    // to hold score / show score on the screen
    public TextField scoreCardTextField;

    // the label for the score
    public Label scoreCardLabel;

    // holds the comments for each action taken by user
    public TextField actionComment;

    private int scoreCard = 0;

    private String text = "";
    private String url = "";
    private String name = "";
    private int customerCounter = 0;

    /**
     * one instance of this class create a virtual chair around the table
     * allows each customer instance/image to be set at each position
     */
    public class Chair
    {
        private ImageView chairPos;
        boolean occupied;
        long seatedTime;
        boolean menuOffered;
        long menuOfferedTime;
        int customerIndex;

        /**
         * contructor of the Chair class which initialized each instance fields
         * @param   i       image of a customer
         */
        Chair(ImageView i)
        {
            chairPos = i;
            occupied = false;
            menuOffered = false;
            customerIndex = 0;
        }

        public void setImageOnChairPos(Image i)
        {
            chairPos.setImage(i);
        }

        public void setOccupied(boolean occupiedStatus)
        {
            occupied = occupiedStatus;
        }

        public void setCustomerIndex(int cIndex)
        {
            customerIndex = cIndex;
        }

        public boolean isOccupied()
        {
            return occupied;
        }

        public ImageView getChairPos()
        {
            return chairPos;
        }

        public void setSeatedTime(long s) { seatedTime = s; }

        public long getSeatedTime() { return seatedTime; }

        public void setMenuOffered() { menuOffered = true;}

        public boolean isMenuOffered() { return menuOffered; }

        public long getMenuOfferedTime() {
            return menuOfferedTime;
        }

        public void setMenuOfferedTime(long menuTime) {
            this.menuOfferedTime = menuTime;
        }

        /**
         * This method removes a customer instance which has not been served in timely manner...
         * we will improve this method in near future
         */
        public void removeCustomer()
        {
            seatedTime = 0;
            occupied = false;
            chairPos = null;
        }


    } // end of Chair class `````````````````

    /**
     * one instance of this class creates a customer object with random temperament
     */
    private class Customer
    {
        //Instance Variables
        //add an instance variable called icon
        private String customer_name;
        private int customer_state;
        private int point_total;
        private long time_at_creation;
        private boolean has_menu;
        private boolean has_water;
        private boolean has_food;

        private ImageView customerPosition;

        /**
         * The constructor of the Customer class
         * it generate random customer
         */
        public Customer()
        {

            //add a field called icon
            Random rand = new Random();
            this.customer_name = "cheese";
            this.customer_state = rand.nextInt(3 + 1);
            this.time_at_creation = currentTimeMillis();
            this.has_menu = false;
            this.has_water = false;
            this.has_food = false;
            this.point_total = 3;
            customerPosition = new ImageView();

            // used to determine which customer to instantiate
            switch (this.customer_state)
            {
                case 1:
                    this.customerPosition.setImage(new Image("upsetCustomer.png"));
                    break;
                case 2:
                    this.customerPosition.setImage(new Image("normalCustomer.jpg"));
                    break;
                case 3:
                    this.customerPosition.setImage(new Image("relaxedCustomer.png"));
                    break;
            }
        }

        //GETTERS-----------------------------------------------------------------------------------------------

        //returns customer name
        public String getName ()
        {
            return this.customer_name;
        }

        //returns the customer state as a string, depending on what number is passed to the constructor
        public String getCustomerState ()
        {
            if (this.customer_state == 1)
            {
                return "Hurried";
            } else if (this.customer_state == 2)
            {
                return "Normal";
            } else if (this.customer_state == 3)
            {
                return "Relaxed";
            }
            else
            {
                return "Invalid Customer Type -- Needs to be int between 1 and 3";
            }
        }


        public ImageView getCustomerPosition()
        {
            return customerPosition;
        }
        //returns the time of creation of customer in milliseconds
        public long getTimeAtCreation ()
        {
            return this.time_at_creation;
        }

        //returns state of has_menu
        public boolean isMenu ()
        {
            return this.has_menu;
        }

        //returns state of has_water
        public boolean isWater ()
        {
            return this.has_water;
        }

        //returns state of has_food
        public boolean isFood ()
        {
            return this.has_food;
        }

        //returns customer Image.jpg
//        public Image getCustomerImage ()
//        {
//            return this.customerImage;
//        }


        //SETTERS-----------------------------------------------------------------------------------------------

        //sets the state of has_menu
        public void setMenu ( boolean menu)
        {
            //the try-catch statement below checks to make sure a bool was entered
            try
            {
                this.has_menu = menu;
            } catch (IllegalArgumentException e)
            {
                System.out.println("Something went wrong - Non bool value input");
            }
        }

        //sets the state of has_water
        public void setWater ( boolean water)
        {
            //the try-catch statement below checks to make sure a bool was entered
            try
            {
                this.has_water = water;
            } catch (IllegalArgumentException e)
            {
                System.out.println("Something went wrong - Non bool value input");
            }
        }

        //set the state of has_food
        public void setFood ( boolean food)
        {
            //the try-catch statement below checks to make sure a bool was entered
            try
            {
                this.has_food = food;
            } catch (IllegalArgumentException e)
            {
                System.out.println("Something went wrong - Non bool value input");
            }
        }

//        //set the customer image (must be jpq file)
//        public void setCustomerImage (Image url)
//        {
//            this.customerImage = url;
//        }

        //HELPER METHODS----------------------------------------------------------------------------------------

        //take away points for not having menu on time
        public void menuPointReduction ()
        {
            if (!this.isMenu() && this.timeSinceCreation() > 10)
            {
                this.point_total = this.point_total - 1;
            }
        }

        //take away points for not having water on time
        public void waterPointReduction ()
        {
            if (!this.isWater() && this.timeSinceCreation() > 20)
            {
                this.point_total = this.point_total - 1;
            }
        }

        public void foodPointReduction ()
        {
            if (!this.isFood() && this.timeSinceCreation() > 30)
            {
                this.point_total = this.point_total - 1;
            }
        }

        //returns the time since creation in seconds
        public double timeSinceCreation ()
        {
            float elapsed_time;
            long current_time = currentTimeMillis();

        /*
        The following equation takes the time when the method is called, and subtracts from it the time
        recorded when the customer was created. Since all of this is in milliseconds we then divide
        by 1000 to get our result in seconds.
         */
            elapsed_time = (current_time - this.time_at_creation) / 1000f;

            return elapsed_time;

        }

        //Our customer's thread execution;
        //@Override
        public void run ()
        {
            //set up a way to exit the while loop
            boolean IsCustomerThread = true;

            //three gates set up so that the customer class doesn't deduct points more than once.
            boolean firstGate = false;
            boolean secondGate = false;
            boolean thirdGate = false;

            String myName = this.customer_name;
            //System.out.println("Hello my name is " + myName);
            //System.out.println();

            //constantly check time since creation of customer in order to know what the customer needs next.
            while (IsCustomerThread)
            {
                double creation = this.timeSinceCreation();

                if (creation == 10.0 && !firstGate)
                {
                    //System.out.print(this.getName());
                    //System.out.println(": Squidward, I need a menu");
                    //System.out.println();
                    this.menuPointReduction();
                    firstGate = true;
                }
                if (creation == 20.0 && !secondGate)
                {
                    //System.out.print(this.getName());
                    //System.out.println(": Squidward, I need some water");
                    //System.out.println();
                    this.waterPointReduction();
                    secondGate = true;
                }
                if (creation == 30.0 && !thirdGate)
                {
                    //System.out.println(this.getName());
                    //System.out.println(": Squidward, I need some food");
                    //System.out.println();
                    this.foodPointReduction();
                    thirdGate = true;
                }
                if (creation == 50.0)
                {
                    //System.out.println(this.getName());
                    //System.out.println(": Alright, i'm gonna head out, later sponge bob.");
                    //System.out.println();
                    IsCustomerThread = false;
                }

            }
        }
    } // end of Customer class (inner class)

//    private Customer customer;
//
//    private Image image;

    private ImageView[] images = new ImageView[8];


    // the following ImageView objects are the actual location of each Chair  object
    @FXML
    ImageView seat1;

    @FXML
    ImageView seat2;

    @FXML
    ImageView seat3;
    @FXML
    ImageView seat4;
    @FXML
    ImageView seat5;
    @FXML
    ImageView seat6;
    @FXML
    ImageView seat7;
    @FXML
    ImageView seat8;

    /**
     * this creates an array of Chairs
     * @return      an array of Chair objects
     */
    private Chair[] seatDeclaration()
    {
        Chair[] chairs = new Chair[NUMBER_OF_CHAIRS];

        images[0] = seat1;
        images[1] = seat2;
        images[2] = seat3;
        images[3] = seat4;
        images[4] = seat5;
        images[5] = seat6;
        images[6] = seat7;
        images[7] = seat8;

        for (int i = 0; i < chairs.length; i++)
        {
            Chair chair = new Chair(images[i]);
            chairs[i] = chair;

        }
        return chairs;

    }

    // takes array of chairs and determine the first open chair
    private int firstAvailable(Chair[] chairs)
    {
        for (int i = 0; i < chairs.length; i++)
        {
            // checks boolean for occupied
            if (!chairs[i].isOccupied())
            {
                return i;
            }
        }
        return -99;
    }

    private void seatAssignment(Image danielImage, Chair[] chairs, int openSeatIndex)
    {
        chairs[openSeatIndex].setOccupied(true);
        chairs[openSeatIndex].getChairPos().setImage(danielImage);
    }


    @FXML
    private ImageView testing;

    @FXML
    private Label testingLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView foodServedImageView;

    @FXML
    private ImageView customerImageView;

    @FXML
    private ImageView secondCustomerImageView;

    @FXML
    private ImageView menuStartIcon;

    @FXML
    private ImageView waterStartIcon;

    @FXML
    private ImageView foodStartIcon;

    // the following 6 ImageView objects are the location of each plate around the table
    @FXML
    private ImageView dish_1;

    @FXML
    private ImageView dish_2;

    @FXML
    private ImageView dish_3;

    @FXML
    private ImageView dish_4;

    @FXML
    private ImageView dish_5;

    @FXML
    private ImageView dish_6;

    // this hold the user entry
    @FXML
    private TextField dishNumberTextField;

    // the submit button used when user enter a chair number
    @FXML
    private Button seatNumSubmitButton;

//    private String custUrl;

    /**
     * this method is called when water button is clicked
     * an instance of water object is created
     * and it is assigned to a chair
     * in near future, this method will be based on the menuServed()
     * it check the food served is valid, and if valid it updates the score...
     *
     * @param e
     */
    public void waterServed(ActionEvent e)
    {
        Image image = new Image("waterGlass.jpg");

//        seatNumSubmitButton.setOnAction(event -> assignSeat(image, dishNumberTextField.getText()));

    }

    /**
     * when menu button clicked, this method is called
     * it assigns the menu object at the desired chair
     * this method uses the helper method named: assignMenu()
     *
     * @param e
     */

    public void menuServed(ActionEvent e)
    {
        Image image = new Image("menuBook.jpg");

        seatNumSubmitButton.setOnAction(event -> assignMenu(image, dishNumberTextField.getText()));

    } // end of menuServed()...

    /**
     * this is a helper method. it assigns the menu object at the proper chair.
     * it check if menu offering is valid
     * 1) check if chair is occupied
     * 2) checks if menu has not already been offered
     * 3) checks if the elapsed time is valid ( less than 15s)
     *
     * @param img           menu image
     * @param st            chair number/location
     */

    public void assignMenu(Image img, String st)
    {
        actionComment.setText("");

        int seatNumber = Integer.parseInt(st);

        long menuOfferedTime = System.currentTimeMillis();

        long timeElapsed = menuOfferedTime - chairs[seatNumber].getSeatedTime();

        // validates whether to update score based on correct action as mentioned above
        if ( chairs[seatNumber].isOccupied() && (!chairs[seatNumber].isMenuOffered()) && (timeElapsed <= 15000) )
        {
            assignSeat(img, seatNumber);
            chairs[seatNumber].setMenuOfferedTime(menuOfferedTime);
            chairs[seatNumber].setMenuOffered();
            scoreCard += 10;

            // displays the success message and score card update on the screen
            scoreCardTextField.setText(Integer.toString(scoreCard));
            actionComment.setText("You served on time.... Good Job...");

        }
        // if actions are incorrect no points are added and
        // the appropriate error type is displayed on the message box on the screen
        else
        {
            if (!chairs[seatNumber].isOccupied())
                actionComment.setText("ERROR: Chair not occupied!!!");
            else if (chairs[seatNumber].isMenuOffered())
                actionComment.setText("ERROR: Menu already served!!!");
            else
                actionComment.setText("ERROR: Too late offering menu!!!");
        }
        dishNumberTextField.setText("");

    }

    /**
     * a helper method that is called to assign menu, water, food objects at
     * the desired chair.
     *
     * @param   image       is the menu or food or water image
     * @param   seatNumber  the location of the chair object
     */
    public void assignSeat(Image image, int seatNumber)
    {
        switch (seatNumber)
        {

            case 1:
                dish_1.setImage(image);
                break;
            case 2:
                dish_2.setImage(image);
                break;
            case 3:
                dish_3.setImage(image);
                break;
            case 4:
                dish_4.setImage(image);
                break;
            case 5:
                dish_5.setImage(image);
                break;
            case 6:
                dish_6.setImage(image);
                break;
        }

    }

    /**
     * this method is called when food button is clicked
     * an instance of food object is created
     * and it is assigned to a chair
     * in near future, this method will be based on the menuServed()
     * it check the food served is valid, and if valid it updates the score...
     *
     * @param e
     */

    public void foodServed(ActionEvent e)
    {
        Image image = new Image("foodServed.jpg");

//        seatNumSubmitButton.setOnAction(event -> assignSeat(image, dishNumberTextField.getText()));

    }



    // array of Chair objects accessible to all methods
    Chair[] chairs;

    /**
     * This method is invoked when the start button is clicked and starts the game
     * @param   event
     */
    @FXML
    public void theRealGameStarter(ActionEvent event)
    {
        // array of chair objects: including image and customer assignments
        chairs = seatDeclaration();

        // the main game thread
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                // start time: game start time
                long startingTime = currentTimeMillis();
                try
                {
                    while (true)
                    {
                        // creates an instance of a customer object
                        Image customerUrl;
                        Customer daniel;
                        int openSeatIndex;

                        long currentTime = currentTimeMillis();

                        long elapsedTime = (currentTime - startingTime) / 1000;

                        // each 2 seconds this creates a new customer, using elapsed time
                        if (elapsedTime % 2 == 0 && elapsedTime != 0)
                        {
                            daniel = new Customer();

                            // finds the first available seat
                            openSeatIndex = firstAvailable(chairs);

                            // takes the customer image
                            customerUrl = daniel.getCustomerPosition().getImage();

                            // assigns the seat, places the customer image on the imageView container of the seat
                            seatAssignment(customerUrl, chairs, openSeatIndex);
                            chairs[openSeatIndex].setSeatedTime(System.currentTimeMillis());

                        }// end of if

                        Thread.sleep(500);
                    }// end  of loop....

                }catch (InterruptedException e) {e.printStackTrace();}
            }
        }).start();
    }

}