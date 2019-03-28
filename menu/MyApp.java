package menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import valObject.Faculty;
import valObject.Student;
import valObject.Mentor;
import impl.MySystemImpl;
import interfaces.MySystem;

public class MyApp{
	static Date date = new Date();
	public static void main(String arg[]) {
		Scanner Scan = new Scanner(System.in);
		int ch = 1;
		while(ch==1||ch==2||ch==3||ch==4||ch==5||ch==6||ch==7||ch==8||ch==9||ch==0){
			
			System.out.println("\t1: Add Student Details\n\t2: Add Faculty Details\n\t3: Add Mentor\n\t4: Remove a Student\n\t5: Remove a Faculty\n\t6: Remove Mentor\n\t7: Display Student\n\t8: Display Faculty\n\t9: Display Mentor\n\t 10:Generate report \n\t0:exit");
			System.out.println("Enter your Choice: ");
		    ch = Integer.parseInt(Scan.nextLine());
			switch(ch) {
				case 1:GetStudent();
						break;
				case 2:GetFaculty();
						break;
				case 3:GetMentor();
						break;
				case 4:GetStudentId();
						break;
				case 5:GetFacultyId();
						break;
				case 6:GetStdId();
						break;
				case 7:ShowStudent();
						break;
				case 8:ShowFaculty();
						break;
				case 9:ShowMentor();
						break;
				case 10:ShowReport();
						break;
				case 0:System.exit(0);
				default:System.out.println("Write Valid input");
			}
		}
		Scan.close();
	}
	
	private static void ShowReport(){
		MySystem myst = new MySystemImpl();
		ArrayList<String> Rlines = null;
		try {
			Rlines = myst.generateReport();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("|\tBranch\t|\tStudent count\t|\tcount of unmentored students\t|");
		System.out.println(" ================================================================================");
		for(String Line:Rlines) {
			System.out.println(Line);
		}
		System.out.println(" =================================================================================");
	}

	private static void ShowMentor() {
		MySystem myst = new MySystemImpl();
		ArrayList<String> Mlines = null;
		try {
			Mlines = myst.displayMentor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("|\tFacultyID\t|\tStudentRegNO\t|");
		System.out.println(" ===============================================================");
		for(String Line:Mlines) {
			System.out.println(Line);
		}
		System.out.println(" ===============================================================");
	}

	private static void ShowFaculty() {
		MySystem myst = new MySystemImpl();
		ArrayList<String> Flines = null;
		try {
			Flines = myst.displayFaculty();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("|  FacultyName  |  FacultyID     |    Department  |  tNo of Students Mentored  |");
		System.out.println(" ===============================================================");
		for(String Line:Flines) {
			System.out.println(Line);
		}
		System.out.println(" ===============================================================");
	}

	private static void ShowStudent() {
		MySystem myst = new MySystemImpl();
		ArrayList<String> Slines = null;
		try {
			Slines = myst.displayStudent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("|\tRollNumber\t|\tStudentName\t|\tBranch\t|");
		System.out.println(" ===============================================================");
		for(String Line:Slines) {
			System.out.println(Line);
		}
		System.out.println(" ===============================================================");
	}

	private static void GetStdId() {
		MySystem myst = new MySystemImpl();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Reg No to Remove");
		String regNo = scan.nextLine();
		try {
			myst.removeMentor(regNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void GetFacultyId() {
		MySystem myst = new MySystemImpl();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Faculty ID to Remove");
		String fid = scan.nextLine();
		try {
			myst.removeFaculty(fid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void GetStudentId() {
		MySystem myst = new MySystemImpl();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Reg Number to Remove");
		String regNo = scan.nextLine();
		try {
			myst.removeStudent(regNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void GetMentor() {
		MySystem myst = new MySystemImpl();	
		Scanner scan = new Scanner(System.in);
		Mentor mt = new Mentor();
		System.out.println("Enter the Faculty ID");
		mt.setFID(scan.nextLine());
		System.out.println("Enter the Student RegNo");
		mt.setRegNo(scan.nextLine());
		try {
			myst.addMentor(mt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void GetFaculty() {
		MySystem myst = new MySystemImpl();	
		Scanner scan = new Scanner(System.in);
		Faculty ft = new Faculty();
		System.out.println("Enter the Faculty Name");
		ft.setName(scan.nextLine());
		System.out.println("Enter the Faculty RegNo");
		ft.setFID(scan.nextLine());
		System.out.println("Enter The Department");
		ft.setDept(scan.nextLine());
		try {
			myst.addFaculty(ft);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void GetStudent() {
		MySystem myst = new MySystemImpl();	
		Scanner scan1 = new Scanner(System.in);
		Student st = new Student();
		System.out.println("Enter the Student Name");
		String name = scan1.nextLine();
		st.setName(name);
		System.out.println("Enter the Student RegNo");
		String reg = scan1.nextLine();
		st.setRollNo(reg);
		System.out.println("Enter the Student Course");
		String course = scan1.nextLine();
		st.setBranch(course);
		try {
			myst.addStudent(st);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}
