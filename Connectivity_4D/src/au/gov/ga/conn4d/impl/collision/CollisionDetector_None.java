package au.gov.ga.conn4d.impl.collision;

import au.gov.ga.conn4d.Boundary;
import au.gov.ga.conn4d.CollisionDetector;
import au.gov.ga.conn4d.Particle;

/**
 * Collision Detector implementation that performs no actions.  Primarily used
 * for debugging and testing.
 * 
 * @author Johnathan Kool
 *
 */

public class CollisionDetector_None implements CollisionDetector, Cloneable {

	/**
	 * Performs actions that relocate a Particle upon encountering a barrier.
	 */
	@Override
	public void handleIntersection(Particle p) {}
	
	/**
	 * Generates a clone of the CollisionDetection instance.
	 */
	@Override
	public CollisionDetector_None clone(){
		return this;
	}
	
	/**
	 * Identifies whether a 4-dimensional coordinate (x,y,z,t) is within bounds
	 * or not.
	 */
	@Override
	public boolean isInBounds(long t, double z, double x, double y){return true;}
	
	/**
	 * Returns the Boundary object associated with this Class.
	 */
	@Override
	public Boundary getBoundary(){return null;}
}
