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
		Graph graph = new Graph(area.length, area[0].length);
		List<SingleNode> testPath = new ArrayList<SingleNode>();
		testPath.add(new SingleNode(new Pair<Integer, Integer>(0, 0), graph));
		testPath.add(new SingleNode(new Pair<Integer, Integer>(1, 1), graph));
		testPath.add(new SingleNode(new Pair<Integer, Integer>(2, 2), graph));
		testPath.add(new SingleNode(new Pair<Integer, Integer>(3, 3), graph));
		testPath.add(new SingleNode(new Pair<Integer, Integer>(4, 4), graph));
		testPath.add(new SingleNode(new Pair<Integer, Integer>(5, 5), graph));
		testPath.add(new SingleNode(new Pair<Integer, Integer>(6, 5), graph));
		testPath.add(new SingleNode(new Pair<Integer, Integer>(7, 5), graph));
		testPath.add(new SingleNode(new Pair<Integer, Integer>(8, 5), graph));
		List<Node> generatedPath = _find.findPath(area, 0, 0, 8, 5);
		
		assertEquals(testPath.size(), generatedPath.size());
		assertEquals(testPath.get(testPath.size()-1), generatedPath.get(generatedPath.size()-1));
		for(int i = 0; i < testPath.size(); i++) {
			assertEquals((testPath.get(i)), generatedPath.get(i));
		}

	}
	
	@Test
	public void invalidPath() {
		_find = new AStarSearch();
		boolean[][] area = new boolean[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				area[i][j] = true;
			}
		}
		area[1][1] = false;
		area[5][5] = false;
		assertEquals(null, _find.findPath(area, 1, 1, 4, 4));
		assertEquals(null, _find.findPath(area, 1, 2, 5, 5));
	}
	
	@Test
	public void testStartIsDestination() {
		_find = new AStarSearch();
		boolean[][] area = new boolean[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				area[i][j] = true;
			}
		}
		
		List<Node> generatedPath = _find.findPath(area, 1, 1, 1, 1);
		assertEquals(1, generatedPath.size());
		assertEquals(new Pair<Integer, Integer>(1, 1), generatedPath.get(0).getNodeID());
	}
	
	private Pathfinding _find;

}
