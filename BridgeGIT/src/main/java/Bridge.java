import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import bridge.presentation.gui.MainController;

public class Bridge {

	public static void main(String[] args) throws InterruptedException, InvocationTargetException {

		buildGui();

	}

	private static void buildGui() throws InterruptedException, InvocationTargetException {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				new MainController();
			}
		});

	}

}
