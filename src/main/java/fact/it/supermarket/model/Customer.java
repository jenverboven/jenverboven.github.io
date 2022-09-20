/*Jen Verboven
 * 1 ITF 14
 * r0889629*/
package fact.it.supermarket.model;

import java.util.ArrayList;

public class Customer extends Person{
    private int cardNumber = -1, yearOfBirth;
    private ArrayList<String> shoppingList = new ArrayList<>();

    public Customer(String firstName, String surName) {
        super(firstName, surName);
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public ArrayList<String> getShoppingList() {
        return shoppingList;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public boolean addToShoppingList(String productName){
        if (this.shoppingList.size() < 5){
            this.shoppingList.add(productName);
            return true;
        }
        return false;
    }

    public int getNumberOnShoppingList(){
        return this.shoppingList.size();
    }

    public String toString(){
        return "Customer " + super.toString() + " with card number " + this.cardNumber;
    }
}
