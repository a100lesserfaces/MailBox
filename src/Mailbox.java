import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.*;

public class Mailbox {

	public static void main(String[] args) throws IOException {
		Display myDisplay = new Display();
		Shell myShell = new Shell(myDisplay);
		myShell.setText("Send a letter to Santa");
	    myShell.setBounds(320, 320, 420, 320);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		myShell.setLayout(gridLayout);
		GridData gridData = new GridData(SWT.HORIZONTAL);
		
		Label label = new Label(myShell, SWT.WRAP | SWT.CENTER | SWT.LEFT);
		label.setText("Outline your Present by manually selecting waveforms.");
		gridData.horizontalSpan = 2;
		label.setLayoutData(gridData);
		
		// Selected waveforms
		final List list = new List(myShell, SWT.RIGHT);
		
		// Waveform selection
		String location = System.getenv("SDRROOT") + "//dom//waveforms";
		File waveforms_folder = new File(location);
		final Tree tree = new Tree(myShell, SWT.RIGHT);
		for (final File fileEntry : waveforms_folder.listFiles()) {
			TreeItem topLevel = new TreeItem(tree, SWT.RIGHT);
			topLevel.setText(fileEntry.getName());
			topLevel.addListener(SWT.MouseDoubleClick, new Listener(){

				@Override
				public void handleEvent(Event event) {
					System.out.println("double click");
					list.add(fileEntry.getName());
					
				}
				
			});
			// working on the nested
			if (fileEntry.getName().equals("rh")) {
				for (final File fileEntry2 : fileEntry.listFiles()) {
					TreeItem lowerLevel = new TreeItem(topLevel,SWT.RIGHT);
					lowerLevel.setText(fileEntry2.getName());
					lowerLevel.addListener(SWT.MouseDoubleClick, new Listener(){
						@Override
						public void handleEvent(Event event) {
							System.out.println("double click");
							list.add(fileEntry.getName());
						}
					});
				}
			}
		}
		// end the tree
		
		myShell.open();
		while (!myShell.isDisposed()) {
			if (!myDisplay.readAndDispatch()) myDisplay.sleep();
		}
		myDisplay.dispose();

	}
	
	public static void listFiles(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println(fileEntry.getName());
				listFiles(fileEntry);
			} 
		}
	}

}
