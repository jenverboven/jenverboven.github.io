/*Jen Verboven
 * 1 ITF 14
 * r0889629*/
package fact.it.supermarket.model;

import java.util.ArrayList;

public class Supermarket {
    private String name;
    private int numberCustomers;
    private ArrayList<Department> departmentsList = new ArrayList<>();
    private Staff generalManager;

    public Supermarket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNumberCustomers() {
        return numberCustomers;
    }

    public ArrayList<Department> getDepartmentList() {
        return departmentsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfDepartments(){
        return this.departmentsList.size();
    }

    public void addDepartment(Department department){
        this.departmentsList.add(department);
    }

    public Department searchDepartmentByName(String name) {
        for (Department department: this.departmentsList) {
            if (name.equals(department.getName())){
                return department;
            }
        }
        return null;
    }
    public void registerCustomer(Customer customer){
        this.numberCustomers ++;
        customer.setCardNumber(this.numberCustomers);
    }

    public Staff getGeneralManager() {
        return generalManager;
    }

    public void setGeneralManager(Staff generalManager) {
        this.generalManager = generalManager;
    }
}
