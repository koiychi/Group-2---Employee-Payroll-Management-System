import java.util.*;
import java.io.*;

public class EPMSystem {
    //Methods:
    /*
    Add employee - Miles
    Edit Record - Gian
    Delete Record - Enzo
    Salary Computation (Batch) - Charles
    Search by Department - Talusan
    Generate Summary (txt file) - Charlene
    */ 
    
    static Scanner input = new Scanner(System.in);
    //ARRAYLIST: Identifier: employeeRecords
    static ArrayList<Employee> employeeRecords = new ArrayList<>();

    //Employee Class: Contains variables for Employees (template for all current and new employees)
    static class Employee {
        String id, name, department; //String ID: para sa IDs with combination of letters, numbers, and chars like dash
        double salary, taxRate, netSalary; //netSalary is salary minus tax
        /*
        STRING           |     INT
        IT-003987             003987
        .equals()               ==
        
        
        */
        //Object to create an employee
        //Works like: Employee(empID, empName, empDept, empSalary, empTaxR)
        public Employee(String id, String name, String department, double salary, double taxRate) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.taxRate = taxRate;
        }
    }
    
    static void addEmployee() {
        System.out.println("\n[ADD AN EMPLOYEE]");
        try {
            System.out.print("Enter Employee ID: ");
            String empID = input.nextLine(); 

            System.out.print("Enter Full Name: ");
            String empName = input.nextLine();

            System.out.print("Enter Department: ");
            String empDept = input.nextLine();

            System.out.print("Enter Monthly Salary: ");
            double empSalary = input.nextDouble();

            System.out.print("Enter Tax Rate (%): ");
            double empTaxR = input.nextDouble();
            input.nextLine();

            System.out.print("SAVE Record? (Y/N): ");
            String confirm = input.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                //Saving records to ArrayList after confirming
                //Creating new Employee object (line 26) named "newEmp" 
                //satisfying the parameters id, name, department, salary, and taxRate
                //with empID, empName, empDept, empSalary, and empTaxR 
                Employee newEmp = new Employee(empID, empName, empDept, empSalary, empTaxR);
                //Adding the records (newEmp) to the employeeRecords ArrayList
                employeeRecords.add(newEmp);
                System.out.println("Employee successfully added to the system!");
            } else {
                System.out.println("Operation cancelled. Record not saved.");
            }

        } catch (InputMismatchException e) {
            System.out.println("ERROR: Invalid numeric input for Salary or Tax. Please try again.");
            input.nextLine(); 
        }
    }
    static void editRecord() {
        System.out.println("\n========================================");
        System.out.println("           EDIT EMPLOYEE RECORD         ");
        System.out.println("========================================");
        
        System.out.print("Enter Employee ID: ");
        String empID = input.nextLine();
        
        //EXPLANATION
        Employee found = null;
        
        for(Employee e : employeeRecords){
            if(e.id.equalsIgnoreCase(empID)){
                found = e;
                break; //ID: 12345 [12346]
            }
        }
        if (found == null){
            System.out.println("Employee not found.\n");
            return;
        }//current details
        System.out.println("Current Details: ");
        System.out.println("ID: " + found.id);
        System.out.println("Name: " + found.name);
        System.out.println("Department: " + found.department);
        System.out.println("Salary: " + found.salary);
        System.out.println("Tax Rate: " + found.taxRate + "%");
        System.out.println("Net Salary: " + found.netSalary);
        
        try{
            //Edit ID 
            //Switch
            System.out.print("Enter new Name: "); // users can leave this blank to keep current info
            String newName = imput.nextLine();
            if(!newName.isBlank()){
                found.name = newName;
            }
            System.out.print("Enter new Department: ");
            String newDept = input.nextLine();
            if(!newDept.isBlank()){ 
                found.department = newDept;
            }
            System.out.print("Enter new Salary: ");
            String salaryInput = input.nextLine();
        }
    }

    static void deleteRecord() {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter ID to delete: ") // 2024000012
        String target = input.nextLine();
        
        boolean found = false;
        //10
        //IT-102345
        /*
        SEARCH: 
        
        0 2025003987 x
        1 2025005867 x
        2 2024084866 X
        3 2024000012 /
        4 2025004345
        
        
        */ 1
        for(int i = 0; i < employeeRecords.size(); i++) {
            // pagkalagay ng user ng id dito nagi-iterate hanggang umabot sa hinahanap na id
            //if (search.equals(employeeRecords.get(i)))
            
            Employee emp = employeeRecords.get(i);
            
            if(emp.getID()equalsIgnoreCase(target)) {
                found = true;
                
                System.out.println("\nRecord Found: "+ emp.name);
                
                System.out.println("DELETE this record? (Y/N): ");
                String confirm = input.nextLine();
                
                if (confirm.equalsIgnoreCase("Y")) {
                    employeeRecords.remove(i);
                    System.out.println("Record Successfully Deleted.");
                } else {
                    System.out.println("Deletion Cancelled.");
                }
            }
        }
    } 
    
    static void computation() {
        System.out.println("==============================================");
        System.out.println("\nNet salary record");    
        System.out.println("==============================================");
        if(employeeRecords.isEmpty()) {
            System.out.println("No employee records found.");
            System.out.println(" ");
    
            return;
        } //empty
        double totalpayRoll = 0;
        for(Employee : employeeRecords) {
            emp.netSalary = salary - (salary * (taxRate / 100));
            System.out.println("EmployeeID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Department: " + dept);
            System.out.println("Computed net salary: " + netSalary);
            totalpayRoll += netSalary;
        } // result
            System.out.println("Total net salary: " + totalpayRoll);
    } // All summary
    
    // Mark - Search 
    static void searchbyDept() {
        
        System.out.print("Enter Department: ");
        String dept = input.nextLine(); 
        //Switch: [1] IT, [2] Accounting
        //IT, Accounting, Marketing

        boolean found = false;
        System.out.println("\nEmployees in " + dept + " department:");
        System.out.println(" ");
        
        for (Employee e : employeeRecords) {
            if (e.department.equalsIgnoreCase(dept)) {
                System.out.println(e);
                found = true;
           }
        }
        /*
        Employees in IT Department:
        ID     | Name      | Salary
        */
    }
    
    
//gensummary
    static void genSummary() {
        System.out.println("\n[GENERATE PAYROLL SUMMARY]");
        
        //if empty terminated
        if (employeeRecords.isEmpty()){
            System.out.println("No employee records found.");
            return;
        }
        System.out.print("Generate payroll summary file? (Y/N?: ");
        String confirm = input.nextLine();
        
        //if no terminated 
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Operation cancelled.");
            return;
        }
    
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("payroll_summary.txt"));
            
            //id, name, dep, salary, tax0  
            writer.println("=====================================");
            writer.println("      EMPLOYEE PAYROLL SUMMARY      ");
            writer.println("=====================================");
            writer.printf("%-10s %-20s %-12s %-12s\n" , "ID" , "NAME" , "SALARY", "NET SALARY");
            writer.println("------------------------------------------------------------");

        double totalSalary = 0;
        double totalNetSalary = 0;

        for (Employee emp : employeeRecords) {
            emp.netSalary = emp.salary - (emp.salary * (emp.taxRate / 100));

            writer.printf("%-10s %-20s %-12.2f %-12.2f\n",
                    emp.id,
                    emp.name,
                    emp.salary,
                    emp.netSalary
            );

            totalSalary += emp.salary;
            totalNetSalary += emp.netSalary;
        }

        writer.println("------------------------------------------------------------");
        writer.printf("%-31s %-12.2f %-12.2f\n", "TOTAL:", totalSalary, totalNetSalary);
        writer.println("========================================");

        System.out.println("Payroll summary successfully generated!");
        System.out.println("File saved as: payroll_summary.txt");

    } catch (IOException e) {
        System.out.println("ERROR: File writing failed.");
    }
}
            /*
            0 id NAME IT
            3 ID NAME IT
            1 ID NAME ACCOUNTING
            2 ID NAME ACCOUNTING
            
            
            */
            
            
            
    //MAIN METHOD
    public static void main (String Group2[]) {
        while (true) { 
            System.out.println("==============================================");
            System.out.println("Welcome to Employee Payroll Management System!");
            System.out.println("==============================================");
            System.out.println("\nSelect operation: \n[1] Add Employee \n[2] Edit Record \n[3] Delete Record \n[4] Calculate Payroll \n[5] Search by Department \n[6] Generate Summary \n[0] Exit");
            System.out.print("Operation: ");
            
            int operation = input.nextInt();
            input.nextLine();
            
            switch (operation) {
                case 1: addEmployee(); break;
                case 2: editRecord(); break;
                case 3: deleteRecord(); break;
                case 4: computation(); break;
                case 5: searchbyDept(); break;
                case 6: genSummary(); break;
                case 0:
                    System.out.println("Closing program ...");
                    System.exit(0);
                default: 
                    System.out.println("Invalid selection.");
            }
        }
    } // end of main()
} //end of EPMS