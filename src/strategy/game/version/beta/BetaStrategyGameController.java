/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.beta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import strategy.common.*;
import strategy.game.*;
import strategy.game.common.*;

/**
 * The DetastrategyGameController implements the game core for the Beta
 * Strategy version.
 * 
 * @author Xiaosong Wen (xwen2) Yuchen Liu (yliu4)
 * @version Sep 10, 2013
 */
public class BetaStrategyGameController implements StrategyGameController
{
	private boolean gameStarted;
	private boolean gameOver;
	private int NumOfTimeCallStartGame;
	private Collection<PieceLocationDescriptor> record;
	private Map<PieceType,Integer> Rank = new Hashtable<PieceType, Integer>();
	private int NumOfMove = 0;
	private PlayerColor LastPlayerColor;
	
	/**
	 * Constructor for BetaStrategyGameController.
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	 * @throws StrategyException 
	 */
	public BetaStrategyGameController(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException
	{
		NumOfTimeCallStartGame=0;
		gameStarted = false;
		gameOver = false;
		checkrecord(redConfiguration, blueConfiguration);
		record = BetaStrategyGameController
				.Initializerecord(redConfiguration, blueConfiguration);
		LastPlayerColor = PlayerColor.BLUE;
		Rank = InitializeRank();
		NumOfMove = 0;
	}

	/**
	 * Method checkrecord.
	 * Check the number of kinds of pieces and the number of each pieces are correct to the rule  
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	
	 * @throws StrategyException */
	public static void checkrecord(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
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
		temprecord.addAll(redConfiguration);
		temprecord.addAll(blueConfiguration);
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
		if (numofflag != 2 || numofmar != 2 || numofcol != 4 || numofcap != 4
				|| numoflie != 6 || numofser != 6)
		{
			throw new StrategyException(
					"the number of piecetype is not correct.");
		}
	}
	/**
	 * Method checkPieceAt.
	 * @param location Location
	 * @param Collectionofpiece Collection<PieceLocationDescriptor>
	
	 * @throws StrategyException */
	public static void checkPieceAt(Location location,
			Collection<PieceLocationDescriptor> Collectionofpiece )
			throws StrategyException
	{
		int numofpiece=0;
		PieceLocationDescriptor temprecord;
		final Iterator<PieceLocationDescriptor> It = Collectionofpiece.iterator();
		while (It.hasNext())
		{
			temprecord = It.next();
			if (temprecord.getLocation().equals(location))
			{
				numofpiece++;
			}

		}
		if (numofpiece != 1){
			throw new StrategyException("there are more than one piece on the same place");
		}
	}

	
	 private static Map<PieceType,Integer> InitializeRank()
	{
		final Hashtable<PieceType, Integer> Rank = new Hashtable<PieceType, Integer>();
		Rank.put(PieceType.MARSHAL, 12);
		Rank.put(PieceType.COLONEL, 10);
		Rank.put(PieceType.CAPTAIN, 8);
		Rank.put(PieceType.LIEUTENANT, 7);
		Rank.put(PieceType.SERGEANT, 6);
		return Rank;

	}
/**
	 * Method Initializerecord.
	 * @param redConfiguration Collection<PieceLocationDescriptor>
	 * @param blueConfiguration Collection<PieceLocationDescriptor>
	 * @return Collection<RecordPiece>
	 */
	private static Collection<PieceLocationDescriptor> Initializerecord(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
	{
		final Collection<PieceLocationDescriptor> record = new ArrayList<PieceLocationDescriptor>();
		record.addAll(redConfiguration);
		record.addAll(blueConfiguration);

		return record;
	}


	/*
	 * @see strategy.game.StrategyGameController#startGame()
	 */
	@Override
	public void startGame()throws StrategyException
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

	

	/*
	 * @see strategy.game.StrategyGameController#move
	 */
	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException
	{
		MoveResult moveresult = null;
		if (gameOver)
		{ 
			throw new StrategyException(
					"The game is over, you cannot make a move");
		}
		if (!gameStarted) 
		{
			throw new StrategyException("You must start the game!");
		}
		
		CheckMoveSource(piece, from);
		checkMoveDestination(from, to);
		if (NumOfMove < 12)
		{
		if(getPieceAt(to) != null){
			if(getPieceAt(to).getType() == PieceType.FLAG){
				moveresult=MovetogetFlag(piece, from, to);
			}
			else{
				moveresult=dostrike(piece, from, to);
			}
		}
		else{
			moveresult=MoveToAnEmptyPlace(piece, from, to);
		}
		}
	
		if (NumOfMove == 12){
			gameOver = true;
			if (moveresult.getStatus().equals(MoveResultStatus.OK)){
				moveresult = new MoveResult(MoveResultStatus.DRAW, moveresult.getBattleWinner());
			}
		}
		return moveresult;
	}
	
	/**
	 * Method StrikeTwoPiece.
	 * @param Piece1 PieceType
	 * @param Piece2 PieceType
	
	 * @return StrikeResult */
	public StrikeResult StrikeTwoPiece(PieceType Piece1, PieceType Piece2)
	{
		StrikeResult result = null;

		if (Rank.get(Piece1) == Rank.get(Piece2))
		{
			result = StrikeResult.equal;
		} else if (Rank.get(Piece1) > Rank.get(Piece2))
		{
			result = StrikeResult.greater;
		} else if (Rank.get(Piece1) < Rank.get(Piece2))
		{
			result = StrikeResult.Less;
		}
		return result;
	}
	/**
	 * Method getRecordPiece.
	 * @param piece  Piece
	 * @param location Location
	 * @return RecordPiece 
	 */
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
	 * Method CheckMoveSource.
	 * @param piecetype PieceType
	 * @param location Location
	 * @throws StrategyException
	 */
	public void CheckMoveSource(PieceType piecetype, Location location)
			throws StrategyException
	{

		if (piecetype == PieceType.FLAG)
		{

			throw new StrategyException(
					"Invalid piece, expected other type and got " + piecetype);

		}
		if (getPieceAt(location) == null){
			throw new StrategyException("you cannot move from an empty place");
		}
		if (getPieceAt(location).getOwner().equals(LastPlayerColor))
		{
			throw new StrategyException("It is not your turn.");
		}

		if (getPieceAt(location).getType() != piecetype)
		{
			throw new StrategyException(
					"The piece and the location are not match.");
		}
	}

	/**
	 * Method checkMoveDestination.
	 * @param from Location
	 * @param to Location
	 * @throws StrategyException
	 */
	public void checkMoveDestination(Location from, Location to)
			throws StrategyException
	{
		if ((to.getCoordinate(Coordinate.X_COORDINATE)) > 5
				|| (to.getCoordinate(Coordinate.X_COORDINATE)) < 0
				|| (to.getCoordinate(Coordinate.Y_COORDINATE)) > 5
				|| (to.getCoordinate(Coordinate.Y_COORDINATE)) < 0)
		{
			throw new StrategyException(
					"Invalid coordinate, expected other destination.");

		}
		if (from.distanceTo(to) != 1)
		{
			throw new StrategyException("Only allow to move one square.");
		}
		if (getPieceAt(to) != null && getPieceAt(to).getOwner() == getPieceAt(from).getOwner())
		{
			throw new StrategyException(
					"Invalid move, cannot move your piece onto your piece.");
		}
	}
	
	/**
	 * Method RemoveOneRecord.
	 * @param location Location
	 */
	public void RemoveOneRecord(Location location)
	{
	final List<PieceLocationDescriptor> temprecordlist =new ArrayList<PieceLocationDescriptor>() ;
		final Iterator<PieceLocationDescriptor> It = record.iterator();
		while (It.hasNext()){
			PieceLocationDescriptor tempRecord = It.next();
			temprecordlist.add(tempRecord);
		}
		for (int i=0;i < temprecordlist.size();i++){
			if (temprecordlist.get(i).equals(getRecordPiece(getPieceAt(location), location))){
				temprecordlist.remove(i);
			}
		}
		record=temprecordlist;
		
	}
	
	/**
	 * Method MoveToAnEmptyPlace.
	 * @param piece PieceType
	 * @param from Location
	 * @param to Location
	 * @return MoveResult
	 */
	public MoveResult MoveToAnEmptyPlace(PieceType piece, Location from,
			Location to)
	{
		LastPlayerColor = getPieceAt(from).getOwner();
		NumOfMove++;
		final MoveResult moveresult = new MoveResult(MoveResultStatus.OK,
				new PieceLocationDescriptor(getPieceAt(from), to));
		record.add(new PieceLocationDescriptor(getPieceAt(from), to));
		RemoveOneRecord(from);
		return moveresult;
	}
	/**
	 * Method MovetogetFlag.
	 * @param piece PieceType
	 * @param from Location
	 * @param to Location
	 * @return MoveResult
	 */
	public MoveResult MovetogetFlag(PieceType piece, Location from, Location to)
	{
		LastPlayerColor = getPieceAt(from).getOwner();
		NumOfMove++;
		MoveResult moveresult;
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
		return moveresult;
	}
	/**
	 * Method dostrike.
	 * @param piece PieceType
	 * @param from Location
	 * @param to Location
	 * @return MoveResult
	 */
	public MoveResult dostrike(PieceType piece, Location from, Location to)
	{
		LastPlayerColor = getPieceAt(from).getOwner();
		NumOfMove++;
		MoveResult moveresult;
		final StrikeResult strikeresult = StrikeTwoPiece(piece, getPieceAt(to)
				.getType());
		if (strikeresult == StrikeResult.equal)
		{
			RemoveOneRecord(from);
			RemoveOneRecord(to);
			if (record.size() == 2)
			{
				moveresult = new MoveResult(MoveResultStatus.DRAW, null);
				gameOver=true;
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
			record.add(new PieceLocationDescriptor(getPieceAt(from), to));
			RemoveOneRecord(to);
			RemoveOneRecord(from);

		}
		else
		{
			moveresult = new MoveResult(MoveResultStatus.OK,
					new PieceLocationDescriptor(getPieceAt(to), from));
			record.add(new PieceLocationDescriptor(getPieceAt(to), from));
			RemoveOneRecord(to);
			RemoveOneRecord(from);
		}
		return moveresult;
	}
}
