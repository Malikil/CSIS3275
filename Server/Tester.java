import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Tester {

	public static void main(String[] args) throws IOException {
		
		
		Table test = new Table("Test");
		test.addField("Name", 0);
		String[] arrayTest = {"Angelo"};
		test.addTuple(arrayTest);
		test.addField("Delete Field", 0);
		test.addField("Added Field", 0);
		String[] arrayTest2 = {"Yes","May this be gone","Vi"};
		test.addTuple(arrayTest2);
		String[] arrayTest3 = {"No","May this be gone","La"};
		test.addTuple(arrayTest3);
		String[] arrayTest4 = {"Angelo", "Dupli", "Dupli"};
		test.addTuple(arrayTest4);
		test.addTuple(arrayTest4);
		test.addTuple(arrayTest4);
		test.addTuple(arrayTest4);
		test.printEntries();
		test.importdata();
		test.genBin("Angelo", 2); 
			

	}
	
	public Table readTable()
	{
		 File file = new File("Default\\Test.txt");
		 FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(file);
			ObjectInputStream objReader = new ObjectInputStream(fileIn);
			Table test2 = (Table) objReader.readObject();
			objReader.close();
			return test2;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			try {
				fileIn.close();
			} catch (IOException e1) {

			}
			return null;
		}
		 
		 
		 
	}

}
