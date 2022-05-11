package net.alur;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ejml.simple.SimpleMatrix;

public class NumPathsMatrix {

    private static BoardSquare[] squaresList = null;
    private static double[][] initialMatrix = null;
    private static ConcurrentMap<Integer, SimpleMatrix> nthPowerMatrix = null;

    private static BoardSquare[] getSquaresList() {
        if (squaresList == null) {
            List<BoardSquare> computedSquares = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    computedSquares.add(new BoardSquare((char) ((int) 'A' + i), 1 + j));
                }
            }
            squaresList = computedSquares.toArray(new BoardSquare[0]);
        }
        return squaresList;
    }

    private static int getIndex(BoardSquare sq) {
        return (((int) sq.getLetter() - (int) 'A') * 8) + (sq.getNumber() - 1);
    }

    private static double[][] getInitialMatrix() {
        if (initialMatrix == null) {
            double[][] computedMatrix = new double[64][64];
            int i = 0;
            for (BoardSquare from : getSquaresList()) {
                int j = 0;
                for (BoardSquare to : getSquaresList()) {
                    if (from.knightMoves().contains(to)) {
                        computedMatrix[i][j] = 1d;
                    } else {
                        computedMatrix[i][j] = 0d;
                    }
                    j++;
                }
                i++;
            }
            initialMatrix = computedMatrix;
        }
        return initialMatrix;
    }

    private static SimpleMatrix getNthPowerMatrix(int n) {
        if (nthPowerMatrix == null) {
            nthPowerMatrix = new ConcurrentHashMap<>();
        }
        SimpleMatrix firstPower = nthPowerMatrix.computeIfAbsent(1, a -> new SimpleMatrix(getInitialMatrix()));
        return nthPowerMatrix.computeIfAbsent(n, a -> getNthPowerMatrix(a - 1).mult(firstPower));
    }

    public static int getNumPaths(int pathsLength, BoardSquare from, BoardSquare to) {
        return (int) getNthPowerMatrix(pathsLength).get(getIndex(from), getIndex(to));
    }

    public static int getNumPaths(int pathsLength, String from, String to) {
        return getNumPaths(pathsLength, new BoardSquare(from), new BoardSquare(to));
    }
}