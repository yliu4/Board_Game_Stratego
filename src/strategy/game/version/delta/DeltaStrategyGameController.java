/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.delta;

import java.util.ArrayList;
import java.util.Collection;
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
 * The DeltastrategyGameController implements the game core for the Delta
 * Strategy version.
 * 
 * @author Xiaosong Wen (xwen2) Yuchen Liu (yliu4)
 * @version Oct, 08, 2013
 */
public class DeltaStrategyGameController extends AbstractStrategyGameController 
{

	/**
	 * Constructor for GammaStrategyGameController.
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	 * @throws StrategyException 
	 */
	public DeltaStrategyGameController(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
			throws StrategyException
	{
		super(redConfiguration, blueConfiguration);
		numofflag = 1;
		
	}
	
	public void checkrecord(
			Collection<PieceLocationDescriptor> Configuration)
			throws StrategyException
	{
		int numofflag = 0;
		int numofbomb = 0;
		int numofgen = 0;
		int numofmarshal = 0;
		int numofmajor = 0;
		int numofmin = 0;
		int numofsco = 0;
		int numofspy = 0;
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
				case BOMB:
					numofbomb++;
					break;
				case GENERAL:
					numofgen++;
					break;
				case MAJOR:
					numofmajor++;
					break;
				case MINER:
					numofmin++;
					break;
				case MARSHAL:
					numofmarshal++;
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
				case SCOUT:
					numofsco++;
					break;
				case SPY:
					numofspy++;
					break;
				default: 
					throw new StrategyException("use Piece not in version");
			}
			checkPieceAt(temppiece.getLocation(), temprecord);
		}
		if (numofflag != 1 || numofmarshal != 1 || numofcol != 2 || numofcap != 4
				|| numoflie != 4 || numofser != 4 || numofbomb != 6 || numofgen != 1
				|| numofmajor != 3 || numofmin != 5 || numofsco != 8 || numofspy != 1)
		{
			throw new StrategyException(
					"the number of piecetype is not correct.");
		}
	}
	
	public  Map<PieceType,Integer> InitializeRank()
	{
		final oldversion oldInitializeRank = new oldversion();
		final deltaDecorator deltaInitializeRank = new deltaDecorator(oldInitializeRank);
		return deltaInitializeRank.InitializeRank();
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
				PieceType.CHOKE_POINT, null), new Location2D(2, 4)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(2, 5)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(3, 4)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(3, 5)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(6, 4)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(6, 5)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(7, 4)));
		record.add(new PieceLocationDescriptor(new Piece(
				PieceType.CHOKE_POINT, null), new Location2D(7, 5)));
		return record;
	}
	
	public void checkMoveDestination(Location from, Location to)
			throws StrategyException
	{
		if ((to.getCoordinate(Coordinate.X_COORDINATE)) > 9
				|| (to.getCoordinate(Coordinate.X_COORDINATE)) < 0
				|| (to.getCoordinate(Coordinate.Y_COORDINATE)) > 9
				|| (to.getCoordinate(Coordinate.Y_COORDINATE)) < 0)
		{
			gameOver = true;
			throw new StrategyException(
					"Invalid coordinate, expected other destination.");
		}

		if ((!isSourceScout(getPieceAt(from).getType())) && from.distanceTo(to) != 1)
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
	
	private static boolean isSourceScout(PieceType piece){
		return piece == PieceType.SCOUT; 
	}
		
	protected void reportToObserver(PieceType piece, Location from, Location to,
			MoveResult moveresult, StrategyException anException) throws StrategyException
	{
		if (anException != null){
			throw anException;
		}
	};
}
