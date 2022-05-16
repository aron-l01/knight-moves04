package net.alur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class PathwayTests {
    private static IPathwayComputer ipc = new PathwayComputer();

    @Test
    void testNumPaths1() {
        int numPaths = NumPathsMatrix.getNumPaths(1, "A1", "C2");
        assertEquals(1, numPaths);
    }

    @Test
    void testPathwayComputer1() throws Exception {
        List<BoardSquare> pathway = ipc.computePathway(BoardSquare.at("A1"), BoardSquare.at("C2"));
        assertEquals(2, pathway.size());
    }

    @Test
    void testNumPaths2() {
        int numPaths = NumPathsMatrix.getNumPaths(2, "C3", "D4");
        assertEquals(2, numPaths);
    }

    @Test
    void testPathwayComputer2() throws Exception {
        List<BoardSquare> pathway = ipc.computePathway(BoardSquare.at("C3"), BoardSquare.at("D4"));
        assertEquals(3, pathway.size());
    }

    @Test
    void testPathwayComputer3() throws Exception {
        assertThrows(IllegalStateException.class, () -> ipc.computePathway(BoardSquare.at("A1"), BoardSquare.at("A1")));
    }

    @Test
    void testCorners() throws Exception {
        List<BoardSquare> pathway = ipc.computePathway(BoardSquare.at("A1"), BoardSquare.at("H8"));
        for (int i = 0; i < pathway.size() - 1; i++) {
            List<BoardSquare> pair = pathway.subList(i, i + 2);
            assertTrue(pair.get(0).reachableByKnight(pair.get(1)));
        }
    }

    @Test
    void testBoardLimits() {
        assertThrows(IndexOutOfBoundsException.class, () -> BoardSquare.at("I9"));
    }
}
