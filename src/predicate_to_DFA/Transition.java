package predicate_to_DFA;

import predicate_to_DFA.Enum.SymbolCase;

public class Transition {
	private State nextState;
	private String symbol;
	private SymbolCase symbolCase;

	public Transition() {

	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}

	public void setSymbolAndCase(String symbol, SymbolCase symbolCase) {
		this.symbol = symbol;
		this.symbolCase = symbolCase;
	}

	public void setSymbolCase(SymbolCase symbolCase) {
		this.symbolCase = symbolCase;
	}

	public State getNextState() {
		return this.nextState;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public SymbolCase getSymbolCase() {
		return this.symbolCase;
	}
}

class EpsilonTransition extends Transition {
	public EpsilonTransition(State nextState) {
		super();
		super.setNextState(nextState);
	}

	public State getNextState() {
		return super.getNextState();
	}
}

class EOFTransition extends Transition {
	public EOFTransition() {
		super();
		super.setNextState(new AcceptState());
		super.setSymbolCase(SymbolCase.EOF);
	}
}