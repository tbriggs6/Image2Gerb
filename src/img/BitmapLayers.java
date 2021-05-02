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

public class BitmapLayers {
	
	int numLayers;
	int width, height;
	boolean data[][][];
	
	public BitmapLayers(int numLayers, int width, int height )
	{
		this.numLayers = numLayers;
		this.width = width;
		this.height = height;
		this.data = new boolean[numLayers][width][height];
	}

	public void set(int x, int y, int l, boolean i) {
		data[l][x][y] = i;
	}
	
	public boolean get(int x, int y, int l) { return data[l][x][y]; }

	public int getNumLayers() {
		return numLayers;
	} 
}
