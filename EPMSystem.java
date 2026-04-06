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
    static String empID = "";
    static String empDept = "";
    static String empName = "";
    static double empSalary = 0.0;
    static double empTaxR = 0.0;


    //ARRAYLIST: Identifier: employeeRecords
    static ArrayList<Employee> employeeRecords = new ArrayList<>();

    // FOR UI ITO
    
    //Pang-clear ng screen para di makalat after lot of operations
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //Para di agad ma-clear if gusto makita ng user yung previous output
    static void pause() {
        System.out.println("\nPress ENTER to return to the main menu...");
        input.nextLine();
    }

    // Custom Exception para maka-exit sa bawat operation if gusto ng user or nagkamali lang siya
    static class CancelOperation extends Exception { }
    static String ask(String message) throws CancelOperation {
        System.out.print(message);
        String response = input.nextLine();
        
        if (response.equalsIgnoreCase("X")) {
            throw new CancelOperation(); 
        }
        return response;
    }

    static void showSelectDepartments() {
        System.out.println("COMPANY DEPARTMENTS");
        System.out.println("[1] IT");
        System.out.println("[2] Marketing");
        System.out.println("[3] HR");
        System.out.println("[4] Accounting");
        System.out.println("[5] Sales ");
        System.out.println("[6] Operations ");
        System.out.println("[7] Customer Service ");

        System.out.println("Enter Department: ");
        try {
            int choice = Integer.parseInt(input.nextLine());
            switch (choice) {
                case 1: empDept = "IT"; break;
                case 2: empDept = "Marketing"; break;
                case 3: empDept = "HR"; break;
                case 4: empDept = "Accounting"; break;
                case 5: empDept = "Sales"; break;
                case 6: empDept = "Operations"; break;
                case 7: empDept = "Customer Service"; break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Defaulting to 'IT'.");
            return;
        }
    } 

    // --- EMPLOYEE CLASS ---
    
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
    
    static void addEmployee() throws CancelOperation {
        System.out.println("\n========================================");
        System.out.println("          ADD AN EMPLOYEE RECORD         ");
        System.out.println("========================================");
        System.out.println("(Enter 'X' at any prompt to cancel operation)\n"); 
        
    //
        String empID = "";
        boolean unique = false;
        
        while(!unique){ //Hangga't di pa unique yung id, tuloy ang loop
            empID = ask("Enter Employee ID: ");
            
            unique = true; // Assume unique initially
            for(Employee e : employeeRecords){ // isa-isang ichecheck lahat ng employee sa arraylist
                if(e.id.equalsIgnoreCase(empID)){  // ichcheck kung yung ID ng current employee ay match sa ininput
                    System.out.println("An employee with the same ID is already existing. Try with another ID or delete the existing record.");
                    unique = false; // Not unique, continue loop
                    break;
                }
            }
        }



    //
        String empName = ask("Enter Full Name: ");
        showSelectDepartments();
        
        double empSalary = Double.parseDouble(ask("Enter Monthly Salary: "));
        try {
            double empTaxR = Double.parseDouble(ask("Enter Tax Rate (%): "));
        } catch (InputMismatchException e) {
            System.out.println("[NOTICE] Only numbers are accepted here. Kindly enter a valid number.");
            return;
        }
        

        String confirm = ask("SAVE Record? (Y/N): ");

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
    }
    
    // EDIT RECORD - GIAN
    
    static void editRecord() throws CancelOperation {
        System.out.println("\n========================================");
        System.out.println("           EDIT EMPLOYEE RECORD         ");
        System.out.println("========================================");
        System.out.println("(Enter 'X' at any prompt to cancel operation)\n");
        
        // Declare a variable (found) to hold the employee we want to edit
        // Initially walang laman (null) kasi wala pa tayong hinahanap
        Employee found = null;
        
        while(found == null){ //Hangga't di pa tama yung id, tuloy ang loop
            String empID = ask("Enter Employee ID: ");
            
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
        //temporary variables; fix for saving issue
        String tempID = found.id;
        String tempName = found.name;
        String tempDept = found.department;
        double tempSalary = found.salary;
        double tempTax = found.taxRate;
        
        //dito na pwedeng mamili si user ng gusto niyang i-edit
        boolean edit = true;
        while(edit){
            System.out.println("\nCurrent Details: ");
            System.out.println("[1] ID: " + tempID);
            System.out.println("[2] Name: " + tempName);
            System.out.println("[3] Department: " + tempDept);
            System.out.println("[4] Salary: " + tempSalary);
            System.out.println("[5] Tax Rate: " + tempTax + "%");
            System.out.println("[0] Exit Editing");
            
            // Exception handling para maiwasan ang error kung mali ang na-input (handled by try-catch in main)
            // converting string to integer
            try {
                int choice = Integer.parseInt(ask("\nSelect field to edit: "));
            
            switch (choice){
                case 1: // Edit ID
                    String newID = ask("Enter new ID: ");
                    if(!newID.isBlank()){
                        boolean unique = true;
                        for(Employee e : employeeRecords){
                            if(e.id.equalsIgnoreCase(newID) && !e.id.equalsIgnoreCase(tempID)){
                                System.out.println("An employee with the same ID is already existing. Try with another ID or delete the existing record.");
                                unique = false;
                                break;
                            }
                        }
                        if(unique){
                            tempID = newID;
                            System.out.println("ID updated to: " + tempID);
                        }
                    }
                    break;
                    
                case 2:
                    String newName = ask("Enter new Name: ");
                    // may ininput = i-uupdate; kung blank = keep old value
                    if(!newName.isBlank()){
                        tempName = newName;
                        System.out.println("Name updated.");
                    }
                    break;
                case 3:
                    String newDept = ask("Enter new Department: ");
                    // check kung may input
                    if(!newDept.isBlank()){
                        tempDept = newDept.toUpperCase();
                        //okie na :DD (ata)
                        System.out.println("Department updated. ID is now: " + tempDept);
                    }
                    break;
                    
                case 4:
                    try{
                        double newSalary = Double.parseDouble(ask("Enter new Salary: "));
                        tempSalary = newSalary;
                        System.out.println("Salary updated.");
                    } catch (NumberFormatException e){
                        System.out.println("Invalid salary input");
                    }
                    break;
                    
                case 5:
                    try{
                        double newTax = Double.parseDouble(ask("Enter new Tax Rate (%): "));
                        tempTax = newTax;
                        System.out.println("Tax Rate updated.");
                    } catch (NumberFormatException e){
                        System.out.println("Invalid tax input");
                    }
                    break;
                case 0:
                    String confirm = ask("Save the changes? (Y/N): "); //confirmation
                    
                    if(confirm.equalsIgnoreCase("Y")){ // pwedeng lowercase or uppercase ang itype
                        //fix main issue
                        found.id = tempID;
                        found.name = tempName;
                        found.department = tempDept;
                        found.salary = tempSalary;
                        found.taxRate = tempTax;
                        
                        found.netSalary = found.salary - (found.salary * (found.taxRate / 100)); //recomputation
                        System.out.println("Record updated successfully!");
                    }else{
                        System.out.println("Changes discarded.");
                    }
                    edit = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // DELETE RECORD - ENZO

    static void deleteRecord() throws CancelOperation {
        System.out.println("\n========================================");
        System.out.println("           DELETE EMPLOYEE RECORD         ");
        System.out.println("==========================================");
        System.out.println("(Enter 'X' at any prompt to cancel operation)\n");

        String empID = ask("Enter ID to delete: ");
        
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
            System.out.println("Salary:   Php" + found.salary);
            System.out.println("Tax Rate:   " + found.taxRate + "%");
         
            String confirm = ask("\nDELETE this record? (Y/N): ");
            
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
        clearScreen();
        System.out.println("==============================================");
        System.out.println("              NET SALARY RECORDS");    
        System.out.println("==============================================");
        if(employeeRecords.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        } //empty
        
        double totalSalary = 0;
        double totalNetSalary = 0;
        
        for(Employee emp : employeeRecords) {
            emp.netSalary = emp.salary - (emp.salary * (emp.taxRate / 100));
            System.out.println("==============================================");
            System.out.println("EmployeeID: " + emp.id);
            System.out.println("Name: " + emp.name);
            System.out.println("Department: " + emp.department);
            System.out.println("Salary: " + emp.salary);
            System.out.println("Tax Rate: " + emp.taxRate + "%");
            System.out.println("Net Salary: " + emp.netSalary);
            totalNetSalary += emp.netSalary;
            totalSalary += emp.salary;
        } // result
        System.out.println("==============================================");
        System.out.println("Total salary: " + totalSalary);
        System.out.println("Total net salary: " + totalNetSalary);
        System.out.println("==============================================");
    } // All summary

    // SEARCH BY DEPT - MARK 
    
    static void searchbyDept() throws CancelOperation {
        System.out.println("\n========================================");
        System.out.println("      SEARCH EMPLOYEES BY DEPARTMENT      ");
        System.out.println("==========================================");
        System.out.println("(Enter 'X' at any prompt to cancel operation)\n");
        showSelectDepartments();

        // Filter List
        ArrayList<Employee> filtered = new ArrayList<>();

        for (Employee e : employeeRecords) {
            if (e.department.equalsIgnoreCase(empDept)) {
                filtered.add(e);
            }
        }

        // No results
        if (filtered.isEmpty()) {
            System.out.println("No employees found in " + empDept);
            return;
        }

        // Comb Sort
        filtered.sort(
            Comparator.comparing((Employee e) -> e.name.toLowerCase())
                      .thenComparing((a, b) -> Double.compare(b.salary, a.salary))
        );

        // Display
        System.out.println("\nEmployees in " + empDept + " Department:");
        
        System.out.printf("%-5s %-15s %-15s %-10s\n",
        "ID", "Name", "Department", "Salary");
        System.out.println("---------------------------------------------------");

        for (Employee e : filtered) {
          System.out.printf("%-5s %-15s %-15s %-10.2f\n",
             e.id, e.name, e.department, e.salary);
        }
    }
    
    // GENERATE SUMMARY - CHARLENE

    static void genSummary() throws CancelOperation {
    System.out.println("\n========================================");
    System.out.println("          GENERATE PAYROLL SUMMARY        ");
    System.out.println("========================================");
    System.out.println("(Enter 'X' at any prompt to cancel operation)\n");
    
    if (employeeRecords.isEmpty()){
        System.out.println("No employee records found.");
        return;
    }

    String confirm = ask("Generate payroll summary file? (Y/N): ");
    
    if (!confirm.equalsIgnoreCase("Y")) {
        System.out.println("Operation cancelled.");
        return;
    }

    PrintWriter writer = null;

    try {
        writer = new PrintWriter(new FileWriter("payroll_summary.txt"));
         
        // HEADER
        writer.println("=====================================");
        writer.println("      EMPLOYEE PAYROLL SUMMARY      ");
        writer.println("=====================================");
        writer.printf("%-10s %-20s %-15s %-12s %-12s\n", 
                "ID", "NAME", "DEPARTMENT", "SALARY", "NET SALARY");
        writer.println("--------------------------------------------------------------------------");

        double totalSalary = 0;
        double totalNetSalary = 0;

        // SORT BY DEPARTMENT
        try {
            employeeRecords.sort(Comparator.comparing(emp -> emp.department));
        } catch (Exception e) {
            System.out.println("[WARNING] Could not sort by department.");
        }

        for (Employee emp : employeeRecords) {
            try {
                if (emp == null) continue;

                emp.netSalary = emp.salary - (emp.salary * (emp.taxRate / 100));

                // department column
                writer.printf("%-10s %-20s %-15s %-12.2f %-12.2f\n",
                        emp.id,
                        emp.name,
                        emp.department,
                        emp.salary,
                        emp.netSalary
                );

                totalSalary += emp.salary;
                totalNetSalary += emp.netSalary;

            } catch (Exception e) {
                System.out.println("[WARNING] Skipping invalid record.");
            }
        }

        writer.println("--------------------------------------------------------------------------");
        writer.printf("%-46s %-12.2f %-12.2f\n", "TOTAL:", totalSalary, totalNetSalary);
        writer.println("========================================");

        System.out.println("Payroll summary successfully generated!");
        System.out.println("File saved as: payroll_summary.txt");

    } catch (IOException e) {
        System.out.println("[ERROR] File writing failed.");
    } finally {
        if (writer != null) writer.close();
    }
}
            
            
            
    //MAIN METHOD
    public static void main (String Group2[]) {
        while (true) { 
            clearScreen(); 
            
            System.out.println("==============================================");
            System.out.println("Welcome to Employee Payroll Management System!");
            System.out.println("==============================================");
            System.out.println("\nSelect operation: \n[1] Add Employee \n[2] Edit Record \n[3] Delete Record \n[4] Calculate Payroll \n[5] Search by Department \n[6] Generate Summary \n[0] Exit");
            
            try {
                int operation = Integer.parseInt(ask("\nOperation: "));
                
                switch (operation) {
                    case 1: addEmployee(); pause(); break;
                    case 2: editRecord(); pause(); break;
                    case 3: deleteRecord(); pause(); break;
                    case 4: computation(); pause(); break;
                    case 5: searchbyDept(); pause(); break;
                    case 6: genSummary(); pause(); break;
                    case 0:
                        System.out.println("Closing program ...");
                        System.exit(0);
                    default: 
                        System.out.println("Invalid selection.");
                        pause();
                }
            } catch (CancelOperation e) {
                System.out.println("\n>>> Operation Cancelled.");
                pause();
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Invalid input. Please enter numbers only where required.");
                pause();
            }
        }
    } // end of main()
} //end of EPMS