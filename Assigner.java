import java.util.ArrayList;

public class Assigner {
    
    public Assigner(){}

    // Produce list of teachers that meet a selected courses requirements
    public ArrayList<String> teachersMeetRequirements(String courseName, TeacherList LoT, CourseList LoC){

        // Set output array list
        ArrayList<String> suitableTeachers = new ArrayList<String>();

        // Access the array list that holds the teachers
        ArrayList<Teacher> teachers = LoT.getTeachers();

        // Training required array to compare course and teacher
        ArrayList<String> trainingRequired = new ArrayList<String>();
       
        // Access the array list that holds the courses
        ArrayList<Course> courses = LoC.getCourses();

        // Match course object with selected course
        for (Course course : courses) {
        
            if (course.getName().equals(courseName)) {
                // Accesss training required
                trainingRequired = course.getTrainingRequired();
            }
        }

        for (Teacher teacher : teachers) {
            
            ArrayList<String> teacherTraining = teacher.getTrainingAttended();
            // Check if each teacher has the ALL required training
            if(teacherTraining.containsAll(trainingRequired)) {
                // Append teacher name to suitable teacher array
                suitableTeachers.add(teacher.getName());
            } 
        }
        return suitableTeachers;
    }

    // Produce a list of teachers that have not been assigned to the selected course
    public ArrayList<String> unassignedTeachers(String courseName, TeacherList LoT, CourseList LoC) {
        
        // Set output array list
        ArrayList<String> unassingedTeachers = new ArrayList<String>();

        // Access the array list that holds the teachers
        ArrayList<Teacher> teachers = LoT.getTeachers();

        // Teachers assigned to course array to compare course and teacher
        ArrayList<Teacher> teachersOnCourse = new ArrayList<Teacher>();

        // Access the array list that holds the courses
        ArrayList<Course> courses = LoC.getCourses();

        // Match course object with selected course
        for (Course course : courses) {
        
            if (course.getName().equals(courseName)) {
                // Access teachers on course
                teachersOnCourse = course.getTeachers();
            }
        }

        for (Teacher teacher : teachers) {
            
            if(!teachersOnCourse.contains(teacher)) {
                unassingedTeachers.add(teacher.getName());
            }
        }
        return unassingedTeachers;
    }
    
    // Assign teachers to course
    public ArrayList<String> assignTeacherToCourse(String courseName, String teacherName, TeacherList LoT, CourseList LoC) {
        
        // Set output 
        ArrayList<String> assignedTeachers = new ArrayList<String>();

        // Access the array list that holds the courses
        ArrayList<Course> courses = LoC.getCourses();

        // Access the array list that holds the teachers
        ArrayList<Teacher> teachers = LoT.getTeachers();

        // Init selected course
        Course selectedCourse;

        // Init selected teacher
        Teacher selectedTeacher;

        for (Course course : courses) {
        
            if (course.getName().equals(courseName)) {

                // Access matched course
                selectedCourse = course;

                for (Teacher teacher : teachers) {
                    
                    if(teacher.getName().equals(teacherName)) {
                        
                        // Access matched teacher
                        selectedTeacher = teacher;

                        // Append teacher to course
                        selectedCourse.addTeacher(selectedTeacher);

                        // Append teacger to out
                        assignedTeachers.add(selectedCourse.toString());
                    }
                }
            }
        }
        return assignedTeachers;
    }

    // Assign teachers training
    public ArrayList<String> assingTeacherTraining(String courseName, String teacherName, TeacherList LoT, CourseList LoC) {

        // Set output 
        ArrayList<String> assignedTraining = new ArrayList<String>();

        // Access the array list that holds the courses
        ArrayList<Course> courses = LoC.getCourses();

        // Access the array list that holds the teachers
        ArrayList<Teacher> teachers = LoT.getTeachers();


         // Init required training
         ArrayList<String> requiredTraining;

         // Init selected teacher
         Teacher selectedTeacher;
        
         for (Course course : courses) {
            // Match selected course
            if (course.getName().equals(courseName)) {

                // Access matched course
                requiredTraining = course.getTrainingRequired();

                for (Teacher teacher : teachers) {
                    // Match selected teacher
                    if(teacher.getName().equals(teacherName)) {
                        
                        // Access matched teacher
                        selectedTeacher = teacher;

                    // Determine what training the teacher can take
                    for (String string : requiredTraining) {

                        // Check teacher doesn't already have the training and hasn't already been assigned the training
                        if (!selectedTeacher.getTrainingAttended().contains(string) && !selectedTeacher.getTrainingToAttend().contains(string)) {
                            
                            // Update teacher object and output arraylist
                            selectedTeacher.addCourseToAttend(string);
                            assignedTraining.add(selectedTeacher.toString());
                        }
                    }
                    }
                }
            }
        }
        return assignedTraining;
    }
}