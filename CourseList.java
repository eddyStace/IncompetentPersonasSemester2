import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

//Implements the List interface that means we can make a generic array list containing this object
public class CourseList implements List{
	private ArrayList<Course> LoC = new ArrayList<Course>();
	
	//Constructor uses the course array created by the file parser to make courses and add them to the LoC
	public CourseList(ArrayList<String> coursesFromFile) {
		for(String string: coursesFromFile) {
			String line = string;
			Scanner s = new Scanner(line);
			String name = s.next();
			int staffNum = Integer.parseInt(s.next());
			ArrayList<String> trainingReq = new ArrayList<String>();
			while(s.hasNext()) {
				trainingReq.add(s.next());
			}
			Course c = new Course(name, staffNum, trainingReq);
			LoC.add(c);
			s.close();
		}
	}
	
	//returns the List of courses array
	public ArrayList<Course> getCourses(){
		return LoC;
	}
	//adds courses to the list of courses array
	public void addCourse(Course c) {
		LoC.add(c);
	}

	//empty teachers from the list of teachers
	public void empty() {
		this.LoC.clear();
	}
		
	//add array of teachers to the list of teachers
	public void updateCoursesArray(ArrayList<Course> cs) {
		empty();
		for(int i=0; i<cs.size();i++) {
			this.LoC.add(cs.get(i));
		}
	}
	
	//returns the names of the courses, method from the List interface
	public String getNames() {
		String line = "";
		for(Course courseNames: LoC) {
			line += courseNames.getName() + "\n";
		}
		return line;
	}
	//toString method 
	public String toString() {
		String line = "";
		Iterator<Course> i = LoC.iterator();
		if(i.hasNext())
			line += i.next();
		while(i.hasNext())
			line += i.next();
		
		return line;
	}
	public static void main(String[] args) {
        ArrayList<String>parsedCourses = new ArrayList<String>();
        parsedCourses.add("Programming 3 english,maths");
        parsedCourses.add("Database 2 english,maths");
        parsedCourses.add("Software_Engineering 1 maths");
        
        CourseList c = new CourseList(parsedCourses);
        System.out.println(c);
	}
}