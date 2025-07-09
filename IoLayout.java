package FinalProject;

import java.util.List;

public interface IoLayout {
	public void saveScore(String date, int score, toDo writeTodo);
	public List<List<String>> LoadTodo();
}
