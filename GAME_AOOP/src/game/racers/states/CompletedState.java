package game.racers.states;


public class CompletedState implements RacerAlertState {
	

	public void alert(RacerState context) {
		System.out.println("Completed the race");
	}
	

	public String toString() {return "Completed";}

}
