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
public class oldversion implements VersionNeedInitializeRank
{

	@Override
	public Map<PieceType, Integer> InitializeRank()
	{
			final Hashtable<PieceType, Integer> Rank = new Hashtable<PieceType, Integer>();
			Rank.put(PieceType.MARSHAL, 12);
			Rank.put(PieceType.COLONEL, 10);
			Rank.put(PieceType.CAPTAIN, 8);
			Rank.put(PieceType.LIEUTENANT, 7);
			Rank.put(PieceType.SERGEANT, 6);
			return Rank;
	}

}
