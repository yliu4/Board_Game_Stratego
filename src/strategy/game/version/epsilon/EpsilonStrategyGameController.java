/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.epsilon;

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
import strategy.game.common.StrategyGameObservable;
import strategy.game.common.StrategyGameObserver;
import strategy.game.version.AbstractStrategyGameController;

/**
 * @author Yuchen Liu (yliu4) Xiaosong Wen (xwen2)
 * @version Oct, 16, 2013
 */
public class EpsilonStrategyGameController extends
		AbstractStrategyGameController implements StrategyGameObservable
{
	Collection<StrategyGameObserver> observers = new ArrayList<StrategyGameObserver>();

	/**
	 * Constructor for
	 * EpsilonStrategyGameController.
	 * 
	 * @param redConfiguration
	 *            Collection<
	 *            PieceLocationDescriptor>
	 * @param blueConfiguration
	 *            Collection<
	 *            PieceLocationDescriptor>
	 * @param observers
	 *            Collection<StrategyGameObserver>
	 * @throws StrategyException
	 */
	public EpsilonStrategyGameController(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration,
			Collection<StrategyGameObserver> observers)
			throws StrategyException
	{
		super(redConfiguration, blueConfiguration);
		numofflag = 2;
		final Iterator<StrategyGameObserver> It = observers.iterator();
		StrategyGameObserver tempobserver;
		while (It.hasNext())
		{
			tempobserver = It.next();
			register(tempobserver);
			tempobserver.gameStart(redConfiguration, blueConfiguration);
		}

	}

	public void checkrecord(Collection<PieceLocationDescriptor> Configuration)
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
		int numoffirstlie = 0;
		int numoflie = 0;
		int numofser = 0;
		boolean canflagnumacceptable = false;
		boolean canbombnumacceptable = false;
		boolean cangennumacceptable = false;
		boolean canmarshalnumacceptable = false;
		boolean canmajornumacceptable = false;
		boolean canminnumacceptable = false;
		boolean cansconumacceptable = false;
		boolean canspynumacceptable = false;
		boolean cancolnumacceptable = false;
		boolean cancapnumacceptable = false;
		boolean canfirstlienumacceptable = false;
		boolean canlienumacceptable = false;
		boolean cansernumacceptable = false;
		PieceLocationDescriptor temppiece;
		final Collection<PieceLocationDescriptor> temprecord 
						= new ArrayList<PieceLocationDescriptor>();
		temprecord.addAll(Configuration);
		final Iterator<PieceLocationDescriptor> It = temprecord.iterator();
		while (It.hasNext())
		{
			temppiece = It.next();
			switch (temppiece.getPiece().getType())
			{
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
			case FIRST_LIEUTENANT:
				numoffirstlie++;
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
		if (numofflag == 2)
		{
			canflagnumacceptable = true;
		}
		if (numofmarshal == 1 || numofmarshal == 0)
		{
			canmarshalnumacceptable = true;
		}
		if (numofcol == 2 || numofcol == 1)
		{
			cancolnumacceptable = true;
		}
		if (numofcap == 4 || numofcap == 3)
		{
			cancapnumacceptable = true;
		}
		if (numoflie == 2 || numoflie == 1)
		{
			canlienumacceptable = true;
		}
		if (numoffirstlie == 2 || numoffirstlie == 1)
		{
			canfirstlienumacceptable = true;
		}
		if (numofser == 4 || numofser == 3)
		{
			cansernumacceptable = true;
		}
		if (numofbomb == 6 || numofbomb == 5)
		{
			canbombnumacceptable = true;
		}
		if (numofgen == 1 || numofgen == 0)
		{
			cangennumacceptable = true;
		}
		if (numofmajor == 3 || numofmajor == 2)
		{
			canmajornumacceptable = true;
		}
		if (numofmin == 5 || numofmin == 4)
		{
			canminnumacceptable = true;
		}
		if (numofsco == 8 || numofsco == 7)
		{
			cansconumacceptable = true;
		}
		if (numofspy == 1 || numofspy == 0)
		{
			canspynumacceptable = true;
		}
		if (!(canbombnumacceptable && cancapnumacceptable
				&& cancolnumacceptable && canfirstlienumacceptable
				&& canflagnumacceptable && cangennumacceptable
				&& canlienumacceptable && canmajornumacceptable
				&& canmarshalnumacceptable && canminnumacceptable
				&& cansconumacceptable && cansernumacceptable && canspynumacceptable))
		{
			throw new StrategyException(
					"the number of piecetype is not correct.");
		}
	}

	public Map<PieceType, Integer> InitializeRank()
	{
		final Hashtable<PieceType, Integer> Rank = new Hashtable<PieceType, Integer>();
		Rank.put(PieceType.MARSHAL, 12);
		Rank.put(PieceType.GENERAL, 11);
		Rank.put(PieceType.COLONEL, 10);
		Rank.put(PieceType.MAJOR, 9);
		Rank.put(PieceType.CAPTAIN, 8);
		Rank.put(PieceType.LIEUTENANT, 7);
		Rank.put(PieceType.FIRST_LIEUTENANT, 7);
		Rank.put(PieceType.SERGEANT, 6);
		Rank.put(PieceType.MINER, 5);
		Rank.put(PieceType.SCOUT, 4);
		Rank.put(PieceType.SPY, 3);

		return Rank;
	}

	public Collection<PieceLocationDescriptor> Initializerecord(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
	{
		final Collection<PieceLocationDescriptor> record = new ArrayList<PieceLocationDescriptor>();
		record.addAll(redConfiguration);
		record.addAll(blueConfiguration);
		record.add(new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT,
				null), new Location2D(2, 4)));
		record.add(new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT,
				null), new Location2D(2, 5)));
		record.add(new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT,
				null), new Location2D(3, 4)));
		record.add(new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT,
				null), new Location2D(3, 5)));
		record.add(new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT,
				null), new Location2D(6, 4)));
		record.add(new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT,
				null), new Location2D(6, 5)));
		record.add(new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT,
				null), new Location2D(7, 4)));
		record.add(new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT,
				null), new Location2D(7, 5)));
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

		if ((!isSourceScout(getPieceAt(from).getType()))
				&& (getPieceAt(from).getType() != PieceType.FIRST_LIEUTENANT)
				&& from.distanceTo(to) != 1)
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

	private static boolean isSourceScout(PieceType piece)
	{
		return piece == PieceType.SCOUT;
	}

	@Override
	public void register(StrategyGameObserver observer)
	{
		observers.add(observer);

	}

	@Override
	public void unregister(StrategyGameObserver observer)
	{
		observers.remove(observer);
	}
	
	public void reportToObserver(PieceType piece, Location from, Location to,
			MoveResult moveresult, StrategyException anException) throws StrategyException
	{
		final Iterator<StrategyGameObserver> It = observers.iterator();
		StrategyGameObserver tempobserver;
		while (It.hasNext())
		{
			tempobserver = It.next();
			tempobserver.moveHappened(piece, from, to, moveresult, anException);
		}
	}
}
