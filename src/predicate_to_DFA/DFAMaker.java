package predicate_to_DFA;

import java.util.ArrayList;

import predicate_to_DFA.Enum.SymbolCase;
import predicate_to_DFA.Enum.Token;

public class DFAMaker {
	private State currentState;
	private ArrayList<State> stateList;
	private ArrayList<StateLabel> openPoints;
	private ArrayList<StateLabel> mergePoints;
	private ArrayList<StateLabel> predicatePoints;
	private boolean optionalFlag;
	private boolean repetitionFlag;
	private boolean predicateFlag;
	private Token token;

	public DFAMaker() {
		this.currentState = new State();
		this.stateList = new ArrayList<State>();
		this.stateList.add(this.currentState);
		this.openPoints = new ArrayList<StateLabel>();
		this.mergePoints = new ArrayList<StateLabel>();
		this.predicatePoints = new ArrayList<StateLabel>();
		this.optionalFlag = false;
		this.repetitionFlag = false;
		this.predicateFlag = false;
	}

	public void makeDFA(ArrayList<String> tokenList)
			throws CodingErrorException, SyntaxErrorException {
		int position = 0;
		while (position < tokenList.size()) {
			this.token = Token.getEnum(tokenList.get(position));
			switch (this.token) {
			case CHOICE:
				if (this.openPoints.isEmpty()) {
					System.out.println("Parentheses is not corresponding.");
					throw new SyntaxErrorException();
				}
				this.mergePoints
						.add(new StateLabel(this.currentState, position));
				this.currentState = this.openPoints.get(
						this.openPoints.size() - 1).getState();
				break;
			case OPTIONAL:
			case REPETITION:
				System.out
						.println("Position of optional or repetition operator(?,*) is invalid.");
				throw new SyntaxErrorException();
			case OPEN:
				this.openPoints
						.add(new StateLabel(this.currentState, position));
				break;
			case CLOSE:
				if (this.openPoints.isEmpty()) {
					System.out.println("Parentheses is not corresponding.");
					throw new SyntaxErrorException();
				}
				int openPoint = this.openPoints.get(this.openPoints.size() - 1)
						.getLabel();
				if (position + 1 < tokenList.size()) {
					if (Token.getEnum(tokenList.get(position + 1)) == Token.OPTIONAL) {
						this.optionalFlag = true;
					} else if (Token.getEnum(tokenList.get(position + 1)) == Token.REPETITION) {
						this.repetitionFlag = true;
					}
				}
				if (!(this.mergePoints.isEmpty())) {
					while (openPoint < this.mergePoints.get(
							this.mergePoints.size() - 1).getLabel()) {
						this.mergePoints
								.get(this.mergePoints.size() - 1)
								.getState()
								.addNextTransition(
										new EpsilonTransition(this.currentState));
						this.mergePoints.remove(this.mergePoints.size() - 1);
						if (this.mergePoints.isEmpty()) {
							break;
						}
					}
				}
				if (this.optionalFlag) {
					this.openPoints
							.get(this.openPoints.size() - 1)
							.getState()
							.addNextTransition(
									new EpsilonTransition(this.currentState));
					this.optionalFlag = false;
					position++;
				}
				if (this.repetitionFlag) {
					this.currentState.addNextTransition(new EpsilonTransition(
							this.openPoints.get(this.openPoints.size() - 1)
									.getState()));
					this.currentState = this.openPoints.get(
							this.openPoints.size() - 1).getState();
					this.repetitionFlag = false;
					position++;
				}
				if (!(this.predicatePoints.isEmpty())) {
					if (this.predicatePoints.get(
							this.predicatePoints.size() - 1).getLabel() + 1 == this.openPoints
							.get(this.openPoints.size() - 1).getLabel()) {
						this.currentState
								.setPredicateNumber(this.predicatePoints.size());
						this.currentState = this.predicatePoints.get(
								this.predicatePoints.size() - 1).getState();
						this.predicatePoints
								.remove(this.predicatePoints.size() - 1);
					}
				}
				this.openPoints.remove(this.openPoints.size() - 1);
				break;
			case PREDICATE:
				if (Token.getEnum(tokenList.get(position + 1)) == Token.ANY
						|| Token.getEnum(tokenList.get(position + 1)) == Token.OTHER
						|| Token.getEnum(tokenList.get(position + 1)) == Token.OPEN) {
					if (Token.getEnum(tokenList.get(position + 1)) == Token.ANY
							|| Token.getEnum(tokenList.get(position + 1)) == Token.OTHER) {
						this.predicateFlag = true;
					}
					this.predicatePoints.add(new StateLabel(this.currentState,
							position));
				} else {
					System.out
							.println("Position of predicate operator(!) is invalid.");
					throw new SyntaxErrorException();
				}
				break;
			case ANY:
			case OTHER:
				Transition transition = new Transition();
				State nextState = new State();
				this.stateList.add(nextState);
				if (this.token == Token.ANY) {
					transition.setSymbolCase(SymbolCase.ANY);
				} else {
					transition.setSymbolAndCase(tokenList.get(position),
							SymbolCase.SYMBOL);
				}
				if (Token.getEnum(tokenList.get(position + 1)) == Token.OPTIONAL) {
					this.optionalFlag = true;
					position++;
				} else if (Token.getEnum(tokenList.get(position + 1)) == Token.REPETITION) {
					this.repetitionFlag = true;
					position++;
				}
				if (this.repetitionFlag) {
					this.currentState.addNextTransition(new EpsilonTransition(
							nextState));
					transition.setNextState(nextState);
					nextState.addNextTransition(transition);
					this.repetitionFlag = false;
				} else {
					if (this.optionalFlag) {
						this.currentState
								.addNextTransition(new EpsilonTransition(
										nextState));
						this.optionalFlag = false;
					}
					transition.setNextState(nextState);
					this.currentState.addNextTransition(transition);
				}
				if (this.predicateFlag) {
					nextState.setPredicateNumber(this.predicatePoints.size());
					this.currentState = this.predicatePoints.get(
							this.predicatePoints.size() - 1).getState();
					this.predicatePoints
							.remove(this.predicatePoints.size() - 1);
					this.predicateFlag = false;
				} else {
					this.currentState = nextState;
				}
			}
			position++;
		}
		this.currentState.addNextTransition(new EOFTransition());
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}
}