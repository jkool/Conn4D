package au.gov.ga.conn4d.impl.behavior;

import au.gov.ga.conn4d.Intersector;
import au.gov.ga.conn4d.Particle;
import au.gov.ga.conn4d.Settlement;

/**
 * 
 * Provides behavior that relates to settling - performing no actions.
 * 
 * @author Johnathan Kool
 * 
 */

public class Settlement_None implements Settlement, Cloneable {

	/**
	 * Performs actions associated with settling - in this case, taking no
	 * action.
	 */

	@Override
	public void apply(Particle p) {
	}

	/**
	 * Returns a copy of the class instance. In this case the same object is
	 * returned since it effectively does nothing.
	 */

	@Override
	public Settlement_None clone() {
		return this;
	}

	/**
	 * Sets the type of Intersector being used to identify intersections between
	 * Particles/Particle Paths and the settlement polygons. In this case, no
	 * action is required.
	 */

	@Override
	public void setIntersector(Intersector isect) {
	}
}
