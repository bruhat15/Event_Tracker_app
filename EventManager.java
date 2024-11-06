import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import javax.swing.JTextArea;

public class EventManager{
    private List<Event> events; // Encapsulation: Private list for storing events

    public EventManager(){
        events=new ArrayList<>();
        loadEventsFromFile(); // Load saved events on init
    }

    public void addEvent(Event event){
        events.add(event);
        saveEventsToFile(); // Save after every add
    }

    public void displayReminders(JTextArea outputArea){
        LocalDate today=LocalDate.now();
        for(Event event:events){
            if(event.getDate().isEqual(today)){
                outputArea.append(event.getReminderMessage()+"\n"); // Polymorphism: calling overridden method for message
            }
        }
    }

    public void displayRemindersForDate(JTextArea outputArea, LocalDate selectedDate){
        for(Event event:events){
            if(event.getDate().isEqual(selectedDate)){
                outputArea.append(event.getReminderMessage()+"\n"); // Polymorphic call for reminder
            }
        }
    }

    // Save events to CSV file
    private void saveEventsToFile(){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("events.csv"))){
            for(Event event:events){
                if(event instanceof Birthday){ // Runtime polymorphism: object type check at runtime
                    Birthday birthday=(Birthday)event;
                    writer.write("Birthday,"+birthday.getName()+","+birthday.getDate()+","+birthday.getAge());
                }else if(event instanceof Anniversary){
                    Anniversary anniversary=(Anniversary)event;
                    writer.write("Anniversary,"+anniversary.getName()+","+anniversary.getDate()+","+anniversary.getCelebrationType());
                }else if(event instanceof GeneralEvent){
                    GeneralEvent generalEvent=(GeneralEvent)event;
                    writer.write("General,"+generalEvent.getName()+","+generalEvent.getDate()+","+generalEvent.getEventType());
                }
                writer.newLine();
            }
        } catch(IOException e){
            e.printStackTrace(); // Handle file I/O exceptions
        }
    }

    // Load events from CSV file
    private void loadEventsFromFile(){
        try(BufferedReader reader=new BufferedReader(new FileReader("events.csv"))){
            String line;
            while((line=reader.readLine())!=null){
                String[] parts=line.split(",");
                String eventType=parts[0];
                String name=parts[1];
                LocalDate date=LocalDate.parse(parts[2]);

                // Polymorphism: create event based on type
                switch(eventType){
                    case "Birthday":
                        int age=Integer.parseInt(parts[3]);
                        events.add(new Birthday(name,date,age));
                        break;
                    case "Anniversary":
                        String celebrationType=parts[3];
                        events.add(new Anniversary(name,date,celebrationType));
                        break;
                    case "General":
                        String generalEventType=parts[3];
                        events.add(new GeneralEvent(name,date,generalEventType));
                        break;
                }
            }
        } catch(IOException e){
            // Exception handling for file operations
        }
    }
}
