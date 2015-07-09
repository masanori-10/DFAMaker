package predicate_to_DFA;

import java.util.ArrayList;

public class State {
	private ArrayList<Transition> nextTransitions;
	private int stateNumber;
	private ArrayList<Integer> coStateNumber;
	static private int stateCounter;
	private boolean isEOP;

	static {
		stateCounter = 0;
	}

	public State() {
		this.nextTransitions = new ArrayList<Transition>();
		this.coStateNumber = new ArrayList<Integer>();
		this.stateNumber = stateCounter;
		this.coStateNumber.add(stateCounter);
		stateCounter++;
		this.isEOP = false;
	}

	public void addNextTransition(Transition nextTransition) {
		this.nextTransitions.add(nextTransition);
	}

	public void setNextTransitions(ArrayList<Transition> nextTransitions) {
		this.nextTransitions = nextTransitions;
	}

	public void setCoStateNumber(ArrayList<Integer> coStateNumber) {
		this.coStateNumber = coStateNumber;
	}

	public void setCoStateNumber(ArrayList<Integer> coStateNumberA,
			ArrayList<Integer> coStateNumberB) {
		this.coStateNumber = new ArrayList<Integer>();
		this.coStateNumber.addAll(coStateNumberA);
		this.coStateNumber.addAll(coStateNumberB);
	}

	public ArrayList<Transition> getNextTransitions() {
		return this.nextTransitions;
	}

	public int getStateNumber() {
		return this.stateNumber;
	}

	public ArrayList<Integer> getCoStateNumber() {
		return this.coStateNumber;
	}

	public void setAccept() {
		this.stateNumber = -1;
	}

	public void setEOP() {
		this.isEOP = true;
	}

	public void setStateNumber(int stateNumber) {
		this.stateNumber = stateNumber;
	}

	public boolean isEOP() {
		return this.isEOP;
	}
}

class StateLabel {
	private State state;
	private int label;

	public StateLabel(State state, int label) {
		this.state = state;
		this.label = label;
	}

	public State getState() {
		return this.state;
	}

	public int getLabel() {
		return this.label;
	}
}

class AcceptState extends State {
	public AcceptState() {
		super();
		super.setAccept();
	}
}