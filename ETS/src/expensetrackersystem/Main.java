/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package expensetrackersystem;

import DataManagement.Expense;
import DataManagement.ExpenseManagement;
//import DataManagement.ExpenseManager;
import DataManagement.ExpenseManagerFileBased;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 *
 * @author hunter
 */
public class Main {
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        try (Scanner input = new Scanner(System.in)) {
            ExecutionManager exec = new ExecutionManager();
            do{
                exec.cls();//clears the whole screen before prompting menu
                int opt = exec.viewMenu();
                switch(opt){
                    case 0 -> {
                        exec.viewExtMsg();
                        System.exit(0);
                    }
                    case 1  -> exec.record();
                    case 2  -> exec.view();
                    case 3  -> exec.delete();
                }
                if(exec.STATUS_OCCURED && !exec.STATUS_MSG.isBlank()) {
                    System.out.println("_____________________________");
                    System.err.println(exec.STATUS_MSG);
                }
                System.out.print("press enter key to continue");
                input.nextLine();
            }while(true);
        }
    }
    
    //INNER CLASS for the execution processes
    private static class ExecutionManager{
        private final ExpenseManagement manage;
        private final Scanner input;
        private String STATUS_MSG="";
        private boolean STATUS_OCCURED;
        
        private void cls(){
            try{
                if(System.getProperty("os.name").contains("Windows")){
                  new ProcessBuilder("cmd","/c", "cls").inheritIO().start().waitFor();
                }else{
                     new ProcessBuilder("cmd","/c", "clear").inheritIO().start().waitFor();
                }
            }catch(IOException | InterruptedException e){
                System.out.println("System clear interrupted");
            }
        }
        
        private ExecutionManager(){
            manage = new ExpenseManagerFileBased();
            input = new Scanner(System.in);
        }
        
        //Validates the input value
        private int isDigit(String optS) {
            boolean isDigit=false;
            if(!optS.isBlank()){
                char[] keys = optS.toCharArray();
                for(char key : keys){
                    if(Character.isDigit(key))
                        isDigit=true;
                }
                if(isDigit && keys.length==1){
                    return Integer.parseInt(optS);
                }else if(isDigit && keys.length>1){
                    STATUS_OCCURED = true;
                    STATUS_MSG = "Error: 1 digit number only";
                }else{
                    STATUS_OCCURED = true;
                    STATUS_MSG = "Error: Invalid Input";
                }
            }else if(optS.isBlank()){
                STATUS_OCCURED = true;
                STATUS_MSG = "Error: No inputted value";
            }
            return -1;
        }
        private int viewMenu(){
            //int s= manage.size();
            System.out.println("@Author: JohnCipher          ");
            System.out.println("@Version: 1.0                ");
            System.out.println("-----------------------------");
            System.out.println("    EXPENSE TRACKER SYSTEM   ");
            System.out.println("_____________________________");
            System.out.println("|--->     SELECTION     <---|");
            System.out.println("|    [1] Record Expense     |");
            System.out.println("|    [2] View Expense       |");
            System.out.println("|    [3] Delete Rcrd Exp    |");
            System.out.println("|    [0] Exit               |");
            System.out.println("_____________________________");
            System.out.print(" Opt->> ");
            String optS = input.nextLine();
            int opt;
            if((opt=isDigit(optS))!=-1) return opt;
            else opt = -1;
            return opt;
        }
        
        private void record() throws Exception{
            ArrayList<Expense> exp = new ArrayList<>();
            String xpT;
            double amt;
            try{
                 System.out.print("Number of Expenses to record?: ");
                 int numExp = input.nextInt();
                 input.nextLine();
                 for(int i=1;i<=numExp;i++){
                    System.out.println("_____________________________");
                    System.out.print("Expense Type: ");
                    xpT = input.nextLine();
                    System.out.print("Amount: ");
                    try{
                        amt = input.nextDouble();
                        exp.add(new Expense(LocalDateTime.now(),xpT,amt));

                        if(toRecord(exp)) STATUS_MSG = " ";//it means successful
                        else STATUS_MSG = "Status: Not recorded";
                    }catch(InputMismatchException ime){
                        if(i==numExp){
                            STATUS_OCCURED = true;
                            STATUS_MSG = "Error: Invalid Amount\nStatus: Not recorded";
                        }else{
                            System.out.println("Error: Invalid Amount");
                            System.out.println("Status: Not recorded");
                        }
                    }
                    input.nextLine();
                 }
            }catch(InputMismatchException ime){
               STATUS_OCCURED = true;
               STATUS_MSG = "Error: Invalid input, numbers only";
               input.nextLine();
            }
        }
        private boolean toRecord(ArrayList<Expense> exp) throws Exception{
            return manage.record(exp);
        }
        
        private void view() throws Exception{
            try{ 
                ArrayList<Expense> FetchExps = manage.view();
                if(!(FetchExps==null)){
                    cls();//clears the screen
                    System.out.println("[RECORDED LIST]");
                    System.out.println("_____________________________________________");
                    for(Expense xp : FetchExps){
                        System.out.println(xp.toView());
                    }
                    System.out.println("_____________________________________________");
                }else{
                    STATUS_OCCURED = true;
                    STATUS_MSG = "INFO: No expenses recorded";
                }
            }catch(NullPointerException npe){
                STATUS_OCCURED = true;
                System.out.println("This executed"); 
                STATUS_MSG = "INFO: No expenses recorded";
           }
        }
        
        private void delete(){ 
           System.out.println("To delete");
        }
        
        private void viewExtMsg(){
            System.out.println("_____________________________");
            System.out.println("      THANKS MY FRIEND!      ");
            System.out.println("     \"<CipherSociety>\"      ");
            System.out.println("-----------------------------");
        }  
    }//END OF INNER CLASS
     /*(Note) 
       - i just put private to all the methods of the 
         inner class for documentation purposes, i know 
         its not necessary...
     */
}
