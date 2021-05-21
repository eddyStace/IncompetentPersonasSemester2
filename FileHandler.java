import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.*;
import org.json.simple.parser.JSONParser;



// **** Read me ***
// Need to add org.json, org.json.simple & gson libraries via Jar file dependency to run code

// TO DO:
// Implement singelton pattern?

public class FileHandler {
    
    // FileHandler Attributes
    private String jsonFile;

//    // Main to test
//    public static void main(String[] args) {
//        
//        // Create FileHandler Object
//        FileHandler fileHandler = new FileHandler();
//
//        // Write the file
//        fileHandler.fileWrite();
//
//        // Read the file
//        JSONArray array = fileHandler.readFile();
//
//        // Parse the teachers
//        ArrayList<String> parsedTeachers = fileHandler.parseTeachers(array);
//
//        // Testing expected output -> "Alice English,Maths"
//        //                         -> "Bob English, Science"
//        //                         -> "Charlie Maths,Science"
//        for (String string : parsedTeachers) System.out.println(string);
//
//        // Parse the courses
//        ArrayList<String> parsedCourses = fileHandler.parseCourses(array);
//
//        // Testing expected output -> "English 2 English"
//        //                         -> "Science 2 Maths,Science "
//        for (String courseString : parsedCourses) System.out.println(courseString); 
//
//        // Hard coding a teacher who's been assigned training to test teacher update method
//        TeacherList teachers = new TeacherList(parsedTeachers);
//        ArrayList<Teacher> eachTeacher = teachers.getTeachers();
//        Teacher testTeacher = eachTeacher.get(0);
//        testTeacher.addCourseToAttend("Science");
//        testTeacher.addCourseToAttend("Geography");
//
//        // Testing updateTeachers -> want that 'Science' & 'Geography' have been added to the 'TrainingToAttend' array of Alice
//        fileHandler.updateTeacherTrainingToAttend(teachers, array);
//
//        // Hard coding a course who's been assigned a teacher
//        CourseList courses = new CourseList(parsedCourses);
//        ArrayList<Course> eachCourse = courses.getCourses();
//        Course testCourse = eachCourse.get(0);
//        testCourse.addTeacher(testTeacher);
//
//        // Testing updateCourse
//        fileHandler.updateCourseTrainingAssinged(courses, array);
//    }
    
    // Constructor
    public FileHandler() {
        // Set file
        this.jsonFile = "TeachingRequirements.json";
    }

    // Read JSON file
    public JSONArray readFile() {
        
        JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(jsonFile));
            String string = obj.toString();
            JSONArray array = new JSONArray(string);
            return array;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
   
    // Parse teachers
    public ArrayList<String> parseTeachers(JSONArray array) {

        ArrayList<String> parsedTeachers = new ArrayList<String>();
       
        // 0th element of JSONArray input is the list of teacher objects
        JSONArray teacherArray = array.getJSONArray(0);
         
            // Iterate over teacher list
            for (int i = 0; i < teacherArray.length(); i++) {

                // Access individual teacher object
                JSONObject outerTeacherObject = teacherArray.getJSONObject(i);
                JSONObject innerTeacherObject = outerTeacherObject.getJSONObject("Teacher");

                // Parse teacher name
                String name = innerTeacherObject.getString("Name");

                // Parsing teacher training
                JSONArray trainingArray = innerTeacherObject.getJSONArray("TrainingAttended");
                String trainingAttended = "";

                // Iterator over training array
                Iterator<Object> it = trainingArray.iterator();
                if (it.hasNext()) {
                    trainingAttended += it.next();
                }
                while (it.hasNext()) {
                    trainingAttended += ("," + it.next());
                }

                // Formatting string for object creation and adding to string ArrayList
                String outStream = String.format("%s %s", name, trainingAttended);
                parsedTeachers.add(outStream);  
            }  
        return parsedTeachers;
    }

    // Parse courses
    public ArrayList<String> parseCourses(JSONArray array) {

        ArrayList<String> parsedCourses = new ArrayList<String>();
       
        // Seoncd element of JSONArray input is the list of courses objects
        JSONArray courseArray = array.getJSONArray(1);

        for (int i = 0; i < courseArray.length(); i++) {

            // Access individual course object
            JSONObject outerCourseObject = courseArray.getJSONObject(i);
            JSONObject innerCourseObject = outerCourseObject.getJSONObject("Course");
            
            // Parse course name
            String name = innerCourseObject.getString("CourseName");
            
            // Parse number of teachers
            int NumStaff = innerCourseObject.getInt("NumStaff");

            // Parse training required
            JSONArray requiredTrainingArray = innerCourseObject.getJSONArray("TrainingRequired");
            String requiredTraining ="";

            // Iterator over training array
            Iterator<Object> it = requiredTrainingArray.iterator();
            if (it.hasNext()) {
                requiredTraining += it.next();
            }
            while (it.hasNext()) {
                requiredTraining += ("," + it.next());
            }

            // Formatting string for object creation and adding to string ArrayList
            String outStream = String.format("%s %d %s", name, NumStaff, requiredTraining);
            parsedCourses.add(outStream);  
        }
        return parsedCourses;
    }

    // Update JSON file with teachers where training has been assigned
    public void updateTeacherTrainingToAttend(TeacherList LoT, JSONArray array) {

        // Access the array list that holds the teachers
        ArrayList<Teacher> teachers = LoT.getTeachers();

        // Access the teachers JSONArray
        JSONArray teacherArray = array.getJSONArray(0);

        // Compare each teacher object to each teacher in the JSONArray
        for (Teacher teacher : teachers) {

            // Iterate over teacher list
            for (int i = 0; i < teacherArray.length(); i++) {

                // Access individual teacher object
                JSONObject outerTeacherObject = teacherArray.getJSONObject(i);
                JSONObject innerTeacherObject = outerTeacherObject.getJSONObject("Teacher");

                // Parse teacher name
                String name = innerTeacherObject.getString("Name");
                
                if (teacher.getName().equals(name)) {
                    
                    // Access trainingRequired JSONArray of inner teacher
                    JSONArray trainingRequired = innerTeacherObject.getJSONArray("TrainingToAttend");

                    // Get array of string of training to attend
                    ArrayList<String> trainingToAttend = teacher.getTrainingToAttend();

                    // Check if field of current teacher object is null
                    if (teacher.getTrainingToAttend().isEmpty()) {
                        continue;
                    }
                    // If not null, update json object with java object data
                    else {
                        for (String string : trainingToAttend) {
                            trainingRequired.put(string);
                        } 
                    }        
                }
            }
        } 
        // Update the file
        this.writePrettyJson(array); 
    }

    // Update JSON file with courses where teachers have been assigned 
    public void updateCourseTrainingAssinged(CourseList LoC, JSONArray array) {

        // Access the array list that holds the courses
        ArrayList<Course> courses = LoC.getCourses();

        // Access the courses JSONArray
        JSONArray courseArray = array.getJSONArray(1);

        // Compare each tcourse object to each course in the JSONArray
        for (Course course : courses) {

            // Iterate over course list
            for (int i = 0; i < courseArray.length(); i++) {

                // Access individual course object
                JSONObject outerCourseObject = courseArray.getJSONObject(i);
                JSONObject innerCourseObject = outerCourseObject.getJSONObject("Course");

                // Parse course name
                String name = innerCourseObject.getString("CourseName");
                
                if (course.getName().equals(name)) {
                    
                    // Access teachers assigned JSONArray of inner course json object
                    JSONArray TeachersAssigned = innerCourseObject.getJSONArray("TeachersAssigned");

                    // Get array of teacher objects of teachers asssigned to current course
                    ArrayList<Teacher> teacherOnCourse = course.getTeachers();

                    // Check if teachers assigned field of teacher object is empty
                    if (course.getTeachers().isEmpty()) {
                        continue;
                    }
                    // Update JSON course object with Java object data
                    else {
                        for (Teacher teacher : teacherOnCourse) {

                            // Gson instance to convert from Java object to string
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            String teacherString = gson.toJson(teacher);

                            // Insert into course JSON array
                            TeachersAssigned.put(teacherString);
                        } 
                    }      
                }
            }
        } 
        // Update the file
        this.writePrettyJson(array); 
    }

    // Write JSON string helper method
    public void writePrettyJson(JSONArray array) { 

        @SuppressWarnings("deprecated")

         // Making the file look nice
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         JsonParser jp = new JsonParser();
         JsonElement je = jp.parse(array.toString());
         String prettyJsonString = gson.toJson(je);
 
         // Write the JSON file
         try (FileWriter file = new FileWriter(jsonFile)) {
             // Write the 'prettyString' to the file
             file.write(prettyJsonString); 
             file.flush();
  
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

    // Generate JSON file
    public void fileWrite() {
        
        // Teachers
        
        // Teacher 1
        JSONObject teacherDetails1 = new JSONObject();
        teacherDetails1.put("Name", "Alice");

        JSONArray trainingAttendedAlice = new JSONArray();
        trainingAttendedAlice.put("English");
        trainingAttendedAlice.put("Maths");
        teacherDetails1.put("TrainingAttended", trainingAttendedAlice);

        JSONArray trainingToAttendAlice = new JSONArray();
        teacherDetails1.put("TrainingToAttend", trainingToAttendAlice);

        JSONObject teacherObject1 = new JSONObject();
        teacherObject1.put("Teacher", teacherDetails1);

        // Teacher 2
        JSONObject teacherDetails2 = new JSONObject();
        teacherDetails2.put("Name", "Bob");

        JSONArray trainingAttendedBob = new JSONArray();
        trainingAttendedBob.put("English");
        trainingAttendedBob.put("Science");
        teacherDetails2.put("TrainingAttended", trainingAttendedBob);

        JSONArray trainingToAttendBob = new JSONArray();
        teacherDetails2.put("TrainingToAttend", trainingToAttendBob);

        JSONObject teacherObject2 = new JSONObject();
        teacherObject2.put("Teacher", teacherDetails2);

        // Teacher 3
        JSONObject teacherDetails3 = new JSONObject();
        teacherDetails3.put("Name", "Charlie");

        JSONArray trainingAttendedCharlie = new JSONArray();
        trainingAttendedCharlie.put("Maths");
        trainingAttendedCharlie.put("Science");
        teacherDetails3.put("TrainingAttended", trainingAttendedCharlie);

        JSONArray trainingToAttendCharlie = new JSONArray();
        teacherDetails3.put("TrainingToAttend", trainingToAttendCharlie);

        JSONObject teacherObject3 = new JSONObject();
        teacherObject3.put("Teacher", teacherDetails3);

        // Add teachers to the list
        JSONArray teacherList = new JSONArray();
        teacherList.put(teacherObject1);
        teacherList.put(teacherObject2);
        teacherList.put(teacherObject3);

        // Wrap in object
        JSONObject teacherListObject = new JSONObject();
        teacherListObject.put("Teachers", teacherList);

        // Courses

        // Course 1
        JSONObject courseDetails1 = new JSONObject();
        courseDetails1.put("CourseName", "English");
        courseDetails1.put("NumStaff", 2);

        JSONArray TrainingRequired = new JSONArray();
        TrainingRequired.put("English");
        courseDetails1.put("TrainingRequired", TrainingRequired);

        JSONArray TeachersAssinged = new JSONArray();
        courseDetails1.put("TeachersAssigned", TeachersAssinged);

        JSONObject courseObject = new JSONObject();
        courseObject.put("Course", courseDetails1);

        // Course 2
        JSONObject courseDetails2 = new JSONObject();
        courseDetails2.put("CourseName", "Science");
        courseDetails2.put("NumStaff", 2);

        JSONArray TrainingRequired2 = new JSONArray();
        TrainingRequired2.put("Maths");
        TrainingRequired2.put("Science");
        courseDetails2.put("TrainingRequired", TrainingRequired2);

        JSONArray TeachersAssinged2 = new JSONArray();
        courseDetails2.put("TeachersAssigned", TeachersAssinged2);

        JSONObject courseObject2 = new JSONObject();
        courseObject2.put("Course", courseDetails2);
        
        // Add courses to the list
        JSONArray courseList = new JSONArray();
        courseList.put(courseObject);
        courseList.put(courseObject2);

        // Wrap in object
        JSONObject courseListObject = new JSONObject();
        courseListObject.put("Courses", courseListObject);
        
        // Wrap teachers and courses in JSON Array
        JSONArray teacherAndCourseList = new JSONArray();
        teacherAndCourseList.put(teacherList);
        teacherAndCourseList.put(courseList);

        // Format array and write it
        this.writePrettyJson(teacherAndCourseList);
    }
}
