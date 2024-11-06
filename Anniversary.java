import java.time.LocalDate;

public class Anniversary extends Event{
    private String celebrationType; // Type of anniversary celebration

    public Anniversary(String name,LocalDate date,String celebrationType){
        super(name,date); // Call superclass constructor (Event)
        this.celebrationType=celebrationType; // Set celebration type
    }

    public String getCelebrationType(){
        return celebrationType;
    }

    @Override
    public String getReminderMessage(){ // Polymorphism
        // Custom reminder message for anniversaries
        return "Celebrate "+getName()+"'s "+celebrationType+" anniversary on "+getDate()+"!";
    }
}
