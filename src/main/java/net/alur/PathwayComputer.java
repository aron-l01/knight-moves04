package net.alur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class PathwayComputer implements IPathwayComputer {

    @Override
    public List<BoardSquare> computePathway(BoardSquare start, BoardSquare destination) throws IllegalStateException {

        // Breadth-first search
        Map<BoardSquare, BoardSquare> breadcrumbs = new HashMap<>();
        Queue<BoardSquare> toInspect = new LinkedList<>();
        Set<BoardSquare> visited = new HashSet<>();

        toInspect.add(start);

        while (toInspect.size() > 0) {
            BoardSquare currentSquare = toInspect.poll();
            visited.add(currentSquare);

            Set<BoardSquare> possibleMoves = getPossibleMoves(currentSquare);
            possibleMoves.removeAll(visited);
            for (BoardSquare possibleMove : possibleMoves) {
                toInspect.add(possibleMove);
                breadcrumbs.put(possibleMove, currentSquare);

                if (possibleMove.equals(destination)) {
                    return constructAnswer(destination, breadcrumbs);
                }
            }

        }

        // This function should always return from within the BFS inner loop
        throw new IllegalStateException("Path not found where one should exist");
    }

    private List<BoardSquare> constructAnswer(BoardSquare destination, Map<BoardSquare, BoardSquare> breadcrumbs) {
        List<BoardSquare> foundPath = new ArrayList<>(Arrays.asList(destination));

        while (breadcrumbs.containsKey(destination)) {
            destination = breadcrumbs.get(destination);
            foundPath.add(destination);
        }

        return foundPath;
    }

    // If we wanted to adjust this algorithm to use e.g. a queen instead of a
    // knight, we could alter this function
    private Set<BoardSquare> getPossibleMoves(BoardSquare currentSquare) {
        return currentSquare.knightMoves();
    }
}