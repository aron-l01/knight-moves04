package net.alur;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Getters, setters, hashCode, toString, and equals functions were auto-generated
 *
 */
public class BoardSquare {
	private char letter;
	private int number;
	
	public BoardSquare(String loc) throws IndexOutOfBoundsException {
		this.letter = loc.charAt(0);
		this.number = Integer.valueOf(loc.substring(1));
		
		
		if (letter < 'A' || letter > 'H') {
			throw new IndexOutOfBoundsException();
		}
		
		if (number < 1 || number > 8) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public BoardSquare(char i, int j) throws IndexOutOfBoundsException {
		this("" + i + j);
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + letter;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardSquare other = (BoardSquare) obj;
		if (letter != other.letter)
			return false;
		if (number != other.number)
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "BoardSquare [letter=" + letter + ", number=" + number + "]";
	}
	
	private BoardSquare getSquareFromOffset(int x, int y) {
		try {
			return new BoardSquare((char) ((int) letter + x), number + y);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private int[][] knightOffsets = {
			{ 2, 1 },
			{ 2, -1 },
			{ -2, 1 },
			{ -2, -1 },
			{ 1, 2 },
			{ 1, -2 },
			{ -1, 2 },
			{ -1, -2 }
	};

	public Set<BoardSquare> knightMoves() {
		Set<BoardSquare> validKnightMoves = new HashSet<>();
		
		for (int[] offset : knightOffsets) {
			BoardSquare hop = getSquareFromOffset(offset[0], offset[1]);
			if (hop != null) validKnightMoves.add(hop);
		}
		 
		return validKnightMoves;
	}
}
