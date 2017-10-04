package strategy.game.version.epsilon;

import static org.junit.Assert.*;
import static strategy.common.PlayerColor.BLUE;
import static strategy.common.PlayerColor.RED;
import static strategy.game.common.MoveResultStatus.BLUE_WINS;
import static strategy.game.common.MoveResultStatus.OK;
import static strategy.game.common.MoveResultStatus.RED_WINS;
import static strategy.game.common.PieceType.*;
import static strategy.game.testutil.TestLocations.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.common.StrategyRuntimeException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;
import strategy.game.reporter.StrategyGameReporter;
import strategy.game.testutil.TestConfigurationFactory;
import strategy.game.version.AbstractStrategyGameController;
import strategy.game.version.delta.DeltaStrategyGameController;

public class EpsilonStrategyMasterTest
{
	private ArrayList<PieceLocationDescriptor> redConfiguration;
	private ArrayList<PieceLocationDescriptor> blueConfiguration;
	private Collection<StrategyGameObserver> observers;
	private StrategyGameObserver reporter;
	private final static StrategyGameFactory gameFactory = 
			StrategyGameFactory.getInstance();
	private final static TestConfigurationFactory configurationFactory = 
			TestConfigurationFactory.getInstance();
	private StrategyGameController game;	// used for many tests
	/*
	 * The board with the initial configuration looks like this:
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 9 | COL | MAJ | MAJ | CAP | CAP | CAP | 1LIE| LIE | LIE | SER | 
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 8 | SER | SER | MIN | MIN | MIN | MIN | SCO | SCO | SCO | SCO |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 7 | SCO | SCO | SCO | BOM | BOM | BOM | BOM | BOM | BOM | SPY |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 6 |  F  | MAR | GEN | COL | MAJ | CAP | 1LIE| SER | MIN |  F  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 5 |     |     |||||||||||||     |     |||||||||||||     |     |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 4 |     |     |||||||||||||     |     |||||||||||||     |     |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 3 |  F  | MAR | GEN | COL | MAJ | CAP | 1LIE| SER | MIN |  F  |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 2 | SCO | SCO | SCO | BOM | BOM | BOM | BOM | BOM | BOM | SPY |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 1 | SER | SER | MIN | MIN | MIN | MIN | SCO | SCO | SCO | SCO |
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 * 0 | COL | MAJ | MAJ | CAP | CAP | CAP | 1LIE| LIE | LIE | SER | 
	 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
	 */
	@Before
	public void setup() throws StrategyException
	{
		redConfiguration = configurationFactory.getRedEpsilonConfiguration();
		blueConfiguration = configurationFactory.getBlueEpsilonConfiguration();
		ArrayList<StrategyGameObserver> tempobservers = new ArrayList<StrategyGameObserver>();
		StrategyGameObserver reporter = new StrategyGameReporter();
		tempobservers.add(reporter);
		observers = tempobservers;
		game = gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
		game.startGame();
	}
	@Test
	public void createStrategyController() throws StrategyException
	{
		assertNotNull(gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers));
	}
	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
	}
	
	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		addToConfiguration(SERGEANT, BLUE, 0, 3);
		gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
	}
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (1, 3)
		addToConfiguration(MARSHAL, RED, 0, 4);
		gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
	}
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (1, 3)
		addToConfiguration(MARSHAL, RED, -1, 0);
		gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(11);	// BOMB @ (8, 7)
		addToConfiguration(BOMB, BLUE, 8, 10);
		gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(11);	// BOMB @ (8, 7)
		addToConfiguration(BOMB, BLUE, 10, 7);
		gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
	}
	@Test(expected=StrategyException.class)
	public void twoPiecesOnSameLocationInStartingConfiguration() throws StrategyException
	{
		redConfiguration.remove(1);	
		addToConfiguration(MARSHAL, RED, 0, 1); // Same place as 
		gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
	}
	@Test(expected=StrategyException.class)
	public void redHasthreeflags() throws StrategyException
	{
		redConfiguration.remove(1); 
		addToConfiguration(FLAG, RED, 1, 3);
		gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeCallingStartGame() throws StrategyException
	{
		game = gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
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
		game.move(PieceType.MINER, loc86, loc85);	
	}
	@Test
	public void redcaptureflag() throws StrategyException
	{
		game.move(MARSHAL, loc13, loc14);
		game.move(MINER, loc86, loc85);
		
		game.move(MARSHAL, loc14, loc04);
		game.move(MAJOR, loc46, loc45);
		
		game.move(MARSHAL, loc04, loc05);
		game.move(MAJOR,  loc45,  loc44);
		final MoveResult moveResult = game.move(MARSHAL, loc05, loc06);
		assertEquals(MoveResultStatus.FLAG_CAPTURED, moveResult.getStatus());
	}
	@Test
	public void bluecaptureflag() throws StrategyException
	{
		game.move(CAPTAIN, loc53, loc54);
		game.move(MARSHAL, loc16, loc15);
		game.move(CAPTAIN, loc54, loc55);
		game.move(MARSHAL, loc15,  loc14);
		game.move(MAJOR, loc43, loc44);
		game.move(MARSHAL, loc14,  loc04);
		game.move(MAJOR, loc44, loc45);
		final MoveResult moveResult = game.move(MARSHAL, loc04, loc03);
		assertEquals(MoveResultStatus.FLAG_CAPTURED, moveResult.getStatus());
	}
	@Test
	public void redwins() throws StrategyException
	{game.move(MARSHAL, loc13, loc14);
	game.move(MARSHAL, loc16, loc15);
	game.move(MARSHAL, loc14, loc04);
	game.move(MARSHAL, loc15, loc14);
	game.move(MARSHAL, loc04, loc05);
	game.move(MARSHAL, loc14, loc04);
	game.move(MARSHAL, loc05, loc06);
	game.move(MARSHAL, loc04, loc05);
	game.move(MINER, loc83, loc84);
	game.move(MARSHAL, loc05, loc15);
	game.move(MINER, loc84, loc94);
	game.move(MARSHAL, loc15, loc14);
	game.move(MINER, loc94, loc95);
	game.move(MARSHAL, loc14, loc04);
	final MoveResult moveResult = game.move(MINER, loc95, loc96);
	assertEquals(MoveResultStatus.RED_WINS, moveResult.getStatus());

		
	}
	@Test
	public void attackerWinsStrike() throws StrategyException
	{
		redConfiguration.remove(4);	 // MAJ@ (4£¬3)
		redConfiguration.remove(1);  // MAR@ (1, 3)
		addToConfiguration(MARSHAL, RED, 4, 3);
		addToConfiguration(MAJOR, RED, 1, 3);

		game = gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
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
		
		game = gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
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
		
		game = gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
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
	@Test
	public void testopponentwin1() throws StrategyException
	{
		AbstractStrategyGameController abs = new EpsilonStrategyGameController(redConfiguration, blueConfiguration,observers);
		abs.opponentwin(null);
	}
	
	@Test
	public void testopponentwin2() throws StrategyException
	{
		AbstractStrategyGameController abs = new EpsilonStrategyGameController(redConfiguration, blueConfiguration,observers);
		abs.startGame();
		abs.move(MARSHAL, loc13, loc14);
		abs.opponentwin(null);
	}
	@Test
	public void testplayerwin1() throws StrategyException
	{
		AbstractStrategyGameController abs = new EpsilonStrategyGameController(redConfiguration, blueConfiguration,observers);
		abs.playerwin(null);
	}
	@Test
	public void testplayerwin2() throws StrategyException
	{
		AbstractStrategyGameController abs = new EpsilonStrategyGameController(redConfiguration, blueConfiguration,observers);
		abs.startGame();
		abs.move(MARSHAL, loc13, loc14);
		abs.playerwin(null);
	}
	@Test
	public void redMoveFirLieStatusIsOK() throws StrategyException
	{
		game.move(CAPTAIN, loc53, loc54);
		game.move(CAPTAIN, loc56, loc55);
		final MoveResult result = game.move(FIRST_LIEUTENANT, loc63, loc53);
		assertEquals(OK, result.getStatus());
	}
	@Test
	public void redstrikeFirLieStatusIsOK() throws StrategyException
	{
		game.move(CAPTAIN, loc53, loc54);
		game.move(MARSHAL, loc16, loc15);
		game.move(CAPTAIN, loc54, loc44);
		game.move(MARSHAL, loc15, loc14);
		game.move(FIRST_LIEUTENANT, loc63, loc53);
		game.move(MARSHAL, loc14, loc04);
		game.move(FIRST_LIEUTENANT, loc53, loc54);
		game.move(MARSHAL, loc04, loc05);
		final MoveResult result = game.move(FIRST_LIEUTENANT, loc54, loc56);
		assertEquals(OK, result.getStatus());

	}
	@Test(expected=StrategyException.class)
	public void redMoveFirLieMovetwosqu() throws StrategyException
	{
		game.move(CAPTAIN, loc53, loc54);
		game.move(MARSHAL, loc16, loc15);
		game.move(CAPTAIN, loc54, loc44);
		game.move(MARSHAL, loc15, loc14);
		game.move(FIRST_LIEUTENANT, loc63, loc53);
		game.move(MARSHAL, loc14, loc04);
		game.move(FIRST_LIEUTENANT, loc53, loc55);	
	}
	@Test
	public void redFirLieStatusstrikeawayonesqu() throws StrategyException
	{
		game.move(CAPTAIN, loc53, loc54);
		game.move(MARSHAL, loc16, loc15);
		game.move(CAPTAIN, loc54, loc44);
		game.move(MARSHAL, loc15, loc14);
		game.move(FIRST_LIEUTENANT, loc63, loc53);
		game.move(MARSHAL, loc14, loc04);
		game.move(FIRST_LIEUTENANT, loc53, loc54);
		game.move(MARSHAL, loc04, loc05);
		game.move(FIRST_LIEUTENANT, loc54, loc55);
		game.move(MARSHAL, loc05, loc15);
		final MoveResult result = game.move(FIRST_LIEUTENANT, loc55, loc56);
		assertEquals(OK, result.getStatus());
	}
	@Test(expected=StrategyException.class)
	public void redMoveFirLieMovethreesqu() throws StrategyException
	{game.move(CAPTAIN, loc53, loc54);
	game.move(MARSHAL, loc16, loc15);
	game.move(CAPTAIN, loc54, loc44);
	game.move(MARSHAL, loc15, loc14);
	game.move(FIRST_LIEUTENANT, loc63, loc53);
	game.move(CAPTAIN, loc56, loc55);
	game.move(MINER, loc83, loc84);
	game.move(CAPTAIN, loc55, loc45);
	game.move(FIRST_LIEUTENANT, loc53, loc56);

	}
	@Test(expected=StrategyException.class)
	public void redMoveFirLiestrikenotopensqu() throws StrategyException
	{
		game.move(CAPTAIN, loc53, loc54);
		game.move(MAJOR, loc46, loc45);
		game.move(CAPTAIN, loc54, loc44);
		game.move(MAJOR, loc45, loc55);
		game.move(FIRST_LIEUTENANT, loc63, loc53);
		game.move(MARSHAL, loc16, loc15);
		game.move(FIRST_LIEUTENANT, loc53, loc54);
		game.move(MARSHAL, loc15, loc14);
		game.move(FIRST_LIEUTENANT, loc54, loc56);

	}
	@Test(expected=StrategyException.class)
	public void destinationisnotcorrect1() throws StrategyException
	{
		game.move(MARSHAL, loc13, new Location2D(-1, 6));
	}
	@Test(expected=StrategyException.class)
	public void destinationisnotcorrect2() throws StrategyException
	{
		game.move(MARSHAL, loc13, new Location2D(10, 6));
	}
	@Test(expected=StrategyException.class)
	public void destinationisnotcorrect3() throws StrategyException
	{
		game.move(MARSHAL, loc13, new Location2D(1, 10));
	}
	@Test(expected=StrategyException.class)
	public void destinationisnotcorrect4() throws StrategyException
	{
		game.move(MARSHAL, loc13, new Location2D(1, -1));
	}
	
	@Test
	public void resign() throws StrategyException{
		game.move(null, null, null);
	}
	@Test
	public void unrigister() throws StrategyException
	{
		EpsilonStrategyGameController temp =  new EpsilonStrategyGameController(
				redConfiguration,blueConfiguration,observers);
		temp.unregister(reporter);
	}
	@Test(expected=StrategyException.class)
	public void InvalidPieceCOnfiguration() throws StrategyException
	{
		redConfiguration.remove(8);	 // MINER@ (8,3)
		redConfiguration.remove(6);	 // FIRST_LIEUTENANT @ (6,3)
		redConfiguration.remove(4);	 // MAJ@ (4,3)
		redConfiguration.remove(2);	 // GEN @ (2,3)
		
		addToConfiguration(MARSHAL, RED, 2,3);
		addToConfiguration(COLONEL, RED, 4,3);
		addToConfiguration(CAPTAIN, RED, 6,3);
		addToConfiguration(SERGEANT, RED, 8,3);
		
		game = gameFactory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration,observers);
		
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
