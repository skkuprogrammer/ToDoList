package FinalProject;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

public class CumulativeImage { // display daily score by chart(only days recorded)

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	
	ArrayList<Integer> values = new ArrayList(); // list to store daily score (not Cumulative Sum)
	
	ScoreAdder scoreadder = new ScoreAdder();
	FileIo fileIo = new FileIo();
	toDo todo = new toDo();
	Map<LocalDate, Integer> scoreMap = fileIo.hashReader(todo); // bring Map<> score matches date from file
	
	public CumulativeImage() { 
		scoreadder.setCumulativeScore(scoreadder.todo,0); 
		initialize();
		for(int value: scoreMap.values()) // traverse score of stored date
		{
			values.add(value); // insert score
		}
	}

	private void initialize() {
		frame = new JFrame("Cumulative Image");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(800,100));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		shapeDrawer shapedrawer = new shapeDrawer();
		frame.add(shapedrawer);
		
		label = new JLabel("accumulation: " + scoreadder.getCumulativeScore()); //display cumulative sum
		panel.add(label);
		frame.setVisible(true);
		
		

	}
	public class shapeDrawer extends JPanel{ // draw chart 
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2= (Graphics2D) g;
		
		g2.setColor(Color.RED); // set color
		for (int i = 0; i < values.size() - 1; i++) { //draw chart
		    int x1 = i * 50; // interval(x-axis) of chart is 50
		    int y1 = 200 - values.get(i); // set height(former y value)
		    int x2 = (i + 1) * 50;
		    int y2 = 200 - values.get(i + 1); // set height(latter y value)
		    g2.drawLine(x1, y1, x2, y2); // connect line
		}


		for (int i = 0; i < values.size(); i++) { // draw oval(points)
		    int x = i * 50;
		    int y = 200 - values.get(i);
		    g2.fillOval(x - 3, y - 3, 6, 6);
		}
	}

	}
}
