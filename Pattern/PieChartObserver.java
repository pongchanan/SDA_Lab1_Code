package Pattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This class represents a pie chart view of a vector of data. Uses the Observer
 * pattern.
 */
@SuppressWarnings("serial")
public class PieChartObserver extends JPanel implements Observer {
	
	/**
	 * Creates a PieChartObserver object
	 * 
	 * @param data
	 *            a CourseData object to observe
	 */
	public PieChartObserver(CourseData data) {
		// (1) Know where the CourseData is
		this.courseData = data.getUpdate();
		
		// (2) Register interest in the CourseData
		data.attach(this);
		
		// Set up the panel
		this.setPreferredSize(new Dimension(400, 300));
		this.setBackground(Color.white);
	}

	/**
	 * Paint method - draws the pie chart
	 * 
	 * @param g
	 *            a Graphics object on which to paint
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		if (courseData.size() == 0) {
			g.setColor(Color.BLACK);
			g.drawString("No courses to display", 50, 50);
			return;
		}
		
		// Calculate total of all values
		int total = 0;
		for (int i = 0; i < courseData.size(); i++) {
			CourseRecord record = courseData.get(i);
			total += record.getNumOfStudents();
		}
		
		if (total == 0) {
			g.setColor(Color.BLACK);
			g.drawString("No students enrolled", 50, 50);
			return;
		}
		
		// Pie chart parameters
		int pieX = 50;   // X position of pie chart
		int pieY = 50;   // Y position of pie chart
		int pieWidth = 150;  // Width of pie chart
		int pieHeight = 150; // Height of pie chart
		
		// Draw pie chart title
		g.setColor(Color.BLACK);
		g.drawString("Course Enrollment Pie Chart", pieX, pieY - 10);
		
		// Draw pie chart slices
		int currentAngle = 0;
		for (int i = 0; i < courseData.size(); i++) {
			CourseRecord record = courseData.get(i);
			int numStudents = record.getNumOfStudents();
			
			// Calculate angle for this slice (360 degrees total)
			int sliceAngle = (int) Math.round((360.0 * numStudents) / total);
			
			// Set color for this slice (use different colors for each course)
			Color sliceColor = LayoutConstants.courseColours[i % LayoutConstants.courseColours.length];
			g.setColor(sliceColor);
			
			// Draw filled arc (pie slice)
			g.fillArc(pieX, pieY, pieWidth, pieHeight, currentAngle, sliceAngle);
			
			// Draw outline of slice
			g.setColor(Color.BLACK);
			g.drawArc(pieX, pieY, pieWidth, pieHeight, currentAngle, sliceAngle);
			
			// Draw course name and percentage in legend
			double percentage = (100.0 * numStudents) / total;
			String label = record.getName() + " (" + String.format("%.1f", percentage) + "%)";
			g.setColor(Color.BLACK);
			g.drawString(label, pieX + pieWidth + 20, pieY + 20 + (i * 20));
			
			// Draw color legend box
			g.setColor(sliceColor);
			g.fillRect(pieX + pieWidth + 5, pieY + 20 + (i * 20) - 12, 10, 10);
			g.setColor(Color.BLACK);
			g.drawRect(pieX + pieWidth + 5, pieY + 20 + (i * 20) - 12, 10, 10);
			
			currentAngle += sliceAngle;
		}
		
		// Draw pie chart outline
		g.setColor(Color.BLACK);
		g.drawOval(pieX, pieY, pieWidth, pieHeight);
	}

	/**
	 * (3) Handle updates from the CourseData correctly
	 * Informs this observer that the observed CourseData object has changed
	 * 
	 * @param o
	 *            the observed CourseData object that has changed
	 */
	public void update(Observable o) {
		CourseData data = (CourseData) o;
		this.courseData = data.getUpdate();
		
		// Repaint the pie chart with updated data
		this.repaint();
	}

	private ArrayList<CourseRecord> courseData;
}
