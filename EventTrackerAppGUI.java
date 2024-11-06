import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EventTrackerAppGUI extends JFrame{
    private EventManager eventManager;
    private JTextField nameField,dateField,typeField,ageField,selectedDateField;
    private JTextArea reminderArea;
    private JComboBox<String> eventTypeBox;

    public EventTrackerAppGUI(){
        eventManager = new EventManager(); // Initialize event manager

        // Set up main frame
        setTitle("Event Tracker");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for user input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8,2));

        // Event type input with JComboBox (dropdown)
        inputPanel.add(new JLabel("Event Type:"));
        eventTypeBox=new JComboBox<>(new String[]{"Birthday","Anniversary","General"});
        inputPanel.add(eventTypeBox);

        inputPanel.add(new JLabel("Event Name:"));
        nameField=new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Event Date (YYYY-MM-DD):"));
        dateField=new JTextField();
        inputPanel.add(dateField);

        // Optional Age input field for birthdays
        inputPanel.add(new JLabel("Age (optional for Birthday):"));
        ageField=new JTextField();
        inputPanel.add(ageField);

        // Type/Celebration type input field
        inputPanel.add(new JLabel("Celebration/Type:"));
        typeField=new JTextField();
        inputPanel.add(typeField);

        // Buttons for adding events and displaying reminders
        JButton addButton=new JButton("Add Event");
        addButton.addActionListener(new AddEventButtonListener());
        inputPanel.add(addButton);

        JButton displayTodayButton=new JButton("Display Today's Reminders");
        displayTodayButton.addActionListener(new DisplayRemindersButtonListener());
        inputPanel.add(displayTodayButton);

        // Input field for selecting a specific date
        inputPanel.add(new JLabel("Select Date for Reminders (YYYY-MM-DD):"));
        selectedDateField=new JTextField();
        inputPanel.add(selectedDateField);

        // Button for displaying reminders on a selected date
        JButton displaySelectedDateButton=new JButton("Display Reminders on Selected Date");
        displaySelectedDateButton.addActionListener(new DisplaySelectedDateRemindersButtonListener());
        inputPanel.add(displaySelectedDateButton);

        // Display area for reminders
        reminderArea=new JTextArea(10,30);
        reminderArea.setEditable(false);

        add(inputPanel,BorderLayout.NORTH);
        add(new JScrollPane(reminderArea),BorderLayout.CENTER);

        loadReminders(); // Load today’s reminders when GUI opens
    }

    private void loadReminders(){
        // Display today's reminders on app start
        reminderArea.setText("Today's Reminders:\n");
        eventManager.displayReminders(reminderArea);
    }

    // Listener for adding events
    private class AddEventButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                String name=nameField.getText();
                LocalDate date=LocalDate.parse(dateField.getText());
                String selectedType=(String)eventTypeBox.getSelectedItem();

                // Handle event type selection and creation
                if(selectedType.equals("Birthday")){
                    int age=ageField.getText().isEmpty() ? -1 : Integer.parseInt(ageField.getText()); // Optional age input
                    eventManager.addEvent(new Birthday(name,date,age));
                } else if(selectedType.equals("Anniversary")){
                    String celebrationType=typeField.getText();
                    eventManager.addEvent(new Anniversary(name,date,celebrationType));
                } else if(selectedType.equals("General")){
                    String eventType=typeField.getText();
                    eventManager.addEvent(new GeneralEvent(name,date,eventType));
                }

                // Clear fields after adding
                nameField.setText("");
                dateField.setText("");
                ageField.setText("");
                typeField.setText("");

                JOptionPane.showMessageDialog(null,"Event added successfully!");

            } catch(DateTimeParseException ex){
                // Handle incorrect date format
                JOptionPane.showMessageDialog(null,"Please enter a valid date (YYYY-MM-DD).");
            } catch(NumberFormatException ex){
                // Handle incorrect number format for age
                JOptionPane.showMessageDialog(null,"Please enter a valid age (only numbers).");
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null,"An error occurred: "+ex.getMessage());
            }
        }
    }

    // Listener for displaying today's reminders
    private class DisplayRemindersButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            reminderArea.setText("Today's Reminders:\n");
            eventManager.displayReminders(reminderArea);
        }
    }

    // Listener for displaying reminders for a selected date
    private class DisplaySelectedDateRemindersButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                LocalDate selectedDate=LocalDate.parse(selectedDateField.getText());
                reminderArea.setText("Reminders for "+selectedDate+":\n");
                eventManager.displayRemindersForDate(reminderArea,selectedDate);
            } catch(DateTimeParseException ex){
                // Handle incorrect date format
                JOptionPane.showMessageDialog(null,"Please enter a valid date (YYYY-MM-DD).");
            }
        }
    }

    public static void main(String[] args){
        EventTrackerAppGUI app=new EventTrackerAppGUI();
        app.setVisible(true); // Launch the GUI
    }
}
