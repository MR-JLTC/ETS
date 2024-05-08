/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author hunter
 */
public class ExpenseManagerFileBased implements ExpenseManagement{
    private File file;
    private final String fileN = "data.txt";
    private final String folderN = "expenses";
    
    @Override
    public boolean record(ArrayList<Expense> exps) throws Exception {
        File folder = new File(folderN);
        if(!(folder.exists())) folder.mkdir();
        file = new File(folder,fileN);
        if(!(file.exists())) file.createNewFile();
        
        try( 
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)
            )
        {
                for(Expense exp : exps){
                   pw.println(exp.toString());
                   pw.flush();
                }
            System.out.println("Status: Expense Recorded Successfully");
            return true;
        }catch(Exception e){
            System.err.println("Status: Expense Not recorded");
            return false;
        }
    }

    @Override
    public ArrayList<Expense> view() throws Exception {
        ArrayList<Expense> expList = new ArrayList<>();
        LocalDateTime ldt;
        String t;
        String date;
        String xpT;
        double amt;
        int size = 0;
        File folderPath = new File(folderN);
        file = new File(folderPath,fileN);
        try{
            try (Scanner scan = new Scanner(file)) {
                scan.useDelimiter("[,\n]");
                int count=0;
                while(scan.hasNext()){
                    date = scan.next();
                    t = scan.next();//this is the time
                    ldt = LocalDateTime.parse(date);
                    xpT = scan.next();
                    amt = Double.parseDouble(scan.next());
                    expList.add(new Expense(ldt,xpT,amt));
                    count++;
                }
                size = count;
                recordSize(size);
            }
             return expList;
        }catch(FileNotFoundException fnfe){
            return null;
        }
    }
    private void recordSize(int s) throws IOException{
        String filen = "size.txt";
        File folder = new File(folderN);
        if(!(folder.exists())) folder.mkdir();
        file = new File(folder,filen);
        if(!(file.exists())) file.createNewFile();
        
        try( 
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)
            )
        { 
            pw.write(String.valueOf(s));
        }
    }
    private int getSize(){
        File folderPath = new File(folderN);
        String filen = "size.txt";
        file = new File(folderPath,filen);
        try{
             Scanner scan = new Scanner(file);
             scan.useDelimiter("[,\n]");
             int s = 0;
             String ss = null;
             while(scan.hasNext()){
                 ss = scan.next();
             }
             s=Integer.parseInt(ss);
             return s;
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
            return 0;
        }
    }
    @Override
    public int size() {
        return this.getSize();
    }
    
    @Override
    public Expense delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
