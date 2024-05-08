/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataManagement;

import java.util.ArrayList;

/**
 *
 * @author hunter
 */
public interface ExpenseManagement {
    boolean record(ArrayList<Expense> exp) throws Exception;
    ArrayList<Expense> view() throws Exception;
    Expense delete();
    int size();
}
