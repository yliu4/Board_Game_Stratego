/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game;

import static org.junit.Assert.*;
import java.util.Collection;
import org.junit.*;
import strategy.common.StrategyException;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.testutil.TestConfigurationFactory;
import strategy.game.version.alpha.AlphaStrategyGameController;
import strategy.game.version.beta.BetaStrategyGameController;

/**
 * Description
 * @author gpollice
 * @version Sep 10, 2013
 */
public class StrategyGameFactoryTest
{
	private static StrategyGameFactory gameFactory;
	private static TestConfigurationFactory configurationFactory;
	
	/**
	 * Get the factory instance
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setupBeforeClass() throws Exception
	{
		gameFactory = StrategyGameFactory.getInstance();
		configurationFactory = TestConfigurationFactory.getInstance();
	}

	@Test
	public void singletonFactoryInstanceIsNotNull()
	{
		assertNotNull(StrategyGameFactory.getInstance());
	}

	@Test
	public void factoryProducesAlphaStrategyGameController()
	{
		assertTrue(gameFactory.makeAlphaStrategyGame() instanceof AlphaStrategyGameController);
	}
	
	@Test
	public void factoryProducesBetaStrategyGameController() throws StrategyException
	{
		final Collection<PieceLocationDescriptor> redBetaConfiguration = 
				configurationFactory.getRedBetaConfiguration();
		final Collection<PieceLocationDescriptor> blueBetaConfiguration = 
				configurationFactory.getBlueBetaConfiguration();
		final StrategyGameController game = gameFactory.makeBetaStrategyGame(redBetaConfiguration, blueBetaConfiguration);
		assertTrue(game instanceof BetaStrategyGameController);
	}
}
