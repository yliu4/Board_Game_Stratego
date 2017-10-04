/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.common;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test cases for Location2D
 * @author gpollice
 * @version Sep 12, 2013
 */
public class Location2DTest
{
	@Test
	public void adjacentLocationsHaveDistance1()
	{
		final Location loc1 = new Location2D(3, 3);
		final Location loc2 = new Location2D(3, 4);
		assertEquals(1, loc1.distanceTo(loc2));
		assertEquals(1, loc2.distanceTo(loc1));
	}
}
