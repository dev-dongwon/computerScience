package step2ComputerSimulator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CPUTest {
	
	// test 계획 필요
	
	@Test
	public void loadTest() {
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
		int[] baseReg = {0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0};
		int[] offsetReg = {0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0};
		
		int[] data = {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};

		// LOAD, R1, R2, R3 => R2 + R3 의 주소 메모리값을 읽어서 R1로 로드
		int[] instructionBit = {0,0,0,1,0,0,1,0,1,0,0,0,0,0,1,1};
		
		cpu.memory.getMEMORY_MODEL()[1][30] = data;
		cpu.register.R2 = baseReg;
		cpu.register.R3 = offsetReg;
		
		cpu.execute(instructionBit);
		assertArrayEquals(data, cpu.register.R1);
	}
	
	@Test
	public void storeTest() {
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
		int[] baseReg = {0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0};
		int[] offsetReg = {0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0};
		
		int[] data = {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};

//		STORE R5, R3, R4 ==> 0011 101 011 000 100 => R5 값을 R3 + R4 메모리 어드레스에 저장
		int[] instructionBit = {0,0,1,1,1,0,1,0,1,1,0,0,0,1,0,0};
		
		cpu.register.R3 = baseReg;
		cpu.register.R4 = offsetReg;
		cpu.register.R5 = data;
		
		cpu.execute(instructionBit);
		assertArrayEquals(data, cpu.memory.getMEMORY_MODEL()[1][30]);
	}
	
	@Test
	public void andTest() {
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
		int[] case1 = {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};
		int[] case2 = {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};
		int[] expectedResult = {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};
		
//		AND R3, R1, R6 ==> 0101 011 001 000 110 => R3 = R1 && R6 
		cpu.register.R1 = case1;
		cpu.register.R6 = case2;
		
		int[] instructionBit = {0,1,0,1,0,1,1,0,0,1,0,0,0,1,1,0};
		cpu.execute(instructionBit);
		
		assertArrayEquals(expectedResult, cpu.register.R3);
	}
	
	@Test
	public void orTest() {
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
		int[] case1 = {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};
		int[] case2 = {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1};
		int[] expectedResult = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		
//		OR R3, R1, R6 ==> 0110 011 001 000 110 => R3 = R1 || R6 
		cpu.register.R1 = case1;
		cpu.register.R6 = case2;
		
		int[] instructionBit = {0,1,1,0,0,1,1,0,0,1,0,0,0,1,1,0};
		cpu.execute(instructionBit);
		
		assertArrayEquals(expectedResult, cpu.register.R3);
	}
	
	@Test
	public void ADDTest() {
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
		int[] case1 = {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};
		int[] case2 = {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1};
		int[] expectedResult = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		
//		ADD R4, R2, R5 ==> 0111 100 010 000 101 => R4 = R2 + R5;
		cpu.register.R2 = case1;
		cpu.register.R5 = case2;
		
		int[] instructionBit = {0,1,1,1,1,0,0,0,1,0,0,0,0,1,0,1};
		cpu.execute(instructionBit);

		assertArrayEquals(expectedResult, cpu.register.R4);
	}
	
	@Test
	public void SUBTest() {
		
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
		int[] case1 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		int[] case2 = {0,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0};
		int[] expectedResult = {1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1};

//		SUB R4, R2, R5 ==> 1010 100 010 000 101 => R4 = R2 - R5;
		int[] instructionBit = {1,0,1,0,1,0,0,0,1,0,0,0,0,1,0,1};

		cpu.register.R2 = case1;
		cpu.register.R5 = case2;
		
		cpu.execute(instructionBit);

		assertArrayEquals(expectedResult, cpu.register.R4);
	}
	
	@Test
	public void MOVTest() {
	
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
//		MOV R4, #250 ==> 1011 100 011111010
		int[] instructionBit = {1,0,1,1,1,0,0,0,1,1,1,1,1,0,1,0};
		int[] expectedResult = {0,0,0,0,0,0,0,0,1,1,1,1,1,0,1,0};
		cpu.execute(instructionBit);
		
		System.out.println(Arrays.toString(cpu.register.R4));
		assertArrayEquals(expectedResult, cpu.register.R4);
		
	}
	
	@Test
	public void resetTest() {

		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
		int[] data = {1,0,1,1,1,0,0,0,1,1,1,1,1,0,1,0};

		cpu.register.PC = 4;
		cpu.register.R1 = data;
		cpu.register.R2 = data;
		cpu.register.R3 = data;
		cpu.register.R4 = data;
		cpu.register.R5 = data;
		cpu.register.R6 = data;
		cpu.register.R7 = data;
		
		cpu.reset();
		int[] expectedArr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		assertEquals(0, cpu.register.PC);
		assertArrayEquals(cpu.register.R1, expectedArr);
		assertArrayEquals(cpu.register.R2, expectedArr);
		assertArrayEquals(cpu.register.R3, expectedArr);
		assertArrayEquals(cpu.register.R4, expectedArr);
		assertArrayEquals(cpu.register.R5, expectedArr);
		assertArrayEquals(cpu.register.R6, expectedArr);
		assertArrayEquals(cpu.register.R7, expectedArr);
		
	}
	
	@Test
	public void fetchTest() {
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);
		
		cpu.register.PC = 20;
		int[] data = {1,0,1,1,1,0,0,0,1,1,1,1,1,0,1,0};
		
		cpu.memory.getMEMORY_MODEL()[0][20] = data;
		
		assertArrayEquals(data, cpu.fetch());
	}
	
	@Test
	public void dumpTest() {
		Memory memory = new Memory();
		CPU cpu = new CPU(memory);

		int[] data1 = {1,0,1,1,1,0,0,0,1,1,1,1,1,0,1,0};
		int[] data2 = {1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1};

		cpu.register.R1 = data1;
		cpu.register.R2 = data2;
		cpu.register.R3 = data1;
		cpu.register.R4 = data2;
		cpu.register.R5 = data1;
		cpu.register.R6 = data2;
		cpu.register.R7 = data1;
		
		int[][] expectedRegisters = {cpu.register.R1, cpu.register.R2, cpu.register.R3, cpu.register.R4, cpu.register.R5, cpu.register.R6, cpu.register.R7};
		
		assertArrayEquals(expectedRegisters, cpu.dump());

	}
}
