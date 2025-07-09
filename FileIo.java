package FinalProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIo implements IoLayout { // specified for file io 
	
	HashMap<LocalDate, Integer> scoreMap = new HashMap<>();
	
	public void saveScore(String date, int score, toDo writeTodo)
	{
		try {
			FileWriter fw = new FileWriter("score.txt", true); // append(because it can be updated) choose latest value later
			writeTodo.calculate();
			fw.write(date +":"+ String.valueOf(score) + "\n"); // need to be string
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void saveTodo(toDo writeTodo) {
	    try {

	        FileWriter fw = new FileWriter("Todo.txt"); // do not append if changed then rewrite
	        List<List<String>> tableData = writeTodo.getTableData();

	        for (int row = 0; row < tableData.size(); row++) { // traverse 2d list
	            for (int col = 0; col < tableData.get(row).size(); col++) {
	                String value = String.valueOf(tableData.get(row).get(col)); // convert to String
	                fw.write(value); // insert each value
	                if (col != tableData.get(row).size() - 1)
	                    fw.write(","); // signify each elements by ,
	            }
	            fw.write("\n");
	        }
	        fw.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public Map<LocalDate, Integer> hashReader(toDo writeTodo) // convert textfile to hash map
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // format pattern
		FileReader fr;
		try {
			fr = new FileReader("score.txt"); // read stored file
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
			    String[] parts = line.split(":"); // former one is date latter one is score
			    if (parts.length == 2) {
			        LocalDate date = LocalDate.parse(parts[0], formatter); // date
			        int score = Integer.parseInt(parts[1]); // score
			        scoreMap.put(date, score);
			    } 
			    } 
			fr.close();
			} catch (IOException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return scoreMap;
	}
	
	public List<List<String>> LoadTodo() // load todo from file to table
	{
		List<List<String>> table = new ArrayList<>();
		try {
			FileReader fr = new FileReader("Todo.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while((line = br.readLine()) != null)
			{
				String[] parts = line.split(","); 
				table.add(Arrays.asList(parts)); // insert to 2dList
 			}
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return table; // return 2dList
	}
	
}
