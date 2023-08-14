package game.racers.states;


public class BrokenState implements RacerAlertState {
	

	@Override
	public void alert(RacerState context) {
		System.out.println("is Broken");
	}
	

	@Override
	public String toString() {return "Broken";}

}
