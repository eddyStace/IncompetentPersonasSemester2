import java.util.ArrayList;

public class Model{ 
	private TeacherList teacherList;
	private CourseList courseList;
	public Assigner assigner = new Assigner(); 
	public FileHandler fileHandler = new FileHandler(); 
    public Model() {
    	this.teacherList = new TeacherList();
    	this.courseList = new CourseList();
    }
           
    public TeacherList getTeachers() {
		return teacherList;
	}
	public CourseList getCourses() {
		return courseList;
	}
	

//	//loop through courses requirements then teacher requirements to find list of teachers suitable to teach a given course
//	public ArrayList<String> teachersMeetRequirements(String courseName){
//		ArrayList<String> suitableTeachers = new ArrayList<String>();
//		for(int i=0; i<getCourses().getCourses().size();i++) { //loop through courses
//			if(getCourses().getCourses().get(i).getName().equals(courseName)) { //if course name selected is in array of courses
//				for(int j=0; j<getTeachers().getTeachers().size();j++) { //loop through teachers
//					for(int m=0; m<getTeachers().getTeachers().get(j).getTrainingAttended().size();m++) { //loop through teacher training
//						if(suitableTeacher(getTeachers().getTeachers().get(j),getCourses().getCourses().get(i).getTrainingRequired())) { //if teacher has all requiremnts
//							if(!getCourses().getCourses().get(i).getTeachers().contains(getTeachers().getTeachers().get(j))){ 	//if courses don't already have teacher allocated then add
//						 		if(!suitableTeachers.contains(getTeachers().getTeachers().get(j).getName())) { //if teachers are already in array
//						 			suitableTeachers.add(getTeachers().getTeachers().get(j).getName());    //add teacher to string of suitable teachers
//								}
//					 		}
//						}
//					}
//				}
//			}
//		}
//		return suitableTeachers;
//	}
//	
//	public boolean suitableTeacher(Teacher t, ArrayList<String> trainingRequired) {
//		//if one of course requirements is not in teacher skillset then return false
//		for(int i=0;i<trainingRequired.size();i++) { //loop through array
//			ArrayList<String> ts = t.getTrainingAttended();
//			if(!t.getTrainingAttended().contains(trainingRequired.get(i))) { //if teaching Required is not in skillset of teacher 
//				return false;
//			}
//		}
//		return true;
//	}
//	
//	//find list of teachers that need training to teach a given course,
//	public ArrayList<String> teachersNeedTraining(String courseName){
//		ArrayList<String> suitableTeachers = new ArrayList<String>();
//			for(int i=0; i<getCourses().getCourses().size();i++) { //loop through courses 
//				if(getCourses().getCourses().get(i).getName().equals(courseName)) { //if course name selected is in array of courses
//					for(int j=0; j<getTeachers().getTeachers().size();j++) { //loop through teachers 
//						if(!getCourses().getCourses().get(i).getTeachers().contains(getTeachers().getTeachers().get(j))) {
//							suitableTeachers.add(getTeachers().getTeachers().get(j).getName());    //add teacher to string of suitable teachers
//						}
//					}
//				}
//			}
//	return suitableTeachers;
//	}	
//
//	//find correct course and add teachers that have been assigned to course and return list of courses that still need to fulfilled
//	public ArrayList<String> assignTeachers(String courseName, String teacherName,int button) {
//		ArrayList<String> updateText = new ArrayList<String>();
//		for(int i=0;i<getCourses().getCourses().size();i++) { //loop through courses in list of courses
//			if(getCourses().getCourses().get(i).getName().equals(courseName)) { //find the Course selected
//				for(int j=0;j<getTeachers().getTeachers().size();j++) { //loop through teachers 
//					if(getTeachers().getTeachers().get(j).getName().equals(teacherName)) { //find teacher selected
//						if(button==0) {
//							getCourses().getCourses().get(i).addTeacher(getTeachers().getTeachers().get(j)); //add this teacher to courses
//							updateText.add(getCourses().getCourses().get(i).toString()); //add teachers to string of teachers that have been assigned to course
//						}else{
//							findCourseToAttend(getTeachers().getTeachers().get(j),getCourses().getCourses().get(i).getTrainingRequired());//get teacher and add traning 
//							updateText.add(getTeachers().getTeachers().get(j).toString()); //add training that teacher needs to attend 
//						}
//						
//					}
//				}
//			}
//		}
//		return updateText; //get courses and return so that it can be printed to text area
//	}
//	
//	//helper method to loop through array of training required for a given course and add to teacher
//	public void findCourseToAttend(Teacher t, ArrayList<String> trainingRequired) {
//		for(int i=0;i<trainingRequired.size();i++) { //loop through array
//			if(!t.getTrainingAttended().contains(trainingRequired.get(i))) { //if teaching Required is not in skillset of teacher 
//				if(!t.getTrainingToAttend().contains(trainingRequired.get(i))) { //check if courseToAttend isn't already in in list of courses to attend
//					t.addCourseToAttend(trainingRequired.get(i));   //assign training to teacher
//				}
//			}
//		}
//	
//	}
	
	public String arrayListToString(ArrayList<String> str) {
		String string = " "; //make string array same size of array list
		for(int i=0;i<str.size();i++) { //loop through array and add to string array
			string+=str.get(i) + "\n";
		}		
		return string;
	}
}
	

	
		

