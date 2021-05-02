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

import java.awt.image.BufferedImage;
import java.io.File;

public class Model {

	
	File imageFile;
	BufferedImage image;
	BitmapLayers layers;
	int milX, milY;
	private File saveFile;
	
	public Model( )
	{
		this.milX = this.milY = 10;

	}
	
	
	public void setMils(int milX, int milY)
	{
		this.milX = milX;
		this.milY = milY;
	}
	
	public int getMilX( ) { return this.milX; }
	
	public int getMilY( ) { return this.milY; }
	
	
	public void setImageFile(File f)
	{
		this.imageFile = f;
	}
	
	public File getImageFile( )
	{
		return this.imageFile;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image, BitmapLayers layers) {
		this.image = image;
		this.layers = layers;
	}


	public BitmapLayers getLayers() {
		// TODO Auto-generated method stub
		return layers;
	}


	public void setGerberFile(File f) {
		this.saveFile = f;	
	}
	
	public File getGerberFile( ) {
		return this.saveFile;
	}

	
}
