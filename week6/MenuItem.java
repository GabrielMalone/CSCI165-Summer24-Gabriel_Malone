// Gabriel Malone / CS165 / Week 6 / Summer 2024

public class MenuItem {
    
    // private instance variables

    private String  name     =  "unknown";
    private double  price    =  1.00;
    private int     calories =  0;

    // constructors 

    /**
     * No argument constructor leaves all fields default
     */
    public MenuItem(){}

    /**
     * Overloaded constructor
     * 
     * @param name (String) of menu item
     * @param price (double) of menu item
     * @param calories (int) in menu item
     */
    public MenuItem(String name, double price, int calories){
        setName(name);
        setPrice(price);
        setCalories(calories);
    }

    /**
     * Method to set the name for the menu item
     * 
     * @param name (String) of menu item
     */
    public void setName(String name){
        if (name != null) 
            this.name = name.toUpperCase();
    }

    /**
     * Method to set the price of a menu item
     * 
     * @param price (double) of menu item
     */
    public void setPrice(double price){
        // if current price set correctly and new price invalid
        // keep current price
        if (this.price > 0 && price <= 0) price = this.price;
        else this.price = 1.00;
        this.price = price;
        
    }
    /**
     * Method to set calories for the menu item
     * 
     * @param calories (int) in menu item
     */
    public void setCalories(int calories){
        if (this.calories >= 0 && calories < 0) calories = this.calories;
        else this.calories = 0;
        this.calories = calories;
    }
    
    /**
     * Method to return the string values of MenuItem's instance variables
     */
    public String toString(){
        String menu = 
        String.format   ("%s%n$%.2f%n%,d%n", 
                        this.name, this.price, this.calories);
        return menu;  
    }

    /**
     * Method to return menu item name
     * 
     * @return (String) name of menu item
     */
    public String getName(){
        return this.name;
    }

    /**
     * Method to return menu item's calories
     * 
     * @return (int) calories in menu item
     */
    public int getCalories(){
        return this.calories;
    }

    /**
     * Method to return menu item's price
     * 
     * @return (double) price of menu item
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Method to compare one menu item to another
     * 
     * @param name (string) of menu item
     * @param price (double) of menu item
     * @param calories (int) in menu item
     * @return
     */
    public boolean equals(MenuItem menu2){
        
        if ( 
                this.name.equals(menu2.name)      &&
                this.price    == menu2.price      &&
                this.calories == menu2.calories
            ) 
                return  true;
        else    return false;
    }

    /**
     * Method that places the menu item with fewer 
     * calories before the menu item with higher calories
     * 
     * @param menu
     * @return (int) 1 for greater than, 0 for equal, -1 for less than
     */
    public int compareTo(MenuItem menu2){

        if 
                ( menu2.calories < this.calories )  return -1;
        else if 

                (menu2.calories  ==  this.calories) return  0;
        
        else                                        return  1;
    }
}