import java.time.LocalDate;

public class GeneralEvent extends Event{ // Inheritance: GeneralEvent inherits from Event
    private String eventType; // Encapsulation: private field for event type

    public GeneralEvent(String name,LocalDate date,String eventType){ // Constructor
        super(name,date); // Call parent constructor
        this.eventType=eventType;
    }

    public String getEventType(){ return eventType; } // Getter for event type

    @Override
    public String getReminderMessage(){ // Polymorphism: Overriding method for reminder
        return "Remember the "+eventType+" for "+getName()+" on "+getDate()+".";
    }
}
