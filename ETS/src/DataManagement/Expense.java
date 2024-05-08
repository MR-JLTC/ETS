/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataManagement;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author hunter
 */
public final class Expense{
    private final String expenseType;
    private final double amt;
    private final LocalDateTime dateRecorded;
    private final DecimalFormat df;
    
    public Expense(LocalDateTime time,String expnsT, double amt){
        this.dateRecorded = time;
        this.expenseType = expnsT;
        this.amt = amt;
        df = new DecimalFormat("#,###.##");
    }
    
    private String getWholeDateCreated(){
        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return dateRecorded.format(DateFormat);
    }
    
    private String getDateCreated(){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/d/yyyy");
        return dateRecorded.format(dateFormat);
    }
    
    private String getDay(){
        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EEEE");
        return dateRecorded.format(dayFormat);
    }
    
    private String getTime(){
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
        return dateRecorded.format(timeFormat);
    }
    
    @Override
    public String toString(){
        return getWholeDateCreated() +","+getTime()+","+expenseType+","+String.valueOf(amt);
    }
    
    public String toView(){
        return getDateCreated()+"    | "+getDay()+"    | "+getTime()+"    | "+expenseType+"    | "+String.valueOf(df.format(amt));
    }
}
