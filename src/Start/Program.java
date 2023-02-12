package Start;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import Services.CopyUtils;
import models.Man;
import models.ManBig;
import models.TestClass;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			var books = new ArrayList<String>();
			books.add("Tom");
			books.add("Alice");
			books.add("Kate");
			books.add("Sam");
			var m = getTestClass();
			//var mm = new Man();
			var res = CopyUtils.deepCopy(m);
			/*res.name = "Test321";
			res.age = 90;
			res.men.clear();*/
			//res.add("test");
			System.out.println("Test");
		   } catch (Exception e) {
			   System.out.printf("%s, %s", e.getMessage(), e.getStackTrace());
		   }		
	}
	
	private static ManBig getClassTest1() { 
		var men = new ArrayList<Man>();
		men.add(new Man("Man1", 1, Arrays.asList("Tom", "Alice", "Kate", "Sam") ));
		men.add(new Man("Man2", 2, Arrays.asList("GSFSFS") ));
		men.add(new Man("Man3", 3, Arrays.asList("HDHD", "IRYEY") ));
		var manBig = new ManBig("Test", 20, men);
		return manBig;
	}
	
	private static TestClass getTestClass() {
		var t = new TestClass();
		t.people = new ArrayDeque<String>();
		t.people.add("Germany");
		t.people.addFirst("France"); // добавляем элемент в самое начало
		t.people.push("Great Britain"); // добавляем элемент в самое начало
		t.people.addLast("Spain"); // добавляем элемент в конец коллекции
		t.people.add("Italy");
		
		t.states = new HashMap<Integer, String>();
		t.states.put(1, "Germany");
		t.states.put(2, "Spain");
		t.states.put(4, "France");
		t.states.put(3, "Italy");
		return t;
	}
}
