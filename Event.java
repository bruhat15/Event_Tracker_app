import java.time.LocalDate;

public abstract class Event{ // Abstraction: base class for common event properties
    private String name; //--> Encapsulation
    private LocalDate date;

    public Event(String name,LocalDate date){
        this.name=name;
        this.date=date;
    }

    public String getName(){ return name; }
    public LocalDate getDate(){ return date; }

    public abstract String getReminderMessage(); // Polymorphism: to be overridden
}
