package de.orangestar.engine.tools.pathfinding;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.orangestar.engine.utils.Pair;

public class PathfindingTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testShortestPath() {
		_find = new AStarSearch();
		boolean[][] area = new boolean[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				area[i][j] = true;
			}
		}
		List<Pair<Integer, Integer>> testPath = new ArrayList<Pair<Integer,Integer>>();
		testPath.add(Pair.New(0, 0));
		testPath.add(Pair.New(1, 1));
		testPath.add(Pair.New(2, 2));
		testPath.add(Pair.New(3, 3));
		testPath.add(Pair.New(4, 4));
		testPath.add(Pair.New(5, 5));
		testPath.add(Pair.New(6, 5));
		List<Pair<Integer, Integer>> generatedPath = _find.findPath(area, 0, 0, 6, 5);
		
		assertEquals(testPath.size(), generatedPath.size());
		for(int i = 0; i < generatedPath.size(); i++) {
			System.out.println(generatedPath.get(i));
		}
		assertEquals(testPath.get(testPath.size()-1), generatedPath.get(generatedPath.size()-1));
		for(int i = 0; i < testPath.size(); i++) {
			assertEquals((testPath.get(i)), generatedPath.get(i));
		}

	}
	
	private Pathfinding _find;

}
