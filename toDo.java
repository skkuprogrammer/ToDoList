package FinalProject;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class toDo {
	
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSaveTodo;
	private JMenuItem mntmB;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel label;
	private JTable table;
	private int todayScore = 0;
	private int cumulativeScore = 0;
	
	FileIo fileIo = new FileIo();
	
	void setScore(int score) // setter of today's score
	{
		todayScore = score;
	}
	
	int getScore() // getter to access 
	{
		return todayScore;
	}
	
	int getCumulativeScore() // getter of cumulative score
	{
		return cumulativeScore;
	}
	
	
	public void calculate() {
	    if (table.isEditing()) {
	        table.getCellEditor().stopCellEditing();
	    }

	    int cum = 0;
	    for (int row = 0; row < table.getRowCount(); row++) {
	        Object boolChecked = table.getValueAt(row, 2); //generic
	        Object completeScore = table.getValueAt(row, 3);

	        if (boolChecked instanceof Boolean) { 
	            try {
	                if (completeScore instanceof Integer && (Boolean) boolChecked) {
	                    cum += (Integer) completeScore; // if integer add score of completed task
	                } else if (completeScore instanceof String) {
	                    cum += Integer.parseInt((String) completeScore); // if String then change to integer and add
	                }
	            } catch (NumberFormatException e) {
	                System.err.println("Invalid score format at row " + row); // if non-int value inserted
	            }
	        }
	    }
	    setScore(cum);
	}
	
	public List<List<String>> getTableData() { // get table data(inserted/written)
	    List<List<String>> data = new ArrayList<>();

	    for (int row = 0; row < table.getRowCount(); row++) {
	        List<String> rowData = new ArrayList<>();
	        for (int col = 0; col < table.getColumnCount(); col++) {
	            Object cell = table.getValueAt(row, col); // get table data to generic
	            rowData.add(cell != null ? cell.toString() : ""); // add non - null data to rowData
	        }
	        data.add(rowData);
	    }

	    return data; //return table data
	}

	LocalDate today = LocalDate.now(); // call today's date
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String formattedDate = today.format(formatter); 
	
	public String getDate()
	{
		return formattedDate;
	}
	
	private static Object[][] data = { //default chart
			{1, "javaPractice", false, 10},
			{2, "data structure practice", false, 5},
	};
	
	
	private static String[] columns = {"priority", "to-do item", "completion status" , "completion score"}; // indexs
	
	private JCheckBox checkBox;
	private JLabel label_1;
	private JButton btnRecalculate;
	private JButton btnCumulativeChart;
	private JButton btnAchievementDisplay;
	private JMenu mnHelp;
	private JMenuItem mntmManual;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					toDo window = new toDo();
					window.frame.setVisible(true); // start ui by setVisible
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public toDo() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame("To-Do");
		frame.setBounds(100, 100, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnFile = new JMenu("file");
		menuBar.add(mnFile);
		
		mntmSaveTodo = new JMenuItem("save to-do");
		mntmSaveTodo.addActionListener(new ActionListener() { // when click save button
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == mntmSaveTodo)
				{
					if (table.isEditing()) {
					    table.getCellEditor().stopCellEditing(); // stop inserting table
					}
					calculate(); 
					fileIo.saveScore(getDate(), getScore(),toDo.this); // save today's total score
					fileIo.hashReader(toDo.this); 
					fileIo.saveTodo(toDo.this); // save list of to-do
				}
			}
		});
		mnFile.add(mntmSaveTodo);
		
		mntmB = new JMenuItem("load to-do"); // load file to table
		mntmB.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        FileIo fileIo = new FileIo();
		        List<List<String>> data = fileIo.LoadTodo();
		        for (int i = 0; i < data.size(); i++) {
		            List<String> row = data.get(i); 
		            for (int j = 0; j < row.size(); j++) {
		            	if(j!= 2)
		            		toDo.this.table.setValueAt(row.get(j), i, j); // insert value except isChecked
		            	else
		            		toDo.this.table.setValueAt(Boolean.parseBoolean(row.get(j)),i,j); // insert isChecked
		            }
		        }
		    }
		});

		mnFile.add(mntmB);
		
		mnHelp = new JMenu("help");
		menuBar.add(mnHelp);
		
		mntmManual = new JMenuItem("instruction");
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame instructionFrame = new JFrame("instruction"); // how to use program
				JLabel instructionLabel = new JLabel("write to-do, recalculate to-do, save to-do, load to-do and visualize score");
				instructionFrame.setSize(450,200);
				instructionFrame.add(instructionLabel);
				instructionFrame.setVisible(true);
			}
		});
		mnHelp.add(mntmManual);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();

		panel.setPreferredSize(new Dimension(1200,100));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		label = new JLabel(formattedDate);
		panel.add(label);
		
		panel_1 = new JPanel();

		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setPreferredSize(new Dimension(100,100));
		
		panel_2 = new JPanel();

		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setPreferredSize(new Dimension(1600,100));
		
		btnAchievementDisplay = new JButton("Achievement Display"); // show score chart
		btnAchievementDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CumulativeImage image = new CumulativeImage();
			}
		});
		panel_2.add(btnAchievementDisplay);
		
		panel_3 = new JPanel();

		frame.getContentPane().add(panel_3, BorderLayout.EAST);
		panel_3.setPreferredSize(new Dimension(100,800));
		
		btnRecalculate = new JButton("Re-calculate"); // recalculate cumulative sum when table is changed
		btnRecalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnRecalculate)
				{
					calculate();
					if (table.isEditing()) {
					    table.getCellEditor().stopCellEditing();
					}
					label_1.setText("Today's Score: " + getScore());
					label_1.revalidate(); // update
					label_1.repaint();
				}
			}
		});
		panel_3.add(btnRecalculate);
		
		panel_4 = new JPanel(new BorderLayout());
		panel_4.setPreferredSize(new Dimension(1000,600));

		frame.getContentPane().add(panel_4, BorderLayout.CENTER);
		
		this.table = new JTable(new DefaultTableModel( // set table test case and empty set
			new Object[][] {
				{1, "javaPractice", false, 10},
				{2, "data structure", true, 5},
				{3, null, false, null},
				{4, null, false, null},
				{5, null, false, null},
				{6, null, false, null},
				{7, null, false, null},
				{8, null, false, null},
				{9, null, false, null},
				{10, null, false, null}

			},
			new String[] {
				"priority", "to-do item", "completion status", "completion score"}
			) {

				private static final long serialVersionUID = 1L;

		});
		
		
		JScrollPane scrollPane = new JScrollPane(this.table);
		TableColumn checkColumn = this.table.getColumnModel().getColumn(2);
		checkColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
		checkColumn.setCellRenderer(this.table.getDefaultRenderer(Boolean.class));
		panel_4.add(scrollPane,BorderLayout.CENTER);
		
		calculate();
		label_1 = new JLabel("Today's Score: "+ getScore());
		panel_4.add(label_1, BorderLayout.SOUTH);

		
	}
}
