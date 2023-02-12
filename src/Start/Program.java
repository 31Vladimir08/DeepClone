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
			   var strBuffer = new StringBuffer();
			   for (var item : e.getStackTrace()) {
				   strBuffer.append(item.getFileName());
			   }
			   System.out.printf("%s, %s", e.getMessage(), strBuffer.toString());
		   }		
	}
	
	private static Man getManTest() {
		var books = new ArrayList<String>();
		books.add("Tom");
		books.add("Alice");
		books.add("Kate");
		books.add("Sam");
		
		var man = new Man("Vladimir", 36, books);
		man.ob = new Integer[] { 1, 2 };
		
		return man;
	}	
}
