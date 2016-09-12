import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class SelectWaveform implements Listener {

	@Override
	public void handleEvent(Event event) {
		switch(event.type) {
		case SWT.MouseDown:
			System.out.println("Double click");
			break;
		}
		
	}

}
