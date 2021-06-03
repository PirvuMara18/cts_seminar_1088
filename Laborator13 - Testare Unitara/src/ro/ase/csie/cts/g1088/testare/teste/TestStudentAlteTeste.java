package ro.ase.csie.cts.g1088.testare.teste;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import ro.ase.csie.cts.g1088.testare.exceptii.ExceptieNota;
import ro.ase.csie.cts.g1088.testare.exceptii.ExceptieVarsta;
import ro.ase.csie.cts.g1088.testare.modele.Student;
import ro.ase.csie.cts.g1088.testare.teste.categorii.TesteImportante;

public class TestStudentAlteTeste {
	
	static Student student;
	static ArrayList<Integer> noteInitiale = new ArrayList<>();
	static ArrayList<Integer> noteRandom ;
	static String numeInitial;
	static int varstaInitiala;
	static int nrNoteInitiale;

	//o data la inceput
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		numeInitial = "Gigel";
		varstaInitiala = Student.MAX_VARSTA + 1;
		nrNoteInitiale = 3;
		for(int i = 0; i < nrNoteInitiale; i++) {
			noteInitiale.add(Student.MAX_NOTA - i);
		}
		
	    noteRandom = new ArrayList<>();
		int nrNote = (int) 1e6; //10^6
		Random random = new Random();
		for(int i = 0; i < nrNote; i++) {
			noteRandom.add(random.nextInt(Student.MAX_NOTA)+1);
		
		}
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//inainte de fiecare test
	@Before
	public void setUp() throws Exception {
		student = new Student(numeInitial, varstaInitiala, noteInitiale);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test(expected = ExceptieVarsta.class)
	public void testSetVarstaRangeMinValue() throws ExceptieVarsta {
		int varstaNoua = Integer.MIN_VALUE;
		student.setVarsta(varstaNoua);
		
	}
	
	
	@Test(expected = ExceptieVarsta.class)
	public void testSetVarstaRangeValoarePozitivaMare() throws ExceptieVarsta {
		int varstaNoua = 200;
		student.setVarsta(varstaNoua);
		
	}
	
	@Test
	public void testSetVarstaBoundaryLimitaInferioara() throws ExceptieVarsta {
		int varstaNoua = Student.MIN_VARSTA;
		student.setVarsta(varstaNoua);
		int varstaStudent = student.getVarsta();
		assertEquals("Test pentru varsta minima", varstaNoua, varstaStudent);
	
	}
	
	@Test
	public void testSetVarstaBoundaryLimitaSuperioara() throws ExceptieVarsta {
		int varstaNoua = Student.MAX_VARSTA;
		student.setVarsta(varstaNoua);
		int varstaStudent = student.getVarsta();
		assertEquals("Test pentru varsta maxima", varstaNoua, varstaStudent);
	
	}
	
	@Test
	public void testSetNoteReferenceShallowCopy() throws ExceptieNota {
		int[] noteStudent = new int[] {9,9,10};
		ArrayList<Integer> refNote = new ArrayList<>(Arrays.asList(9,9,10));
		
		
		student.setNote(refNote);
		refNote.set(0, 5);
		
		int noteExistente[] = new int[student.getNrNote()];
		for(int i = 0; i< student.getNrNote(); i++) {
			noteExistente[i] = student.getNota(i);
		}
		
		assertArrayEquals("Test shallow copy pe setNote", noteStudent, noteExistente);
		
		
	}
	
	//junit3
	@Test
	public void testGetMediePerformance() throws ExceptieNota {
		
		ArrayList<Integer> note = new ArrayList<>();
		int nrNote = (int) 1e6; //10^6
		Random random = new Random();
		for(int i = 0; i < nrNote; i++) {
			note.add(random.nextInt(Student.MAX_NOTA)+1);
		
		}
		
		student.setNote(note);
		long tStart = System.currentTimeMillis();
		student.getMedie();
		long tFinal = System.currentTimeMillis();
		
		long durata = tFinal - tStart;
		
		if(durata <= 20) {
			assertTrue(true);
		}
		
		else {
			fail("Calculul mediei dureaza mai mult de 10 milisecunde");
		}
		
	}
	
	//junit4
	
	@Test(timeout = 15)
	public void getTestMediePerformance2() throws ExceptieNota{
		
		
		student.setNote(noteRandom);
		student.getMedie();
		
		
	}
	
	
	//inverse
	
	@Test
	public void testSetVarstaInverse() throws ExceptieVarsta {
		int varstaNoua = 22;
		student.setVarsta(varstaNoua);
		assertNotEquals("Set u modifica valoarea atributului", varstaInitiala, student.getVarsta());
	}
	
	@Category(TesteImportante.class)

	//inverse
	@Test
	public void testGetNotaMinimaInverse() throws ExceptieNota {
		ArrayList<Integer> note = new ArrayList<>();
		Random random = new Random();
		note.add(random.nextInt(Student.MAX_NOTA)+1);
		note.add(random.nextInt(Student.MAX_NOTA)+1);
		note.add(random.nextInt(Student.MAX_NOTA)+1);
		
		student.setNote(note);
		
		int notaMinima = student.getNotaMinima();
		for(int i = 0; i< student.getNrNote(); i ++) {
			if(notaMinima >student.getNota(i)) {
				fail("Minimul nu este calculat corect");
			}
		}
		
		assertTrue(true);
		
		
	}
	
	@Category(TesteImportante.class)

	@Test 
	public void testGetNotaMinimaCrossCheck() throws ExceptieNota {
		ArrayList<Integer> note = new ArrayList<>();
		Random random = new Random();
		note.add(random.nextInt(Student.MAX_NOTA)+1);
		note.add(random.nextInt(Student.MAX_NOTA)+1);
		note.add(random.nextInt(Student.MAX_NOTA)+1);
		
		student.setNote(note);
		
		int notaMinima = Collections.min(note);
		int notaMinimaCalculata = student.getNotaMinima();
		
		assertEquals("Nota minima nu este ok", notaMinima, notaMinimaCalculata);
	}
	
	

}
