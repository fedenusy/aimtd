package test.edu.upenn.eas499.aimtd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.eas499.aimtd.*;

public class CoordinatorTest {

	private Coordinator coordinator1;
	
	@Before
	public void setUp() throws Exception {
		int[][] layout15x15 = 
				new int[][]{	{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
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
								{0,0,0,1,1,1,0,0,0,0,0,0,0,0,0} };
		
		Map map1 = new Map(layout15x15);
		coordinator1 = new Coordinator(map1, 1);
	}

	@Test
	public void testIntLevel1() {
		MonsterImpl monster1 = new MonsterImpl(12, 2, 75, 100);
		MonsterImpl monster2 = new MonsterImpl(11, 2, 75, 200);
		coordinator1.addMonster(monster1);
		coordinator1.addMonster(monster2);
		
		coordinator1.tick();
		assertTrue(monster1.getX() == 13); assertTrue(monster1.getY() == 2);
		assertTrue(monster2.getX() == 13); assertTrue(monster2.getY() == 2);
		
		MonsterImpl monster3 = new MonsterImpl(11, 6, 75, 150);
		MonsterImpl monster4 = new MonsterImpl(12, 8, 75, 230);
		coordinator1.addMonster(monster3);
		coordinator1.addMonster(monster4);
		
		coordinator1.tick();
		assertTrue(monster1.getX() == 14); assertTrue(monster1.getY() == 2);
		assertTrue(monster1.reachedObjective());
		assertTrue(monster2.getX() == 14); assertTrue(monster2.getY() == 2);
		assertTrue(monster2.reachedObjective());
		assertTrue(monster3.getRoundedX() == 10); assertTrue(monster2.getRoundedY() == 5);
		assertFalse(monster3.reachedObjective());
		assertTrue(monster3.getRoundedX() == 11); assertTrue(monster2.getRoundedY() == 6);
		assertFalse(monster3.reachedObjective());
	}

}