package predicate_to_DFA;

import java.util.ArrayList;

public class DFAReshaper {
	public EpsilonEraser epsilonEraser;
	public Marger marger;
	public DummyEraser dummyEraser;
	public PredicateEraser predicateEraser;
	public ArrayList<State> stateList;

	public DFAReshaper() {
		epsilonEraser = new EpsilonEraser();
		marger = new Marger();
		dummyEraser = new DummyEraser();
		predicateEraser = new PredicateEraser();
	}

	public void reshapeDEA(ArrayList<State> stateList) {
		epsilonEraser.eraseEpsilon(stateList);

		marger.margeTransition(stateList);
		dummyEraser.eraseDummy(marger.getStateList());
		predicateEraser.erasePredicate(dummyEraser.getStateList());

		this.stateList = predicateEraser.getStateList();
	}

	public ArrayList<State> getStateList() {
		return this.stateList;
	}

}
