package game.racers.states;


public class ActiveState implements RacerAlertState {


	public void alert(RacerState context) {
		System.out.println("is Active");
	}
	

	@Override
	public String toString() {return "Active";}

}
