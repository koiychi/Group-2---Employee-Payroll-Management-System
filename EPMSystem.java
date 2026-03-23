//1. 

import java.util.*;
import java.io.*;

class EPMSystem {
    
    //Methods:
    /*
    Add employee - Miles
    Edit Record - Gian
    Delete Record - Enzo
    Salary Computation (Batch) - Charles
    Search by Department - Talusan
    Generate Summary (txt file) - Charlene
    */
    //Initialization: 
    //static int variable = 0;
    
    static void addEmployee() {
        
    }
    
    static void editRecord() {
        
    }
    
    static void deleteRecord() {
        
    }
    
    static void computation() {
        
    }
    
    static void searchbyDept() {
        
    }
    
    static void genSummary() {
        
    }
    
    
    
    public static void main (String Group2[]) {
        Scanner option = new Scanner(System.in);
        System.out.println("Welcome \nSelect operation: \n[1] Add Employee \n[2] Edit Record \n[3] Delete Record \n[4] Calculate Payroll \n[5] Search by Department \n[6] Generate Summary \n[0] Exit");
        System.out.print("Selected operation: ");
        int operation = option.nextInt();
        
        switch (operation) {
            case 1: 
                addEmployee();
            case 2:
                editRecord();
            case 3:
                deleteRecord();
            case 4:
                computation();
            case 5:
                searchbyDept();
            case 6:
                genSummary();
            case 0:
                System.out.println("Closing program ...");
                System.exit(0);
            default: //invalid?   
        }
        
    }
}






//PARKING            