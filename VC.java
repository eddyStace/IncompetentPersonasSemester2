import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.json.JSONArray;

public class VC extends JFrame implements ActionListener{
	private JButton courseDirectorButton;
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
	private TeacherList teacherList;
	private CourseList courseList;
	private String teacherName;
	private String courseName;
	private boolean teacherAssignment; 
	private int button; //1 if assignButton, 0 is assignTrainingButton
	private JLabel label1;
	private JLabel label2;
	private JSONArray array;
	private FileHandler fileHandler;
	
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
	    panel2 = new Panel("Pick a Teacher", this);
	    //initialise labels
	    this.label1 = new JLabel("Teachers Assigned to Course");
	    label1.setBorder(BorderFactory. createEmptyBorder( 100 , 1 , 1 , 100 )); //create border to position label over text area
	    this.label2 = new JLabel("Training Assigned to Teacher");
	    label2.setBorder(BorderFactory. createEmptyBorder( 100 , 1 , 1 , 10 ));
	    //initialise buttons
	    this.courseDirectorButton = new JButton("Course Director");
		this.courseDirectorButton.addActionListener(this);  //assign action listener to button
	    this.assignButton = new JButton("Assign Teacher");
		this.assignButton.addActionListener(this);
		this.assignTrainingButton = new JButton("Assign Training");
		this.assignTrainingButton.addActionListener(this);
		this.saveExit = new JButton("Save & Exit");
		this.saveExit.addActionListener(this);
		//initialise text area
		this.text1 = new JTextArea(10,25); //create text area with predetermined dimensions
		this.text1.setEditable(false); //ensure text area can't be written in
		this.text2 = new JTextArea(10,25);
		this.text2.setEditable(false);
		//add panels and buttons to JFrame
		this.add(courseDirectorButton);
		this.add(panel1);
		this.add(panel2);
	    this.add(assignButton);
	    this.add(assignTrainingButton);
	    this.add(saveExit);
	    this.add(label1);
	    this.add(label2);
	    this.add(text1);
	    this.add(text2);
	    //disable buttons
	    this.assignButton.setEnabled(false);
	    this.assignTrainingButton.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.courseDirectorButton) {
			//file handler stuff
	    	this.fileHandler = new FileHandler(); //create filehandler
	        fileHandler.fileWrite();	//write JSON file
	        this.array = fileHandler.readFile();    // Read the file
	        ArrayList<String> parsedTeachers = fileHandler.parseTeachers(array); //Separate teachers into string array
	        ArrayList<String> parsedCourses = fileHandler.parseCourses(array);  //Separate courses into string array
	        
	        //make a list of teacher and course objects from JSON file output
	        this.teacherList = new TeacherList(parsedTeachers);
	        this.courseList = new CourseList(parsedCourses);
	        //update controller to then update view
	        this.teachers = teacherList.getTeachers(); 
	        this.courses = courseList.getCourses();
	        //update model with teachers and courses written from file
	        this.modelObject.addTeachers(teachers);
	        this.modelObject.addCourses(courses);
			
			  for(int i=0;i<courses.size();i++) { //loop through courses
		    	  if(!courses.get(i).fullyStaffed()) { //if course does not have enough staff, then do not show in list
		    		  this.panel1.addItem(courses.get(i).getName()); //add courses that need staff into list
		    	  }
			  }
			  //when courseDirector button is pressed once, then disable button so can't be pressed again
			  this.courseDirectorButton.setEnabled(false);
			  //get list of teachers / courses from file
			  
			  
		}else if(e.getSource()==this.panel1.getList()) { 	//if course list is selected
			teacherAssignment = false;
			//find what teachers can teach each course
			//retrieve the courses that was clicked
			JComboBox cb = (JComboBox)e.getSource();
			this.courseName = (String)cb.getSelectedItem();		
			this.panel2.deleteList(); //empty list to add new list of teachers
			this.panel2.updateList(modelObject.teachersMeetRequirements(courseName)); //find what teachers have requirements to teach a course and update list
			if(this.panel2.getList().getItemCount()==1) { //if teacher list is empty i.e., there are no more teachers that fit requirements
				this.teacherAssignment = true;  //set true since teachers now need to be assigned training			
				this.panel2.updateList(modelObject.teachersNeedTraining(courseName));
			}
			this.assignButton.setEnabled(false); 	//set buttons to not be enabled
			this.assignTrainingButton.setEnabled(false);
			
		}else if(e.getSource()==this.panel2.getList()) { //if teacher list is selected
			if(teacherAssignment) { //if a teacher needs to be allocated training then set assign button invisible
				this.assignButton.setEnabled(false); 
				this.assignTrainingButton.setEnabled(true); 
			}
				else {
				this.assignButton.setEnabled(true); 
				this.assignTrainingButton.setEnabled(false); 
			}
			//retrieve the item that was clicked
			JComboBox cb = (JComboBox)e.getSource();
			teacherName = (String)cb.getSelectedItem();
			
		}else if(e.getSource()==this.assignButton) {		
			button = 0;
			//set text to the teachers that have been assigned to the course selected
			this.text1.setText(modelObject.arrayListToString((modelObject.assignTeachers(courseName,teacherName,button))));
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
			button = 1;
			//set text2 to the teachers that need to undergo training
			this.text2.setText(modelObject.arrayListToString((modelObject.assignTeachers(courseName,teacherName,button))));
			//remove teacher from list and set selection to empty element
			this.panel2.removeItem(teacherName); 
			this.panel2.setSelection();
			
		}else if(e.getSource()==this.saveExit) {
			//save teachers / courses list to file
			//update teacherlist and courselist objects to then send to filehandler
			// update JSON file with updated teachers and courses objects
			fileHandler.updateTeachers(this.teacherList, array);
			System.exit(0);
		}
	}	

}
