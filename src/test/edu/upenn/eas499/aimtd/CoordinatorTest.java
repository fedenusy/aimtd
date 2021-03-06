package test.edu.upenn.eas499.aimtd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.eas499.aimtd.*;

public class CoordinatorTest {

	private Map _map1, _map2;
	
	@Before
	public void setUp() {
		_map1 = new Map(new int[][]{	{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
										{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
										{0,1,0,1,1,1,1,1,1,1,1,1,1,1,2},
										{0,1,0,0,1,0,0,0,0,0,0,0,0,0,1},
										{0,1,0,0,1,0,0,0,0,0,0,1,1,1,1},
										{0,0,1,0,1,0,0,0,0,0,1,0,0,0,0},
										{0,1,0,0,1,0,0,0,0,0,1,1,1,0,0},
										{0,1,0,0,1,0,0,0,0,1,0,0,1,0,0},
										{0,1,0,0,1,0,0,0,0,0,1,1,1,1,0},
										{1,0,0,0,0,1,1,1,1,0,0,0,0,0,1},
										{0,1,0,0,0,1,0,0,1,0,0,0,0,1,0},
										{0,0,1,0,0,1,0,0,1,0,0,0,1,0,0},
										{0,1,0,0,0,1,0,0,1,0,0,1,0,1,0},
										{0,0,1,0,0,1,0,0,0,1,1,0,1,0,0},
										{0,0,0,1,1,1,0,0,0,0,0,0,0,0,0} });

		_map2 = new Map(new int[][]{	{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
										{0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0},
										{0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0},
										{0,0,1,0,1,1,1,1,1,1,1,1,1,0,0,0,0,1,0,0},
										{0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0},
										{0,0,0,0,1,1,1,1,0,0,0,0,1,0,0,0,0,1,0,0},
										{0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
										{0,0,0,8,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,2},
										{0,0,8,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
										{0,8,0,8,0,0,0,1,8,0,0,0,0,0,0,1,0,0,0,0},
										{0,0,0,0,1,1,1,1,8,0,0,0,0,0,0,1,1,1,1,0},
										{0,0,0,0,1,0,0,1,1,1,1,0,0,0,0,0,0,0,1,0},
										{0,0,0,1,1,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0},
										{0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
										{0,0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
										{0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,1,0},
										{0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
										{0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0},
										{0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
										{0,2,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2} });
	}

	@Test
	public void testShortestPath() {
		Coordinator coordinator = new Coordinator(_map1);
		
		Monster monster1 = new MonsterImpl(12, 2, 75, 100, 1);
		Monster monster2 = new MonsterImpl(11, 2, 75, 200, 1);
		coordinator.addMonster(monster1);
		coordinator.addMonster(monster2);
		
		coordinator.tick();
		assertTrue(monster1.getRoundedX() == 13); assertTrue(monster1.getRoundedY() == 2);
		assertTrue(monster2.getRoundedX() == 13); assertTrue(monster2.getRoundedY() == 2);
		
		Monster monster3 = new MonsterImpl(11, 6, 75, 150, 1);
		Monster monster4 = new MonsterImpl(12, 8, 75, 230, 1);
		coordinator.addMonster(monster3);
		coordinator.addMonster(monster4);
		
		coordinator.tick();
		assertTrue(monster1.getRoundedX() == 14); assertTrue(monster1.getRoundedY() == 2);
		assertTrue(monster1.reachedObjective());
		assertTrue(monster2.getRoundedX() == 14); assertTrue(monster2.getRoundedY() == 2);
		assertTrue(monster2.reachedObjective());
		assertTrue(monster3.getRoundedX() == 10); assertTrue(monster3.getRoundedY() == 5);
		assertFalse(monster3.reachedObjective());
		assertTrue(monster4.getRoundedX() == 11); assertTrue(monster4.getRoundedY() == 6);
		assertFalse(monster4.reachedObjective());
	}

	@Test
	public void testShortestPathWaypoints() {
		Coordinator coordinator = new Coordinator(_map2);
		
		Monster monster1 = new MonsterImpl(3, 12, 75, 10, 1);
		monster1.addWaypoint(4, 11);
		monster1.addWaypoint(5, 9);
		monster1.addWaypoint(5, 10);
		coordinator.addMonster(monster1);
		
		for (int i=0; i<14; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 4); assertTrue(monster1.getRoundedY() == 11);
		
		for (int i=0; i<25; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 5); assertTrue(monster1.getRoundedY() == 10);
		
		for (int i=0; i<500; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 1); assertTrue(monster1.getRoundedY() == 19);
		assertTrue(monster1.reachedObjective());
	}
	
	@Test
	public void testSurvivalPathNoDamage() {
		Coordinator coordinator = new Coordinator(_map2);
		
		Monster monster1 = new MonsterImpl(3, 12, 75, 10, 2);
		monster1.addWaypoint(4, 11);
		monster1.addWaypoint(5, 9);
		monster1.addWaypoint(5, 10);
		coordinator.addMonster(monster1);
		
		Tower tower1 = new TowerImpl(0, 0, 20, 100, 1);
		coordinator.addTower(tower1);
		
		for (int i=0; i<14; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 4); assertTrue(monster1.getRoundedY() == 11);
		
		for (int i=0; i<25; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 5); assertTrue(monster1.getRoundedY() == 10);
		
		for (int i=0; i<500; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 1); assertTrue(monster1.getRoundedY() == 19);
		assertTrue(monster1.reachedObjective());
	}
	
	@Test
	public void testSurvivalPath() {
		Coordinator coordinator = new Coordinator(_map2);
		
		Monster monster1 = new MonsterImpl(3, 12, 75, 10, 2);
		monster1.addWaypoint(4, 11);
		monster1.addWaypoint(5, 9);
		monster1.addWaypoint(5, 10);
		coordinator.addMonster(monster1);
		
		Tower tower1 = new TowerImpl(2, 17, 20, 100, 1);
		coordinator.addTower(tower1);
		
		for (int i=0; i<14; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 4); assertTrue(monster1.getRoundedY() == 11);
		
		for (int i=0; i<25; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 5); assertTrue(monster1.getRoundedY() == 10);
		
		for (int i=0; i<1000; i++) coordinator.tick();
		assertTrue(monster1.getRoundedX() == 19); assertTrue(monster1.getRoundedY() == 19);
		assertTrue(monster1.reachedObjective());
	}
	
}
