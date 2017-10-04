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

import java.util.Hashtable;
import java.util.Map;

import strategy.game.common.PieceType;

/**
 * @author Yuchen Liu (yliu4) Xiaosong Wen (xwen2)
 * @version Oct, 16, 2013
 */
public class deltaDecorator extends Decorator
{

	public deltaDecorator(VersionNeedInitializeRank version)
	{
		super(version);
		
	}
	public  Map<PieceType,Integer> InitializeRank(){
		Map<PieceType, Integer> Rank = new Hashtable<PieceType, Integer>();
		Rank = version.InitializeRank();
		Rank.put(PieceType.GENERAL, 11);
		Rank.put(PieceType.MAJOR, 9);
		Rank.put(PieceType.MINER, 5);
		Rank.put(PieceType.SCOUT, 4);
		Rank.put(PieceType.SPY, 3);
		return Rank;
	}

}
