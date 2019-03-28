package temp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;


import exceptions.addFacultyException;
import exceptions.addUserException;
import exceptions.invalidRegnoException;
import exceptions.invalidfidException;
import exceptions.mentorComboException;
import interfaces.MySystem;
import valObject.Faculty;
import valObject.Mentor;
import valObject.Student;

public class MySystemImpl implements MySystem {
	static Date date = new Date();
    public void addStudent(Student sObj) {
    	Boolean flag = true;
		String name = sObj.getName();
		String regno = sObj.getRollNo();
		String branch = sObj.getBranch();
		 try {
			File inputFile = new File("Student.txt");	
	    	BufferedReader reader = new BufferedReader(new FileReader(inputFile));    	
	    	String curLine;		
    		while((curLine = reader.readLine())!=null) {
    			String[] sent = curLine.split(",");
    			try {
    				if (sent[1].equals(regno)) {
    					flag=false;
    					throw new addUserException("Register No Already Exists");
    				}
    			}catch (Exception e) {
    				e.printStackTrace(); 			
    			}
    		}
    		if(flag) {
    			FileWriter writer = new FileWriter("Student.txt", true);
    			writer.write(name+','+regno+','+branch+'\n');
    			writer.close();
    		}
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	}
    
    
    public void addFaculty(Faculty fObj) {
    	Boolean flag = true;
    	String name = fObj.getName();
    	String fid = fObj.getFID();
    	String dept = fObj.getDept();
    	try {
    		File inputFile = new File("Faculty.txt");	    			
	    	BufferedReader reader = new BufferedReader(new FileReader(inputFile));    	
	    	String curLine;
	    	while((curLine = reader.readLine())!=null) {
    			String[] sent = curLine.split(",");
    			try {
    				if (sent[1].equals(fid)) {
    					flag=false;
    					throw new addFacultyException("Faculty No Already Exists");
    				}
    			}catch (Exception e) {
    				e.printStackTrace(); 			
    			}
    		}
	    	if(flag) {
    			FileWriter writer = new FileWriter("Faculty.txt", true);
    			writer.write(name+','+fid+','+dept+'\n');
    			writer.close();
    		}
    	
    	} catch (Exception e) {
 			e.printStackTrace();
 		}
    }
    public void addMentor(Mentor mObj) {
    	Boolean flag = false, flag1 = false, flag2 = true;
    	int count = 0;
    	String fid = mObj.getFID();
    	String regno = mObj.getRegNo();
    	try {
    		File inputFile = new File("Student.txt");
    		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    		
	    	String curLine;		
    		while((curLine = reader.readLine())!=null) {
    			String[] sent = curLine.split(",");
    			try {
    				if (sent[1].equals(regno)) {
    					flag=true;
    				}
    			}catch (Exception e) {
    				e.printStackTrace(); 			
    			}
    		}
    		reader.close();
    		
    		File inputFile1 = new File("Faculty.txt");
    		BufferedReader reader1 = new BufferedReader(new FileReader(inputFile1));
    		String curLine1 = null;
    		while((curLine1 = reader1.readLine())!=null) {
    			String[] sent1 = curLine1.split(",");
    			try {
    				if(sent1[1].equals(fid)) {
    					flag1=true;
    				}
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    		reader1.close();
    		File inputFile3 = new File("Mentor.txt");
    		BufferedReader reader3 = new BufferedReader(new FileReader(inputFile3));
    		
    		String curLine2;
    		while((curLine2 = reader3.readLine())!=null) {
    			String[] sent2 = curLine2.split(",");
    			try {
    				if(sent2[1].equals(regno)) {
    					flag2=false;
    					System.out.println("ERROR!!Student is already being mentored");
    					throw new mentorComboException("Student is already being mentored");
    				}
    				if(sent2[0].equals(fid)) {
    					count++;
    					/*System.out.println("ERROR!!Student is already being mentored");
    					throw new mentorComboException("Student is already being mentored");*/
    				}
    				if(count==5) { 
    					flag2=false;
    					System.out.println("ERROR!!Faculty can mentor only a maximum of 5 students");
    				}
    			}catch(Exception e) {
    				e.getStackTrace();
    			}
    		}
    		while((curLine2 = reader3.readLine())!=null) {
    			String[] sent2 = curLine2.split(",");
    			try {
    				
    			}catch(Exception e) {
    				e.getStackTrace();
    			}
    		}
    		if(flag && flag1 && flag2) 
    		{  
    			FileWriter writer = new FileWriter("Mentor.txt", true);
    			writer.write(fid+','+regno+'\n');
    			writer.close();
    		}
    		reader3.close();
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    public void removeStudent(String regno) {
    	try {
    		File inputFile = new File("Student.txt");
    		File tempFile = new File("MyTempFile.txt");
		
    		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
    		String lineToRemove = regno;
    		String curLine;
		
    		while((curLine = reader.readLine())!=null) {
    			String[] sent = curLine.split(",");
    			if (sent[1].equals(lineToRemove)){
    				System.out.println("Line Removed");
    			}else {
    				writer.write(curLine+'\n');
    			}
    		}
    		reader.close();
    		writer.close();
    		inputFile.delete();
    		if(tempFile.renameTo(inputFile)) {
    			System.out.println("Changed");
    		}
    		removeMentor(regno);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void removeFaculty(String fid) {
    	try {
    		File inputFile = new File("Faculty.txt");
    		File tempFile = new File("MyTempFile.txt");
    		
    		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    		
    		String lineToRemove = fid;
    		String curLine;
    		
    		while((curLine = reader.readLine())!=null) {
    			String[] sent = curLine.split(",");
    			if (sent[1].equals(lineToRemove)){
    				System.out.println("Line Removed");
    			}else {
    				writer.write(curLine+'\n');
    			}
    		}
    		reader.close();
    		writer.close();
    		inputFile.delete();
    		if(tempFile.renameTo(inputFile)) {
    			System.out.println("Changed");
    		}
    		removeFacultyMentor(fid);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void removeFacultyMentor(String fid) {
    	try {
    		File inputFile = new File("Mentor.txt");
    		File tempFile = new File("MyTempFile.txt");
    		
    		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    		
    		String lineToRemove = fid;
    		String curLine;
    		
    		while((curLine = reader.readLine())!=null) {
    			String[] sent = curLine.split(",");
    			if (sent[0].equals(lineToRemove)){
    				System.out.println("Line Removed");
    			}else {
    				writer.write(curLine+'\n');
    			}
    		}
    		reader.close();
    		writer.close();
    		inputFile.delete();
    		if(tempFile.renameTo(inputFile)) {
    			System.out.println("Changed");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public void removeMentor(String regno) {
    	try {
    		File inputFile = new File("Mentor.txt");
    		File tempFile = new File("MyTempFile.txt");
    		
    		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    		
    		String lineToRemove = regno;
    		String curLine;
    		
    		while((curLine = reader.readLine())!=null) {
    			String[] sent = curLine.split(",");
    			if (sent[1].equals(lineToRemove)){
    				System.out.println("Line Removed");
    			}else {
    				writer.write(curLine+'\n');
    			}
    		}
    		reader.close();
    		writer.close();
    		inputFile.delete();
    		if(tempFile.renameTo(inputFile)) {
    			System.out.println("Changed");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public ArrayList<String> displayStudent(){
    	ArrayList<String> lines = new ArrayList<String>();
    	try {
    		File inputfile = new File("Student.txt");
    		BufferedReader reader = new BufferedReader(new FileReader(inputfile));
    		String curLine;
    		while((curLine = reader.readLine())!=null) {
    			lines.add(curLine);
    		}
    		reader.close();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}return lines;
    }
    
    public ArrayList<String> displayFaculty(){
    	ArrayList<String> lines = new ArrayList<String>();
    	try {
    		File inputfile = new File("Faculty.txt");
    		BufferedReader reader = new BufferedReader(new FileReader(inputfile));
    		String curLine;
    		while((curLine = reader.readLine())!=null) {
    			lines.add(curLine);
    		}
    		reader.close();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}return lines;
    }
    
    public ArrayList<String> displayMentor(){
    	ArrayList<String> lines = new ArrayList<String>();
    	try {
    		File inputfile = new File("Mentor.txt");
    		BufferedReader reader = new BufferedReader(new FileReader(inputfile));
    		String curLine;
    		while((curLine = reader.readLine())!=null) {
    			lines.add(curLine);
    		}
    		reader.close();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}return lines;
    	
    }


	@Override
	public ArrayList<String> generateReport() throws Exception {
		// TODO Auto-generated method stub
		return null;
	} 
    
    
}
