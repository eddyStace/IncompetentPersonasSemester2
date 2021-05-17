import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class VC extends JFrame implements ActionListener{
	private JButton assignButton;
	private JButton assignTrainingButton;
	private JButton saveExit;
	private Panel panel1;
	private Panel panel2;
	private JTextArea text1;
	private JTextArea text2;
	private Model modelObject;
	private ArrayList <Teacher> teachers;
	private ArrayList <Course> courses;
	private String teacherName;
	private String courseName;
	private boolean teacherAssignment; 
	
	public VC(Model model) {
		//add model to VC
		modelObject = model;
		
		//initialise JFrame with size and title etc
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    this.setSize(600,400);
	    this.setLocation(100,100);
	    this.setTitle("PTT System");
	    this.setLayout(new FlowLayout());	
	    
	    //get courses and teachers from model 
		this.teachers = this.modelObject.getTeachers(); 
		this.courses = this.modelObject.getCourses();
		
	    //initialise panel
	    panel1 = new Panel("Pick a Course", this);
	    for(int i=0;i<courses.size();i++) {
	    	  if(!courses.get(i).fullyStaffed()) { //if course does not have enough staff, then do not show in list
	    		  panel1.addItem(courses.get(i).getName());
	    	  }
	    }
	 
//	    panel1.updateList(modelObject.getCourseNames());
	    panel2 = new Panel("Pick a Teacher", this);
	    //initialise buttons
	    this.assignButton = new JButton("Assign Teacher");
		this.assignButton.addActionListener(this);
		this.assignTrainingButton = new JButton("Assign Training");
		this.assignTrainingButton.addActionListener(this);
		this.saveExit = new JButton("Save & Exit");
		this.saveExit.addActionListener(this);
		//initialise text area
		this.text1 = new JTextArea(10,25);
		this.text1.setEditable(false);
		this.text2 = new JTextArea(10,25);
		this.text2.setEditable(false);
		//add panels and buttons to JFrame
		this.add(panel1);
		this.add(panel2);
	    this.add(assignButton);
	    this.add(assignTrainingButton);
	    this.add(saveExit);
	    this.add(text1);
	    this.add(text2);
	    
	    this.assignButton.setEnabled(false);
	    this.assignTrainingButton.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		//if user clicks on an item in the course list
		if(e.getSource()==this.panel1.getList()) {
			//set button to not be enabled
			this.assignButton.setEnabled(false);
			this.assignTrainingButton.setEnabled(false);
			teacherAssignment = false;
			//find what teachers can teach each course
			//retrieve the courses that was clicked
			JComboBox cb = (JComboBox)e.getSource();
			this.courseName = (String)cb.getSelectedItem();		
			
			//find what teachers have requirements to teach a course
			//loop through each course and compare the requirements with each teacher requirements
			this.panel2.deleteList(); //empty list to add new list of teachers
			for(int i=0; i<courses.size();i++) { //loop through course array
				if(courses.get(i).getName().equals(courseName)) { //if course name selected is in array of courses
					for(int j=0; j<teachers.size();j++) { //loop through teacher array
						for(int m=0; m<teachers.get(j).getTrainingAttended().size();m++) { //loop through teachinglevel array in teachers
							if(teachers.get(j).getTrainingAttended().get(m).contains(courses.get(i).getTrainingRequired().get(courses.get(i).getTrainingRequired().size()-1))){ //if teacher has attended course of highest course requirment 
								//if courses don't already have teacher allocated then add
								 if(!courses.get(i).getTeachers().contains(teachers.get(j))){
									 this.panel2.addItem(teachers.get(j).getName());
								 }
							}
						}
					}
				}
			}
			//if there are no teachers left for a given course, show teachers that are not already allocated to course to assign training
			if(this.panel2.getList().getItemCount()==1) { //if teacher list is empty
				this.teacherAssignment = true; 
				for(int i=0; i<courses.size();i++) {
					if(courses.get(i).getName().equals(courseName)) { //if course name selected is in array of courses
						for(int j=0; j<teachers.size();j++) { 
							if(!courses.get(i).getTeachers().contains(teachers.get(j))) {
								 this.panel2.addItem(teachers.get(j).getName());
							}
						}
					}
				}
			}	
			
		}else if(e.getSource()==this.panel2.getList()) { //if select teacher list
			//if a teacher needs to be allocated training then set assign button invisible
			if(teacherAssignment) {
				this.assignButton.setEnabled(false); 
				this.assignTrainingButton.setEnabled(true); 
			}else {
				this.assignButton.setEnabled(true); 
				this.assignTrainingButton.setEnabled(false); 
			}
			//retrieve the item that was clicked
			JComboBox cb = (JComboBox)e.getSource();
			teacherName = (String)cb.getSelectedItem();

		}else if(e.getSource()==this.assignButton) {		
			//find Course object with the selected courseName
			//Update Course object and allocate Teacher object
			for(int i=0;i<courses.size();i++) {
				if(courses.get(i).getName().equals(courseName)) { //find the Course selected
					for(int j=0;j<teachers.size();j++) {
						if(teachers.get(j).getName().equals(teacherName)) {
							courses.get(i).addTeacher(teachers.get(j));
							//get course and print to text area
							this.text1.setText(courses.get(i).toString());
						}
					}
				}
			}
			//remove teacher from list and set selection to empty element
			this.panel2.removeItem(teacherName); 
			this.panel2.setSelection();
			
			//if course has been allocated sufficient teachers, remove course from list
			this.panel1.deleteList();
			  for(int i=0;i<courses.size();i++) {
		    	  if(!courses.get(i).fullyStaffed()) { //if course does not have enough staff, then do not show in list
		    		  panel1.addItem(courses.get(i).getName());
		    	  }
		    }
			 
			
		}else if(e.getSource()==this.assignTrainingButton) {
			//add training assigned to teacher
			//print teacher with training to be completed to text2
			
		}else if(e.getSource()==this.saveExit) {
			System.exit(0);
	}
	}	

}
