/*Jen Verboven
 * 1 ITF 14
 * r0889629*/
package fact.it.supermarket.controller;


import fact.it.supermarket.model.Customer;
import fact.it.supermarket.model.Department;
import fact.it.supermarket.model.Staff;
import fact.it.supermarket.model.Supermarket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class MainController {
    private ArrayList<Staff> staffArrayList = new ArrayList<>();
    private ArrayList<Customer> customerArrayList = new ArrayList<>();
    private ArrayList<Supermarket> supermarketArrayList = new ArrayList<>();

    @PostConstruct
    public void fillData() {
        this.staffArrayList.addAll(fillStaffMembers());
        this.customerArrayList.addAll(fillCustomers());
        this.supermarketArrayList.addAll(fillSupermarkets());
    }
    @RequestMapping("/1_newCustomer")
    public String newCustomer(Model model){
        model.addAttribute("supermarketArrayList", supermarketArrayList);

        return "1_newCustomer";
    }

    @RequestMapping("/submitCustomer")
    public String submitCustomer(HttpServletRequest request, Model model){
        String firstName = request.getParameter("firstName");
        String surName = request.getParameter("surName");
        int yearOfBirth = Integer.parseInt(request.getParameter("yearOfBirth"));
        int supermarketIndex = Integer.parseInt(request.getParameter("supermarketIndex"));

        Customer customer = new Customer(firstName, surName);
        customer.setYearOfBirth(yearOfBirth);

        this.supermarketArrayList.get(supermarketIndex).registerCustomer(customer);
        this.customerArrayList.add(customer);

        model.addAttribute("customer", customer);

        return "2_welcomeCustomer";
    }

    @RequestMapping("/3_newStaffMember")
    public String newStaffMember(){
        return "3_newStaffMember";
    }

    @RequestMapping("/submitStaffMember")
    public String submitStaffMember(HttpServletRequest request, Model model){
        String firstName = request.getParameter("firstName");
        String surName = request.getParameter("surName");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern ("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"), dtf);
        boolean workingStudent = (request.getParameter("workingStudent") != null);

        Staff staff = new Staff(firstName, surName);
        staff.setStartDate(startDate);
        staff.setStudent(workingStudent);

        this.staffArrayList.add(staff);

        model.addAttribute("staff", staff);

        return "4_infoStaffMember";
    }

    @RequestMapping("/5_listStaffMembers")
    public String listStaffMembers(Model model){
        model.addAttribute("staffArrayList", this.staffArrayList);

        return "5_listStaffMembers";
    }

    @RequestMapping("/6_listCustomers")
    public String listCustomers(Model model){
        model.addAttribute("customerArrayList", this.customerArrayList);

        return "6_listCustomers";
    }

    @RequestMapping("/7_newSupermarket")
    public String newSupermarket(Model model){
        model.addAttribute("staffArrayList", this.staffArrayList);
        return "7_newSupermarket";
    }

    @RequestMapping("/8_listSupermarkets")
    public String listSupermarkets(Model model){
        model.addAttribute("supermarketArrayList", this.supermarketArrayList);

        return "8_listSupermarkets";
    }

    @RequestMapping("/submitSupermarket")
    public String submitSupermarket(HttpServletRequest request, Model model){
        String nameSupermarket = request.getParameter("nameSupermarket");

        int staffIndex = Integer.parseInt(request.getParameter("staffIndex"));
        if(staffIndex<0){
            model.addAttribute("errorMessage", "You didn't select a general manager!");
            return "error";
        }

        Supermarket supermarket = new Supermarket(nameSupermarket);
        supermarket.setGeneralManager(staffArrayList.get(staffIndex));

        this.supermarketArrayList.add(supermarket);

        model.addAttribute("supermarket", supermarket);

        return "0_exam";
    }

    @RequestMapping("/9_newDepartment")
    public String newDepartment(Model model){
        model.addAttribute("supermarketArrayList", this.supermarketArrayList);
        model.addAttribute("staffArrayList", this.staffArrayList);
        return "9_newDepartment";
    }

    @RequestMapping("/10_listDepartments")
    public String listDepartments(HttpServletRequest request, Model model){
        int supermarketIndex = Integer.parseInt(request.getParameter("supermarketIndex"));

        Supermarket supermarket = this.supermarketArrayList.get(supermarketIndex);

        model.addAttribute("supermarket", supermarket);
        model.addAttribute("supermarketArraylist", this.supermarketArrayList);

        return "10_listDepartments";
    }

    @RequestMapping("/submitDepartment")
    public String submitDepartment(HttpServletRequest request, Model model){
        String nameDepartment = request.getParameter("nameDepartment");
        String photo = request.getParameter("photo");
        boolean refrigerated = (request.getParameter("refrigerated") != null);
        int supermarketIndex = Integer.parseInt(request.getParameter("supermarketIndex"));
        if(supermarketIndex<0){
            model.addAttribute("errorMessage", "You didn't select a supermarket!");
            return "error";
        }
        int staffIndex = Integer.parseInt(request.getParameter("staffIndex"));
        if(staffIndex<0){
            model.addAttribute("errorMessage", "You didn't select a staff member!");
            return "error";
        }

        Supermarket supermarket = this.supermarketArrayList.get(supermarketIndex);
        Staff staff = this.staffArrayList.get(staffIndex);

        Department department = new Department(nameDepartment);
        department.setPhoto(photo);
        department.setRefrigerated(refrigerated);
        department.setResponsible(staff);

        supermarket.addDepartment(department);

        model.addAttribute(supermarket);

        return "10_listDepartments";
    }

    @RequestMapping("/searchDepartment")
    public String searchDepartment(HttpServletRequest request, Model model){
        String departmentName = request.getParameter("departmentName");
        for (Supermarket supermarket : this.supermarketArrayList) {
            Department department = supermarket.searchDepartmentByName(departmentName);
            int departmentIndex = supermarket.getDepartmentList().indexOf(department);
            if (department != null){
                model.addAttribute("supermarketArrayList", this.supermarketArrayList);
                model.addAttribute("departmentIndex", departmentIndex);
                model.addAttribute("department", department);
                return "11_departmentByName";
            }
        }
        model.addAttribute("errorMessage", "There is no department with the name '" + departmentName + "'");
        return "error";
    }

    private ArrayList<Staff> fillStaffMembers() {
        ArrayList<Staff> staffMembers = new ArrayList<>();

        Staff staff1 = new Staff("Johan", "Bertels");
        staff1.setStartDate(LocalDate.of(2002, 5, 1));
        Staff staff2 = new Staff("An", "Van Herck");
        staff2.setStartDate(LocalDate.of(2019, 3, 15));
        staff2.setStudent(true);
        Staff staff3 = new Staff("Bruno", "Coenen");
        staff3.setStartDate(LocalDate.of(1995, 1, 1));
        Staff staff4 = new Staff("Wout", "Dayaert");
        staff4.setStartDate(LocalDate.of(2002, 12, 15));
        Staff staff5 = new Staff("Louis", "Petit");
        staff5.setStartDate(LocalDate.of(2020, 8, 1));
        staff5.setStudent(true);
        Staff staff6 = new Staff("Jean", "Pinot");
        staff6.setStartDate(LocalDate.of(1999, 4, 1));
        Staff staff7 = new Staff("Ahmad", "Bezeri");
        staff7.setStartDate(LocalDate.of(2009, 5, 1));
        Staff staff8 = new Staff("Hans", "Volzky");
        staff8.setStartDate(LocalDate.of(2015, 6, 10));
        staff8.setStudent(true);
        Staff staff9 = new Staff("Joachim", "Henau");
        staff9.setStartDate(LocalDate.of(2007, 9, 18));
        staffMembers.add(staff1);
        staffMembers.add(staff2);
        staffMembers.add(staff3);
        staffMembers.add(staff4);
        staffMembers.add(staff5);
        staffMembers.add(staff6);
        staffMembers.add(staff7);
        staffMembers.add(staff8);
        staffMembers.add(staff9);
        return staffMembers;
    }

    private ArrayList<Customer> fillCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer("Dominik", "Mioens");
        customer1.setYearOfBirth(2001);
        Customer customer2 = new Customer("Zion", "Noops");
        customer2.setYearOfBirth(1996);
        Customer customer3 = new Customer("Maria", "Bonetta");
        customer3.setYearOfBirth(1998);
        Customer customer4 = new Customer("Jen", "Verboven");
        customer4.setYearOfBirth(2000);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.get(0).addToShoppingList("Butter");
        customers.get(0).addToShoppingList("Bread");
        customers.get(1).addToShoppingList("Apple");
        customers.get(1).addToShoppingList("Banana");
        customers.get(1).addToShoppingList("Grapes");
        customers.get(1).addToShoppingList("Oranges");
        customers.get(2).addToShoppingList("Fish");
        customers.get(3).addToShoppingList("Beans");
        customers.get(3).addToShoppingList("Potatoes");
        return customers;
    }

    private ArrayList<Supermarket> fillSupermarkets() {
        ArrayList<Supermarket> supermarkets = new ArrayList<>();
        Supermarket supermarket1 = new Supermarket("Delhaize");
        Supermarket supermarket2 = new Supermarket("Colruyt");
        Supermarket supermarket3 = new Supermarket("Albert Heyn");
        Department department1 = new Department("Fruit");
        Department department2 = new Department("Bread");
        Department department3 = new Department("Vegetables");
        department1.setPhoto("/img/fruit.jpg");
        department2.setPhoto("/img/bread.jpg");
        department3.setPhoto("/img/vegetables.jpg");
        department1.setResponsible(staffArrayList.get(0));
        department2.setResponsible(staffArrayList.get(1));
        department3.setResponsible(staffArrayList.get(2));
        supermarket1.addDepartment(department1);
        supermarket1.addDepartment(department2);
        supermarket1.addDepartment(department3);
        supermarket2.addDepartment(department1);
        supermarket2.addDepartment(department2);
        supermarket3.addDepartment(department1);
        supermarket3.addDepartment(department3);
        supermarkets.add(supermarket1);
        supermarkets.add(supermarket2);
        supermarkets.add(supermarket3);
        supermarket1.setGeneralManager(this.staffArrayList.get(0));
        supermarket2.setGeneralManager(this.staffArrayList.get(1));
        supermarket3.setGeneralManager(this.staffArrayList.get(2));
        return supermarkets;
    }

}