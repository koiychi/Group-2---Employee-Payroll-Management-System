import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class EPMSystem {
    static Scanner input = new Scanner(System.in);
    static String empID = "";
    static String empDept = "";
    static String empName = "";
    static double empSalary = 0.0;
    static double empTaxR = 0.0;

    // ARRAY LIST
    static ArrayList<Employee> employeeRecords = new ArrayList<>();

    //UI Methods
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pause() {
        System.out.println("\nPress ENTER to return to the main menu...");
        input.nextLine();
    }

    // Custom exception for operation cancellation
    static class CancelOperation extends Exception { }
    
    static String ask(String message) throws CancelOperation {
        System.out.print(message);
        String response = input.nextLine();
        
        if (response.equalsIgnoreCase("X")) {
            throw new CancelOperation(); 
        }
        return response;
    }

    // DEPARTMENT SELECTION
    static void showSelectDepartments() throws CancelOperation {
        System.out.println("COMPANY DEPARTMENTS");
        System.out.println("[1] IT");
        System.out.println("[2] Marketing");
        System.out.println("[3] HR");
        System.out.println("[4] Accounting");
        System.out.println("[5] Sales ");
        System.out.println("[6] Operations ");
        System.out.println("[7] Customer Service ");

        while(true) {
            try {
                int choice = Integer.parseInt(ask("Enter Department: "));
                switch (choice) {
                    case 1: empDept = "IT"; return;
                    case 2: empDept = "Marketing"; return;
                    case 3: empDept = "HR"; return;
                    case 4: empDept = "Accounting"; return;
                    case 5: empDept = "Sales"; return;
                    case 6: empDept = "Operations"; return;
                    case 7: empDept = "Customer Service"; return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    } 

    // Employee class to hold employee details
    static class Employee {
        String id, name, department; 
        double salary, taxRate, netSalary; 
        
        public Employee(String id, String name, String department, double salary, double taxRate) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.taxRate = taxRate;
        }
    }
    
    // ADDING EMPLOYEE
    static void addEmployee() throws CancelOperation {
        System.out.println("\n========================================");
        System.out.println("          ADD AN EMPLOYEE RECORD         ");
        System.out.println("========================================");
        System.out.println("(Enter 'X' at any prompt to cancel operation)\n"); 
        
        String empID = "";
        boolean unique = false;
        
        while(!unique) { 
            empID = ask("Enter Employee ID: ");
            
            if(empID.isBlank()) {
                System.out.println("[ERROR] ID cannot be empty. Please try again.");
                continue;
            }
            
            unique = true;
            for(Employee e : employeeRecords) { 
                if(e.id.equalsIgnoreCase(empID)) {  
                    System.out.println("An employee with the same ID is already existing. Try with another ID or delete the existing record.");
                    unique = false; 
                    break;
                }
            }
        }

        String empName = "";
        while(empName.isBlank()) {
            empName = ask("Enter Full Name: ");
            if(empName.isBlank()) {
                System.out.println("[ERROR] Name cannot be empty. Please try again.");
            }
        }
        
        showSelectDepartments();
        
        double empSalary = 0;
        while(true) {
            try {
                empSalary = Double.parseDouble(ask("Enter Monthly Salary: PHP "));
                if(empSalary < 0) {
                    System.out.println("[ERROR] Salary cannot be a negative value. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("[NOTICE] Only numbers are accepted here. Kindly enter a valid number.");
            }
        }
        
        while(true) {
            try {
                empTaxR = Double.parseDouble(ask("Enter Tax Rate (e.g., enter 12 for 12%): "));
                if(empTaxR < 0 || empTaxR > 100) {
                    System.out.println("[ERROR] Tax rate must be between 0 and 100. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("[NOTICE] Only numbers are accepted here. Kindly enter a valid number.");
            }
        }
        
        String confirm = ask("SAVE Record? (Y/N): ");

        if (confirm.equalsIgnoreCase("Y")) {
            Employee newEmp = new Employee(empID, empName, empDept, empSalary, empTaxR);
            employeeRecords.add(newEmp);
            System.out.println("Employee successfully added to the system!");
        } else {
            System.out.println("Operation cancelled. Record not saved.");
        }
    }
    
    // EDIT EMPLOYEE RECORD
    static void editRecord() throws CancelOperation {
        System.out.println("\n========================================");
        System.out.println("           EDIT EMPLOYEE RECORD         ");
        System.out.println("========================================");
        System.out.println("(Enter 'X' at any prompt to cancel operation)\n");
        
        Employee found = null;
        
        while(found == null) {
            String empID = ask("Enter Employee ID: ");
            
            for(Employee e : employeeRecords) { 
                if(e.id.equalsIgnoreCase(empID)) { 
                    found = e; 
                    break; 
                }
            }
            if (found == null) {
                System.out.println("Employee not found. Please try again.");
            }
        }
        
        String tempID = found.id;
        String tempName = found.name;
        String tempDept = found.department;
        double tempSalary = found.salary;
        double tempTax = found.taxRate;
        
        boolean edit = true;
        while(edit) {
            System.out.println("\nCurrent Details: ");
            System.out.println("[1] ID: " + tempID);
            System.out.println("[2] Name: " + tempName);
            System.out.println("[3] Department: " + tempDept);
            System.out.println("[4] Salary: PHP " + tempSalary);
            System.out.println("[5] Tax Rate: " + tempTax + "%");
            System.out.println("[0] Exit Editing");
            
            try {
                int choice = Integer.parseInt(ask("\nSelect field to edit: "));
            
                switch (choice) {
                    case 1:
                        String newID = ask("Enter new ID: ");
                        if(newID.isBlank()) {
                            System.out.println("[ERROR] ID cannot be empty.");
                        } else {
                            boolean unique = true;
                            for(Employee e : employeeRecords) {
                                if(e.id.equalsIgnoreCase(newID) && !e.id.equalsIgnoreCase(tempID)) {
                                    System.out.println("An employee with the same ID is already existing. Try with another ID or delete the existing record.");
                                    unique = false;
                                    break;
                                }
                            }
                            if(unique) {
                                tempID = newID;
                                System.out.println("ID updated to: " + tempID);
                            }
                        }
                        break;
                        
                    case 2:
                        String newName = ask("Enter new Name: ");
                        if(newName.isBlank()) {
                            System.out.println("[ERROR] Name cannot be empty.");
                        } else {
                            tempName = newName;
                            System.out.println("Name updated.");
                        }
                        break;
                        
                    case 3:
                        showSelectDepartments();
                        tempDept = empDept;
                        System.out.println("Department updated to: " + tempDept);
                        break;
                        
                    case 4:
                        while(true) {
                            try {
                                double newSalary = Double.parseDouble(ask("Enter new Salary: PHP "));
                                if(newSalary < 0) {
                                    System.out.println("[ERROR] Salary cannot be a negative value. Please try again.");
                                    continue;
                                }
                                tempSalary = newSalary;
                                System.out.println("Salary updated.");
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid salary input. Please enter a valid number.");
                            }
                        }
                        break;
                        
                    case 5:
                        while(true) {
                            try {
                                double newTax = Double.parseDouble(ask("Enter new Tax Rate (e.g., enter 12 for 12%): "));
                                if(newTax < 0 || newTax > 100) {
                                    System.out.println("[ERROR] Tax rate must be between 0 and 100. Please try again.");
                                    continue;
                                }
                                tempTax = newTax;
                                System.out.println("Tax Rate updated.");
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid tax input. Please enter a valid number.");
                            }
                        }
                        break;
                        
                    case 0:
                        String confirm = ask("Save the changes? (Y/N): "); 
                        
                        if(confirm.equalsIgnoreCase("Y")) { 
                            found.id = tempID;
                            found.name = tempName;
                            found.department = tempDept;
                            found.salary = tempSalary;
                            found.taxRate = tempTax;
                            
                            found.netSalary = found.salary - (found.salary * (found.taxRate / 100)); 
                            System.out.println("Record updated successfully!");
                        } else {
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

    // DELETE EMPLOYEE RECORD
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
            System.out.println("Salary:   PHP " + found.salary);
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
     
    // COMPUTATION OF NET SALARY (BATCH)
    static void computation() {
        clearScreen();
        System.out.println("==============================================");
        System.out.println("               NET SALARY RECORDS");    
        System.out.println("==============================================");
        
        if(employeeRecords.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        } 
        
        double totalSalary = 0;
        double totalNetSalary = 0;
        
        for(Employee emp : employeeRecords) {
            emp.netSalary = emp.salary - (emp.salary * (emp.taxRate / 100));
            System.out.println("==============================================");
            System.out.println("EmployeeID: " + emp.id);
            System.out.println("Name: " + emp.name);
            System.out.println("Department: " + emp.department);
            System.out.println("Salary: PHP " + emp.salary);
            System.out.println("Tax Rate: " + emp.taxRate + "%");
            System.out.println("Net Salary: PHP " + emp.netSalary);
            totalNetSalary += emp.netSalary;
            totalSalary += emp.salary;
        } 
        
        System.out.println("==============================================");
        System.out.println("Total salary: PHP " + totalSalary);
        System.out.println("Total net salary: PHP " + totalNetSalary);
        System.out.println("==============================================");
    } 


    // SEARCHING EMPLOYEES BY DEPARTMENT
    static void searchbyDept() throws CancelOperation {
        System.out.println("\n========================================");
        System.out.println("      SEARCH EMPLOYEES BY DEPARTMENT      ");
        System.out.println("==========================================");
        System.out.println("(Enter 'X' at any prompt to cancel operation)\n");
        showSelectDepartments();

        ArrayList<Employee> filtered = new ArrayList<>();

        for (Employee e : employeeRecords) {
            if (e.department.equalsIgnoreCase(empDept)) {
                filtered.add(e);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("No employees found in " + empDept);
            return;
        }

        filtered.sort(
            Comparator.comparing((Employee e) -> e.name.toLowerCase())
                      .thenComparing((a, b) -> Double.compare(b.salary, a.salary))
        );

        System.out.println("\nEmployees in " + empDept + " Department:");
        
        System.out.printf("%-10s %-25s %-20s %-20s\n",
        "ID", "Name", "Department", "Salary");
        System.out.println("-------------------------------------------------------------------------------");

        for (Employee e : filtered) {
          System.out.printf("%-10s %-25s %-20s PHP %-15.2f\n",
             e.id, e.name, e.department, e.salary);
        }
    }
    
    // GENERATE PAYROLL SUMMARY (with timestamps for payroll history)
    static void genSummary() throws CancelOperation {
        System.out.println("\n========================================");
        System.out.println("          GENERATE PAYROLL SUMMARY        ");
        System.out.println("========================================");
        System.out.println("(Enter 'X' at any prompt to cancel operation)\n");
        
        if (employeeRecords.isEmpty()) {
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
            String timestamp = new SimpleDateFormat("MM-dd_HH-mm-ss").format(new Date());
            String filename = "payroll_summary_" + timestamp + ".txt";
            
            writer = new PrintWriter(new FileWriter(filename));
             
            writer.println("=============================================================================================");
            writer.println("                               EMPLOYEE PAYROLL SUMMARY                                      ");
            writer.println("=============================================================================================");
            writer.printf("%-10s %-25s %-20s %-18s %-18s\n", 
                    "ID", "NAME", "DEPARTMENT", "SALARY", "NET SALARY");
            writer.println("---------------------------------------------------------------------------------------------");

            double totalSalary = 0;
            double totalNetSalary = 0;

            try {
                employeeRecords.sort(Comparator.comparing(emp -> emp.department));
            } catch (Exception e) {
                System.out.println("[WARNING] Could not sort by department.");
            }

            for (Employee emp : employeeRecords) {
                try {
                    if (emp == null) continue;

                    emp.netSalary = emp.salary - (emp.salary * (emp.taxRate / 100));

                    writer.printf("%-10s %-25s %-20s PHP %-14.2f PHP %-14.2f\n",
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

            writer.println("---------------------------------------------------------------------------------------------");
            writer.printf("%-57s PHP %-14.2f PHP %-14.2f\n", "TOTAL:", totalSalary, totalNetSalary);
            writer.println("=============================================================================================");

            System.out.println("Payroll summary successfully generated!");
            System.out.println("File saved as: " + filename);

        } catch (IOException e) {
            System.out.println("[ERROR] File writing failed.");
        } finally {
            if (writer != null) writer.close();
        }
    }
            

    // MAIN METHOD
    public static void main (String Group2[]) {
        while (true) { 
            clearScreen(); 
            
            System.out.println("==============================================");
            System.out.println("Welcome to Employee Payroll Management System!");
            System.out.println("==============================================");
            System.out.println("Employees: " + employeeRecords.size());
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
    }
}