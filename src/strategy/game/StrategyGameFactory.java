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

import java.util.Collection;
import java.util.Iterator;

import strategy.common.*;
import strategy.game.common.Coordinate;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.StrategyGameObserver;
import strategy.game.version.alpha.AlphaStrategyGameController;
import strategy.game.version.beta.BetaStrategyGameController;
import strategy.game.version.delta.DeltaStrategyGameController;
import strategy.game.version.epsilon.EpsilonStrategyGameController;
import strategy.game.version.gamma.GammaStrategyGameController;

/**
 * <p>
 * Factory to produce various versions of the Strategy game. This is implemented
 * as a singleton.
 * </p><p>
 * NOTE: If an error occurs creating any game, that is not specified in the particular
 * factory method's documentation, the factory method should throw a 
 * StrategyRuntimeException.
 * </p>
 * 
 * @author gpollice
 * @version Sep 10, 2013
 */
public class StrategyGameFactory
{
	private static final StrategyGameFactory instance = new StrategyGameFactory();
	
	/**
	 * Default private constructor to ensure this is a singleton.
	 */
	private StrategyGameFactory()
	{
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static StrategyGameFactory getInstance()
	{
		return instance;
	}
	 
	/**
	 * Create an Alpha Strategy game.
	
	 * @return the created Alpha Strategy game */
	public static StrategyGameController makeAlphaStrategyGame()
	{
		final StrategyGameController AlphaStrategyGame = new AlphaStrategyGameController();
	    return AlphaStrategyGame;
	
	}
	
	/**
	 * Create a new Beta Strategy game given the
	 * 
	 * @param redConfiguration
	 *            the initial starting configuration for the RED pieces
	 * @param blueConfiguration
	 *            the initial starting configuration for the BLUE pieces
	
	
	 * @return the Beta Strategy game instance with the initial configuration of
	 *         pieces  
	 * @throws StrategyException
	 *             if either configuration is correct * @throws StrategyException
	 */
	public static StrategyGameController makeBetaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
			throws StrategyException
	{
		if (redConfiguration == null || blueConfiguration == null){
		throw new StrategyException("Configuration cannot be null.");}
	
	if (redConfiguration.size() != 12 || blueConfiguration.size() != 12){
		throw new StrategyException("the size of Configuration should be 12.");
	}
	if (!StrategyGameFactory.canPieceOnValidPlace(redConfiguration, blueConfiguration, 5, 5, 1)){
		throw new StrategyException("the piece is not on valid place.");
	}
	  final StrategyGameController BetaStrategyGame = new BetaStrategyGameController(
			redConfiguration, blueConfiguration);
	return BetaStrategyGame;
	
	}
	
	/**
	 * Create a new Gamma Strategy game given the
	 * 
	 * @param redConfiguration
	 *            the initial starting configuration for the RED pieces
	 * @param blueConfiguration
	 *            the initial starting configuration for the BLUE pieces
	
	
	 * @return the Beta Strategy game instance with the initial configuration of
	 *         pieces  
	 * @throws StrategyException
	 *             if either configuration is correct * @throws StrategyException
	 */
	public static StrategyGameController makeGammaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
			throws StrategyException
	{
		if (redConfiguration == null || blueConfiguration == null){
		throw new StrategyException("Configuration cannot be null.");
		}
		
		if (redConfiguration.size() != 12){
			throw new StrategyException("the size of RedConfiguration should be 12");
		}
		if (blueConfiguration.size() != 12){
			throw new StrategyException("the size of BlueConfiguration should be 12.");
		}
		
		
		if (!StrategyGameFactory.canPieceOnValidPlace(
				redConfiguration, blueConfiguration, 5, 5, 1)){
			throw new StrategyException("the piece is not on valid place.");
		}
		final StrategyGameController GammmaStrategyGame = new GammaStrategyGameController(
				redConfiguration, blueConfiguration);
		return GammmaStrategyGame;
	}
	/**
	 * Create a new Delta Strategy game given the
	 * 
	 * @param redConfiguration
	 *            the initial starting configuration for the RED pieces
	 * @param blueConfiguration
	 *            the initial starting configuration for the BLUE pieces
	
	
	 * @return the Delta Strategy game instance with the initial configuration of
	 *         pieces  
	 * @throws StrategyException
	 *             if either configuration is correct * @throws StrategyException
	 */
	public static StrategyGameController makeDeltaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
			throws StrategyException
	{
		if (redConfiguration == null || blueConfiguration == null){
		throw new StrategyException("Configuration cannot be null.");
		}
		
		if (redConfiguration.size() != 40){
			throw new StrategyException("the size of RedConfiguration should be 40");
		}
		if (blueConfiguration.size() != 40){
			throw new StrategyException("the size of BlueConfiguration should be 40.");
		}
		
		
		if (!StrategyGameFactory.canPieceOnValidPlace(
				redConfiguration, blueConfiguration, 9, 9, 3)){
			throw new StrategyException("the piece is not on valid place.");
		}
		final StrategyGameController DeltaStrategyGame = new DeltaStrategyGameController(
				redConfiguration, blueConfiguration);
		return DeltaStrategyGame;
	}
	/**
	 * Create a new Epsilon Strategy game given the
	 * 
	 * @param redConfiguration
	 *            the initial starting configuration for the RED pieces
	 * @param blueConfiguration
	 *            the initial starting configuration for the BLUE pieces
	 * @param observers 
	 * 				the collection of the observer
	 *  
	 * @return the Beta Strategy game instance with the initial configuration of
	 *         pieces  
	 * @throws StrategyException
	 *             if either configuration is correct * @throws StrategyException
	 */
	public static StrategyGameController makeEpsilonStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration,
			Collection<StrategyGameObserver>observers)
			throws StrategyException
	{
		if (redConfiguration == null || blueConfiguration == null){
		throw new StrategyException("Configuration cannot be null.");
		}
		
		if (redConfiguration.size() != 40){
			throw new StrategyException("the size of RedConfiguration should be 40");
		}
		if (blueConfiguration.size() != 40){
			throw new StrategyException("the size of BlueConfiguration should be 40.");
		}
		
		
		if (!StrategyGameFactory.canPieceOnValidPlace(
				redConfiguration, blueConfiguration, 9, 9, 3)){
			throw new StrategyException("the piece is not on valid place.");
		}
		final StrategyGameController EpsilonStrategyGame = new EpsilonStrategyGameController(
				redConfiguration, blueConfiguration, observers);
		return EpsilonStrategyGame;
	}
	// helper method
	
	
	private static boolean canPieceOnValidPlace(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration, int row, int column, int line)
	{
		boolean isRedValid=true;
		boolean isBlueValid=true;
		PieceLocationDescriptor temp;
		 final Iterator<PieceLocationDescriptor> ItRed = redConfiguration.iterator();
		 final Iterator<PieceLocationDescriptor> ItBlue = blueConfiguration.iterator();
		while (ItRed.hasNext() && isRedValid)
		{
			temp = ItRed.next();
			if (temp.getLocation().getCoordinate(Coordinate.X_COORDINATE) < 0
					|| temp.getLocation()
							.getCoordinate(Coordinate.X_COORDINATE) > column
					|| temp.getLocation()
							.getCoordinate(Coordinate.Y_COORDINATE) < 0
					|| temp.getLocation()
							.getCoordinate(Coordinate.Y_COORDINATE) > line)
			{
				isRedValid = false;
			}
		}
		while (ItBlue.hasNext() && isBlueValid && isRedValid)
		{
			temp = ItBlue.next();
			if (temp.getLocation().getCoordinate(Coordinate.X_COORDINATE) < 0
					|| temp.getLocation()
							.getCoordinate(Coordinate.X_COORDINATE) > column
					|| temp.getLocation()
							.getCoordinate(Coordinate.Y_COORDINATE) < row - line
					|| temp.getLocation()
							.getCoordinate(Coordinate.Y_COORDINATE) > row)
			{
				isBlueValid = false;
			}

		}
		return isBlueValid && isRedValid;
	}

}