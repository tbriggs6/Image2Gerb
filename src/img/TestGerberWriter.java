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

import static org.junit.Assert.*;

import org.junit.Test;

public class TestGerberWriter {

	@Test
	public void testGetCoord1() {
		
		// format is 2.5 (5 zeros, always omit leading zeros)
		// 1mil in inches = 0.001 = 0001
		// 10 mil = 0.01 = 0010
		// 100 mil = 0.1 = 0100
		// 1000 mil = 1.0 = 1000
		// 1234 mil = 1.234 = 1234
		// 10000 mil = 10.000 = 10000
		assertEquals("0001", GerberWriter.getCoord(1));
		assertEquals("0010", GerberWriter.getCoord(10));
		assertEquals("0100", GerberWriter.getCoord(100));
		assertEquals("1000", GerberWriter.getCoord(1000));
		assertEquals("1234", GerberWriter.getCoord(1234));
		assertEquals("10000", GerberWriter.getCoord(10000));
		
	}

}
