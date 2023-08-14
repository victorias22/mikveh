package game.racers.states;

public class DisabledState implements RacerAlertState {


	public void alert(RacerState context) {
		System.out.println("is Disabled");
	}
	

	@Override
	public String toString() {return "Disabled";}
	
}
