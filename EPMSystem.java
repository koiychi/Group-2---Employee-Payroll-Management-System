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
        STRING           |   INT
        IT-003987             003987
        .equals()                ==
        
        
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
    
    // ADD EMPLOYEE - MILES
    
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
    
    // EDIT RECORD - GIAN
    
    static void editRecord() {
        System.out.println("\n========================================");
        System.out.println("           EDIT EMPLOYEE RECORD         ");
        System.out.println("========================================");
        
        // Declare a variable (found) to hold the employee we want to edit
        // Initially walang laman (null) kasi wala pa tayong hinahanap
        Employee found = null;
        
        while(found == null){ //Hangga't di pa tama yung id, tuloy ang loop
            System.out.print("Enter Employee ID: ");
            String empID = input.nextLine();
            
            for(Employee e : employeeRecords){ // isa-isang ichecheck lahat ng employee sa arraylist
                if(e.id.equalsIgnoreCase(empID)){  // ichcheck kung yung ID ng current employee ay match sa ininput
                    found = e; //store the employee in "found"
                    break; //loop stops pag may nahanap na
                }
            }
            if (found == null){ // pag walang nahanap, uulit ung loop
                System.out.println("Employee not found. Please try again.");
            }
        }
        //dito na pwedeng mamili si user ng gusto niyang i-edit
        boolean edit = true;
        while(edit){
            System.out.println("Current Details: ");
            System.out.println("1. ID: " + found.id);
            System.out.println("2. Name: " + found.name);
            System.out.println("3. Department: " + found.department);
            System.out.println("4. Salary: " + found.salary);
            System.out.println("5. Tax Rate: " + found.taxRate + "%");
            System.out.println("6. Exit Editing");
            
            System.out.print("\nSelect field to edit (1-6): ");
            int choice = 0;
            // Exception handling para maiwasan ang error kung mali ang na-input
            try{
                choice = Integer.parseInt(input.nextLine()); // converting string to integer
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Enter number only.");
                continue; 
            }
            switch (choice){
                case 1: // Edit ID
                    System.out.print("Enter new ID number: ");
                    String idNumber = input.nextLine();
                    
                    if(!idNumber.isBlank()){
                        found.id = found.department.toUpperCase() + "-" + idNumber; //auto format;no need for user to type it manually: dept-id (IT-1234)
                        System.out.println("ID updated to: " + found.id);
                    }
                    break;
                case 2:
                    System.out.print("Enter new Name: ");
                    String newName = input.nextLine();
                    // may ininput = i-uupdate; kung blank = keep old value
                    if(!newName.isBlank()){
                        found.name = newName;
                        System.out.println("Name updated.");
                    }
                    break;
                case 3:
                    System.out.print("Enter new Department: ");
                    String newDept = input.nextLine();
                    // check kung may input
                    if(!newDept.isBlank()){
                        found.department = newDept.toUpperCase();
                        
                        String[] parts = found.id.split("-", 2); // hinati lang ang current ID using "-";  Example: "IT-12345" -> parts[0]="IT", parts[1]="12345"
                        if(parts.length == 2){ // make sure the format is correct (may dalawang parts)
                        
                            // Combine new department + old ID number
                            found.id = found.department + "-" + parts[1];
                        }
                        System.out.println("Department updated. ID is now: " + found.id);
                    }
                    break;
                case 4:
                    try{
                        System.out.print("Enter new Salary: ");
                        double newSalary = Double.parseDouble(input.nextLine());
                        
                        found.salary = newSalary;
                        System.out.println("Salary updated.");
                    } catch (NumberFormatException e){
                        System.out.println("Invalid salary input.");
                    }
                    break;
                case 5:
                    try{
                        System.out.print("Enter new Tax Rate (%): ");
                        double newTax = Double.parseDouble(input.nextLine());
                        
                        found.taxRate = newTax;
                        System.out.println("Tax Rate updated.");
                    }catch (NumberFormatException e){
                        System.out.println("Invalid tax input.");
                    }
                    break;
                case 6:
                    System.out.print("Save the changes? (Y/N): "); //confirmation
                    String confirm = input.nextLine();
                    
                    if(confirm.equalsIgnoreCase("Y")){ // pwedeng lowercase or uppercase ang itype
                        found.netSalary = found.salary - (found.salary * (found.taxRate / 100)); //recomputation
                        
                        System.out.println("Record updated successfully!");
                    }else{
                        System.out.println("Changes discarded.");
                    }
                    
                    edit = false;
                    break;
                default:
                    System.out.print("Invalid choice.");
            }
        }
    }

    // DELETE RECORD - ENZO

    static void deleteRecord() {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter ID to delete: ");
        String empID = input.nextLine();
        
        Employee found = null;
        
        for (Employee e : employeeRecords) {
            if (e.id.equalsIgnoreCase(empID)) {
                found = e;
                break;
            }
        }
     
        if(found != null) {
            
            System.out.println("\nRECORD FOUND.");
            System.out.println("ID:         " + found.id);
            System.out.println("Name:       " + found.name);
            System.out.println("Department: " + found.department);
            System.out.println("Salary:     ₱" + found.salary);
            System.out.println("Tax Rate:   " + found.taxRate + "%");
         
            System.out.print("DELETE this record? (Y/N): ");
            String confirm = input.nextLine();
            
            if (confirm.equalsIgnoreCase("Y")) {
                employeeRecords.remove(found);
                System.out.println("Record Successfully Deleted.");
            } else {
                System.out.println("Deletion Cancelled.");
            }
            
        } else {
            System.out.println("[ERROR] Employee ID [" + empID + "] not found.");
        }
    }
     
    //  SALARY COMPUTATION - CHARLES
    
    static void computation() {
        System.out.println("==============================================");
        System.out.println("\nNet salary record");    
        System.out.println("==============================================");
        if(employeeRecords.isEmpty()) {
            System.out.println("No employee records found.");
            System.out.println(" ");
    
            return;
        } //empty
        double totalSalary = 0;
        
        for(Employee emp : employeeRecords) {
           
            emp.netSalary = emp.salary - (emp.salary * (emp.taxRate / 100));
            System.out.println("==============================================");
            System.out.println("EmployeeID: " + emp.id);
            System.out.println("Name: " + emp.name);
            System.out.println("Department: " + emp.department);
            System.out.println("Computed net salary: " + emp.netSalary);
            totalSalary += emp.netSalary;
        } // result
        System.out.println("==============================================");
        System.out.println("Total net salary: " + totalSalary);
        System.out.println("==============================================");
    } // All summary
    

    // SEARCH BY DEPT - MARK 
    
    static void searchbyDept() {
        
        System.out.println("\nSelect Department:");
        System.out.println("[1] Soft Dev");
        System.out.println("[2] Web Dev");
        System.out.println("[3] Cybersec ");
        System.out.println("[4] Data Analyst ");
        System.out.println("[5] Cloud Engineer ");
        System.out.print("Choice: ");

        int choice = input.nextInt();
        input.nextLine(); // fix newline

        String dept;

        // Switch
        switch (choice) {
            case 1:
                dept = "Soft Dev";
                break;
            case 2:
                dept = "Web Dev";
                break;
            case 3:
                dept = "Cybersec";
                break;
            case 4:
                dept = "Data Analyst";
                break;
            case 5:
                dept = "Cloud Engineer";
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        // Filter List
        ArrayList<Employee> filtered = new ArrayList<>();

        for (Employee e : employeeRecords) {
            if (e.department.equalsIgnoreCase(dept)) {
                filtered.add(e);
            }
        }

        // No results
        if (filtered.isEmpty()) {
            System.out.println("No employees found in " + dept);
            return;
        }

        // Comb Sort
        filtered.sort(
            Comparator.comparing((Employee e) -> e.name.toLowerCase())
                      .thenComparing((a, b) -> Double.compare(b.salary, a.salary))
        );

        // Display
        System.out.println("\nEmployees in " + dept + " Department:");
        System.out.println("ID | Name | Department | Salary");
        System.out.println("--------------------------------");

        for (Employee e : filtered) {
            System.out.println(e.id + " | " + e.name + " | " + e.department + " | " + e.salary);
        }
    }
    
    
    // GENERATE SUMMARY - CHARLENE

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
            writer.close();

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