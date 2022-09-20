/*Jen Verboven
 * 1 ITF 14
 * r0889629*/
package fact.it.supermarket.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Staff extends Person{
    private LocalDate startDate = LocalDate.now();
    private boolean student;

    public Staff(String firstName, String surName) {
        super(firstName, surName);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public String toString(){
        LocalDate myDate = LocalDate.parse("2020-03-01");
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern ("dd/MM/yyyy");

        if (isStudent()){
            return "Staff member " + super.toString() + " (working student) is employed since " + this.startDate.format(dtf);
        }
        return "Staff member " + super.toString() + " is employed since " + this.startDate.format(dtf);
    }
}
