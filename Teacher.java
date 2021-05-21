import java.util.ArrayList;

public class Teacher {
	private String name;
	private ArrayList <String> trainingAttended;
	private ArrayList <String> trainingToAttend = new ArrayList<String>();

	public Teacher(String n, ArrayList <String> tc) {
		this.name=n;
		this.trainingAttended = tc;
	}
	
	public void addCourseAttended(String tc) {
		this.trainingAttended.add(tc);
	}
	public void addCourseToAttend(String tcta) {
		this.trainingToAttend.add(tcta);
	}
	
	public String getName() {
		return this.name; 
	}
	public ArrayList <String> getTrainingAttended(){
		return this.trainingAttended;
	}
	public ArrayList <String> getTrainingToAttend(){
		return this.trainingToAttend;
	}
	
	public String toString() {
		String str = this.name + ":";
		for(int i=0; i<trainingToAttend.size();i++) {
			str += " " + trainingToAttend.get(i);
		}
		return str;
	}
	
}
