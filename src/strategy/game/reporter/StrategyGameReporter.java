/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.reporter;

import java.util.Collection;

import strategy.common.StrategyException;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;

/**
 * @author Yuchen Liu (yliu4) Xiaosong Wen (xwen2)
 * @version Oct, 16, 2013
 */
public class StrategyGameReporter implements StrategyGameObserver
{

	@Override
	public void gameStart(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
	{
		System.out.println("The game has been successfully started!");
		
	}
	
	@Override
	public void moveHappened(PieceType piece, Location from, Location to,
			MoveResult result, StrategyException fault) throws StrategyException
	{
		if (fault == null){
			if(piece == null && from == null && to == null){
				System.out.println("Player surrender" + result);
			}
			else{
			System.out.println("Move " + piece.toString() +
								" from position " + from.toString() + 
								" to position " + to.toString() + " " + result);
			
			}
		}
		else {
			System.out.println("Failed to move " + piece.toString() + 
								" from position: " + from.toString() + 
								" to position " + to.toString());
			throw fault;
		}
	}
}