/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common;

/**
 * @author Yuchen Liu (yliu4) Xiaosong Wen (xwen2)
 * @version Oct, 16, 2013
 */
public interface StrategyGameObservable
{
	// Registers an observer
	/**
	 * This method add observer to collection
	 * @param observer the observer to add
	 */
	void register(StrategyGameObserver observer);
	
	// Removes an observer
	/**
	 * This method remove observer from collection
	 * @param observer the observer to remove
	 */
	void unregister(StrategyGameObserver observer);
}
