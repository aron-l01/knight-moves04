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
        if (start.equals(destination)) {
            throw new IllegalStateException("Cannot compute a path of length zero");
        }

        // Breadth-first search
        Map<BoardSquare, BoardSquare> breadcrumbs = new HashMap<>();
        Queue<BoardSquare> toInspect = new LinkedList<>();
        Set<BoardSquare> visited = new HashSet<>();

        toInspect.add(start);

        while (toInspect.size() > 0) {
            BoardSquare currentSquare = toInspect.poll();
            visited.add(currentSquare);

            Set<BoardSquare> possibleMoves = currentSquare.knightMoves();
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
}