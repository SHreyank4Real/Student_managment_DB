package interfaces;

import java.util.ArrayList;

import valObject.Faculty;
import valObject.Mentor;
import valObject.Student;

public interface MySystem {
	void addStudent(Student sObj) throws Exception;
	void addFaculty(Faculty fObj) throws Exception;
	void addMentor(Mentor mObj) throws Exception;
	void removeStudent(String regno) throws Exception;
	void removeFaculty(String fid) throws Exception;
	void removeMentor(String regno) throws Exception;
	ArrayList<String> displayStudent() throws Exception;
	ArrayList<String> displayFaculty() throws Exception;
	ArrayList<String> displayMentor() throws Exception;
	ArrayList<String> generateReport() throws Exception;
}
