/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataManagement;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @Note 
 *  - i just put deprecated since this the behavior 
 *    of each methods in this class is not yet full 
 *    implemented. If you know how to write an object
 *    to a file with append mode, then successfully 
 *    be able to read the file, please implement it here.
 * --------------->>> THANKS IN ADVANCE <<<---------------
 * |                  From: John Cipher                  |
 * -------------------------------------------------------
 */
@Deprecated
public class ExpenseManagerObjectFileBased implements ExpenseManagement{
    private File file;
    @Override
    public boolean record(ArrayList<Expense> exps) throws Exception{
        File folder = new File("expenses");
        if(!(folder.exists())) folder.mkdir();
        
        file = new File(folder,"data.txt");
        if(!(file.exists())) file.createNewFile();
       
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file,true))){
            for(Expense exp : exps){
                oos.writeObject(exp);
            }
            oos.flush();
            System.out.println("Status: Expense Recorded Successfully");
        }catch(Exception e){
            System.err.println("Status: Expense Not recorded");
        }
        return false;
        
    }

    @Override
    public ArrayList<Expense> view() throws Exception{
        try(
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis))
        {
            ArrayList<Expense> fetch = new ArrayList<>();
            Expense data = null;
            while(true){
                try{
                    data = (Expense) ois.readObject();
                    fetch.add(data);
                }catch(EOFException e){
                    break;
                }
            }
            System.out.println("Fetch Data: "+fetch);
            return fetch;
        }catch(Exception e){
            System.err.println("Error: fetching failed");
            return null;
        }
    }

    @Override
    public Expense  delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }  

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
