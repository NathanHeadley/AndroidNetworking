package zelphinstudios.courseworkapp.system.util;

public class BaseThread implements Runnable {

	protected Thread thread = null;
	protected boolean running = false;

	public void run() {}

	public void onPause() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException ie) { ie.printStackTrace(); }
		thread = null;
	}

	public void onResume() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
}
