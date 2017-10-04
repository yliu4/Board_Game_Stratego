/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.gamma;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import strategy.common.StrategyException;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.AbstractStrategyGameController;

/**
 * The GammastrategyGameController implements the game core for the Gamma
 * Strategy version.
 * @author Yuchen Liu (yliu4) Xiaosong Wen (xwen2)
 * @version Sep 21, 2013
 */

public class GammaStrategyGameController extends AbstractStrategyGameController
										
{

	/**
	 * Constructor for GammaStrategyGameController.
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	 * @throws StrategyException 
	 */
	public GammaStrategyGameController (
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException
	{
		super(redConfiguration, blueConfiguration);
		numofflag = 1;
		
	}
	public Collection<PieceLocationDescriptor> Initializerecord(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
	{
		final Collection<PieceLocationDescriptor> record 
			= new  ArrayList <PieceLocationDescriptor> ();
		record.addAll(redConfiguration);
		record.addAll(blueConfiguration);
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(2, 2)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(2, 3)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(3, 2)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(3, 3)));
		return record;
	}
	/**
	 * Method checkrecord.
	 * Check the number of kinds of pieces and the number of each pieces are correct to the rule  
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	
	 * @throws StrategyException */
	public void checkrecord(
			Collection<PieceLocationDescriptor> Configuration)
			throws StrategyException
	{
		int numofflag = 0;
		int numofmar = 0;
		int numofcol = 0;
		int numofcap = 0;
		int numoflie = 0;
		int numofser = 0;
		PieceLocationDescriptor temppiece;
		final Collection<PieceLocationDescriptor> temprecord = 
				new ArrayList<PieceLocationDescriptor>();
		temprecord.addAll(Configuration);
		final Iterator<PieceLocationDescriptor> It = temprecord.iterator();
		while (It.hasNext())
		{
			temppiece = It.next();
			switch (temppiece.getPiece().getType()){
				case FLAG:
					numofflag++;
					break;
				case MARSHAL:
					numofmar++;
					break;
				case COLONEL:
					numofcol++;
					break;
				case CAPTAIN:
					numofcap++;
					break;
				case LIEUTENANT:
					numoflie++;
					break;
				case SERGEANT:
					numofser++;
					break;
				default: 
					throw new StrategyException("use Piece not in version");
			}
			checkPieceAt(temppiece.getLocation(), temprecord);
		}
		if (numofflag != 1 || numofmar != 1 || numofcol != 2 || numofcap != 2
				|| numoflie != 3 || numofser != 3)
		{
			throw new StrategyException(
					"the number of piecetype is not correct.");
		}
	}
	

	
	public  Map<PieceType,Integer> InitializeRank()
		{
			final Hashtable<PieceType, Integer> Rank = new Hashtable<PieceType, Integer>();
			Rank.put(PieceType.MARSHAL, 12);
			Rank.put(PieceType.COLONEL, 10);
			Rank.put(PieceType.CAPTAIN, 8);
			Rank.put(PieceType.LIEUTENANT, 7);
			Rank.put(PieceType.SERGEANT, 6);
			return Rank;
		}
	public void checkMoveDestination(Location from, Location to)
			throws StrategyException
	{
		if ((to.getCoordinate(Coordinate.X_COORDINATE)) > 5
				|| (to.getCoordinate(Coordinate.X_COORDINATE)) < 0
				|| (to.getCoordinate(Coordinate.Y_COORDINATE)) > 5
				|| (to.getCoordinate(Coordinate.Y_COORDINATE)) < 0)
		{
			gameOver = true;
			throw new StrategyException(
					"Invalid coordinate, expected other destination.");
		}

		if (from.distanceTo(to) != 1)
		{
			gameOver = true;
			throw new StrategyException("Only allow to move one square.");
		}
		if (getPieceAt(to) != null
				&& getPieceAt(to).getOwner() == getPieceAt(from).getOwner())
		{
			gameOver = true;
			throw new StrategyException(
					"Invalid move, cannot move your piece onto your piece.");
		}
	}
	
	protected void reportToObserver(PieceType piece, Location from, Location to,
			MoveResult moveresult, StrategyException anException) throws StrategyException
	{
		if (anException != null){
			throw anException;
		}
	};
	
	
	}
