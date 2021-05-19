import java.util.ArrayList;

import org.json.JSONArray;

public class Test {
	public static void main(String [] args)	{
        
        //MVC stuff
        //add teachers and courses to model, add model to view and controller
    	Model model = new Model();
		VC viewController = new VC(model);
		viewController.setVisible(true);
		
	}
}
