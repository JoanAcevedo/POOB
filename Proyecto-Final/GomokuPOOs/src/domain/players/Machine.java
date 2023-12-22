package domain.players;

//Class Machine

public class Machine extends Player{

    private String gameMode;

    public Machine(String name, String color){
        super(name, color);
        this.gameMode = "Default";
    }
    
}
