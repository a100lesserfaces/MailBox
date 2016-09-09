import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import java.io.*;
import java.util.ArrayList;

public class Mailbox {

	public static void main(String[] args) throws IOException {
		final ArrayList<String> waveforms = new ArrayList<String>();
		Display my_display = new Display();
		Shell my_shell = new Shell(my_display);
		my_shell.setText("Send a letter to Santa");
		my_shell.setBounds(320, 320, 420, 320);
		my_shell.setLayout(new FillLayout());
		Label label = new Label(my_shell, SWT.WRAP | SWT.CENTER | SWT.LEFT);
		label.setText("Outline your Present by manually selecting waveforms.");
		Listener listener =  new SelectWaveform();
		label.addListener(SWT.MouseDoubleClick, listener);
		
		String location = System.getenv("SDRROOT") + "//dom//waveforms";
		File waveforms_folder = new File(location);
		final Tree tree = new Tree(my_shell, 0);
		TreeItem placeholder = new TreeItem(tree, 0);
		placeholder.setText("placeholder");
		for (File file_entry : waveforms_folder.listFiles()) {
			TreeItem topLevel = new TreeItem(tree, 0);
			topLevel.setText(file_entry.getName());
			// working on the nested
			if (file_entry.getName().equals("rh")) {
				for (File file_entry2 : file_entry.listFiles()) {
					TreeItem lowerLevel = new TreeItem(topLevel,0);
					lowerLevel.setText(file_entry2.getName());
				}
			}
		}
		
		// Selected waveforms
		final Tree selectedWaveforms = new Tree(my_shell, 0);
		Button add_waveform = new Button(my_shell, 0);
		add_waveform.setText("Add >>");
		add_waveform.addListener(SWT.MouseDown, new Listener(){

			@Override
			public void handleEvent(Event event) {
				TreeItem[] selection = tree.getSelection();
				for (int i = 0; i < selection.length; i++) {
					TreeItem item = new TreeItem(selectedWaveforms, 0);
					item.setText(selection[i].getText());
					waveforms.add(selection[i].getText());
				}
			}
			
		});
		my_shell.open();
		while (!my_shell.isDisposed()) {
			if (!my_display.readAndDispatch()) my_display.sleep();
		}
		my_display.dispose();

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
