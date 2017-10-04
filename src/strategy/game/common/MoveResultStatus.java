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
 * This is an enumeration of the possible outcomes of any legal move in Strategy.
 * @author gpollice
 * @version Aug 31, 2013
 */
public enum MoveResultStatus
{
	OK, BLUE_WINS, RED_WINS, DRAW, FLAG_CAPTURED;
}
