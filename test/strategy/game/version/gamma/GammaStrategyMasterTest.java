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

/**
 * Description
 * @author gpollice
 * @version Sep 21, 2013
 */
public class GammaStrategyMasterTest
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
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 5 | MAR | COL | COL | CPT | CPT | LT  |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 4 | LT  | LT  | SGT | SGT | SGT |  F  |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 3 |     |     |     |     |     |     |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 2 |     |     |     |     |     |     |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 1 |  F  | LT  | LT  | SGT | SGT | SGT |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 0 | MAR | COL | COL | CPT | CPT | LT  |
	 * - +-----+-----+-----+-----+-----+-----+
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |
	 */
	@Before
	public void setup() throws StrategyException
	{
		redConfiguration = configurationFactory.getRedGammaConfiguration();
		blueConfiguration = configurationFactory.getBlueGammaConfiguration();
		game = gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
	}
	
	@Test
	public void createStrategyController() throws StrategyException
	{
		assertNotNull(gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration));
	}
	
	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		addToConfiguration(SERGEANT, BLUE, 0, 3);
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (0, 0)
		addToConfiguration(MARSHAL, RED, 0, 3);
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (0, 0)
		addToConfiguration(MARSHAL, RED, -1, 0);
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (0, 4)
		addToConfiguration(SERGEANT, BLUE, 0, 2);
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (0, 4)
		addToConfiguration(SERGEANT, BLUE, 6, 4);
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void twoPiecesOnSameLocationInStartingConfiguration() throws StrategyException
	{
		redConfiguration.remove(1);	// Marshall @ (0, 0)
		addToConfiguration(MARSHAL, RED, 0, 1); // Same place as RED Flag
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void usePieceNotInVersionInStartingConfiguration() throws StrategyException
	{
		redConfiguration.remove(1); // Marshall @ (0, 0)
		addToConfiguration(BOMB, RED, 0, 0);
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void redHasOneColonelAndFourSergeants() throws StrategyException
	{
		redConfiguration.remove(2); // Colonel @ (1, 0)
		addToConfiguration(SERGEANT, RED, 1, 0);
		gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeCallingStartGame() throws StrategyException
	{
		game = gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
		game.move(LIEUTENANT, loc11, loc12);
	}
	
	@Test
	public void redMakesValidFirstMoveStatusIsOK() throws StrategyException
	{
		final MoveResult result = game.move(LIEUTENANT, loc11, loc12);
		assertEquals(OK, result.getStatus());
	}
	
	@Test
	public void redMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		assertNull(game.getPieceAt(loc11));
		assertEquals(new Piece(LIEUTENANT, RED), game.getPieceAt(loc12));
	}
	
	@Test(expected=StrategyException.class)
	public void redAttemptsMoveFromEmptyLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc12, loc13);
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesPieceNotOnFromLocation() throws StrategyException
	{
		game.move(LIEUTENANT, loc31, loc32);
	}
	
	@Test
	public void blueMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(LIEUTENANT, loc04, loc03);
		assertEquals(new Piece(LIEUTENANT, BLUE), game.getPieceAt(loc03));
	}
	
	@Test(expected=StrategyException.class)
	public void redMovesPieceNotInGame() throws StrategyException
	{
		game.move(SCOUT, loc11, loc12);
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
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc14, loc13);
		game.move(SERGEANT, loc52, loc53);
		game.move(LIEUTENANT, loc13, loc14);
		game.move(SERGEANT, loc53, loc54);
		game.move(LIEUTENANT, loc14, loc13);
	}
	
	@Test(expected=StrategyException.class)
	public void moveWrongColorPiece() throws StrategyException
	{
		game.move(LIEUTENANT, loc04, loc03);
	}
	
	@Test
	public void redWins() throws StrategyException
	{
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc04, loc03);
		game.move(SERGEANT, loc52, loc53);
		game.move(LIEUTENANT,  loc03,  loc02);
		final MoveResult moveResult = game.move(SERGEANT, loc53, loc54);
		assertEquals(RED_WINS, moveResult.getStatus());
	}
	
	@Test
	public void blueWins() throws StrategyException
	{
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc04, loc03);
		game.move(SERGEANT, loc52, loc53);
		game.move(LIEUTENANT,  loc03,  loc02);
		game.move(SERGEANT, loc53, loc52);
		final MoveResult moveResult = game.move(LIEUTENANT, loc02, loc01);
		assertEquals(BLUE_WINS, moveResult.getStatus());
	}
	
	@Test
	public void attackerWinsStrike() throws StrategyException
	{
		redConfiguration.remove(10);	// SGT @ (4, 1)
		redConfiguration.remove(5);		// CPT @ (4, 0)
		addToConfiguration(SERGEANT, RED, 4, 0);
		addToConfiguration(CAPTAIN, RED, 4, 1);
		game = gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		game.move(CAPTAIN, loc41, loc42);
		game.move(SERGEANT, loc44, loc43);
		final MoveResult moveResult = game.move(CAPTAIN, loc42, loc43);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(CAPTAIN, RED), loc43),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc42));
		assertEquals(new Piece(CAPTAIN, RED), game.getPieceAt(loc43));
	}
	
	@Test
	public void defenderWinsStrike() throws StrategyException
	{
		redConfiguration.remove(10);	// SGT @ (4, 1)
		redConfiguration.remove(5);		// CPT @ (4, 0)
		addToConfiguration(SERGEANT, RED, 4, 0);
		addToConfiguration(CAPTAIN, RED, 4, 1);
		game = gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		game.move(CAPTAIN, loc41, loc42);
		game.move(SERGEANT, loc44, loc43);
		game.move(LIEUTENANT, loc11, loc12);
		final MoveResult moveResult = game.move(SERGEANT, loc43, loc42);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(CAPTAIN, RED), loc43),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc42));
		assertEquals(new Piece(CAPTAIN, RED), game.getPieceAt(loc43));
	}
	
	@Test
	public void strikeResultsInTie() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(LIEUTENANT, loc14, loc13);
		final MoveResult moveResult = game.move(LIEUTENANT, loc12, loc13);
		assertEquals(OK, moveResult.getStatus());
		assertNull(moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc12));
		assertNull(game.getPieceAt(loc13));
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToStrikeYourOwnPiece() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc21);
	}
	
	@Test
	public void attemptToMoveDiagonally() throws StrategyException
	{
		try {
			game.move(LIEUTENANT, loc11, loc22);
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
			game.move(LIEUTENANT, loc11, loc13);
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
		game.move(FLAG, loc01, loc02);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartGameInProgress() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.startGame();
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartCompletedGame() throws StrategyException
	{
		game.move(SERGEANT, loc51, loc52);
		game.move(LIEUTENANT, loc04, loc03);
		game.move(SERGEANT, loc52, loc53);
		game.move(LIEUTENANT,  loc03,  loc02);
		game.move(SERGEANT, loc53, loc54);
		game.startGame();
	}
	
	// Gamma specific methods
	@Test
	public void initialConfigurationHasFourChokePoints() throws StrategyException
	{
		assertEquals(CHOKE_POINT, game.getPieceAt(loc22).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc23).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc32).getType());
		assertEquals(CHOKE_POINT, game.getPieceAt(loc33).getType());
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveAChokePoint() throws StrategyException
	{
		game.move(CHOKE_POINT, loc22, loc21);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveToChokePoint() throws StrategyException
	{
		game.move(LIEUTENANT, loc21, loc22);
	}
	
	@Test
	public void redLosesByMoveRepetition() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(SERGEANT, loc44, loc43);
		game.move(LIEUTENANT, loc12, loc11);
		game.move(SERGEANT, loc43, loc42);
		try {
			MoveResult mr = game.move(LIEUTENANT, loc11, loc12);
			assertEquals(BLUE_WINS, mr.getStatus());
		} catch (StrategyException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void blueLosesByMoveRepetition() throws StrategyException
	{
		game.move(LIEUTENANT, loc11, loc12);
		game.move(SERGEANT, loc44, loc43);
		game.move(LIEUTENANT, loc12, loc13);
		game.move(SERGEANT, loc43, loc44);
		game.move(LIEUTENANT, loc13, loc12);
		try {
			MoveResult mr = game.move(SERGEANT, loc44, loc43);
			assertEquals(RED_WINS, mr.getStatus());
		} catch (StrategyException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void drawWhenNoMorePieces() throws StrategyException
	{
		redConfiguration.remove(1);		// MARSHAL @ (0, 0)
		redConfiguration.remove(0);		// FLAG @(0, 1)
		addToConfiguration(FLAG, RED, 0, 0);
		addToConfiguration(MARSHAL, RED, 0, 1);
		blueConfiguration.remove(1);	// MARSHAL @ (0, 5)
		blueConfiguration.remove(0);	// FLAG @ (5, 4)
		addToConfiguration(FLAG, BLUE, 0, 5);
		addToConfiguration(MARSHAL, BLUE, 5, 4);
		game = gameFactory.makeGammaStrategyGame(redConfiguration, blueConfiguration);
		game.startGame();
		game.move(MARSHAL, loc01, loc02);
		game.move(MARSHAL, loc54, loc53);
		game.move(MARSHAL, loc02, loc03);
		game.move(MARSHAL, loc53, loc52);
		game.move(MARSHAL, loc03, loc04);
		game.move(MARSHAL, loc52, loc51);
		game.move(MARSHAL, loc04, loc14);
		game.move(MARSHAL, loc51, loc50);
		game.move(MARSHAL, loc14, loc15);
		game.move(MARSHAL, loc50, loc40);
		game.move(MARSHAL, loc15, loc25);
		game.move(MARSHAL, loc40, loc41);
		game.move(MARSHAL, loc25, loc24);
		game.move(MARSHAL, loc41, loc31);
		game.move(MARSHAL, loc24, loc34);
		game.move(MARSHAL, loc31, loc30);
		game.move(MARSHAL, loc34, loc35);
		game.move(MARSHAL, loc30, loc20);
		game.move(MARSHAL, loc35, loc45);
		game.move(MARSHAL, loc20, loc21);
		game.move(MARSHAL, loc45, loc44);
		game.move(MARSHAL, loc21, loc11);
		game.move(MARSHAL, loc44, loc54);
		game.move(MARSHAL, loc11, loc10);
		game.move(MARSHAL, loc54, loc55);
		game.move(MARSHAL, loc10, loc11);
		game.move(MARSHAL, loc55, loc54);
		game.move(MARSHAL, loc11, loc21);
		game.move(MARSHAL, loc54, loc53);
		game.move(MARSHAL, loc21, loc31);
		game.move(MARSHAL, loc53, loc52);
		game.move(MARSHAL, loc31, loc41);
		game.move(MARSHAL, loc52, loc51);
		MoveResult mr = game.move(MARSHAL, loc41, loc51);
		assertEquals(MoveResultStatus.DRAW, mr.getStatus());
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
