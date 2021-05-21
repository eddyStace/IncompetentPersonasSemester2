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

public class Controller implements ActionListener{
	private Model modelObject;
	private View viewObject; 
	private String teacherName;
	private String courseName;
	private boolean teacherAssignment; 
	
	public Controller(Model model) {
		//add model to Controller
		modelObject = model;
		//initialise view
		viewObject = new View(model, this);
		viewObject.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==viewObject.getCourseDirectorButton()) {
			//file handler stuff
	        modelObject.fileHandler.fileWrite(); //write JSON file
	        ArrayList<String> parsedTeachers = modelObject.fileHandler.parseTeachers(modelObject.fileHandler.readFile()); //Separate teachers into string array
	        ArrayList<String> parsedCourses = modelObject.fileHandler.parseCourses(modelObject.fileHandler.readFile());  //Separate courses into string array
	        
	        //update model with a list of teacher and course objects from JSON file output
	        this.modelObject.getTeachers().populateTeachers(parsedTeachers);
	        this.modelObject.getCourses().populateCourses(parsedCourses);
			
			  for(int i=0;i<this.modelObject.getCourses().getCourses().size();i++) { //loop through courses
		    	  if(!this.modelObject.getCourses().getCourses().get(i).fullyStaffed()) { //if course does not have enough staff, then do not show in list
		    		  viewObject.getPanel1().addItem(this.modelObject.getCourses().getCourses().get(i).getName()); //add courses that need staff into list
		    	  }
			  }
			  //when courseDirector button is pressed once, then disable button so can't be pressed again
			  viewObject.getCourseDirectorButton().setEnabled(false);			  
			  
		}else if(e.getSource()==viewObject.getPanel1().getList()) { 	//if course list is selected
			teacherAssignment = false;
			//find what teachers can teach each course
			//retrieve the courses that was clicked
			JComboBox cb = (JComboBox)e.getSource();
			this.courseName = (String)cb.getSelectedItem();		
			viewObject.getPanel2().deleteList(); //empty list to add new list of teachers
			viewObject.getPanel2().updateList(modelObject.teachersMeetRequirements(courseName)); //find what teachers have requirements to teach a course and update list
			if(viewObject.getPanel2().getList().getItemCount()==1) { //if teacher list is empty i.e., there are no more teachers that fit requirements
				this.teacherAssignment = true;  //set true since teachers now need to be assigned training			
				viewObject.getPanel2().updateList(modelObject.teachersNeedTraining(courseName));
			}
			viewObject.getAssignButton().setEnabled(false); 	//set buttons to not be enabled
			viewObject.getAssignTrainingButton().setEnabled(false);
			
		}else if(e.getSource()==viewObject.getPanel2().getList()) { //if teacher list is selected
			if(teacherAssignment) { //if a teacher needs to be allocated training then set assign button invisible
				viewObject.getAssignButton().setEnabled(false); 
				viewObject.getAssignTrainingButton().setEnabled(true); 
			}
				else {
				viewObject.getAssignButton().setEnabled(true); 
				viewObject.getAssignTrainingButton().setEnabled(false); 
			}
			//retrieve the item that was clicked
			JComboBox cb = (JComboBox)e.getSource();
			teacherName = (String)cb.getSelectedItem();
			
			}else if(e.getSource()==viewObject.getAssignButton()) {
			int button = 0;
			//set text to the teachers that have been assigned to the course selected
			viewObject.getText1().setText(modelObject.arrayListToString((modelObject.assignTeachers(courseName,teacherName,button))));
			//remove teacher from list and set selection to empty element (for display)
			viewObject.getPanel2().removeItem(teacherName); 
			viewObject.getPanel2().setSelection();
			//if course has been allocated sufficient teachers, remove course from list
			viewObject.getPanel1().deleteList();
			for(int i=0;i<this.modelObject.getCourses().getCourses().size();i++) {
				if(!this.modelObject.getCourses().getCourses().get(i).fullyStaffed()) { //if course does not have enough staff, then do not show in list
					viewObject.getPanel1().addItem(this.modelObject.getCourses().getCourses().get(i).getName());
				}
			}
			 			
		}else if(e.getSource()==viewObject.getAssignTrainingButton()) {
			int button = 1;
			//set text2 to the teachers that need to undergo training
			viewObject.getText2().setText(modelObject.arrayListToString((modelObject.assignTeachers(courseName,teacherName,button))));
			//remove teacher from list and set selection to empty element
			viewObject.getPanel2().removeItem(teacherName); 
			viewObject.getPanel2().setSelection();
			
		}else if(e.getSource()==viewObject.getSaveExit()) {
			//save teachers / courses list to file
			//update teacherlist and courselist objects to then send to filehandler
			// update JSON file with updated teachersList and coursesList objects from model
			modelObject.fileHandler.updateTeacherTrainingToAttend(this.modelObject.getTeachers(), modelObject.fileHandler.readFile());
			modelObject.fileHandler.updateCourseTrainingAssinged(this.modelObject.getCourses(), modelObject.fileHandler.readFile());
			System.exit(0);
		}
	}	

}
