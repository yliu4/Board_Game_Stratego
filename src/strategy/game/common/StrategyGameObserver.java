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

import java.util.Collection;

import strategy.common.StrategyException;

/**
 * @author Yuchen Liu (yliu4) Xiaosong Wen (xwen2)
 * @version Oct, 16, 2013
 */
public interface StrategyGameObserver
{
	
	/**
	 * Called at the beginning of the game with the initial configurations.
	 * @param redConfiguration
	 * @param blueConfiguration
	 */
	void gameStart(
	Collection<PieceLocationDescriptor> redConfiguration,
	Collection<PieceLocationDescriptor> blueConfiguration);

	/**
	 * Called whenever a move is made by the game controller. 
	 * If the controller caught an exception, it returns null for the result, 
	 * but the exception in the fault; otherwise, fault is null.
	 * @param piece
	 * @param from
	 * @param to
	 * @param result
	 * @param fault
	 * @throws StrategyException 
	 */
	void moveHappened(PieceType piece, Location from, Location to,
			MoveResult result, StrategyException fault) throws StrategyException;
}
