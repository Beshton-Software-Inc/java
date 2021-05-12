package immutableClass;
import java.util.HashMap;
import java.util.Map;

public final class Student {
	private final String name;
	private final int id;
	private final Map<String, String> metadata;
	
	public Student(String name, int id, Map<String,String> metadata) {
		this.name = name;
		this.id = id;
		Map<String,String> tempMap = new HashMap<>();
		 for (Map.Entry<String, String> entry :
             metadata.entrySet()) {
            tempMap.put(entry.getKey(), entry.getValue());
        }
        this.metadata = tempMap;
    }
 
    public String getName() { return name; }
 
    public int getid() { return id; }
 
    public Map<String, String> getMetadata()
    {
        Map<String, String> tempMap = new HashMap<>();
        for (Map.Entry<String, String> entry :
             this.metadata.entrySet()) {
            tempMap.put(entry.getKey(), entry.getValue());
        }
        return tempMap;
    }
}
/*
//The class must be declared as final (So that child classes can¡¯t be created)
//Data members in the class must be declared as private (So that direct access is not allowed)
//Data members in the class must be declared as final (So that we can¡¯t change the value of it after object creation)
//A parametrized constructor should initialize all the fields performing a deep copy (So that data members can¡¯t be modified with object reference)
//Deep Copy of objects should be performed in the getter methods (To return a copy rather than returning the actual object reference)
//No setters (To not have the option to change the value of the instance variable)
*/

class Test {
    public static void main(String[] args)
    {
        Map<String, String> map = new HashMap<>();
        map.put("1", "first");
        map.put("2", "second");
        Student s = new Student("ABC", 101, map);
        System.out.println(s.getName());
        System.out.println(s.getid());
        System.out.println(s.getMetadata());
 
        
 
        map.put("3", "third");
        System.out.println(s.getMetadata()); 
 
        s.getMetadata().put("4", "fourth");
        System.out.println(s.getMetadata());
    }
}