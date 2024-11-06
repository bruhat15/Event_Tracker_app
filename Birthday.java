import java.time.LocalDate;

public class Birthday extends Event{ // Birthday event class inherits from Event
    private int age; // Encapsulation: private field for age

    public Birthday(String name,LocalDate date,int age){ // Constructor
        super(name,date); // Call superclass constructor
        this.age=age; // Initialize age
    }

    public int getAge(){ return age; } // Getter for age

    @Override
    public String getReminderMessage(){ // Polymorphism: overriding method for specific reminder
        return "Don't forget "+getName()+"'s birthday on "+getDate()+"! They will be "+age+" years old."; // Return reminder
    }
}
