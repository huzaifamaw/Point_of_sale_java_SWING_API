/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class DBmysqlconnection {
    Connection con;
    public void connection(){
        try{  
//    Class.forName("com.mysql.cj.jdbc.Driver");  
    con=DriverManager.getConnection("jdbc:sqlite:pos.db");  
    System.out.println("Connected");
     
    }catch(Exception e){ JOptionPane.showMessageDialog(null, e);}  
    }
    public String getCatagoryName(int id){
        String name = null;
        String sql = "Select * from catagories";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(rs.getInt(1)== id)
                {
                    name=rs.getString(2);
                    return name;
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return name;
    }
    public int getCatagoryID(String CatagoryName){
        int id =0;
        String sql = "Select * from catagories";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(rs.getString(2).equals(CatagoryName)){
                    id=rs.getInt(1);
                    return id;
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
    public int LoadItems(JTable j){
         
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
       
        int count =0;
        String sql = "Select * from itemslist";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                Object[] row = {rs.getString(1),rs.getString(2),rs.getString(3),this.getCatagoryName(rs.getInt(4))};
                model.addRow(row);
                ++count;
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return count;
     }
    public void loadCatagorycomboBox(JComboBox j){
         int id =0;
        String sql = "Select * from catagories";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                j.addItem(rs.getString(2));
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
     }
    public boolean InsertItems(String name , String Catagory , double rate){
        
        
        try{
        PreparedStatement s = con.prepareStatement("INSERT INTO \"itemslist\"(\"productname\",\"rate\",\"itemcatagory\") VALUES (?,?,?)");
            s.setString(1,name);
            s.setDouble(2, rate);
            s.setInt(3,this.getCatagoryID(Catagory));
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    }
    public boolean DeleteItems(int id){
        try{
        PreparedStatement s = con.prepareStatement("DELETE FROM itemslist WHERE (`productid` = ? )");
            s.setInt(1,id);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    } 
    public void refresh(){
        try{
        PreparedStatement p = con.prepareStatement("DROP TABLE \"main\".\"itemslist\";");
        p.executeUpdate();
        p.close();
            
       PreparedStatement t  = con.prepareStatement("ALTER TABLE itemslist ADD COLUMN `productid` INT NOT NULL PRIMARY KEY AUTOINCREMENT, ADD PRIMARY KEY (`productid`)");
       t.executeUpdate();
        t.close();
        }catch(Exception c){
           JOptionPane.showMessageDialog(null, c);
       }
    }
    public void update(int id , String name , double rate,String Catagory){
        try{
        PreparedStatement s = con.prepareStatement("UPDATE itemslist SET productname = ? , rate = ? , itemcatagory = ?  WHERE productid = ?");
            s.setString(1,name);
            s.setDouble(2, rate);
            s.setInt(3,this.getCatagoryID(Catagory));
            s.setInt(4, id);
            s.executeUpdate();

        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            
        }
    }
    public int checkfornameinitems(String name){
        String sql = "Select * from itemslist";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(name.replaceAll("\\s+","").equalsIgnoreCase(rs.getString(2).replaceAll("\\s+",""))){
                    JOptionPane.showMessageDialog(null, ""+name+" Already exists !");
                    return -1;
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
    public void searchByName(String name,JTable j){
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        
        String sql = "Select * from itemslist";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                
                if(rs.getString(2).toLowerCase().contains(name.toLowerCase())){
                Object[] row = {rs.getString(1),rs.getString(2),rs.getString(3),this.getCatagoryName(rs.getInt(4))};
                model.addRow(row);
                
                    
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    public void searchByCatagory(String name,JTable j){
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        
        String sql = "Select * from itemslist";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                
                if(this.getCatagoryName(rs.getInt(4)).equals(name)){
                Object[] row = {rs.getString(1),rs.getString(2),rs.getString(3),this.getCatagoryName(rs.getInt(4))};
                model.addRow(row);
                
                    
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    public void searchByCatagoryAndName(String name,String name2,JTable j){
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        
        String sql = "Select * from itemslist where itemcatagory = '"+this.getCatagoryID(name2)+"' ";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(rs.getString(2).toLowerCase().contains(name.toLowerCase())){
                Object[] row = {rs.getString(1),rs.getString(2),rs.getString(3),this.getCatagoryName(rs.getInt(4))};
                model.addRow(row);
                }
                    
                
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    public int LoadCatagories(JTable j){
         int count=0;
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
       
        
        String sql = "Select * from catagories";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                Object[] row = {rs.getString(1),rs.getString(2)};
                model.addRow(row);  
                ++count;
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return count;
     }
    public boolean InsertCatagory(String name ){
        
        
        try{
        PreparedStatement s = con.prepareStatement("INSERT INTO \"catagories\"(\"catagoryname\") VALUES (?)");
            s.setString(1,name);
            
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    }
    public void updatecatagory(int id , String name){
        try{
            PreparedStatement s = con.prepareStatement("UPDATE catagories SET catagoryname = ? WHERE catagoryid = ?");
            s.setString(1,name);
            s.setInt(2, id);
            s.executeUpdate();

        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            
        }
    }
    public int checkfornameinicatagory(String name){
        String sql = "Select * from catagories";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(name.replaceAll("\\s+","").equalsIgnoreCase(rs.getString("catagoryname").replaceAll("\\s+",""))){
                    JOptionPane.showMessageDialog(null, ""+name+" Already exists !");
                    
                    return -1;
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
    public boolean DeleteCatagory(int id){
        try{
        PreparedStatement s = con.prepareStatement("DELETE FROM catagories WHERE (`catagoryid` = ? )");
            s.setInt(1,id);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    } 
    public int LoadStock(JTable j){
         int count=0;
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
       
        
        String sql = "Select * from stock";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                Object[] row = {rs.getString(1),rs.getString(2),rs.getString(3)};
                model.addRow(row);  
                ++count;
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return count;
     }
    public boolean InsertStock(String name,String QTY ){
        
        
        try{
        PreparedStatement s = con.prepareStatement("INSERT INTO \"stock\"(\"stockname\",\"QTY\") VALUES (?,?)");
            s.setString(1,name);
            s.setString(2, QTY);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    }
    public void updateStock(int id , String name, String QTY){
        try{
            PreparedStatement s = con.prepareStatement("UPDATE stock SET stockname = ? , QTY = ? WHERE stockid = ?");
            s.setString(1,name);
            s.setString(2,QTY);
            s.setInt(3, id);
            s.executeUpdate();

        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            
        }
    }
    public int checkfornameinStock(String name){
        String sql = "Select * from stock";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(name.replaceAll("\\s+","").equalsIgnoreCase(rs.getString("stockname").replaceAll("\\s+",""))){
                    JOptionPane.showMessageDialog(null, ""+name+" Already exists !");
                    
                    return -1;
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
    public boolean DeleteStock(int id){
        try{
        PreparedStatement s = con.prepareStatement("DELETE FROM stock WHERE (`stockid` = ? )");
            s.setInt(1,id);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    } 
    public Vector getItemsInVector(int orderid){
        Vector list=new Vector(); 
        String sql = "Select * from itemslist";
        try{
             int arr[];
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            Vector names = new Vector();
            Vector price = new Vector();
            
         
            while(rs.next()){
                if(rs.getInt("itemcatagory")==orderid)
                {
                    names.add(rs.getString("productname"));
                    price.add(rs.getDouble("rate"));
                }
                
            }
            
            list.add(names);
            list.add(price);
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return list;
    }
    public Vector getcatagoriesInVector(){
        
        Vector names = new Vector();
        String sql = "Select * from catagories";
        try{
             int arr[];
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
            
            
         
            while(rs.next()){
               names.add(rs.getString("catagoryname"));
               
            }
            
            
           
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return names;
    }
    public int getnooftables(){
        int number=0;
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT * FROM tablenumber");
            int count=0;
            while(rs.next()){
                number=rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return number;
    }
    public void setnooftables(int n){
       try{
            PreparedStatement s = con.prepareStatement("UPDATE tablenumber SET tablenumber = ?");
            s.setInt(1,n);
            s.executeUpdate();

        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            
        } 
    }
    //deals
    public void ShowDealsIntable(JTable j){
            
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
       
        
        String sql = "Select * from Deals";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                Object[] row = {rs.getInt(1),rs.getString(2),rs.getInt(3)};
                model.addRow(row);
                
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

            
    }
    public boolean InsertDataInDeals(Double Price , String txt){
         try{
        PreparedStatement s = con.prepareStatement("INSERT INTO \"Deals\"(\"DealName\",\"Price\") VALUES (?,?)");
            s.setString(1,txt);
            s.setDouble(2, Price);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
        
    }
    public int getLastDealsID(){
        int number=0;
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT * FROM Deals");
            
            while(rs.next()){
                number=rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return number;
    }
    public boolean InsertItemsOfTheDeals(int dealid, String txt, int QTY){
        try{
        PreparedStatement s = con.prepareStatement("INSERT INTO \"SubDeals\"(\"itemName\",\"QTY\",\"DealID\") VALUES (?,?,?)");
            s.setString(1,txt);
            s.setDouble(2, QTY);
            s.setInt(3, dealid);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    }
    public void ShowItemsInComboBox(JComboBox j){
        
        String sql = "Select * from itemslist";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                j.addItem(rs.getString(2));
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    public void ShowDealsDeatilsIntable(JTable j,int id){
            
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
       
        
        String sql = "Select * from SubDeals";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(rs.getInt(4)==id){
                Object[] row = {rs.getInt(1),rs.getString(2),rs.getInt(3)};
                model.addRow(row);
                
                }
                
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

            
    }
    public int checkfornameinDeals(String name){
        String sql = "Select * from Deals";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(name.replaceAll("\\s+","").equalsIgnoreCase(rs.getString(2).replaceAll("\\s+",""))){
                    JOptionPane.showMessageDialog(null, ""+name+" Already exists !");
                    return -1;
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
    public boolean deleteItemIndeals(int id){
        
        try{
         PreparedStatement s = con.prepareStatement("DELETE FROM SubDeals WHERE (`subid` = ? )");
            s.setInt(1,id);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    }
    public boolean updateDealsList(int id , String txt , int dealid , int QTY){
        try{
            PreparedStatement s = con.prepareStatement("UPDATE SubDeals SET itemName = ? , QTY = ? , DealID = ? WHERE subid = ?");
            s.setString(1,txt);
            s.setInt(2, QTY);
            s.setInt(3, dealid);
            s.setInt(4, id);
            s.executeUpdate();
            return true;

        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            
        }
        return false;
    }
    public boolean deleteAllItemsIndeals(int id){
        
        try{
         PreparedStatement s = con.prepareStatement("DELETE FROM SubDeals WHERE (`DealID` = ? )");
            s.setInt(1,id);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    }
    public boolean deleteDeal(int id){
        try{
         PreparedStatement s = con.prepareStatement("DELETE FROM Deals WHERE (`DealsID` = ? )");
            s.setInt(1,id);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    }
    public boolean UpdateDealnameorprice(int id, String txt , double q ){
        try{
        PreparedStatement s = con.prepareStatement("UPDATE Deals SET DealName = ? , Price = ?  WHERE DealsID = ?");
            s.setString(1,txt);
            s.setDouble(2,q);
            s.setInt(3,id);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
    }
    //Order
    public Vector getDealsItems(){
        Vector list=new Vector(); 
        String sql = "Select * from Deals";
        try{
             int arr[];
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            Vector names = new Vector();
            Vector price = new Vector();
            
         
            while(rs.next()){
                
                    names.add(rs.getString("DealName"));
                    price.add(rs.getDouble("Price"));
                
                
            }
            
            list.add(names);
            list.add(price);
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return list;
    }
    public boolean InsertDataInOrder(int table,String OrderDate, double price, String status, String OrderType,String time){
         try{
        PreparedStatement s = con.prepareStatement("INSERT INTO \"OrderMain\"(\"table\",\"orderdate\",\"totalprice\",\"status\",\"ordertype\",\"OrderTime\") VALUES (?,?,?,?,?,?)");
            s.setInt(1, table);
            s.setString(2, OrderDate);
            s.setDouble(3, price);
            s.setString(4, status);
            s.setString(5, OrderType);
            s.setString(6, time);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
        
    }
    public boolean InsertDataInSubOrder(int orderid,String itemname, int QTY, double rate, double amount){
         try{
        PreparedStatement s = con.prepareStatement("INSERT INTO \"suborder\"(\"orderid\",\"itemname\",\"QTY\",\"rate\",\"amount\") VALUES (?,?,?,?,?)");
            s.setInt(1, orderid);
            s.setString(2, itemname);
            s.setInt(3, QTY);
            s.setDouble(4, rate);
            s.setDouble(5, amount);
            s.executeUpdate();
            return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            return false;
        }
        
    }
    public void showOrderStatus(JTable j){
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        String sql = "Select * From OrderMain" ;
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
            LocalDateTime now = LocalDateTime.now();  
            
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            Date date = new Date();  
            String date1 = formatter.format(date);
            
            while(rs.next()){
                if(rs.getString("orderdate").equals(date1)){
                Object[] row = {rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7)};
                model.addRow(row);}
                
                
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public int getorderid(){
        int number=0;
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT * FROM OrderMain");
            
            while(rs.next()){
                number=rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return number;
    }
    public boolean update(String status,int id){
        try{
        PreparedStatement s = con.prepareStatement("UPDATE OrderMain SET status = ? WHERE orderid = ?");
        s.setString(1,status);
        s.setInt(2, id);
        s.executeUpdate();
        return true;
        }catch(Exception c){
            JOptionPane.showMessageDialog(null, c);
            
        }
        return false;
    }
    public String getTime(int a){
       int number=0;
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT * FROM OrderMain");
            
            while(rs.next()){
                if(a==rs.getInt(1))
                return rs.getString("OrderTime");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }
    public String getDate(int a){
        int number=0;
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT * FROM OrderMain");
            
            while(rs.next()){
                if(a==rs.getInt(1))
                return rs.getString("orderdate");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }
    //SalesReport
    public void loadItemscomboBox(JComboBox j){
         int id =0;
        String sql = "Select * from itemslist ";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                j.addItem(rs.getString(2));
            }
            this.loadDealscomboBox(j);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
     }
    public void loadDealscomboBox(JComboBox j){
         int id =0;
        String sql = "Select * from Deals ";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                j.addItem(rs.getString(2));
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
     }
    public void showOrderAll(JTable j){
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        String sql = "Select * From OrderMain" ;
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                
                this.showSubOrderAll(j,rs.getInt(1),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6));
                
                
                
            }
            
            
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void showSubOrderAll(JTable j, int orderid , String date, double price , String status , String ordertype){
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        String sql = "Select * From suborder" ;
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                if(rs.getInt(2)==orderid){
                Object[] row = {orderid,rs.getInt(1),date,rs.getString(3),rs.getInt(4),price,status,ordertype};
                model.addRow(row);}
                
                
            }
            
            
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void showOrderAllBydates(JTable j, int sdate , int smonth , int syear, int edate , int emonth , int eyear){
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        String sql = "Select * From OrderMain " ;
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
            while(rs.next()){
            int date = Integer.parseInt(rs.getString(3).substring(0,2));
            int month = Integer.parseInt(rs.getString(3).substring(3,5));
            int year = Integer.parseInt(rs.getString(3).substring(6,10));
                
//                if(date>=sdate&&month>=smonth && year>=syear && date<=edate && month<=emonth && year<=eyear){
                if(year>=syear && year <=eyear){
//                    if(month>=smonth && month<=emonth){
//                           if(date>=sdate && date<=edate){
                
                this.showSubOrderAll(j,rs.getInt(1),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6));}
                
                
                
            }
            
            
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void showOrderAllBydatesS(JTable j, Date sdate , Date edate){
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        String sql = "Select * From OrderMain " ;
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
            while(rs.next()){
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = null;
                try {
                    date1 = sdf.parse(rs.getString(3));
                } catch (ParseException ex) {
                    Logger.getLogger(DBmysqlconnection.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                if((date1.after(sdate) || date1.equals(sdate)) && (date1.before(edate)|| date1.equals(edate))){
                
                this.showSubOrderAll(j,rs.getInt(1),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6));}
                
                
                
            }
            
            
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public boolean login (String username , String pass){
        
        String sql = "Select * from login";
        try{
             
            Statement pst = con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            
         
            while(rs.next()){
                
                if(rs.getString(1).equals(username) && (rs.getString(2).equals(pass) || rs.getString(3).equals(pass) ))
                {
                    return true;
                    
                }
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
}
        
        
     
    
