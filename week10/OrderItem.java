// Gabriel Malone / CSCI65 / Week 10 / Summer 2024



public class OrderItem {
   
    private int item_total = 0;
    private MenuItem menuItem; 

    /**
     * No methods constructor
     */
    public OrderItem (){}

    /**
     * Overloaded method constructor
     * @param menuItem
     * @param item_total
     */
    public OrderItem(MenuItem menuItem, int item_total) {
        // clone any menuItems passed into the method
        this.menuItem = new MenuItem(menuItem);
        this.item_total = item_total;
    }

    public OrderItem (OrderItem copy){
        this.menuItem = copy.menuItem;
        // not sure if this one needed since int
        this.item_total = copy.item_total;
    }

    /**
     * Method to return a cloned menu item
     * @return
     */
    public MenuItem getMenuItem () {
        // cloned when object initialized
       return this.menuItem;
    }

    /**
     * Method to retun the number of items a OrderItem has
     * @return int of total number of items an OrderItem has
     */
    public int getItemTotal (){
        return this.item_total;
    }

    /*
     * Method to return a string detailing the current OrderItem
     */
    @Override
    public String toString() {
        return "OrderItem [orderTotal=" +
        "$" + item_total * menuItem.getPrice()
        + ", item_total=" + item_total + ", menuItem=" + menuItem + "]";
    }

    /*
     * Method to increase the quantity of a MenuItem outside of Terminal Display.
     */
    public void updateQuantity (int quant){
        if (this.item_total + quant > 0)
            this.item_total += quant;
    }

    /*
     * Method to get the quanity of a MenuItem outside of Terminal Display.
     */
    public int getQuantity () {
      return this.item_total;
    }


    /**
     * Method to compare two OrderItems
     * @param otherOrderItem
     * @return
     */
    public boolean equals(OrderItem otherOrderItem){
        // compare name and quantity, not memory address
        if (this.menuItem.equals(otherOrderItem.menuItem) && this.getItemTotal() == otherOrderItem.getItemTotal())
            return true;
        else
            return false;
    }
}
