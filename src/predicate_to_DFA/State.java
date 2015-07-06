package predicate_to_DFA;

import java.util.ArrayList;

public class State {
	private ArrayList<Transition> nextTransitions;
	private int stateNumber;
	private ArrayList<Integer> coStateNumber;
	private int predicateNumber;
	static private int stateCounter;

	static {
		stateCounter = 0;
	}

	public State() {
		this.nextTransitions = new ArrayList<Transition>();
		this.coStateNumber = new ArrayList<Integer>();
		this.stateNumber = stateCounter;
		this.coStateNumber.add(stateCounter);
		this.predicateNumber = 0;
		stateCounter++;
	}

	public void addNextTransition(Transition nextTransition) {
		this.nextTransitions.add(nextTransition);
	}

	public void setNextTransitions(ArrayList<Transition> nextTransitions) {
		this.nextTransitions = nextTransitions;
	}

	public void setPredicateNumber(int predicatePoint) {
		this.predicateNumber = predicatePoint;
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

	public int getPredicateNumber() {
		return this.predicateNumber;
	}

	public ArrayList<Integer> getCoStateNumber() {
		return this.coStateNumber;
	}

	public void setAccept() {
		this.stateNumber = -1;
	}

	public void setStateNumber(int stateNumber) {
		this.stateNumber = stateNumber;
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
