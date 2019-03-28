package impl;
import java.sql.*;
import java.util.ArrayList;

import interfaces.MySystem;
import valObject.Faculty;
import valObject.Mentor;
import valObject.Student;

public class MySystemImpl implements MySystem {
	
	
	Statement getConn() throws ClassNotFoundException, SQLException{
		 Class.forName("com.mysql.jdbc.Driver");
	   	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaproj2","root","root");
	   	 Statement st = con.createStatement();
	   	 return st ;
		
	}
	
    @Override
    public void addStudent(Student sObj) throws Exception {
     Statement st = getConn();
   	 String num = sObj.getRollNo();;
   	 String name = sObj.getName();
   	 String ban = sObj.getBranch();
   	 String insert = "Insert into students(roll_no,s_name,branch) values ('"+num+"','"+name+"','"+ban+"')";
   	 try {
   		 st.executeUpdate(insert);
   	 }catch(Exception e) {
   		 System.out.println(e);
   		 System.exit(0);
   	 }finally{
  		 st.close();
  	 }
   	 System.out.println("Inserting Complete");

    }
    @Override
    public void addFaculty(Faculty fObj) throws Exception {
     Statement st = getConn();
   	 String name = fObj.getName();
   	 String fid = fObj.getFID();
   	 String dept = fObj.getDept();
   	 
   	 String insert = "Insert into faculty(f_id,f_name,branch) values ('"+fid+"','"+name+"','"+dept+"')";
   	 try {
   		 st.executeUpdate(insert);
   	 }catch(Exception e) {
   		 System.out.println("Error while adding");
   		 System.exit(0);
   	 }finally{
   		 st.close();
   	 }
   	 System.out.println("Inserting Complete");
   	 
   	 //st.close();
    }
    
    @Override
    public void addMentor(Mentor mObj) throws SQLException, ClassNotFoundException {
     Class.forName("com.mysql.jdbc.Driver");
     Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaproj2","root","root");
     Statement st = con.createStatement();
     con.setAutoCommit(false);
     Savepoint savepoint1 = con.setSavepoint("Savepoint1");
     try{
   	 String fid = mObj.getFID();
   	 String regno = mObj.getRegNo();
   	 
   	 ResultSet rs1 = st.executeQuery("SELECT * FROM students where roll_no='"+regno+"'");
   	 rs1.next();
   	 String studentbranch = rs1.getString(3);
   	 ResultSet rs2 = st.executeQuery("SELECT * FROM faculty where f_id='"+fid+"'");
   	 rs2.next();
   	 int studentcount = rs2.getInt(4);
   	 System.out.println(studentbranch);
   	 System.out.println(rs2.getString(3));
   	 if(studentbranch.equals(rs2.getString(3)) && rs2.getInt(4)<5){
   	 String insert = "Insert into mentor(s_num,fac_id) values ('"+regno+"','"+fid+"')";
   	 String update = "Update faculty set studentsmentored="+(++studentcount)+" where f_id='"+fid+"'";
   	 try {
   		 st.executeUpdate(insert);
   		 st.executeUpdate(update);
   		 con.commit();
   	 }catch(Exception e) {
   		 System.out.println("error in inserting");
   		 System.exit(0);
   		 con.rollback(savepoint1);
   	 }
   	 System.out.println("Inserting Complete");
   	 }
   	 else{
   		 System.out.println(" Cannot insert sorry!Either the faculty has his hands full or the branch does not match");
   		 con.rollback(savepoint1);
   	 }
   	 rs1.close();
  	 rs2.close();
   	 }catch(Exception e) {
   		 System.out.println("Invalid entry");
   		 con.rollback(savepoint1);
   	 }finally{
  		 st.close();
  	 }
    }
    @Override
    public void removeStudent(String regno) throws Exception {
     Class.forName("com.mysql.jdbc.Driver");
	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaproj2","root","root");
	 Statement st = con.createStatement();
     con.setAutoCommit(false);
   	 int times = 0;
   	 Savepoint savepoint1 = con.setSavepoint("Savepoint1");
   	 String stmt = "delete from students where roll_no='"+regno+"'";
   	 try {
   		 times = st.executeUpdate(stmt);
   		 con.commit();
   		  System.out.println("Changed "+times+" rows");
      	 System.out.println("Deleted");
   	 }catch(Exception e) {
   		 System.out.println("Error While Removing");
   	     con.rollback(savepoint1);
   	 }finally{
  		 st.close();
  	 }
    }
    @Override
    public void removeFaculty(String fid) throws Exception {
    Statement st = getConn();
   	 int times = 0;
   	 
   	 String stmt = "delete from faculty where f_id="+fid;
   	 try {
   		 times = st.executeUpdate(stmt);
   	 }catch(Exception e) {
   		 System.out.println("Error While Removing");
   	 }finally{
  		 st.close();
  	 }
   	 System.out.println("Changed "+times+" rows");
   	 System.out.println("Deleted"); 	 
    }
    @Override
    public void removeMentor(String regno) throws Exception {
     Statement st = getConn();
   	 int times = 0;
   	 
   	 String stmt = "delete from mentor where s_num='"+regno+"'";
   	 ResultSet rs1 = st.executeQuery("select * from mentor where s_num='"+regno+"'");
   	 rs1.next();
   	 String facultyid = rs1.getString(2);
   	 ResultSet rs2 = st.executeQuery("SELECT * FROM faculty where f_id= '"+facultyid+"'");
   	 rs2.next();
   	 int studentcount = rs2.getInt(4);
   	 String update = "Update faculty set studentsmentored="+(--studentcount)+" where f_id='"+facultyid+"'";
   	 try {
   		 times = st.executeUpdate(stmt);
   		 st.executeUpdate(update);
   	 }catch(Exception e) {
   		 System.out.println("Error While Removing");
   	 }
   	finally{
  		 st.close();
  	 }
   	 System.out.println("Changed "+times+" rows");
   	 System.out.println("Deleted");
   	 
    }

    @Override
    public ArrayList<String> displayStudent() throws ClassNotFoundException, SQLException {
    Statement st = getConn();
   	 ArrayList<String> lines = new ArrayList<String>();
   	 
   	 ResultSet rs = st.executeQuery("SELECT * FROM students");
   	 
   	System.out.println(" ===============================================================");
   	 while(rs.next()) {
   		 lines.add("|\t"+rs.getString(1)+"\t|\t"+rs.getString(2)+"\t\t|\t"+rs.getString(3)+"\t|");
   	 }
   	
   	 rs.close();
   	 st.close();
   	
   	 return lines;
    }
    @Override
    public ArrayList<String> displayFaculty() throws Exception {
     Statement st = getConn();
   	 ArrayList<String> lines = new ArrayList<String>();
   	 
   	 ResultSet rs = st.executeQuery("SELECT * FROM faculty");
   	 
   	System.out.println(" ===============================================================");
   	 while(rs.next()) {
   		 lines.add("|\t"+rs.getString(1)+"\t|\t"+rs.getString(2)+"\t|\t"+rs.getString(3)+"\t|\t"+rs.getInt(4)+"|");
   	 }
   	 
   	 rs.close();
   	 st.close();
   	 return lines;
    }
    @Override
    public ArrayList<String> displayMentor() throws Exception {
    Statement st = getConn();
   	 ArrayList<String> lines = new ArrayList<String>();
   	 
   	 ResultSet rs = st.executeQuery("SELECT * FROM mentor");
   	System.out.println(" ===============================================================");
   	 while(rs.next()) {
   		 lines.add("|\t"+rs.getString(1)+"\t|\t"+rs.getString(2)+"\t|");
   	 }
   	 
   	 rs.close();
   	 st.close();
   	 return lines;
    }  
     
    @Override
    public ArrayList<String> generateReport() throws Exception {
    	Statement st = getConn();
      	 ArrayList<String> lines = new ArrayList<String>();
      	 int[] students = new int[20];
      	 int[] tutored = new int[20];
      	 int i=0;
      	ResultSet rs1 = st.executeQuery("select studentsmentored from faculty group by branch");
      	while(rs1.next()){
      		tutored[i]=Integer.parseInt(rs1.getString(1));
      		i++;
      	}
      	i=0;
      	 ResultSet rs = st.executeQuery(" select branch,count(*) from students group by branch");
      	System.out.println(" =================================================================================");
      	 while(rs.next()) {
      		 //if(rs.getString(1))
      		 students[i]=Integer.parseInt(rs.getString(2));
      		 lines.add("|\t"+rs.getString(1)+"\t|\t"+rs.getString(2)+"\t|\t"+(students[i]-tutored[i]));
      		 i++;
      	 }     	 
      	 rs.close();
      	 st.close();
      	 return lines;
    }
}
