package finalProject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

/********************
 *Program Name: Brookstone v3
 *Programmer Name: Katie Milligan 
 *Program Date: January 18th 2016
 *Program Description:  This is the Brookstone B&B reservation system.  It creates a main window with menu items to search rooms, show the guest list, etc.  
 *						It has two main classes - Room and Guest.  The main variable it uses is roomList, which is an arrayList of Rooms.  The initial setup
 *					    of the hotel is done by reading in a configuration file.  Any changes to the bookings are automatically saved in a reservation file. 
*********************/

public class Brookstonev3 extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	public String[] roomTypes = {"Standard", "Deluxe", "Premium" };
	public String[] bedTypes = {"Twin", "Queen", "King" };
	public String[] mealTypes = {"Regular", "Vegetarian", "Vegan", "Halal", "Kosher"};
	String checkInDate, checkOutDate;
	public static ArrayList<Room> roomList = new ArrayList<Room>();
	public static int numRooms = 0;
	public static String CONFIG_PATH = "c:/brookstone/roomconfig.txt";
	public static String RESERVATION_PATH = "c:/brookstone/reservations.txt";
	public static int MAX_CALENDAR = 366;
	int checkInIndex = 0;
	int checkOutIndex = 0;
	public Guest newGuest;
	
	public static int STANDARD_RATE = 100;
	public static int DELUXE_RATE = 150;
	public static int PREMIUM_RATE = 200;
	
	Container contentPane;
	JPanel roomPane;
	JLabel L1, L2, L3, L4, L5, L6, L7;
	JTable table;
	JFrame frame;
	JTextField roomNumber;
	JTextField firstname, lastname, mealtype, phonenumber, creditCard;
	Room roomSelected;
	
	/********************
	 *Method Name: brookstonev3() 
	 *Method Date: January 18th 2016
	 *Method Description: this method calls the initUI() to run 
	 *Inputs: N/A
	 *Outputs: N/A
	 ********************/

	public Brookstonev3() {

        initUI();
    }
	
	/********************
	 *Method Name: initUI()
	 *Method Date: January 18th 2016
	 *Method Description: this method lays out the GUI on the screen 
	 *Inputs: N/A
	 *Outputs: N/A
	 ********************/
	
    private void initUI() {
        
    	createMenuBar();
    	
        setTitle("Brookstone V3");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
            
    } /* initUI */
    
	/********************
	 *Method Name: createMenuBar
	 *Method Date: January 18th 2016
	 *Method Description: this creates the menu and menu items for each item on the GUI 
	 *Inputs: N/A
	 *Outputs: N/A
	 ********************/
	
    private void createMenuBar() {
    	
    	/* declare JMenuBar, JMenu and JMenuItem */
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		/* Initialize and set the JMenuBar */
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menu = new JMenu("File");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Load");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Quit");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	
		menu = new JMenu("Bookings");
		menuBar.add(menu);

		menuItem = new JMenuItem("Find Room");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("View Status");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu = new JMenu("Guest");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Prepare Breakfast");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Guest List");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Checkout");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu = new JMenu("Help");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("How To");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("About");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
    } /* createMenuBar */

	/********************
	 *Method Name: actionedPreformed()
	 *Method Date: January 18th 2016
	 *Method Description: All the menu items call their methods from this method.
	 *Inputs: ActionEvent e
	 *Outputs: N/A
	 ********************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		String event = e.getActionCommand();
		
		if(event.equals("Load")) {
			try {
				loadReservations();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} /* if */
		
		else if(event.equals("Save")) {
			try {
				saveReservations();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} /* if */
		
		else if(event.equals("Quit")) {
			System.exit(0);
		} /* if */
		
		else if(event.equals("Find Room")) {
			try {
				findRoom();
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			} /* if */
			
		else if(event.equals("Book Room")) {
			try {
				bookRoom();
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			} /* if */
		
		else if(event.equals("View Status")) {
			viewStatus();
		} /* if */
			
		else if(event.equals("Prepare Breakfast")) {
			prepareBreakfast();
		} /* if */
			
		else if(event.equals("Guest List")) {
			guestList();
			
		} /* if */
			
		else if(event.equals("Checkout")) {
			try {
				checkOut();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} /* if */
			
		else if(event.equals("How To")) {
			JFrame frame = new JFrame();
			JPanel p1;
			contentPane = frame.getContentPane();
			
			p1 = new JPanel();
			p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		
			JOptionPane.showMessageDialog( p1, "Welcome to the Brookstone B&B. This program will let you Find, Book, View and Checkout guests into your B&B. \n If you have any issues with the program contact 416-518-9800 for assistance", "How To", JOptionPane.PLAIN_MESSAGE);
			
		} /* if */
		
		else if(event.equals("About")) {
			
			JFrame frame = new JFrame();
			JPanel p1;
			contentPane = frame.getContentPane();
			
			p1 = new JPanel();
			p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		
			JOptionPane.showMessageDialog( p1, "Brookstone B&B V3\nby: Katie Milligan", "About", JOptionPane.PLAIN_MESSAGE);
			
		} /* if */
				
	} /* actionPerformed */
	
	/********************
	 *Method Name: prepareBreakfast()
	 *Method Date: January 18th 2016
	 *Method Description: This method is for the kitchen.  It looks at all currently checked in guests and shows what kind of meal to make for them. 
	 *Inputs: ActionEvent e
	 *Outputs: N/A
	 ********************/

	public void prepareBreakfast() {
		
		int i;
		String temp;
		int tableIndex = 0;
		String[][] roomTable = new String[numRooms][5]; 
				
		for (i=0; i<numRooms; i++) {
			if (!(roomList.get(i).isVacant())) {
				temp = roomList.get(i).currentGuest().firstname + " " +
			roomList.get(i).currentGuest().lastname;
				roomTable[tableIndex][0] = temp; 
				roomTable[tableIndex][1] = roomList.get(i).currentGuest().getMealType();
				tableIndex++;
			} /* if */
		} /* for */
		
		String[] headings = {"Guest Name", "Meal Type"};
	

		Container contentPane = getContentPane();
		
		contentPane = getContentPane();
	
		if(contentPane != null)
			contentPane.removeAll();
		validate();
		contentPane.repaint();
	
		roomPane = new JPanel();
		roomPane.setLayout(new BorderLayout());
	
		L1 = new JLabel("Breakfast List", JLabel.CENTER);
		L1.setFont(new Font ("Book Antiqua", Font.BOLD, 24));
		roomPane.add(L1, BorderLayout.NORTH);
	
		L2 = new JLabel("End of Page", JLabel.CENTER);
		roomPane.add (L2, BorderLayout.SOUTH);
	
	    /* Initialize the JTable */
		
	    table = new JTable(roomTable, headings);
	    
		JScrollPane scrollPane = new JScrollPane(table);
		roomPane.add(scrollPane, BorderLayout.CENTER);
	
		int result = JOptionPane.showConfirmDialog(null, roomPane, "Breakfast List", JOptionPane.OK_CANCEL_OPTION);
	
		validate();	
		
		
	} /* prepareBreakfast */

	/********************
	 *Method Name: findRoom()
	 *Method Date: January 18th 2016 
	 *Method Description: This method creates a jPanel that asks for users room type information, 
	 *then calls searchRoom to find a room to match the guests requirements. 
	 *Inputs: N/A
	 *Outputs: N/A
	 ********************/
	
    public void findRoom() throws IOException {
		
		JPanel p1 = null;
		JLabel L1, L2, L3, L4;
	
		Container contentPane = getContentPane();
		
		if(contentPane != null){
			contentPane.removeAll();
		}
		validate();
		contentPane.repaint();
		
		p1 = new JPanel();
	
		L1 = new JLabel ("Room Type");
		L2 = new JLabel ("Bed Type ");
		L3 = new JLabel ("Check in date: ");
		L4 = new JLabel ("Check out date: ");
		
		final JComboBox<String> typeList = new JComboBox<String>();
		typeList.addItem(roomTypes[0]);
		typeList.addItem(roomTypes[1]);
		typeList.addItem(roomTypes[2]);
		
		final JComboBox<String> bedList = new JComboBox<String>();
		bedList.addItem(bedTypes[0]);
		bedList.addItem(bedTypes[1]);
		bedList.addItem(bedTypes[2]);
		
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		
		p1.add(L1);
		p1.add(typeList);
	
		p1.add(L2);
		p1.add(bedList);
				
		Date today = new Date();
				
		SpinnerModel model1 = new SpinnerDateModel(today, null, null, Calendar.MONTH);
		SpinnerModel model2 = new SpinnerDateModel(today, null, null, Calendar.MONTH);
		
	    JSpinner checkInDateSpinner = new JSpinner(model1);
	    JSpinner checkOutDateSpinner = new JSpinner(model2);
	    
	    JSpinner.DateEditor de1 = new JSpinner.DateEditor(checkInDateSpinner, "dd/MM/yy");
	    checkInDateSpinner.setEditor(de1);
	    
	    JSpinner.DateEditor de2 = new JSpinner.DateEditor(checkOutDateSpinner, "dd/MM/yy");
	    checkOutDateSpinner.setEditor(de2);
    
		p1.add(L3);
	    p1.add(checkInDateSpinner, BorderLayout.LINE_END);
		
		p1.add(L4);
		p1.add(checkOutDateSpinner, BorderLayout.LINE_END);
		
		int result = JOptionPane.showConfirmDialog(null, p1, "Find Room", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	    
	    	Object checkin = checkInDateSpinner.getValue();
	    	Date checkinDate = (Date) checkin;
	    		    	
	    	Object checkout = checkOutDateSpinner.getValue();
	    	Date checkoutDate = (Date) checkout;
	    	
	    	if (checkinDate.after(checkoutDate)){
		    	JOptionPane.showMessageDialog(null, "Check Out Date must be after Check In Date");
	    	}	/* if */
	    	
	    	checkInDate = checkinDate.toString();
	    	checkOutDate = checkoutDate.toString();
	    	
	    	searchRooms((String)typeList.getSelectedItem(), (String)bedList.getSelectedItem(), checkinDate, checkoutDate);
	    	
	    } /* if */
	    
	} /* findRoom */

	/********************
	 *Method Name: bookRoom()
	 *Method Date: January 18th 2016
	 *Method Description: This method is called after finding a room, after you press the "Book Room" button.  It loads another Jpanel
	 *					  which allows the user to put in the guests information and meal type, then book the room for those dates. It will also call saveReservations() 
	 *					  so that the the new guest and booking is saved to the file.
	 *Inputs: N/A
	 *Outputs: N/A
	 ********************/
    
    public void bookRoom() throws IOException {
    		
		Container contentPane = getContentPane();
		if(contentPane != null)
			contentPane.removeAll();
		validate();
		contentPane.repaint();
		
		final JComboBox<String> mealList = new JComboBox<String>();
		mealList.addItem(mealTypes[0]);
		mealList.addItem(mealTypes[1]);
		mealList.addItem(mealTypes[2]);
		mealList.addItem(mealTypes[3]);
		mealList.addItem(mealTypes[4]);
    	
		JPanel	p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		
    	L1 = new JLabel ("Add First Name ");
		L2 = new JLabel ("Add Last Name: ");
		L3 = new JLabel ("Add Phone Number: ");
		L4 = new JLabel ("Add Meal Option: ");
		L5 = new JLabel ("Add Credit Card: ");
		L6 = new JLabel ("Check In Date " + checkInDate);
		L7 = new JLabel ("Check Out Date " + checkOutDate);
		
		p1.add(L1);
		firstname = new JTextField(20);
		p1.add(firstname);

		p1.add(L2);
		lastname = new JTextField(20);
		p1.add(lastname);
		
		p1.add(L3);
		phonenumber = new JTextField(20);
		p1.add(phonenumber);
		
		p1.add(L4);
		p1.add(mealList);
		
		p1.add(L5);
		creditCard = new JTextField(20);
		p1.add(creditCard);
		
		p1.add(L6);
		p1.add(L7);
		
		int result = JOptionPane.showConfirmDialog(null, p1, 
	               "Confirm", JOptionPane.DEFAULT_OPTION);
	
		if (result == JOptionPane.OK_OPTION) {
		
			/* 
			 * this code makes sure that the credit card is numerical only
			 */
			
			boolean validCC = true;
			newGuest = new Guest();
			String testCC = creditCard.getText().trim();
			
			try {
				newGuest.creditCard = Double.parseDouble(testCC);
				
			} catch (NumberFormatException e1) {
				validCC = false;
			} /* catch */
			
			if (validCC) {
			
				
				newGuest.firstname = firstname.getText().trim();
				newGuest.lastname = lastname.getText().trim();
				newGuest.phonenumber = phonenumber.getText().trim();
				newGuest.setMealType((String) mealList.getSelectedItem());
				newGuest.currentroom = roomSelected;
				
				for (int i = checkInIndex; i <= checkOutIndex; i++) {
					roomSelected.availableDates[i] = newGuest;
					
				} /* for */
				
				/*
				 * make sure new booking gets saved
				 */
								
				saveReservations();
				
				JOptionPane.showMessageDialog(null, "Guest Check In Successful");
				
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Credit Card Number");
			} /* if */
			
		} /* if */
    	
    } /* bookRoom */

	/********************
	 *Method Name: searchRooms()
	 *Method Date: January 18th 2016
	 *Method Description: This method goes through each of the rooms and matches the guest information to see if there are any rooms that match.
	 *				      If not it tells the user that that room does not exist.
	 *Inputs: roomType, bedType, checkin, checkout
	 *Outputs: N/A
	 ********************/
	
    public void searchRooms(String roomType, String bedType, Date checkin, Date checkout){
		
    	/* create an array to hold all the available rooms that are found */
    	
		int[] availRooms = new int[numRooms];
		int availRoomIndex = 0;
		
		/* use this to convert a date to a number between 0 and 365 */
		
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);
		
		cal.setTime(checkin);
		checkInIndex = cal.get(Calendar.DAY_OF_YEAR) - 1;
		
		boolean dateAvailable = true;
		
		/*
		 * Take one away from the checkOutIndex because we don't need to book the room on the day of checkout
		 */
		
		cal.setTime(checkout);
		checkOutIndex = cal.get(Calendar.DAY_OF_YEAR) - 2;
		
		for (int i = 0; i < numRooms; i++){
			/* set dateAvailable to true each time through - if the room is booked, it'll get set to false */
			dateAvailable = true;
			if (roomList.get(i).getRoomType().equalsIgnoreCase(roomType)) {
				if (roomList.get(i).bedType.equalsIgnoreCase(bedType)) {
		
					/* if we get here, we found the right kind of room.  Now see if its free on all the dates required */
					for (int k = checkInIndex; k <= checkOutIndex; k++){
						if (roomList.get(i).availableDates[k] != null){
							dateAvailable = false;
						} /* if */
						
					} /* for */
				
					if (dateAvailable == true){
						/*
						 * If we get here, the room must be available and be the right type
						 * Put the index of the available room in the availRooms array
						 */
						availRooms[availRoomIndex] = roomList.get(i).index;
						availRoomIndex++;
					} /* if */
				} /* if */
			} /* if */
		} /* for */
		
		if (availRoomIndex == 0) {
			JOptionPane.showMessageDialog(null, "Room Not Available.");
		} else {
		
			/*
			 * We found some free rooms.  Now create a table and let the user pick one.
			 */
			
			int i;
			String[][] roomTable = new String[availRoomIndex][5]; 
					
			for (i=0; i<availRoomIndex; i++) {
				roomTable[i][0] = String.valueOf(roomList.get(availRooms[i]).roomNumber);
				roomTable[i][1] = roomList.get(availRooms[i]).getRoomType();
				roomTable[i][2] = roomList.get(availRooms[i]).bedType;
				roomTable[i][3] = String.valueOf(roomList.get(availRooms[i]).getNumBeds());
				roomTable[i][4] = String.valueOf(roomList.get(availRooms[i]).maxCapacity);
							
			} /* for */
			
			String[] headings = {"Room Number", "Room Type", "Bed Type", "Number of Beds", "Max Capacity"};
			
			JButton Button;

			/*
			 * clean up any stuff on the contentPane
			 */
			
			Container contentPane = getContentPane();
			if(contentPane != null)
				contentPane.removeAll();
			validate();
			contentPane.repaint();

			JPanel roomPane = new JPanel();
			roomPane.setLayout(new BorderLayout());
			
			JPanel bottomPane = new JPanel();
			bottomPane.setLayout(new BorderLayout());

			L1 = new JLabel("Available Rooms", JLabel.CENTER);
			L1.setFont(new Font ("Book Antiqua", Font.BOLD, 24));
			roomPane.add(L1, BorderLayout.NORTH);

			Button = new JButton ("Book Room");
			Button.addActionListener (this);
			bottomPane.add (Button, BorderLayout.EAST);

			/* Initialize the JTable */
			
		    table = new JTable(roomTable, headings);

		    /* default the first row, in case the user doesn't select anything */
		    
			roomSelected = roomList.get(availRooms[0]);
		    
		    /*
		     * The addMouseListener method takes a MouseEvent and gives us back the row
		     * that the user clicked on.  The actual bookRoom method will be called from the 
		     * event handler on the "Book Room" button on this panel.
		     */
		    
			table.addMouseListener (new MouseAdapter ()
			{
			    public void mouseClicked (MouseEvent e)
			    {
					int row = table.getSelectedRow ();
					roomSelected = roomList.get(availRooms[row]);
			   	} /* mouseClicked */
			} /* new MouseAdapter */
			);

			JScrollPane scrollPane = new JScrollPane(table);
			roomPane.add(scrollPane, BorderLayout.CENTER);
						
			contentPane.add(scrollPane, BorderLayout.CENTER);
			contentPane.add(bottomPane, BorderLayout.SOUTH);
			
			validate();	
			
		} /* if */

	} /* searchRooms */
    
    /********************
	 *Method Name: viewStatus()
	 *Method Date: January 18th 2016
	 *Method Description: This method creates a JTable to show all the rooms with their type of bed, max capacity etc and if someone is currently in the room 
	 *Inputs: N/A
	 *Outputs: N/A
	 ********************/

    public void viewStatus() {
		
		/*
		 * create 2D array from arraylist of Room objects for the JTable
		 */
		
		int i;
		int temp;
		String[][] roomTable = new String[numRooms][5]; 
				
		for (i=0; i<numRooms; i++) {
			temp = roomList.get(i).roomNumber;
			roomTable[i][0] = Integer.toString(temp);
					
			roomTable[i][1] = roomList.get(i).getRoomType();
			
			temp = roomList.get(i).maxCapacity;
			roomTable[i][2] = Integer.toString(temp);
			roomTable[i][3] = roomList.get(i).bedType;
			if ((roomList.get(i).currentGuest() == null)) { 
				roomTable[i][4] = null;
			} /* if */
			else {
				roomTable[i][4] = roomList.get(i).currentGuest().firstname + " " + roomList.get(i).currentGuest().lastname;
			} /* else */
						
		} /* for */
		
		String[] headings = {"Room Number", "Room Type", "Capacity", "Bed Type", "Current Guest Name"};
		
		/*
		 * clean up any stuff on the contentPane
		 */
		
		Container contentPane = getContentPane();
				
		if(contentPane != null)
			contentPane.removeAll();
		validate();
		contentPane.repaint();
	
		roomPane = new JPanel();
		roomPane.setLayout(new BorderLayout());
	
		L1 = new JLabel("Room Status", JLabel.CENTER);
		L1.setFont(new Font ("Book Antiqua", Font.BOLD, 24));
		roomPane.add(L1, BorderLayout.NORTH);
	
		L2 = new JLabel("End of Page", JLabel.CENTER);
		roomPane.add (L2, BorderLayout.SOUTH);
	
	    /* Initialize the JTable */
		
	    table = new JTable(roomTable, headings);
	    
		JScrollPane scrollPane = new JScrollPane(table);
		roomPane.add(scrollPane, BorderLayout.CENTER);

		int result = JOptionPane.showConfirmDialog(null, roomPane, "Room Status", JOptionPane.OK_CANCEL_OPTION);
	
		validate();	
	    
	} /* viewStatus */

	/********************
	*Method Name:guestList()
	*Method Date: January 18th 2016
	*Method Description: similar to View status, but this JTable only shows the rooms and what guests are already booked into them 
	*Inputs: N/A
	*Outputs: N/A
	********************/

	public void guestList() {

		int i;
		int temp;
		int tableIndex = 0;
		String[][] roomTable = new String[numRooms][5]; 

		/*
		 * Go through all the rooms and call isVacant to see if anyone is currently in them.  If not, get the room number and name of the guest
		 * and put it in the JTable.
		 */
		
		for (i=0; i<numRooms; i++) {
			if (!(roomList.get(i).isVacant())) {
				temp = roomList.get(i).roomNumber;
				roomTable[tableIndex][0] = Integer.toString(temp); 
				roomTable[tableIndex][1] = roomList.get(i).currentGuest().firstname + " " + roomList.get(i).currentGuest().lastname;
				tableIndex++;
			} /* if */
		} /* for */
		
		String[] headings = {"Room Number", "Current Guest Name"};
	
		Container contentPane = getContentPane();
	
		/* clean up the GUI */
		
		contentPane = getContentPane();
		if(contentPane != null)
			contentPane.removeAll();
		validate();
		contentPane.repaint();
	
		roomPane = new JPanel();
		roomPane.setLayout(new BorderLayout());
	
		L1 = new JLabel("Guest List", JLabel.CENTER);
		L1.setFont(new Font ("Book Antiqua", Font.BOLD, 24));
		roomPane.add(L1, BorderLayout.NORTH);
	
		L2 = new JLabel("End of Page", JLabel.CENTER);
		roomPane.add (L2, BorderLayout.SOUTH);
	
	    /* Initialize the JTable */
		
	    table = new JTable(roomTable, headings);
	    
		JScrollPane scrollPane = new JScrollPane(table);
		roomPane.add(scrollPane, BorderLayout.CENTER);
	
		int result = JOptionPane.showConfirmDialog(null, roomPane, "Guest List", JOptionPane.OK_CANCEL_OPTION);
	
		validate();	
	    
	} /* guestList */

	/********************
	 *Method Name:checkout()
	 *Method Date: January 18th 2016
	 *Method Description: This method allows the user to enter the guests room number, which then finds the room and deletes the guest from being there and 
	 *					  calls the checkout method on the room object to check them out, which will display the total amount owing. 
	 *Inputs: N/A
	 *Outputs: N/A
	 * @throws IOException 
	 ********************/

	public void checkOut() throws IOException {
		
		/* clean up the GUI */
		
		Container contentPane = getContentPane();
		if(contentPane != null)
			contentPane.removeAll();
		validate();
		contentPane.repaint();
	
		JPanel p1 = null;
		JLabel L1;
		JTextField room;
		int roomnum;
		
		/* roomforCheckout will hold the index in the roomList of the room being checked out */
		int roomforCheckout = -1;
		
		p1 = new JPanel();
	
		L1 = new JLabel ("Room Number");
		
		room = new JTextField(5);
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		
		p1.add(L1);
		p1.add(room);
		
		int result = JOptionPane.showConfirmDialog(null, p1, "Check Out", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	    	roomnum = Integer.valueOf(room.getText());
	    	/* this loop will convert the room number (i.e 101) to the index in the roomList */
	    	
	    	for ( int i = 0; i < numRooms; i++) {
	    		if (roomList.get(i).roomNumber == roomnum){
	    			roomforCheckout = i;
	    		} /* if */
	    	} /* for */
	    	
	    	/* if roomforCheckout is still -1, it means we didn't find a room with that number */
	    		    	
	    	if (roomforCheckout != -1) {
	    		if (!(roomList.get(roomforCheckout).isVacant())){
	    			roomList.get(roomforCheckout).checkout();
	    		} else {
	    			JOptionPane.showMessageDialog(null, "Room Currently Empty");
	    		} /* else */
	    		
	    	} else {
	    		JOptionPane.showMessageDialog(null, "Room Not Found!");
	    	} /* if */
		
	    	/* make sure the reservation file gets updated */
	    	
			saveReservations();
	    	
	    } /* if */
	    
	} /* checkOut */
		
	/********************
	 *Method Name: saveReservations()
	 *Method Date: January 18th 2016 
	 *Method Description: this method saves the guest list to a file 
	 *Inputs: N/A
	 *Outputs: N/A
	 ********************/
	
	public void saveReservations() throws IOException {
		FileOutputStream out = new FileOutputStream(RESERVATION_PATH);
				
		/* objectoutputstream is a class that allows a program to write objects directly to a file. */ 
		
		ObjectOutputStream oout = new ObjectOutputStream(out);
		
		oout.writeObject(numRooms); 
		/*
		 * Write number of rooms to the file.
		 * Loop though the array and write out each object.
		 */
		
		int counter = 0;
		
		while (counter < numRooms) {
			/* we need to tell the writeObject method that we're writing a Room object so we use a cast */
			
			oout.writeObject((Room)roomList.get(counter));
			counter++;
		} /* while */
		
		/* flush and close the file to make sure writes get sent to the disk */
		oout.flush();
		oout.close();
		
	} /* saveReservations */
		
	/********************
	 *Method Name: loadReservations
	 *Method Date: January 18th 2016 
	 *Method Description: this method leads the guest list from the same file it saved them to 
	 *Inputs:N/A
	 *Outputs: N/A
	 ********************/
	public void loadReservations() throws IOException, ClassNotFoundException {
		
		boolean emptyfile = false;
		FileInputStream in = null;
		
		try {
			in = new FileInputStream(RESERVATION_PATH);
		} catch (FileNotFoundException e1) {
			emptyfile = true;
		} /* catch */
		
		if (emptyfile == false){	
			ObjectInputStream oin = new ObjectInputStream(in);
				
			/* we need to cast the object we read to an integer data type */
			numRooms = (int) oin.readObject(); 
						
			int counter = 0;
				
			while (counter < numRooms) {
					
				/* Similarly we need to cast the rest of the objects to a Room data type. */
				roomList.set(counter,((Room) oin.readObject())); 
				counter++;
			} /* while */
				
			/* close the file */
			oin.close();
		} /* if */
		
	} /* loadReservations */
		
	
	/********************
	 *Method Name: initializeRooms()
	 *Method Date: January 18th 2016
	 *Method Description: This method takes the rooms that have been pre-made from a text file and loads them into Room objects and 
	 *					  then puts them in the roomList.  A future enhancement would be to make this file XML or JSON or something better than just
	 *					  text.
	 *Inputs: N/A
	 *Outputs: N/A
	 ********************/
	
    private static void initializeRooms() throws IOException {
		
    	FileReader fr;
    	BufferedReader textReader;
    	
    	try {
    		fr = new FileReader(CONFIG_PATH);
    		textReader = new BufferedReader(fr);
    	} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "FATAL ERROR - no configuration file");
			System.exit(ERROR);
		}
		
		
		String configLine;
		
		
		fr = new FileReader(CONFIG_PATH);
		
		textReader = new BufferedReader(fr);
		
		configLine = textReader.readLine();
		numRooms = Integer.valueOf(configLine);
		
		for (int i = 0; i < numRooms; i++){
			Room temp = new Room();
			temp.index = i;
			temp.setRoomType(textReader.readLine());
			temp.setNumBeds(Integer.valueOf(textReader.readLine()));
			temp.bedType = textReader.readLine();
			temp.sofabed = Boolean.valueOf(textReader.readLine());
			temp.maxCapacity = Integer.valueOf(textReader.readLine());
			temp.breakfastInBed = Boolean.valueOf(textReader.readLine());
			temp.roomNumber = Integer.valueOf(textReader.readLine());
			temp.currentDuration = 0;
			
			temp.availableDates = new Guest[MAX_CALENDAR];
			
			for (int k = 0; k < MAX_CALENDAR; k++){
				temp.availableDates[k] = null;
			} /* for */

			/* set the price for each room based on roomType */
			
			if (temp.getRoomType().equalsIgnoreCase("Standard")){
				temp.roomRate = STANDARD_RATE;
			} else if (temp.getRoomType().equalsIgnoreCase("Deluxe")) {
				temp.roomRate = DELUXE_RATE;
			} else if (temp.getRoomType().equalsIgnoreCase("Premium")) {
				temp.roomRate = PREMIUM_RATE;
			}
			
			roomList.add(temp);
			
		} /* for */
		
		textReader.close();
		
	} /* initializeRooms */

	/********************
	 *Method Name: main 
	 *Method Date: January 18th 2016
	 *Method Description: this method calls initializeRooms and then loads the GUI by creating a copy of Brookstonev3. 
	 *Inputs: String[] args
	 *Outputs: N/A
	 ********************/
	
    public static void main(String[] args) throws IOException {

    	initializeRooms();
    	
        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
                Brookstonev3 ex = new Brookstonev3();
                ex.setVisible(true);
            }
        });
    } /* main */
    
} /* Brookstonev3 class */


/********************
 *Class Name: Room
 *Class Date: January 18th 2016
 *Class Description: This class sets all the global variables and methods for the rooms specifically in order for them to be easily accessed and used. 
 *					 It also creates the calendar and the total price for checkout
 *Inputs: N/A
 *Outputs: N/A
 ********************/

class Room implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String roomType;
	private int beds;
	public String bedType;
	public boolean sofabed;
	public int maxCapacity;
	public boolean breakfastInBed;
	public Guest availableDates[];
	public int roomNumber;
	public int index;
	public int currentDuration;
	public int roomRate;

	
	/********************
	 *Method Name: getNumBeds 
	 *Method Date: January 18th 2016
	 *Method Description: this method returns the private variable beds 
	 *Inputs: N/A 
	 *Outputs: number of beds (int)
	 ********************/
	
	public int getNumBeds(){
		return beds;
	} /* getNumBeds */
	
	/********************
	 *Method Name: getRoomType 
	 *Method Date: January 18th 2016
	 *Method Description: this method returns the private variable roomType 
	 *Inputs: N/A
	 *Outputs: type of room (String)
	 ********************/
	
	public String getRoomType() {
		return roomType;
	} /* getRoomType */
	
	/********************
	 *Method Name: setRoomType 
	 *Method Date: January 18th 2016
	 *Method Description: this method sets the private variable roomType 
	 *Inputs: roomType (String)
	 *Outputs: N/A
	 ********************/
	
	public void setRoomType(String rt) {
		roomType = rt;
	} /* setRoomType */
	
	/********************
	 *Method Name: setNumBeds 
	 *Method Date: January 18th 2016
	 *Method Description: this method sets the private variable beds 
	 *Inputs: beds (int)
	 *Outputs: N/A
	 ********************/
	
	public void setNumBeds(int b){
		beds = b;
	} /* setNumBeds */
		
	/********************
	 *Method Name: isVacant
	 *Method Date: January 18th 2016
	 *Method Description: this method calls currentGuest and if gets a null return, it means the room is free and returns true 
	 *Inputs: N/A
	 *Outputs: boolean
	 ********************/
	
	public boolean isVacant(){
		
		if (currentGuest() == null){
			return true;
		}
		else {
			return false;
		}
	} /* isVacant */
	
	/********************
	 *Method Name: currentGuest
	 *Method Date: January 18th 2016
	 *Method Description: this method gets the current date and then looks in the array of available dates to see if there is a Guest object.
	 *					  If so, it returns the Guest.  If not, it returns null. 
	 *Inputs: N/A
	 *Outputs: Guest object (or null)
	 ********************/
	
	public Guest currentGuest() {
		
		Calendar cal = Calendar.getInstance();
		int index = cal.get(Calendar.DAY_OF_YEAR) - 1;
		
		if (availableDates[index] == null) {
			return null;
		} else {
			return availableDates[index];
		} /* else */
		
	} /* currentGuest */
	
	/********************
	 *Method Name: bookRoom
	 *Method Date: January 18th 2016
	 *Method Description: this method doesn't do anything, but might be used for future features.
	 *					  
	 *Inputs: Guest
	 *Outputs: boolean
	 ********************/
	
	public boolean bookRoom(Guest g){
		
		return true;
	} /* bookRoom */
	
	/********************
	 *Method Name: checkout
	 *Method Date: January 18th 2016
	 *Method Description: This method gets the current guest for the room, then looks at the availableDates array to find out how long they stayed.
	 *					  It then calculates the balance owing and deletes the guest out of the current date in the array. 
	 *					  
	 *Inputs: N/A
	 *Outputs: boolean
	 ********************/
	
	public boolean checkout(){
		
		int totalDays = 0, totalBill = 0;
		Guest checkoutGuest = currentGuest();
					
		/* get todays date */
		Calendar cal = Calendar.getInstance();
		int index = cal.get(Calendar.DAY_OF_YEAR) - 1;
		
		for (int i = index; i != -1; i--) {
			if (availableDates[i] == checkoutGuest) {
				totalDays++;
			} /* if */
		} /* for */
		
		totalBill = totalDays * roomRate; 
		JOptionPane.showMessageDialog(null, "Total Charge Is: $" + totalBill);
		
		availableDates[index]= null;
		
		return true;
		
	} /* checkout */
	
} /* Room */

/********************
 *Class Name: Guest
 *Class Date: January 18th 2016
 *Class Description: this class sets global variables and methods for the guest specifically so they can be accessed throughout the code.
 *Inputs: N/A
 *Outputs: N/A
 ********************/

class Guest implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String mealType;
	public String firstname;
	public String lastname;
	public String phonenumber;
	public double creditCard;
	public Room currentroom;
	
	public String getMealType(){
		return mealType; 
	} /* getMealType */
	
	public void setMealType(String mt){
		mealType = mt; 
	} /* setMealType */
	
} /* Guest */
