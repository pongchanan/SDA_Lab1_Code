package Pattern;
 
import java.util.ArrayList;


/**
 * An abstract class for all Observable subjects
 */
public abstract class Observable {
	/**
	 * Constructs an Observable object
	 */
	public Observable() {
		this.observers = new ArrayList<Observer>();
	}

	/**
	 * Attach to this Subject
	 * 
	 * @param o
	 *            the Observer that wishes to attach
	 */
	public void attach(Observer o) {
		this.observers.add(o);
	}

	/**
	 * Detach from this Subject
	 * 
	 * @param o
	 *            the Observer that wishes to detach
	 */
	public void detach(Observer o) {
		for (int i = 0; i < observers.size(); i++) {
			if (observers.get(i).equals(o))
				observers.remove(i);
		}
	}

	/**
	 * Notify all Observers that Subject has changed
	 */
	@SuppressWarnings("unchecked")
	public void notifyObservers() {
		ArrayList<CourseRecord> data = (ArrayList<CourseRecord>) this.getUpdate(); // Get the data to push
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = observers.get(i);
			observer.update(this, data); // Push the data to observers
		}
	}

	/**
	 * Pull updated data from this Subject
	 * 
	 * @return the updated data from the Subject
	 */
	public abstract Object getUpdate();

	protected ArrayList<Observer> observers;
}