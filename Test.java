import java.util.ArrayList;

import org.json.JSONArray;

public class Test {
	public static void main(String [] args)	{
        //MVC stuff
        //add teachers and courses to model, add model to controller and create view inside controller
    	Model model = new Model();
		Controller controller = new Controller(model); 
	}
}
