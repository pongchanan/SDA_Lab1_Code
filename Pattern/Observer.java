package Pattern;

/**
 * An interface for all Observers
 */
public interface Observer {
	/**
	 * Informs this observer that an observed subject has changed
	 * 
	 * @param o
	 *            the observed subject that has changed
	 * @param data
	 *            the updated data being pushed from the subject
	 */
	public void update(Observable o, java.util.ArrayList<CourseRecord> data);
}