package au.gov.ga.conn4d.impl.movement;

import au.gov.ga.conn4d.Diffuser;
import au.gov.ga.conn4d.Particle;
import au.gov.ga.conn4d.utils.GeometryUtils;
import cern.jet.random.Normal;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister64;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.engine.RandomSeedTable;

/**
 * Performs simple anisotropic diffusion in three dimensions.
 * 
 * @author Johnathan Kool based on FORTRAN code developed by Robert K. Cowen,
 *         Claire Paris and Ashwanth Srinivasan.
 */

public class Diffusion_Simple_3D implements Diffuser, Cloneable {

	// from http://drs.nio.org/drs/bitstream/2264/503/1/Proc_AP_Akademi_Sci_7_123.pdf
	// horizontal eddy diffusion coefficients are around 1E3 cm2/s.  Vertical coeffs are around 1 cm2/s
	
	//private float TL = 21600f;
	//private float uK = (float) Math.sqrt(2 * 0.03f / TL); // The magic number in distance/seconds.  .03 is the variance in u, 21600 is 6 hours.
	//private float vK = (float) Math.sqrt(2 * 0.03f / TL); // The magic number in distance/seconds.  .03 is the variance in v, 21600 is 6 hours.
	private float uK = 2f;  // Open-ocean vertical eddy diffusivity coefficient in m/s from http://oceanworld.tamu.edu/resources/ocng_textbook/chapter08/chapter08_05.htm (Ledwell, Watson and Law 1991)
	private float vK = 2f;  // Open-ocean vertical eddy diffusivity coefficient in m/s from http://oceanworld.tamu.edu/resources/ocng_textbook/chapter08/chapter08_05.htm (Ledwell, Watson and Law 1991)
	private float wK = 1.0E-5f; // Open-ocean vertical eddy diffusivity coefficient in m/s from http://oceanworld.tamu.edu/resources/ocng_textbook/chapter08/chapter08_05.htm (Munk, 1966)
	private float h = 7200; // Minimum integration time step (default=2hrs)
	private float sqrt_h = (float) Math.sqrt(h);

	// Seed the random number generator by randomly picking from a seed table.
	
	private int seed = RandomSeedTable.getSeedAtRowColumn(
			Uniform.staticNextIntFromTo(0, Integer.MAX_VALUE),
			Uniform.staticNextIntFromTo(0, RandomSeedTable.COLUMNS));
	private RandomEngine re = new MersenneTwister64(seed);
	private Normal norm = new Normal(0, 1, re);
	
	public Diffusion_Simple_3D(){}

	public Diffusion_Simple_3D(float h) {
		this.h = h / 1000; // convert h from milliseconds to seconds
		this.sqrt_h = (float) Math.sqrt(h);
	}

	/**
	 * Applies turbulent diffusion velocity to a particle.
	 * 
	 * The equations are based on equations 8 and 9 of Dimou, K.N. and Adams,
	 * E.E. 1993 - Estuarine and Coastal Shelf Science 37:99-110. A Random-walk,
	 * Particle Tracking Model for Well-Mixed Estuaries and Coastal Waters
	 * 
	 * Because we are calculating the diffusion coefficients independently (sqrt2D in
	 * Dimou's text), we multiply the result by sqrt of h (delta t in Dimou's text)
	 * since the square root of products is equal to the product of their square roots.
	 * 
	 * @param p
	 *            - The particle to be acted upon.
	 */

	@Override
	public void apply(Particle p) {

		float usc = uK * sqrt_h;
		float vsc = vK * sqrt_h;
		float wsc = wK * sqrt_h;

		double dx = usc * (float) norm.nextDouble();
		double dy = vsc * (float) norm.nextDouble();
		double dz = wsc * (float) norm.nextDouble();

		// Determine the new coordinates

		double[] coords = GeometryUtils.latLon(new double[] { p.getY(), p.getX() }, dy,
				dx);

		// Update the particle's coordinates.

		p.setX(coords[1]);
		p.setY(coords[0]);
		p.setZ(p.getZ()+dz);
	}

	/**
	 * Gets the minimum integration time step currently being used (in seconds)
	 */

	public float getH() {
		return h * 1000;
	}

	/**
	 * Sets the minimum integration time step (in seconds)
	 * 
	 * @param h
	 */

	public void setH(float h) {
		this.h = h / 1000;
		this.sqrt_h = (float) Math.sqrt(h);
	}
	
	/**
	 * Returns a copy of the class instance
	 */

	@Override
	public Diffusion_Simple_3D clone() {
		return new Diffusion_Simple_3D(h * 1000);
	}
}
