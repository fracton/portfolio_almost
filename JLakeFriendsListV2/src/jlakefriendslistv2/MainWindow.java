package jlakefriendslistv2;

import java.io.*;
import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.*;

public class MainWindow {

	private ArrayList<Friend> friends = new ArrayList<Friend>();
	protected Shell shlSomeText;
	Properties appSettings = new Properties();
	Cursor defaultCursor; // To change the cursor to an arrow at any point after MainWindow() has executed, use
							// setCursor(defaultCursor);
	Cursor waitCursor; // To change the cursor to an hourglass at any point after MainWindow() has executed, use
						// setCursor(waitCursor);
	private Composite clientArea;
	private Label statusText;
	private Text firstNameText;
	private Text lastNameText;
	private List friendsListView;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();

		setPreferences();
		waitCursor = shlSomeText.getDisplay().getSystemCursor(SWT.CURSOR_WAIT);
		defaultCursor = shlSomeText.getDisplay().getSystemCursor(SWT.CURSOR_ARROW);
		clientArea.setFocus();

		shlSomeText.open();
		shlSomeText.layout();
		while (!shlSomeText.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Load application settings from .ini file
	private void setPreferences() {
		try {
			appSettings.load(new FileInputStream("appsettings.ini"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		// By default, center window
		int width = Integer.parseInt(appSettings.getProperty("width", "640"));
		int height = Integer.parseInt(appSettings.getProperty("height", "480"));
		Rectangle screenBounds = shlSomeText.getDisplay().getBounds();
		int defaultTop = (screenBounds.height - height) / 2;
		int defaultLeft = (screenBounds.width - width) / 2;
		int top = Integer.parseInt(appSettings.getProperty("top", String.valueOf(defaultTop)));
		int left = Integer.parseInt(appSettings.getProperty("left", String.valueOf(defaultLeft)));
		shlSomeText.setSize(width, height);
		shlSomeText.setLocation(left, top);
		saveShellBounds();
	}

	// Save window location in appSettings hash table
	private void saveShellBounds() {
		// Save window bounds in app settings
		Rectangle bounds = shlSomeText.getBounds();
		appSettings.setProperty("top", String.valueOf(bounds.y));
		appSettings.setProperty("left", String.valueOf(bounds.x));
		appSettings.setProperty("width", String.valueOf(bounds.width));
		appSettings.setProperty("height", String.valueOf(bounds.height));
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSomeText = new Shell();
		shlSomeText.addControlListener(new ShellControlListener());
		shlSomeText.addDisposeListener(new ShellDisposeListener());
		shlSomeText.setImage(SWTResourceManager.getImage(MainWindow.class, "/images/16x16.png"));
		shlSomeText.setSize(640, 480);
		shlSomeText.setText("Some Text");
		GridLayout gl_shlSomeText = new GridLayout(1, false);
		gl_shlSomeText.marginHeight = 3;
		gl_shlSomeText.marginWidth = 3;
		shlSomeText.setLayout(gl_shlSomeText);

		Menu menu = new Menu(shlSomeText, SWT.BAR);
		shlSomeText.setMenuBar(menu);

		MenuItem fileMenuItem = new MenuItem(menu, SWT.CASCADE);
		fileMenuItem.setText("&File");

		Menu menu_1 = new Menu(fileMenuItem);
		fileMenuItem.setMenu(menu_1);

		MenuItem newFileMenuItem = new MenuItem(menu_1, SWT.NONE);
		newFileMenuItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/images/new.gif"));
		newFileMenuItem.setText("&New");

		MenuItem openFileMenuItem = new MenuItem(menu_1, SWT.NONE);
		openFileMenuItem.addSelectionListener(new OpenFileMenuItemSelectionListener());
		openFileMenuItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/images/open.gif"));
		openFileMenuItem.setText("&Open");

		MenuItem closeFileMenuItem = new MenuItem(menu_1, SWT.NONE);
		closeFileMenuItem.setText("&Close");

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem saveFileMenuItem = new MenuItem(menu_1, SWT.NONE);
		saveFileMenuItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/images/save.gif"));
		saveFileMenuItem.setText("&Save");

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem exitMenuItem = new MenuItem(menu_1, SWT.NONE);
		exitMenuItem.addSelectionListener(new ExitMenuItemSelectionListener());
		exitMenuItem.setText("E&xit");

		MenuItem helpMenuItem = new MenuItem(menu, SWT.CASCADE);
		helpMenuItem.setText("&Help");

		Menu menu_2 = new Menu(helpMenuItem);
		helpMenuItem.setMenu(menu_2);

		MenuItem aboutMenuItem = new MenuItem(menu_2, SWT.NONE);
		aboutMenuItem.addSelectionListener(new AboutMenuItemSelectionListener());
		aboutMenuItem.setText("&About");

		ToolBar toolBar = new ToolBar(shlSomeText, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		ToolItem newToolItem = new ToolItem(toolBar, SWT.NONE);
		newToolItem.setToolTipText("New");
		newToolItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/images/new.gif"));

		ToolItem openToolItem = new ToolItem(toolBar, SWT.NONE);
		openToolItem.addSelectionListener(new OpenToolItemSelectionListener());
		openToolItem.setToolTipText("Open");
		openToolItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/images/open.gif"));

		ToolItem saveToolItem = new ToolItem(toolBar, SWT.NONE);
		saveToolItem.setToolTipText("Save");
		saveToolItem.setImage(SWTResourceManager.getImage(MainWindow.class, "/images/save.gif"));

		Label label = new Label(shlSomeText, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.SHADOW_IN);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		clientArea = new Composite(shlSomeText, SWT.NONE);
		clientArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblNewLabel = new Label(clientArea, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 64, 19);
		lblNewLabel.setText("First Name");
		
		Label label_2 = new Label(clientArea, SWT.NONE);
		label_2.setText("Last Name");
		label_2.setBounds(10, 41, 64, 19);
		
		firstNameText = new Text(clientArea, SWT.BORDER);
		firstNameText.setBounds(80, 10, 310, 19);
		
		lastNameText = new Text(clientArea, SWT.BORDER);
		lastNameText.setBounds(78, 38, 312, 19);
		
		Button addButton = new Button(clientArea, SWT.NONE);
		addButton.addSelectionListener(new AddButtonSelectionListener());
		addButton.setBounds(396, 21, 94, 28);
		addButton.setText("Add");
		
		friendsListView = new List(clientArea, SWT.BORDER);
		friendsListView.addSelectionListener(new FriendsListViewSelectionListener());
		friendsListView.setBounds(10, 94, 438, 260);
		
		Button deleteButton = new Button(clientArea, SWT.NONE);
		deleteButton.addSelectionListener(new DeleteButtonSelectionListener());
		deleteButton.setBounds(136, 60, 94, 28);
		deleteButton.setText("Delete");

		Label label_1 = new Label(shlSomeText, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.SHADOW_IN);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Composite statusArea = new Composite(shlSomeText, SWT.NONE);
		statusArea.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FillLayout fl_statusArea = new FillLayout(SWT.HORIZONTAL);
		fl_statusArea.marginWidth = 2;
		fl_statusArea.marginHeight = 2;
		statusArea.setLayout(fl_statusArea);
		GridData gd_statusArea = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_statusArea.widthHint = 125;
		statusArea.setLayoutData(gd_statusArea);

		statusText = new Label(statusArea, SWT.NONE);
		statusText.setText("Ready");

	}

	private void handleFileOpenRequest() {
		FileDialog dialog = new FileDialog(shlSomeText, SWT.OPEN);
		String filename = dialog.open();
		if (filename != null) {
			shlSomeText.setText(filename);
		}
	}

	private class ExitMenuItemSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				// Save app settings to file
				appSettings.store(new FileOutputStream("appsettings.ini"), "");
			} catch (Exception ex) {
			}
			shlSomeText.dispose();
		}
	}

	private class OpenFileMenuItemSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			handleFileOpenRequest();
		}
	}

	private class OpenToolItemSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			handleFileOpenRequest();
		}
	}

	private class AboutMenuItemSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			MessageBox message = new MessageBox(shlSomeText, SWT.OK | SWT.ICON_INFORMATION);
			message.setText("About Change_This_Title");
			message.setMessage("Change this about message\n\nApplicationName v1.0");
			message.open();
		}
	}

	private class ShellDisposeListener implements DisposeListener {
		public void widgetDisposed(DisposeEvent arg0) {
			try {
				// Save app settings to file
				appSettings.store(new FileOutputStream("appsettings.ini"), "");
			} catch (Exception ex) {
			}
		}
	}

	private class ShellControlListener extends ControlAdapter {
		@Override
		public void controlMoved(ControlEvent e) {
			try {
				saveShellBounds();
			} catch (Exception ex) {
				setStatus(ex.getMessage());
			}
		}

		@Override
		public void controlResized(ControlEvent e) {
			try {
				saveShellBounds();
			} catch (Exception ex) {
				setStatus(ex.getMessage());
			}
		}
	}
	private class AddButtonSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			  Friend friend = new Friend(firstNameText.getText(), lastNameText.getText());
			  friends.add(friend);
			  friendsListView.add(friend.toString());
		}
	}
	private class FriendsListViewSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			int index = friendsListView.getSelectionIndex(); 
			String name[] = friendsListView.getItem(index).toString().split(","); 
			firstNameText.setText(name[1].trim());
			lastNameText.setText(name[0].trim()); 
		}
	}
	private class DeleteButtonSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			int index[] = friendsListView.getSelectionIndices();
			for (int x:index){
				String name[] = friendsListView.getItem(x).toString().split(","); 
				for (Friend friend:friends)
				{ 
					if (friend.firstName.equals(name[1]) && friend.lastName.equals(name[0]))
					{   
						friends.remove(friends.indexOf(friend));
					}
				}
				friendsListView.remove(x);
			}
		}
	}

	private void setStatus(String message) {
		statusText.setText(message);
	}
}
