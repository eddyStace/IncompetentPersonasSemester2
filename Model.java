import java.util.ArrayList;

public class Model{ 
	private ArrayList<Teacher> teachers;
	private ArrayList<Course> courses;
    public Model() {
    	this.teachers = new ArrayList<Teacher>();
    	this.courses = new ArrayList<Course>();
//    	this.assingerObject = new Assigner(teachers, courses)  ------when adding assigner
    }
           
    public ArrayList<Teacher> getTeachers() {
		return teachers;
	}
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	//loop through courses requirements then teacher requirements to find list of teachers suitable to teach a given course
	public ArrayList<String> teachersMeetRequirements(String courseName){
		ArrayList<String> suitableTeachers = new ArrayList<String>();
		for(int i=0; i<this.courses.size();i++) { //loop through courses
			if(this.courses.get(i).getName().equals(courseName)) { //if course name selected is in array of courses
				for(int j=0; j<teachers.size();j++) { //loop through teachers
					for(int m=0; m<teachers.get(j).getTrainingAttended().size();m++) { //loop through teacher training
						if(teachers.get(j).getTrainingAttended().get(m).contains(courses.get(i).getTrainingRequired().get(courses.get(i).getTrainingRequired().size()-1))){ //if teacher has attended highest course requirement 
						 	if(!courses.get(i).getTeachers().contains(teachers.get(j))){ 	//if courses don't already have teacher allocated then add
						 		suitableTeachers.add(teachers.get(j).getName());    //add teacher to string of suitable teachers
							}
						}
					}
				}
			}
		}
		return suitableTeachers;
	}
	
	//find list of teachers that need training to teach a given course,
	public ArrayList<String> teachersNeedTraining(String courseName){
		ArrayList<String> suitableTeachers = new ArrayList<String>();
			for(int i=0; i<courses.size();i++) { //loop through courses 
				if(courses.get(i).getName().equals(courseName)) { //if course name selected is in array of courses
					for(int j=0; j<teachers.size();j++) { //loop through teachers 
						if(!courses.get(i).getTeachers().contains(teachers.get(j))) {
							suitableTeachers.add(teachers.get(j).getName());    //add teacher to string of suitable teachers
						}
					}
				}
			}
	return suitableTeachers;
	}	

	//find correct course and add teachers that have been assigned to course and return list of courses that still need to fulfilled
	public ArrayList<String> assignTeachers(String courseName, String teacherName,int button) {
		ArrayList<String> updateText = new ArrayList<String>();
		for(int i=0;i<courses.size();i++) { //loop through courses 
			if(courses.get(i).getName().equals(courseName)) { //find the Course selected
				for(int j=0;j<teachers.size();j++) { //loop through teachers 
					if(teachers.get(j).getName().equals(teacherName)) { //find teacher selected
						if(button==0) {
							courses.get(i).addTeacher(teachers.get(j)); //add this teacher to courses
							updateText.add(courses.get(i).toString()); //add teachers to string of teachers that have been assigned to course
						}else{	
							findCourseToAttend(teachers.get(j),courses.get(i).getTrainingRequired());//get teacher and add traning 
							updateText.add(teachers.get(j).toString()); //add training that teacher needs to attend 
						}
						
					}
				}
			}
		}
		return updateText; //get courses and return so that it can be printed to text area
	}
	
	//helper method to loop through array of training required for a given course and add to teacher
	public void findCourseToAttend(Teacher t, ArrayList<String> trainingRequired) {
		for(int i=0;i<trainingRequired.size();i++) { //loop through array
			if(!t.getTrainingAttended().contains(trainingRequired.get(i))) { //if teaching Required is not in skillset of teacher 
				if(!t.getTrainingToAttend().contains(trainingRequired.get(i))) { //check if courseToAttend isn't already in in list of courses to attend
					t.addCourseToAttend(trainingRequired.get(i));   //assign training to teacher
				}
			}
		}
	}
	
	public String arrayListToString(ArrayList<String> str) {
		String string = " "; //make string array same size of array list
		for(int i=0;i<str.size();i++) { //loop through array and add to string array
			string+=str.get(i) + "\n";
		}		
		return string;
	}

	public void addTeachers(ArrayList<Teacher> teachers) {
		for(int i=0; i<teachers.size();i++) {
			this.teachers.add(teachers.get(i));
		}
	}
	public void addCourses(ArrayList<Course> courses) {
		for(int i=0; i<courses.size();i++) {
			this.courses.add(courses.get(i));
		}
	}
		
}

