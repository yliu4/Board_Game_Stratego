/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.testutil;

import static strategy.common.PlayerColor.*;
import static strategy.game.common.PieceType.*;
import java.util.*;
import strategy.common.PlayerColor;
import strategy.game.common.*;

/**
 * A single place to get configurations for the various tests.
 * 
 * @author gpollice
 * @version Sep 21, 2013
 */
public class TestConfigurationFactory
{
	private static TestConfigurationFactory instance = new TestConfigurationFactory();
	
	private ArrayList<PieceLocationDescriptor> currentConfiguration = null;
	
	private TestConfigurationFactory()
	{
		// Intentionally empty
	}
	
	public ArrayList<PieceLocationDescriptor> getRedBetaConfiguration()
	{
		currentConfiguration = new ArrayList<PieceLocationDescriptor>();
		addToConfiguration(FLAG, RED, 0, 1);
		addToConfiguration(MARSHAL, RED, 0, 0);
		addToConfiguration(COLONEL, RED, 1, 0);
		addToConfiguration(COLONEL, RED, 2, 0);
		addToConfiguration(CAPTAIN, RED, 3, 0);
		addToConfiguration(CAPTAIN, RED, 4, 0);
		addToConfiguration(LIEUTENANT, RED, 5, 0);
		addToConfiguration(LIEUTENANT, RED, 1, 1);
		addToConfiguration(LIEUTENANT, RED, 2, 1);
		addToConfiguration(SERGEANT, RED, 3, 1);
		addToConfiguration(SERGEANT, RED, 4, 1);
		addToConfiguration(SERGEANT, RED, 5, 1);
		return currentConfiguration;
	}
	
	public ArrayList<PieceLocationDescriptor> getBlueBetaConfiguration()
	{
		currentConfiguration = new ArrayList<PieceLocationDescriptor>();
		addToConfiguration(FLAG, BLUE, 5, 4);
		addToConfiguration(MARSHAL, BLUE, 0, 5);
		addToConfiguration(COLONEL, BLUE, 1, 5);
		addToConfiguration(COLONEL, BLUE, 2, 5);
		addToConfiguration(CAPTAIN, BLUE, 3, 5);
		addToConfiguration(CAPTAIN, BLUE, 4, 5);
		addToConfiguration(LIEUTENANT, BLUE, 5, 5);
		addToConfiguration(LIEUTENANT, BLUE, 0, 4);
		addToConfiguration(LIEUTENANT, BLUE, 1, 4);
		addToConfiguration(SERGEANT, BLUE, 2, 4);
		addToConfiguration(SERGEANT, BLUE, 3, 4);
		addToConfiguration(SERGEANT, BLUE, 4, 4);
		return currentConfiguration;
	}
	
	public ArrayList<PieceLocationDescriptor> getRedGammaConfiguration()
	{
		return getRedBetaConfiguration();
	}
	
	public ArrayList<PieceLocationDescriptor> getBlueGammaConfiguration()
	{
		return getBlueBetaConfiguration();
	}
	
	public ArrayList<PieceLocationDescriptor> getRedDeltaConfiguration()
	{
		currentConfiguration = new ArrayList<PieceLocationDescriptor>();
		addToConfiguration(FLAG, RED, 0, 3);
		addToConfiguration(MARSHAL, RED, 1, 3);
		addToConfiguration(GENERAL, RED, 2, 3);
		addToConfiguration(COLONEL, RED, 3, 3);
		addToConfiguration(MAJOR, RED, 4, 3);
		addToConfiguration(CAPTAIN, RED, 5, 3); 
		addToConfiguration(LIEUTENANT, RED, 6, 3);
		addToConfiguration(SERGEANT, RED, 7, 3);
		addToConfiguration(MINER, RED, 8, 3);
		addToConfiguration(SCOUT, RED, 9, 3);
		addToConfiguration(SPY, RED, 9, 2);
		addToConfiguration(BOMB, RED, 8, 2);
		
		addToConfiguration(COLONEL, RED, 0, 0);
		addToConfiguration(MAJOR, RED, 1, 0);
		addToConfiguration(MAJOR, RED, 2, 0);
		addToConfiguration(CAPTAIN, RED, 3, 0);
		addToConfiguration(CAPTAIN, RED, 4, 0);
		addToConfiguration(CAPTAIN, RED, 5, 0);
		addToConfiguration(LIEUTENANT, RED, 6, 0);
		addToConfiguration(LIEUTENANT, RED, 7, 0);
		addToConfiguration(LIEUTENANT, RED, 8, 0);
		addToConfiguration(SERGEANT, RED, 9, 0);
		addToConfiguration(SERGEANT, RED, 0, 1);
		addToConfiguration(SERGEANT, RED, 1, 1);
		addToConfiguration(MINER, RED, 2, 1);
		addToConfiguration(MINER, RED, 3, 1);
		addToConfiguration(MINER, RED, 4, 1);
		addToConfiguration(MINER, RED, 5, 1);
		addToConfiguration(SCOUT, RED, 6, 1);
		addToConfiguration(SCOUT, RED, 7, 1);
		addToConfiguration(SCOUT, RED, 8, 1);
		addToConfiguration(SCOUT, RED, 9, 1);
		addToConfiguration(SCOUT, RED, 0, 2);
		addToConfiguration(SCOUT, RED, 1, 2);
		addToConfiguration(SCOUT, RED, 2, 2);
		addToConfiguration(BOMB, RED, 3, 2);
		addToConfiguration(BOMB, RED, 4, 2);
		addToConfiguration(BOMB, RED, 5, 2);
		addToConfiguration(BOMB, RED, 6, 2);
		addToConfiguration(BOMB, RED, 7, 2);
		return currentConfiguration;
	}
	public ArrayList<PieceLocationDescriptor> getBlueDeltaConfiguration()
	{
		currentConfiguration = new ArrayList<PieceLocationDescriptor>();
		addToConfiguration(FLAG, BLUE, 0, 6);
		addToConfiguration(MARSHAL, BLUE, 1, 6);
		addToConfiguration(GENERAL, BLUE, 2, 6);
		addToConfiguration(COLONEL, BLUE, 3, 6);
		addToConfiguration(MAJOR, BLUE, 4, 6);
		addToConfiguration(CAPTAIN, BLUE, 5, 6); 
		addToConfiguration(LIEUTENANT, BLUE, 6, 6);
		addToConfiguration(SERGEANT, BLUE, 7, 6);
		addToConfiguration(MINER, BLUE, 8, 6);
		addToConfiguration(SCOUT, BLUE, 9, 6);
		addToConfiguration(SPY, BLUE, 9, 7);
		addToConfiguration(BOMB, BLUE, 8, 7);
		
		addToConfiguration(COLONEL, BLUE, 0, 9);
		addToConfiguration(MAJOR, BLUE, 1, 9);
		addToConfiguration(MAJOR, BLUE, 2, 9);
		addToConfiguration(CAPTAIN, BLUE, 3, 9);
		addToConfiguration(CAPTAIN, BLUE, 4, 9);
		addToConfiguration(CAPTAIN, BLUE, 5, 9);
		addToConfiguration(LIEUTENANT, BLUE, 6, 9);
		addToConfiguration(LIEUTENANT, BLUE, 7, 9);
		addToConfiguration(LIEUTENANT, BLUE, 8, 9);
		addToConfiguration(SERGEANT, BLUE, 9, 9);
		addToConfiguration(SERGEANT, BLUE, 0, 8);
		addToConfiguration(SERGEANT, BLUE, 1, 8);
		addToConfiguration(MINER, BLUE, 2, 8);
		addToConfiguration(MINER, BLUE, 3, 8);
		addToConfiguration(MINER, BLUE, 4, 8);
		addToConfiguration(MINER, BLUE, 5, 8);
		addToConfiguration(SCOUT, BLUE, 6, 8);
		addToConfiguration(SCOUT, BLUE, 7, 8);
		addToConfiguration(SCOUT, BLUE, 8, 8);
		addToConfiguration(SCOUT, BLUE, 9, 8);
		addToConfiguration(SCOUT, BLUE, 0, 7);
		addToConfiguration(SCOUT, BLUE, 1, 7);
		addToConfiguration(SCOUT, BLUE, 2, 7);
		addToConfiguration(BOMB, BLUE, 3, 7);
		addToConfiguration(BOMB, BLUE, 4, 7);
		addToConfiguration(BOMB, BLUE, 5, 7);
		addToConfiguration(BOMB, BLUE, 6, 7);
		addToConfiguration(BOMB, BLUE, 7, 7);
		return currentConfiguration;
	}
	public ArrayList<PieceLocationDescriptor> getRedEpsilonConfiguration()
	{
		currentConfiguration = new ArrayList<PieceLocationDescriptor>();
		addToConfiguration(FLAG, RED, 0, 3);
		addToConfiguration(MARSHAL, RED, 1, 3);
		addToConfiguration(GENERAL, RED, 2, 3);
		addToConfiguration(COLONEL, RED, 3, 3);
		addToConfiguration(MAJOR, RED, 4, 3);
		addToConfiguration(CAPTAIN, RED, 5, 3); 
		addToConfiguration(FIRST_LIEUTENANT, RED, 6, 3);
		addToConfiguration(SERGEANT, RED, 7, 3);
		addToConfiguration(MINER, RED, 8, 3);
		addToConfiguration(FLAG, RED, 9, 3);
		addToConfiguration(SPY, RED, 9, 2);
		addToConfiguration(BOMB, RED, 8, 2);
		
		addToConfiguration(COLONEL, RED, 0, 0);
		addToConfiguration(MAJOR, RED, 1, 0);
		addToConfiguration(MAJOR, RED, 2, 0);
		addToConfiguration(CAPTAIN, RED, 3, 0);
		addToConfiguration(CAPTAIN, RED, 4, 0);
		addToConfiguration(CAPTAIN, RED, 5, 0);
		addToConfiguration(FIRST_LIEUTENANT, RED, 6, 0);
		addToConfiguration(LIEUTENANT, RED, 7, 0);
		addToConfiguration(LIEUTENANT, RED, 8, 0);
		addToConfiguration(SERGEANT, RED, 9, 0);
		addToConfiguration(SERGEANT, RED, 0, 1);
		addToConfiguration(SERGEANT, RED, 1, 1);
		addToConfiguration(MINER, RED, 2, 1);
		addToConfiguration(MINER, RED, 3, 1);
		addToConfiguration(MINER, RED, 4, 1);
		addToConfiguration(MINER, RED, 5, 1);
		addToConfiguration(SCOUT, RED, 6, 1);
		addToConfiguration(SCOUT, RED, 7, 1);
		addToConfiguration(SCOUT, RED, 8, 1);
		addToConfiguration(SCOUT, RED, 9, 1);
		addToConfiguration(SCOUT, RED, 0, 2);
		addToConfiguration(SCOUT, RED, 1, 2);
		addToConfiguration(SCOUT, RED, 2, 2);
		addToConfiguration(BOMB, RED, 3, 2);
		addToConfiguration(BOMB, RED, 4, 2);
		addToConfiguration(BOMB, RED, 5, 2);
		addToConfiguration(BOMB, RED, 6, 2);
		addToConfiguration(BOMB, RED, 7, 2);
		return currentConfiguration;
	}
	public ArrayList<PieceLocationDescriptor> getBlueEpsilonConfiguration()
	{
		currentConfiguration = new ArrayList<PieceLocationDescriptor>();
		addToConfiguration(FLAG, BLUE, 0, 6);
		addToConfiguration(MARSHAL, BLUE, 1, 6);
		addToConfiguration(GENERAL, BLUE, 2, 6);
		addToConfiguration(COLONEL, BLUE, 3, 6);
		addToConfiguration(MAJOR, BLUE, 4, 6);
		addToConfiguration(CAPTAIN, BLUE, 5, 6); 
		addToConfiguration(FIRST_LIEUTENANT, BLUE, 6, 6);
		addToConfiguration(SERGEANT, BLUE, 7, 6);
		addToConfiguration(MINER, BLUE, 8, 6);
		addToConfiguration(FLAG, BLUE, 9, 6);
		addToConfiguration(SPY, BLUE, 9, 7);
		addToConfiguration(BOMB, BLUE, 8, 7);
		
		addToConfiguration(COLONEL, BLUE, 0, 9);
		addToConfiguration(MAJOR, BLUE, 1, 9);
		addToConfiguration(MAJOR, BLUE, 2, 9);
		addToConfiguration(CAPTAIN, BLUE, 3, 9);
		addToConfiguration(CAPTAIN, BLUE, 4, 9);
		addToConfiguration(CAPTAIN, BLUE, 5, 9);
		addToConfiguration(FIRST_LIEUTENANT, BLUE, 6, 9);
		addToConfiguration(LIEUTENANT, BLUE, 7, 9);
		addToConfiguration(LIEUTENANT, BLUE, 8, 9);
		addToConfiguration(SERGEANT, BLUE, 9, 9);
		addToConfiguration(SERGEANT, BLUE, 0, 8);
		addToConfiguration(SERGEANT, BLUE, 1, 8);
		addToConfiguration(MINER, BLUE, 2, 8);
		addToConfiguration(MINER, BLUE, 3, 8);
		addToConfiguration(MINER, BLUE, 4, 8);
		addToConfiguration(MINER, BLUE, 5, 8);
		addToConfiguration(SCOUT, BLUE, 6, 8);
		addToConfiguration(SCOUT, BLUE, 7, 8);
		addToConfiguration(SCOUT, BLUE, 8, 8);
		addToConfiguration(SCOUT, BLUE, 9, 8);
		addToConfiguration(SCOUT, BLUE, 0, 7);
		addToConfiguration(SCOUT, BLUE, 1, 7);
		addToConfiguration(SCOUT, BLUE, 2, 7);
		addToConfiguration(BOMB, BLUE, 3, 7);
		addToConfiguration(BOMB, BLUE, 4, 7);
		addToConfiguration(BOMB, BLUE, 5, 7);
		addToConfiguration(BOMB, BLUE, 6, 7);
		addToConfiguration(BOMB, BLUE, 7, 7);
		return currentConfiguration;
	}
	/**
	 * @return the instance
	 */
	public static TestConfigurationFactory getInstance()
	{
		return instance;
	}
	
	// Helper methods
	private void addToConfiguration(PieceType type, PlayerColor color, int x, int y)
	{
		final PieceLocationDescriptor confItem = new PieceLocationDescriptor(
				new Piece(type, color), new Location2D(x, y));
		if (color == PlayerColor.BLUE) {
			currentConfiguration.add(confItem);
		} else {
			currentConfiguration.add(confItem);
		}
	}
}
