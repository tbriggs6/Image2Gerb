/*
 	This file is part of Image2Gerb.

    Image2Gerb is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Image2Gerb is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Image2Gerb.  If not, see <http://www.gnu.org/licenses/>.
 */
package img;

import java.io.PrintStream;

public class GerberWriter {

	
	Model model;
	
	public GerberWriter(Model model)
	{
		this.model = model;
	}
	
	
	public void writeGerber(PrintStream out)
	{
		out.format("%%FSLAX23Y23*%%\n"); 	// format absolute,X in 2.5 and Y in 2.5 format
		out.format("%%MOIN*%%\n"); 		// format in inches
		out.format("%%OFA0B0*%%\n");
		out.format("%%SFA1.0B1.0*%%\n");
		out.format("%%ADD10R,%3.3fX%3.3f*%%\n", model.getMilX() / 1000.0, model.getMilY() / 1000.0);	// format apperature D10
		
		for (int i = 0; i < model.getLayers().getNumLayers(); i++)
			writeLayer(out, i);
	}
	
	
	protected static String getCoord(int mils)
	{
		if (mils == 0) return "0";
		if (mils < 10)
			return "000" + mils;
		if (mils < 100)
			return "00" + mils;
		if (mils < 1000)
			return "0" + mils;
		return "" + mils;
		
	}
	
	
	private String getCoordX(int value) 
	{
		return getCoord(value * model.getMilX());
	}
	
	private String getCoordY(int value)
	{
		return getCoord(value * model.getMilY());
	}
	
	public void writeLayer(PrintStream out, int n)
	{
		out.format("%%LNLAYER%d*%%\n", n);
		
		out.format("G54D10*\n");
		
		BitmapLayers layers = model.getLayers();
		
		for (int y = layers.height-1; y >= 0; y--) {
			int x = 0;

			while (x < layers.width)
			{
				int start, end;
				
				// process segment
				while ((x < layers.width) &&  !layers.get(x,y,n)) x++;
				if (x == layers.width) break;
				start = x;
				
				while ((x < layers.width) && layers.get(x,y,n)) x++;
				end = x;
				
				out.format("X%sY%sD02*\n", getCoordX(start),getCoordY(layers.height-y));
				out.format("X%sY%sD01*\n", getCoordX(end),getCoordY(layers.height-y));
			}
			
			
			
		}
	
		out.format("M02*\n");
	
	}
	
}
