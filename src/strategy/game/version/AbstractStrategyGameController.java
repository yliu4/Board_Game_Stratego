/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceStep;
import strategy.game.common.PieceType;
import strategy.game.common.StrikeResult;

/**
 * The AbstractStrategyGameController implements
 * the game core for the Gamma Strategy version.
 * 
 * @author Yuchen Liu (yliu4) Xiaosong Wen (xwen2)
 * @version Sep 21, 2013
 */
public abstract class AbstractStrategyGameController implements
		StrategyGameController
{

	protected boolean gameStarted;
	protected boolean gameOver;
	private int NumOfTimeCallStartGame;
	protected Collection<PieceLocationDescriptor> record;
	private final Collection<PieceStep> steps;
	protected int NumOfMove;
	private Map<PieceType, Integer> Rank = new Hashtable<PieceType, Integer>();
	protected PlayerColor LastPlayerColor;
	protected int numofflag;
	private int flagcaptured;

	/**
	 * Constructor for
	 * GammaStrategyGameController.
	 * 
	 * @param redConfiguration
	 *            Collection<
	 *            PieceLocationDescriptor>
	 * @param blueConfiguration
	 *            Collection<
	 *            PieceLocationDescriptor>
	 * 
	 * 
	
	 * @throws StrategyException */
	protected AbstractStrategyGameController(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
			throws StrategyException
	{
		gameStarted = false;
		gameOver = false;
		checkrecord(redConfiguration);
		checkrecord(blueConfiguration);
		record = Initializerecord(redConfiguration, blueConfiguration);
		Rank = InitializeRank();
		steps = new ArrayList<PieceStep>();
		LastPlayerColor = PlayerColor.BLUE;
		new PieceLocationDescriptor(new Piece(null, PlayerColor.BLUE), null);
		NumOfMove = 0;
		NumOfTimeCallStartGame = 0;
	}

	// Helpers for the constructor of the
	// GameController
	/**
	 * Method checkrecord.
	 * 
	 * @param Configuration
	 *            Collection<
	 *            PieceLocationDescriptor>
	
	 * @throws StrategyException */
	public abstract void checkrecord(
			Collection<PieceLocationDescriptor> Configuration)
			throws StrategyException;

	/**
	 * Method Initializerecord.
	 * 
	 * @param redConfiguration
	 *            Collection<
	 *            PieceLocationDescriptor>
	 * @param blueConfiguration
	 *            Collection<
	 *            PieceLocationDescriptor>
	
	 * @return Collection<PieceLocationDescriptor> */
	public abstract Collection<PieceLocationDescriptor> Initializerecord(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration);

	/**
	 * Method InitializeRank.
	 * 
	 * 
	
	 * @return Map<PieceType,Integer> */
	public abstract Map<PieceType, Integer> InitializeRank();

	public void startGame() throws StrategyException
	{
		NumOfTimeCallStartGame++;
		if (NumOfTimeCallStartGame > 1)
		{
			throw new StrategyException("you can only call startgame once.");
		}
		else
		{
			gameStarted = true;
			gameOver = false;
		}
	}

	
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException
	{
		MoveResult moveresult = null;
		StrategyException anException = null;
		try{
			CheckGameIsOver();
			CheckIsGameStarted();
			if (piece == null && from == null && to == null)
			{
				moveresult = playerwin(null);
			}
			else{
				CheckMoveSource(piece, from);
				if (piece == PieceType.SCOUT && from.distanceTo(to) != 1)
				{
					moveresult = moveScout(piece, from, to);
				}
				else if (piece == PieceType.FIRST_LIEUTENANT
						&& (from.distanceTo(to) > 1))
				{
					moveresult = validateFirLieMove(from, to);
				}
				else{
					checkMoveDestination(from, to);
					checkMoveObeyRepititionRule(NumOfMove, getPieceAt(from), from,
							to);
					checkChokePoint(to);
					if (getPieceAt(to) != null)
					{
						if (getPieceAt(to).getType() == PieceType.FLAG)
						{
							moveresult = MovetogetFlag(piece, from, to);
						}
						else{
							moveresult = dostrike(piece, from, to);
						}
					}
					else{
						moveresult = MoveToAnEmptyPlace(piece, from, to);
					}
				}
			}
		}catch (StrategyException e) {
			anException = e;
		}
		reportToObserver(piece, from, to,  moveresult, anException);
		return moveresult;
	}
	private void CheckIsGameStarted() throws StrategyException  {
		if (!gameStarted)
		{
			throw new StrategyException("You must start the game!");
		}
	}

	private void CheckGameIsOver() throws StrategyException {
		if (gameOver)
		{
			throw new StrategyException(
					"The game is over, you cannot make a move");
		}
	}

	/**
	 * Method reportToObserver.
	 * @param piece PieceType
	 * @param from Location
	 * @param to Location
	 * @param moveresult MoveResult
	 * @param anException StrategyException
	 * @throws StrategyException
	 */
	protected abstract void reportToObserver(PieceType piece, Location from, Location to,
			MoveResult moveresult, StrategyException anException) throws StrategyException;
	private MoveResult moveScout(PieceType piece, Location from, Location to)
			throws StrategyException
	{
		checkMoveDestinationScout(from, to);
		checkMoveObeyRepititionRule(NumOfMove, getPieceAt(from), from, to);
		checkChokePoint(to);
		MoveResult moveresult = null;
		moveresult = MoveToAnEmptyPlace(piece, from, to);

		return moveresult;
	}

	private void checkMoveDestinationScout(Location from, Location to)
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
		if (isNotOpenSquare(from, to))
		{
			throw new StrategyException(
					"Invalid move, scout can not move through obstacle.");
		}
		if (getPieceAt(to) != null)
		{
			gameOver = true;
			throw new StrategyException(
					"Invalid move, SCOUT cannot move and strike in the same turn.");
		}
	}

	/**
	 * Method isNotOpenSquare.
	 * @param from Location
	 * @param to Location
	 * @return boolean
	 */
	protected boolean isNotOpenSquare(Location from, Location to)
	{
		boolean result = false;
		final int fx = from.getCoordinate(Coordinate.X_COORDINATE);
		final int fy = from.getCoordinate(Coordinate.Y_COORDINATE);
		final int tx = to.getCoordinate(Coordinate.X_COORDINATE);
		final int ty = to.getCoordinate(Coordinate.Y_COORDINATE);
		if (fx == tx)
		{
			for (int k = Math.min(fy, ty) + 1; k < Math.max(fy, ty); k++)
			{
				if (getPieceAt(new Location2D(fx, k)) != null)
				{
					result = true;
				}
			}
		}
		if (fy == ty)
		{
			for (int k = Math.min(fx, tx) + 1; k < Math.max(fx, tx); k++)
			{
				if (getPieceAt(new Location2D(k, fy)) != null)
				{
					result = true;
				}
			}
		}
		return result;
	}

	// Helper for the move methods
	/**
	 * Method CheckMoveSource.
	 * 
	 * @param piecetype
	 *            PieceType
	 * @param location
	 *            Location
	 * 
	 * 
	
	 * @throws StrategyException */
	public void CheckMoveSource(PieceType piecetype, Location location)
			throws StrategyException
	{
		if (piecetype == PieceType.CHOKE_POINT)
		{
			gameOver = true;
			throw new StrategyException("you cannot move chokepoint");
		}
		if (piecetype == PieceType.FLAG)
		{
			gameOver = true;
			throw new StrategyException(
					"Invalid piece, expected other type and got " + piecetype);
		}
		if (getPieceAt(location) == null)
		{
			gameOver = true;
			throw new StrategyException("you cannot move from an empty place");
		}
		if (getPieceAt(location).getOwner().equals(LastPlayerColor))
		{
			gameOver = true;
			throw new StrategyException("It is not your turn.");
		}
		if (getPieceAt(location).getType() != piecetype)
		{
			gameOver = true;
			throw new StrategyException(
					"The piece and the location are not match.");
		}
	}

	/**
	 * Method getPieceAt get the Piece At given
	 * Location
	 * 
	 * @param location
	 *            Location
	 */
	public Piece getPieceAt(Location location)
	{
		Piece result = null;
		boolean FindIt = false;
		PieceLocationDescriptor tempRecord;
		final Iterator<PieceLocationDescriptor> It = record.iterator();
		while (It.hasNext() && (!FindIt))
		{
			tempRecord = It.next();
			if (tempRecord.getLocation().equals(location))
			{
				FindIt = true;
				result = tempRecord.getPiece();
			}

		}
		return result;
	}

	/**
	 * Method checkMoveDestination. Check whether
	 * Destination of the move is valid
	 * 
	 * @param from
	 *            Location
	 * @param to
	 *            Location
	 * 
	 * 
	
	 * @throws StrategyException */
	public abstract void checkMoveDestination(Location from, Location to)
			throws StrategyException;

	/**
	 * Method checkMoveObeyRepititionRule
	 * 
	 * @param numberofstep
	 * @param movepiece
	 * @param to
	 *            Location
	 * @param from
	 *            Location
	 * 
	 * 
	
	 * @throws StrategyException */
	protected void checkMoveObeyRepititionRule(int numberofstep,
			Piece movepiece, Location from, Location to)
			throws StrategyException
	{
		if (!canObeyRepititionRule(numberofstep, movepiece, from, to))
		{
			gameOver = true;
			throw new StrategyException("not obey the move repitition rules.");
		}
	}

	private boolean canObeyRepititionRule(int numberofstep, Piece movepiece,
			Location from, Location to)
	{
		boolean canObeyRepititionRule = true;
		final Iterator<PieceStep> It = steps.iterator();
		while (It.hasNext())
		{
			PieceStep tempstep = It.next();
			if (tempstep.getFrom() == from
					&& tempstep.getMovePiece() == movepiece
					&& tempstep.getTo() == to
					&& (numberofstep - tempstep.getNumOfStep()) < 5)
			{
				canObeyRepititionRule = false;
			}
		}
		return canObeyRepititionRule;
	}

	/**
	 * Method checkChokePoint.
	 * 
	 * @param to
	 *            Location
	 * 
	 * 
	
	 * @throws StrategyException */
	protected void checkChokePoint(Location to) throws StrategyException
	{
		if (getPieceAt(to) != null
				&& getPieceAt(to).getType() == PieceType.CHOKE_POINT)
		{

			gameOver = true;
			throw new StrategyException("you cannot move on the choke point.");
		}

	}

	/**
	 * Method MovetogetFlag.
	 * 
	 * @param piece
	 *            PieceType
	 * @param from
	 *            Location
	 * @param to
	 *            Location
	 * 
	 * 
	
	 * @return MoveResult */
	public MoveResult MovetogetFlag(PieceType piece, Location from, Location to)
	{
		LastPlayerColor = getPieceAt(from).getOwner();
		NumOfMove++;
		flagcaptured++;
		steps.add(new PieceStep(NumOfMove, getPieceAt(from), from, to));
		MoveResult moveresult;
		if (numofflag == flagcaptured)
		{
			if (getPieceAt(from).getOwner() == PlayerColor.RED)
			{
				gameOver = true;
				moveresult = new MoveResult(MoveResultStatus.RED_WINS,
						new PieceLocationDescriptor(getPieceAt(from), from));
			}
			else
			{
				gameOver = true;
				moveresult = new MoveResult(MoveResultStatus.BLUE_WINS,
						new PieceLocationDescriptor(getPieceAt(from), from));
			}
		}
		else
		{
			final PieceLocationDescriptor winner = new PieceLocationDescriptor(
					getPieceAt(from), to);
			moveresult = new MoveResult(MoveResultStatus.FLAG_CAPTURED, winner);
			RemoveOneRecord(to);
			RemoveOneRecord(from);
			record.add(winner);

		}
		return moveresult;
	}

	/**
	 * Method dostrike.
	 * 
	 * @param piece
	 *            PieceType
	 * @param from
	 *            Location
	 * @param to
	 *            Location
	 * 
	 * 
	
	 * @return MoveResult */
	public MoveResult dostrike(PieceType piece, Location from, Location to)
	{
		LastPlayerColor = getPieceAt(from).getOwner();
		NumOfMove++;
		MoveResult moveresult;
		if (piece != PieceType.MINER
				&& getPieceAt(to).getType() == PieceType.BOMB)
		{
			RemoveOneRecord(from);
			moveresult = new MoveResult(MoveResultStatus.OK,
					new PieceLocationDescriptor(getPieceAt(to), to));
			if (!canAnyPieceMove(record, LastPlayerColor))
			{
				moveresult = opponentwin(new PieceLocationDescriptor(
						getPieceAt(to), to));
			}
		}
		else
		{
			final StrikeResult strikeresult = StrikeTwoPiece(piece,
					getPieceAt(to).getType());
			if (strikeresult == StrikeResult.equal)
			{
				RemoveOneRecord(from);
				RemoveOneRecord(to);
				if (!(canAnyPieceMove(record, PlayerColor.BLUE) || canAnyPieceMove(
						record, PlayerColor.RED)))
				{
					moveresult = new MoveResult(MoveResultStatus.DRAW, null);
				}
				else if (!canAnyPieceMove(record, LastPlayerColor))
				{
					moveresult = opponentwin(null);
				}
				else if (!canAnyPieceMove(record, opponentcolor()))
				{
					moveresult = playerwin(null);
				}
				else
				{
					moveresult = new MoveResult(MoveResultStatus.OK, null);
				}
			}
			else if (strikeresult == StrikeResult.greater)
			{
				moveresult = new MoveResult(MoveResultStatus.OK,
						new PieceLocationDescriptor(getPieceAt(from), to));
				final PieceLocationDescriptor winner = new PieceLocationDescriptor(
						getPieceAt(from), to);
				steps.add(new PieceStep(NumOfMove, getPieceAt(from), from, to));
				RemoveOneRecord(to);
				RemoveOneRecord(from);
				record.add(winner);
				if (!canAnyPieceMove(record, opponentcolor()))
				{
					moveresult = playerwin(winner);
				}
			}
			else
			{
				final PieceLocationDescriptor winner = new PieceLocationDescriptor(
						getPieceAt(to), from);
				moveresult = new MoveResult(MoveResultStatus.OK, winner);
				RemoveOneRecord(to);
				RemoveOneRecord(from);
				record.add(winner);
				if (!canAnyPieceMove(record, LastPlayerColor))
				{
					moveresult = opponentwin(winner);
				}
			}
		}
		return moveresult;
	}

	/**
	 * Method RemoveOneRecord.
	 * 
	 * @param location
	 *            Location
	 */
	public void RemoveOneRecord(Location location)
	{
		final List<PieceLocationDescriptor> temprecordlist 
						= new ArrayList<PieceLocationDescriptor>();
		final Iterator<PieceLocationDescriptor> It = record.iterator();
		while (It.hasNext())
		{
			PieceLocationDescriptor tempRecord = It.next();
			temprecordlist.add(tempRecord);
		}
		for (int i = 0; i < temprecordlist.size(); i++)
		{
			if (temprecordlist.get(i).equals(
					getRecordPiece(getPieceAt(location), location)))
			{
				temprecordlist.remove(i);
			}
		}
		record = temprecordlist;
	}

	/**
	 * Method getRecordPiece. Get the RecordPiece
	 * on the given location
	 * 
	 * @param piece
	 *            Piece
	 * @param location
	 *            Location
	 * 
	 * 
	
	 * @return RecordPiece */
	public PieceLocationDescriptor getRecordPiece(Piece piece, Location location)
	{
		PieceLocationDescriptor tempRecord;
		PieceLocationDescriptor recordfinally = null;
		final Iterator<PieceLocationDescriptor> It = record.iterator();
		while (It.hasNext())
		{
			tempRecord = It.next();
			if (tempRecord.getPiece().equals(piece)
					&& tempRecord.getLocation().equals(location))
			{
				recordfinally = tempRecord;
			}
		}
		return recordfinally;
	}

	/**
	 * Method StrikeTwoPiece.
	 * 
	 * @param Piece1
	 *            PieceType
	 * @param Piece2
	 *            PieceType
	 * 
	 * 
	
	 * @return StrikeResult */
	public StrikeResult StrikeTwoPiece(PieceType Piece1, PieceType Piece2)
	{
		StrikeResult result = null;
		if (Piece1 == PieceType.MINER && Piece2 == PieceType.BOMB)
		{
			result = StrikeResult.greater;
		}
		else if (Piece1 == PieceType.SPY && Piece2 == PieceType.MARSHAL)
		{
			result = StrikeResult.greater;
		}
		else if (Rank.get(Piece1) == Rank.get(Piece2))
		{
			result = StrikeResult.equal;
		}
		else if (Rank.get(Piece1) > Rank.get(Piece2))
		{
			result = StrikeResult.greater;
		}
		else if (Rank.get(Piece1) < Rank.get(Piece2))
		{
			result = StrikeResult.Less;
		}
		return result;
	}

	/**
	 * Method MoveToAnEmptyPlace.
	 * 
	 * @param piece
	 *            PieceType
	 * @param from
	 *            Location
	 * @param to
	 *            Location
	 * 
	 * 
	
	 * @return MoveResult */
	public MoveResult MoveToAnEmptyPlace(PieceType piece, Location from,
			Location to)
	{
		LastPlayerColor = getPieceAt(from).getOwner();
		NumOfMove++;
		steps.add(new PieceStep(NumOfMove, getPieceAt(from), from, to));
		new PieceLocationDescriptor(getPieceAt(from), to);
		final MoveResult moveresult = new MoveResult(MoveResultStatus.OK,
				new PieceLocationDescriptor(getPieceAt(from), to));
		record.add(new PieceLocationDescriptor(getPieceAt(from), to));
		RemoveOneRecord(from);
		return moveresult;
	}

	/**
	 * Method checkPieceAt.
	 * 
	 * @param location
	 *            Location
	 * @param Collectionofpiece
	 *            Collection<
	 *            PieceLocationDescriptor>
	 * 
	 * 
	
	 * @throws StrategyException */
	public static void checkPieceAt(Location location,
			Collection<PieceLocationDescriptor> Collectionofpiece)
			throws StrategyException
	{
		int numofpiece = 0;
		PieceLocationDescriptor temprecord;
		final Iterator<PieceLocationDescriptor> It = Collectionofpiece
				.iterator();
		while (It.hasNext())
		{
			temprecord = It.next();
			if (temprecord.getLocation().equals(location))
			{
				numofpiece++;
			}

		}
		if (numofpiece != 1)
		{
			throw new StrategyException(
					"there are more than one piece on the same place");
		}
	}

	/**
	 * Method CanAnyPieceMove.
	 * 
	 * @param record
	 *            Collection<
	 *            PieceLocationDescriptor>
	 * @param color
	 *            PlayerColor
	
	 * @return boolean */
	static boolean canAnyPieceMove(Collection<PieceLocationDescriptor> record,
			PlayerColor color)
	{
		boolean CanAnyPieceMove = false;
		final Iterator<PieceLocationDescriptor> It = record.iterator();
		PieceLocationDescriptor temppiece;
		while (It.hasNext())
		{
			temppiece = It.next();
			if (temppiece.getPiece().getOwner() == color)
			{
				if (temppiece.getPiece().getType() == PieceType.CAPTAIN
						|| temppiece.getPiece().getType() == PieceType.COLONEL
						|| temppiece.getPiece().getType() == PieceType.GENERAL
						|| temppiece.getPiece().getType() == PieceType.LIEUTENANT
						|| temppiece.getPiece().getType() == PieceType.MAJOR
						|| temppiece.getPiece().getType() == PieceType.MARSHAL)
				{
					CanAnyPieceMove = true;
				}
				if (temppiece.getPiece().getType() == PieceType.SPY
						|| temppiece.getPiece().getType() == PieceType.MINER
						|| temppiece.getPiece().getType() == PieceType.SCOUT
						|| temppiece.getPiece().getType() == PieceType.SERGEANT)
				{
					CanAnyPieceMove = true;
				}
			}
		}
		return CanAnyPieceMove;
	}

	/**
	 * Method opponentwin.
	 * 
	 * @param winner
	 *            PieceLocationDescriptor
	
	 * @return MoveResult */
	public MoveResult opponentwin(PieceLocationDescriptor winner)
	{
		MoveResult moveresult;
		if (LastPlayerColor == PlayerColor.BLUE)
		{
			moveresult = new MoveResult(MoveResultStatus.RED_WINS, winner);
		}
		else
		{
			moveresult = new MoveResult(MoveResultStatus.BLUE_WINS, winner);
		}
		return moveresult;
	}

	/**
	 * Method playerwin.
	 * 
	 * @param winner
	 *            PieceLocationDescriptor
	
	 * @return MoveResult */
	public MoveResult playerwin(PieceLocationDescriptor winner)
	{
		MoveResult moveresult;
		if (LastPlayerColor == PlayerColor.BLUE)
		{
			moveresult = new MoveResult(MoveResultStatus.BLUE_WINS, winner);
		}
		else
		{
			moveresult = new MoveResult(MoveResultStatus.RED_WINS, winner);
		}
		return moveresult;
	}

	/**
	 * Method opponentcolor.
	 * 
	
	 * @return PlayerColor */
	public PlayerColor opponentcolor()
	{
		PlayerColor opponentcolor;
		if (LastPlayerColor == PlayerColor.BLUE)
		{
			opponentcolor = PlayerColor.RED;
		}
		else
		{
			opponentcolor = PlayerColor.BLUE;
		}
		return opponentcolor;
	}
 
	/**
	 * Method validateFirLieMove.
	 * @param from Location
	 * @param to Location
	 * @return MoveResult
	 * @throws StrategyException
	 */
	public MoveResult validateFirLieMove(Location from, Location to)
			throws StrategyException
	{
		final MoveResult moveresult;
		checkMoveObeyRepititionRule(NumOfMove, getPieceAt(from), from, to);
		checkChokePoint(to);
		if ((to.getCoordinate(Coordinate.X_COORDINATE)) > 9
				|| (to.getCoordinate(Coordinate.X_COORDINATE)) < 0
				|| (to.getCoordinate(Coordinate.Y_COORDINATE)) > 9
				|| (to.getCoordinate(Coordinate.Y_COORDINATE)) < 0)

		{
			gameOver = true;
			throw new StrategyException(
					"Invalid coordinate, expected other destination.");
		}
		if (from.distanceTo(to) == 2)
		{
			if (isNotOpenSquare(from, to))
			{
				gameOver = true;
				throw new StrategyException(
						"There exists pieces blocking the path.");
			}
			if (getPieceAt(to) == null)
			{
				gameOver = true;
				throw new StrategyException("There is no piece for strike.");
			}
			if (getPieceAt(to).getOwner() != LastPlayerColor)
			{
				gameOver = true;
				throw new StrategyException("you can not strike your piece.");
			}
			else
			{
				moveresult = dostrikeforfirlie(from, to);
			}
		}
		else
		{
			gameOver = true;
			throw new StrategyException(
					"you can only move 1 square or strike 2 square away.");
		}
		return moveresult;
	}

	private MoveResult dostrikeforfirlie(Location from, Location to)
	{
		MoveResult moveresult;
		if (getPieceAt(to).getType() == PieceType.FLAG)
		{
			moveresult = MovetogetFlag(PieceType.FIRST_LIEUTENANT, from, to);
		}

		else
		{
			moveresult = dostrike(PieceType.FIRST_LIEUTENANT, from, to);
			if (moveresult.getStatus() == MoveResultStatus.OK
					&& moveresult.getBattleWinner() != null)
			{
				if (moveresult.getBattleWinner().getPiece().getType() != PieceType.FIRST_LIEUTENANT)
				{
					final PieceLocationDescriptor newwinner = new PieceLocationDescriptor(
							moveresult.getBattleWinner().getPiece(), to);
					moveresult = new MoveResult(MoveResultStatus.OK, newwinner);
					RemoveOneRecord(from);
					record.add(newwinner);
				}
			}
		}
		return moveresult;
	}

}
