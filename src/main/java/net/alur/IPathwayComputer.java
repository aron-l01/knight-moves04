package net.alur;

import java.util.List;

public interface IPathwayComputer {
	public List<BoardSquare> computePathway(BoardSquare origin, BoardSquare destination) throws Exception;
}
