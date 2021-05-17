import java.util.ArrayList;

public class Model{ 
	private ArrayList<Teacher> teachers;
	private ArrayList<Course> courses;
    public Model(ArrayList<Teacher> teachers, ArrayList<Course> courses) {
    	this.teachers = teachers;
    	this.courses = courses;
    }
           
    public ArrayList<Teacher> getTeachers() {
		return teachers;
	}
	public ArrayList<Course> getCourses() {
		return courses;
	}

	public ArrayList<String> getCourseNames(){
		ArrayList<String> courseNames = new ArrayList<String>();
		for(int i=0;i<courses.size();i++) {
			courseNames.add(courses.get(i).getName());
		}
		return courseNames;
	}
	
	public static void main(String [] args)	{
    	//create teachers and courses
    	ArrayList<String> teachingLevelEd = new ArrayList<String>();
    	teachingLevelEd.add("Level1");  
    	teachingLevelEd.add("Level2");  
    	teachingLevelEd.add("Level3");  
    	Teacher Ed = new Teacher("Ed", teachingLevelEd);    	
    	ArrayList<String> teachingLevelWill = new ArrayList<String>();
    	teachingLevelWill.add("Level1");  
    	teachingLevelWill.add("Level2");  
    	Teacher Will = new Teacher("Will", teachingLevelWill);    	
    	ArrayList<String> teachingLevelTom = new ArrayList<String>();
    	teachingLevelTom.add("Level1");  
    	teachingLevelTom.add("Level2");  
    	Teacher Thom = new Teacher("Thom", teachingLevelTom);    	
    	ArrayList<String> teachingLevelCon = new ArrayList<String>();
    	teachingLevelCon.add("Level1"); 
    	Teacher Con = new Teacher("Con", teachingLevelCon);    	
    	ArrayList<String> teachingLevelEli = new ArrayList<String>();
    	teachingLevelEli.add("Level1"); 
    	Teacher Eli = new Teacher("Eli", teachingLevelEli);
    	    	    	
    	ArrayList<String> courseRequirementsC1 = new ArrayList<String>();
    	courseRequirementsC1.add("Level1");  	
    	Course c1 = new Course("c1",2,courseRequirementsC1);
    	ArrayList<String> courseRequirementsC2 = new ArrayList<String>();
    	courseRequirementsC2.add("Level1");
    	courseRequirementsC2.add("Level2");    	
    	Course c2 = new Course("c2",2,courseRequirementsC2);
    	ArrayList<String> courseRequirementsC3 = new ArrayList<String>();
    	courseRequirementsC3.add("Level1");
    	courseRequirementsC3.add("Level2"); 
    	courseRequirementsC3.add("Level3"); 
    	Course c3 = new Course("c3",3,courseRequirementsC3);
    	
    	//put teachers and courses into arrays
    	ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    	teachers.add(Ed);
    	teachers.add(Will);
    	teachers.add(Con);
    	teachers.add(Eli);
    	teachers.add(Thom);
    	ArrayList<Course> courses = new ArrayList<Course>();
    	courses.add(c1);
    	courses.add(c2);
    	courses.add(c3);
    	
    	//add teachers and courses to model, add model to view and controller
		Model model = new Model(teachers,courses);
		VC viewController = new VC(model);
		viewController.setVisible(true);
	}
}

