package Start;

import java.util.ArrayList;
import Services.CopyUtils;
import models.Man;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			var m = getManTest();
			var res = CopyUtils.deepCopy(m);
		   } catch (Exception e) {
			   System.out.printf("%s, %s", e.getMessage(), e.getStackTrace());
		   }		
	}
	
	private static Man getManTest() {
		var books = new ArrayList<String>();
		books.add("Tom");
		books.add("Alice");
		books.add("Kate");
		books.add("Sam");
		
		var man = new Man("Vladimir", 36, books);
		return man;
	}	
}
