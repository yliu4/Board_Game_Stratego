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
 * @author Yuchen Liu (yliu4) & Xiaosong Wen (xwen2)
 * @version Sept 22, 2013
 */
public class PieceStep
{
private final int NumOfStep;
private final Piece MovePiece;
private final Location from;
private final Location to;
public int getNumOfStep()
{
	return NumOfStep;
}

/**
 * Constructor for PieceStep.
 * @param numOfStep int
 * @param movePiece Piece
 * @param from Location
 * @param to Location
 */
public PieceStep(int numOfStep, Piece movePiece, Location from, Location to)
{
	
	NumOfStep = numOfStep;
	MovePiece = movePiece;
	this.from = from;
	this.to = to;
}

public Piece getMovePiece()
{
	return MovePiece;
}

public Location getFrom()
{
	return from;
}

public Location getTo()
{
	return to;
}


}
