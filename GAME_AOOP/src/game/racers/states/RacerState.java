package game.racers.states;


public class RacerState {
	private RacerAlertState currentState;
	

	public RacerState() {
		currentState = new ActiveState();
	}
	

	public void setState(RacerAlertState state) {
		this.currentState = state;
	}
	

	public RacerAlertState getCurrentState() {
		return currentState;
	}
	

	public void alert() {
		this.currentState.alert(this);
	}
}
