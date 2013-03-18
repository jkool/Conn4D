package lagrange.utils;

import com.vividsolutions.jts.geom.Coordinate;

public class PrjTransform_None implements PrjTransform{
	public double[] project(double[] coords){return coords;}

	public double[] project(double x, double y){return new double[]{x,y};}

	public Coordinate project(Coordinate c){return c;}
	
	public Coordinate[] project(Coordinate[] ca){return ca;}
	
	public double[] inverse(double[] coords){return coords;}

	public double[] inverse(double x, double y){return new double[]{x,y};}

	public Coordinate inverse(Coordinate c){return c;}
	
	public Coordinate[] inverse(Coordinate[] ca){return ca;}
}
