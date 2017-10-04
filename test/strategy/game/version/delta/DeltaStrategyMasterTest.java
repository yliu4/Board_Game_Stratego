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
import static org.junit.Assert.*;
import static strategy.common.PlayerColor.*;
import static strategy.game.common.MoveResultStatus.*;
import static strategy.game.common.PieceType.*;
import static strategy.game.testutil.TestLocations.*;
import java.util.ArrayList;
import org.junit.*;
import strategy.common.*;
import strategy.game.*;
import strategy.game.common.*;
import strategy.game.testutil.TestConfigurationFactory;
import strategy.game.version.AbstractStrategyGameController;

/**
 * Description
 * @author gpollice
 * @version Sep 21, 2013
 */
public class DeltaStrategyMasterTest
{
	private ArrayList<PieceLocationDescriptor> redConfiguration;
	private ArrayList<PieceLocationDescriptor> blueConfiguration;
	private final static StrategyGameFactory gameFactory = 
			StrategyGameFactory.getInstance();
	private final static TestConfigurationFactory configurationFactory = 
			TestConfigurationFactory.getInstance();
	private StrategyGameController game;	// used for many tests
	
	/*
	 * The board with the initial configuration looks like this:
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 9 | COL | MAJ | MAJ | CAP | CAP | CAP | LIE | LIE | LIE | SER | 
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 8 | SER | SER | MIN | MIN | MIN | MIN | SCO | SCO | SCO | SCO |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 7 | SCO | SCO | SCO | BOM | BOM | BOM | BOM | BOM | BOM | SPY |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 6 |  F  | MAR | GEN | COL | MAJ | CAP | LIE | SER | MIN | SCO |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 5 |     |     |||||||||||||     |     |||||||||||||     |     |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 4 |     |     |||||||||||||     |     |||||||||||||     |     |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 3 |  F  | MAR | GEN | COL | MAJ | CAP | LIE | SER | MIN | SCO |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 2 | SCO | SCO | SCO | BOM | BOM | BOM | BOM | BOM | BOM | SPY |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 1 | SER | SER | MIN | MIN | MIN | MIN | SCO | SCO | SCO | SCO |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 0 | COL | MAJ | MAJ | CAP | CAP | CAP | LIE | LIE | LIE | SER | 
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
	 */
	@Before
	public void setup() throws StrategyException
	{
		redConfiguration = configurationFactory.getRedDeltaConfiguration();
		blueConfiguration = configurationFactory.getBlueDeltaConfiguration();
		game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
	}
	
	@Test
	public void createStrategyController() throws StrategyException
	{
		assertNotNull(gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration));
	}
	
	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		addToConfiguration(SERGEANT, BLUE, 0, 3);
		gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (1, 3)
		addToConfiguration(MARSHAL, RED, 0, 4);
		gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (1, 3)
		addToConfiguration(MARSHAL, RED, -1, 0);
		gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(11);	// BOMB @ (8, 7)
		addToConfiguration(BOMB, BLUE, 8, 10);
		gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(11);	// BOMB @ (8, 7)
		addToConfiguration(BOMB, BLUE, 10, 7);
		gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(expected=StrategyException.class)
	public void twoPiecesOnSameLocationInStartingConfiguration() throws StrategyException
	{
		redConfiguration.remove(1);	
		addToConfiguration(MARSHAL, RED, 0, 1); // Same place as 
		gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void redHastwoflags() throws StrategyException
	{
		redConfiguration.remove(1); 
		addToConfiguration(FLAG, RED, 1, 3);
		gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeCallingStartGame() throws StrategyException
	{
		game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.move(LIEUTENANT, loc11, loc12);
	}

	@Test
	public void redMakesValidFirstMoveStatusIsOK() throws StrategyException
	{
		final MoveResult result = game.move(MARSHAL, loc13, loc14);
		assertEquals(OK, result.getStatus());
	}
	
	@Test
	public void redMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		game.move(PieceType.MARSHAL, loc13, loc14);
		assertNull(game.getPieceAt(loc13));
		assertEquals(new Piece(MARSHAL, RED), game.getPieceAt(loc14));
	}
	
	@Test(expected=StrategyException.class)
	public void redAttemptsMoveFromEmptyLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc14, loc15);
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesPieceNotOnFromLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc31, loc32);
	}
	
	@Test
	public void blueMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc14);
		game.move(MAJOR, loc46, loc45);
		assertEquals(new Piece(MAJOR, BLUE), game.getPieceAt(loc45));
	}
	
	
	@Test(expected=StrategyException.class)
	public void redMovesFromInvalidLocation() throws StrategyException
	{
		game.move(LIEUTENANT, badLoc, loc12);
	}
	
	@Test(expected=StrategyException.class)
	public void blueMovesToInvalidLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(SERGEANT, loc24, badLoc);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveAfterGameIsOver() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc14);
		game.move(MAJOR, loc46, loc45);
		game.move(MARSHAL, loc14, loc15);
		game.move(MAJOR, loc45, loc44);
		game.move(MARSHAL, loc15, loc05);
		game.move(SCOUT, loc96, loc94);
		game.move(MARSHAL, loc05, loc06);
		game.move(MAJOR, loc44, loc43);
	}
	
	@Test(expected=StrategyException.class)
	public void moveWrongColorPiece() throws StrategyException
	{
		game.move(SCOUT, loc96, loc94);	
	}
	
	@Test
	public void redWins() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc14);
		game.move(MINER, loc86, loc85);
		
		game.move(MARSHAL, loc14, loc04);
		game.move(MAJOR, loc46, loc45);
		
		game.move(MARSHAL, loc04, loc05);
		game.move(MAJOR,  loc45,  loc44);
		final MoveResult moveResult = game.move(MARSHAL, loc05, loc06);
		assertEquals(RED_WINS, moveResult.getStatus());
	}

	/////////////////////
	@Test
	public void blueWins() throws StrategyException
	{
		game.move(CAPTAIN, loc53, loc54);
		game.move(MARSHAL, loc16, loc15);
		game.move(CAPTAIN, loc54, loc55);
		game.move(MARSHAL, loc15,  loc14);
		game.move(MAJOR, loc43, loc44);
		game.move(MARSHAL, loc14,  loc04);
		game.move(MAJOR, loc44, loc45);
		final MoveResult moveResult = game.move(MARSHAL, loc04, loc03);
		assertEquals(BLUE_WINS, moveResult.getStatus());
	}
	////////////////////////////////////////
	@Test
	public void attackerWinsStrike() throws StrategyException
	{
		redConfiguration.remove(4);	 // MAJ@ (4£¬3)
		redConfiguration.remove(1);  // MAR@ (1, 3)
		addToConfiguration(MARSHAL, RED, 4, 3);
		addToConfiguration(MAJOR, RED, 1, 3);

		game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		game.move(MARSHAL, loc43, loc44);
		game.move(MAJOR, loc46, loc45);
		final MoveResult moveResult = game.move(MARSHAL, loc44, loc45);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(MARSHAL, RED), loc45),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc44));
		assertNull(game.getPieceAt(loc43));
		assertEquals(new Piece(MARSHAL, RED), game.getPieceAt(loc45));
	}

	@Test
	public void defenderWinsStrike() throws StrategyException
	{
		redConfiguration.remove(4);	 // MAJ@ (4£¬3)
		redConfiguration.remove(1);  // MAR@ (1, 3)
		addToConfiguration(MARSHAL, RED, 4, 3);
		addToConfiguration(MAJOR, RED, 1, 3);
		
		game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		game.move(MAJOR, loc13, loc14);
		game.move(MARSHAL, loc16, loc15);
		
		final MoveResult moveResult = game.move(MAJOR, loc14, loc15);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(MARSHAL, BLUE), loc14),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc15));
		assertEquals(new Piece(MARSHAL, BLUE), game.getPieceAt(loc14));
	}
////////////////////////////////////////////////////////////////////
	
	@Test(expected=StrategyException.class)
	public void attemptToStrikeYourOwnPiece() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc23);
	}
	
	@Test
	public void attemptToMoveDiagonally() throws StrategyException
	{
		try {
			game.move(MAJOR, loc43, loc54);
			fail("Exception expected");
		} catch (StrategyException e) {
			assertTrue(true);
		} catch (StrategyRuntimeException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void attemptToMoveFurtherThanOneLocation() throws StrategyException
	{
		try {
			game.move(MAJOR, loc43, loc45);
			fail("Exception expected");
		} catch (StrategyException e) {
			assertTrue(true);
		} catch (StrategyRuntimeException e) {
			assertTrue(true);
		}
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveFlag() throws StrategyException
	{
		game.move(FLAG, loc03, loc04);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartGameInProgress() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc14);
		game.startGame();
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartCompletedGame() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc14);
		game.move(MINER, loc86, loc85);
		
		game.move(MARSHAL, loc14, loc04);
		game.move(MAJOR, loc46, loc45);
		
		game.move(MARSHAL, loc04, loc05);
		game.move(MAJOR,  loc45,  loc44);
		game.move(MARSHAL, loc05, loc06);
		game.startGame();
	}
	
	// Delta specific methods
	@Test
	public void initialConfigurationHasFourChokePoints() throws StrategyException
	{
		assertEquals(CHOKE_POINT, game.getPieceAt(loc24).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc25).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc34).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc35).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc64).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc65).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc74).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc75).getType());
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveAChokePoint() throws StrategyException
	{
		game.move(CHOKE_POINT, loc24, loc14);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveToChokePoint() throws StrategyException
	{
		game.move(GENERAL, loc23, loc24);
	}
	////////////////////////////
	@Test
	public void redLosesByMoveRepetition() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc14);
		game.move(MAJOR, loc46, loc45);
		game.move(MARSHAL, loc14, loc13);
		game.move(MAJOR, loc45, loc44);
		try {
			MoveResult mr = game.move(MARSHAL, loc13, loc14);
			assertEquals(BLUE_WINS, mr.getStatus());
		} catch (StrategyException e) {
			assertTrue(true);
		}
	}
	@Test(expected=StrategyException.class)
	public void toandfromareequal() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc13);}
	@Test(expected=StrategyException.class)
	public void scoutfromisntocorrect() throws StrategyException
	{
		game.move(SCOUT, badLoc, loc13);}
	@Test
	public void blueLosesByMoveRepetition() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc14);
		game.move(MAJOR, loc46, loc45);
		game.move(MARSHAL, loc14, loc15);
		game.move(MAJOR, loc45, loc46);
		game.move(MARSHAL, loc15, loc05);
		try {
			MoveResult mr = game.move(MAJOR, loc46, loc45);
			assertEquals(RED_WINS, mr.getStatus());
		} catch (StrategyException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	public void MinerDefuseaBomb() throws StrategyException
	{
		blueConfiguration.remove(11);	 // BOM @ (8,7)
		blueConfiguration.remove(8);  // MIN @ (8,6)
		addToConfiguration(BOMB, BLUE, 8, 6);
		addToConfiguration(MINER, BLUE, 8, 7);
		
		game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		game.move(MINER, loc83, loc84);
		game.move(MARSHAL, loc16, loc15);
		game.move(MINER, loc84, loc85);
		game.move(MARSHAL, loc15, loc14);
		
		final MoveResult moveResult = game.move(MINER, loc85, loc86);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(MINER, RED), loc86),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc85));
		assertEquals(new Piece(MINER, RED), game.getPieceAt(loc86));
	}
	@Test
	public void NotMinerStrickWithBomb() throws StrategyException
	{
		blueConfiguration.remove(11); // BOM @ (8,7)
		blueConfiguration.remove(9);  // SCO @ (9,6)
		addToConfiguration(BOMB, BLUE, 9, 6);
		addToConfiguration(SCOUT, BLUE, 8, 7);
		
		game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		game.move(SCOUT, loc93, loc94);
		game.move(MARSHAL, loc16, loc15);
		game.move(SCOUT, loc94, loc95);
		game.move(MARSHAL, loc15, loc14);
		
		final MoveResult moveResult = game.move(SCOUT, loc95, loc96);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(BOMB, BLUE), loc96),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc95));
		assertEquals(new Piece(BOMB, BLUE), game.getPieceAt(loc96));
	}
	
	@Test
	public void ScoutUsePriviledge() throws StrategyException
	{
		final MoveResult moveResult = game.move(SCOUT, loc93, loc95);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(SCOUT, RED), loc95),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc93));
		assertEquals(new Piece(SCOUT, RED), game.getPieceAt(loc95));
	}
	
	@Test
	public void ScoutUsePriviledge2() throws StrategyException
	{
		redConfiguration.remove(9);	 // SCO@ (9, 3)
		redConfiguration.remove(7);  // SERGEANT@ (7, 3)
		addToConfiguration(SCOUT, RED, 7, 3);
		addToConfiguration(SERGEANT, RED, 9, 3);
		
		game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		game.move(SERGEANT, loc93, loc94);
		game.move(SCOUT, loc96, loc95);
		game.move(MINER, loc83, loc84);
		game.move(MINER, loc86, loc85);
		
		final MoveResult moveResult = game.move(SCOUT, loc73, loc93);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(SCOUT, RED), loc93),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc73));
		assertEquals(new Piece(SCOUT, RED), game.getPieceAt(loc93));
	}
	 @Test
		public void strikeResultsInTie() throws StrategyException
		{
			game.move(MARSHAL, loc13, loc14);
			game.move(MARSHAL, loc16, loc15);
			final MoveResult moveResult = game.move(MARSHAL, loc14, loc15);
			assertEquals(OK, moveResult.getStatus());
			assertNull(moveResult.getBattleWinner());
			assertNull(game.getPieceAt(loc14));
			assertNull(game.getPieceAt(loc15));
		}
		@Test(expected=StrategyException.class)
		public void ScoutUsePriviledgeInproperly() throws StrategyException
		{
			game.move(SCOUT, loc93, loc96);
				
		}
		@Test(expected=StrategyException.class)
		public void ScoutUsePriviledgeInproperly2() throws StrategyException
		{
			
			game.move(MARSHAL, loc13, loc14);
			game.move(SCOUT, loc96, loc95);		
			game.move(SCOUT, loc93, loc96);
			
		}
		@Test
		public void testopponentwin1() throws StrategyException
		{
			AbstractStrategyGameController abs = new DeltaStrategyGameController(redConfiguration,blueConfiguration );
			abs.opponentwin(null);
		}
		@Test
		public void testopponentwin2() throws StrategyException
		{
			AbstractStrategyGameController abs = new DeltaStrategyGameController(redConfiguration,blueConfiguration );
			abs.startGame();
			abs.move(MARSHAL, loc13, loc14);
			abs.opponentwin(null);
		}
		@Test
		public void testplayerwin1() throws StrategyException
		{
			AbstractStrategyGameController abs = new DeltaStrategyGameController(redConfiguration,blueConfiguration );
			abs.playerwin(null);
		}
		@Test
		public void testplayerwin2() throws StrategyException
		{
			AbstractStrategyGameController abs = new DeltaStrategyGameController(redConfiguration,blueConfiguration );
			abs.startGame();
			abs.move(MARSHAL, loc13, loc14);
			abs.playerwin(null);
		}
		@Test
		public void SpyStrikeMarshal() throws StrategyException
		{
			redConfiguration.remove(10);		// SPY @ (9, 2)
			redConfiguration.remove(1);		// MAR @(1, 3)
			addToConfiguration(SPY, RED, 1, 3);
			addToConfiguration(MARSHAL, RED, 9, 2);
			
			game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
			game.startGame();
			game.move(SPY, loc13, loc14);
			game.move(MARSHAL, loc16, loc15);
			final MoveResult moveResult = game.move(SPY, loc14, loc15);
			
			assertEquals(OK, moveResult.getStatus());
			assertEquals(
					new PieceLocationDescriptor(new Piece(SPY, RED), loc15),
					moveResult.getBattleWinner());
			assertNull(game.getPieceAt(loc14));
			assertEquals(new Piece(SPY, RED), game.getPieceAt(loc15));
		}
		
		@Test
		public void MarshalStrikeSpy() throws StrategyException
		{
			blueConfiguration.remove(10);		// SPY @ (9, 7)
			blueConfiguration.remove(1);		// MAR @(1, 6)
			addToConfiguration(SPY, BLUE, 1, 6);
			addToConfiguration(MARSHAL, BLUE, 9, 7);
			
			game = gameFactory.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
			game.startGame();
			game.move(MARSHAL, loc13, loc14);
			game.move(SPY, loc16, loc15);
			final MoveResult moveResult = game.move(MARSHAL, loc14, loc15);
			
			assertEquals(OK, moveResult.getStatus());
			assertEquals(
					new PieceLocationDescriptor(new Piece(MARSHAL, RED), loc15),
					moveResult.getBattleWinner());
			assertNull(game.getPieceAt(loc14));
			assertEquals(new Piece(MARSHAL, RED), game.getPieceAt(loc15));
		}

	// Helper methods
	private void addToConfiguration(PieceType type, PlayerColor color, int x, int y)
	{
		final PieceLocationDescriptor confItem = new PieceLocationDescriptor(
				new Piece(type, color),
				new Location2D(x, y));
		if (color == PlayerColor.RED) {
			redConfiguration.add(confItem);
		} else {
			blueConfiguration.add(confItem);
		}
	}
	
	private void showConfiguration(ArrayList<PieceLocationDescriptor> configuration)
	{
		for (PieceLocationDescriptor pld : configuration) {
			System.out.println(pld.getLocation() + ": " + pld.getPiece().getType());
		}
	}
	
	private void showBoard()
	{
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 6; x++) {
				Location2D loc = new Location2D(x, y);
				Piece p = game.getPieceAt(loc);
				if (p != null) {
					System.out.println(loc + ": " + p.getType());
				}
			}
		}
	}
}
