package FinalProject;

import java.time.LocalDate;
import java.util.Map;

public class ScoreAdder {
	private int cumulativeScore = 0; // initialize
	toDo todo = new toDo();
	FileIo fileIo = new FileIo(); 
	
	public ScoreAdder()
	{

	}
	void setCumulativeScore(toDo writeTodo, int cumulativeScore) { // calculate and set cumulative score
	    Map<LocalDate, Integer> scoreMap = fileIo.hashReader(todo); // convert file text to Map<> data structure
	    for (int value : scoreMap.values()) {
	        cumulativeScore += value;
	    }
	    this.cumulativeScore = cumulativeScore; 
	}
	
	int getCumulativeScore() // to access from other class by instance
	{
		return cumulativeScore;
	}
}


