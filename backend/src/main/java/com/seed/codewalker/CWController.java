package com.seed.codewalker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CWController {
	
//	@GetMapping("/compile-java")
	private List<String> compile() {
		return runCommand("javac Main.java");
	}
	
//	@GetMapping("/run-java")
	private List<String> run() {
		return runCommand("java Main");
	}
	
	private List<String> runCommand(String cmd) {
		List<String> out = new ArrayList<>();
		int errors = 0;
		
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			String s = null;

			while ((s = stdIn.readLine()) != null) {
				out.add(s);
			}
			while ((s = stdErr.readLine()) != null) {
				out.add(s);
				errors++;
			}
			if (cmd.equals("java Main") && errors == 0) out.add("Code run successfully!");
			else if (cmd.equals("javac Main.java") && errors == 0) out.add("Code compiled successfully!");
			return out;
		}
		catch (IOException e) {
			e.printStackTrace();
			out.add("Command " + cmd + " failed.");
			return out;
		}
	}
	
	
	
	@GetMapping("/get-question-data/{questionId}")
	private List<String> sendQuestionData(@PathVariable String questionId) {
		List<String> toSend = new ArrayList<>();
		
		if (questionId.equals("wk11criiiqs1")) {
			toSend.add("To answer this question you should use (and modify) the Java code you wrote for worksheet 8. \n Implement the Scale problem fitness function using the following function prototype: \n  \n public static double ScalesFitness(ArrayList<Boolean> rep, ArrayList<Double> weights) { \n  \n } \n  \n Here ‘rep’ is an ArrayList of Boolean values where a true value represents the corresponding weight being on the left hand side and a false value represents the weight being on the right hand side. ‘weights’ are the corresponding set of weights. \n Test #1: 2 marks \n  \n The method should return -100 under the following conditions: \n 1) If either ‘rep’ or ‘weights’ is of size zero or if either is null. \n 2) If the size of ‘rep’ is bigger than the number of weights. \n Test #2: 2 marks");  // question text
			toSend.add("    ScalesFitness(arg1, arg2);");  // method call
		}
		else if (questionId.equals("wk11criiiqs2")) {
			toSend.add("To answer this question you should use (and modify) the Java code you wrote for worksheet 8. \n  \n Write a method that creates a random binary (ones and zeros) string of length n. \n (Test#1: 1 mark). \n  \n If the input size (‘n’) is less than or equal to zero then the method should return an empty string. \n (Test #2: 1 mark). \n  \n Implement the method using the following function prototype: \n  \n public static String RandomBinaryString(int n){ \n  \n }");
			toSend.add("    RandomBinaryString(arg1);");
		}
		
		return toSend;
	}
	
	
	@PostMapping("/{questionId}/test-answer")
	private List<String> testSolution(@PathVariable String questionId, @RequestBody String code) {		
		System.out.println(code);
		
		if (questionId.equals("wk11criiiqs1")) {
			code = wk11criiiqs1Modify(code);
		}
		else if (questionId.equals("wk11criiiqs2")) {
			code = wk11criiiqs2Modify(code);
		}
		
		
		// write code to Main.java
		File file = new File("Main.java");
		if (file.exists()) {
			file.delete();
		}
		
		String fileContents = code;
		
		try {
			file.createNewFile();
			
			var pw = new PrintWriter(file);
			pw.println(fileContents);
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		List<String> compilationList = compile();
		file.delete();
		List<String> runList = run();
		file = new File("Main.class");
		file.delete();
		
		
		List<String> toSend = new ArrayList<>(compilationList);
		toSend.addAll(runList);
		
//		String doNotInclude1 = "private static void runUserMethod(){int[] testData ={20, 10, 5, 3, 2, 1, 0, -1, -2, -10};mainloop: for (int n: testData){String output = RandomBinaryString(n);if (n <= 0){if (output.length() == 0) System.out.println(\"Test passed.\");else System.out.println(\"Test failed. On argument \" + n + \". Should return empty string.\");}else{if (output.length() != n){System.out.println(\"Test failed. String length is not equal to n.\");continue;}for (char c: output.toCharArray()){if (c != '0' && c != '1'){System.out.println(\"Test failed. String should contain only ones and zeroes.\");continue mainloop;}}System.out.println(\"Test passed.\");}}}public static void main(String[] args){runUserMethod();}}";
//		String doNotInclude2 = "public static void runUserMethod(){Boolean[][] testData1 ={{true, true, true, false, true},{true, true, false, false, true},{true, true, true, true, true},{false, false, false, false, false},{false, true, true, true, false},{true, false, true, false, true, false, true, false},{true, false, true, false, true, false, true, false},{true, false, true, false, true, false, true, false},{true, false},{false, true},{false, false},{true, true},{true},{false},{},				null,{true},{true},{true, false},{true, false, true, false},{true, false, true, false},{true}};Double[][] testData2 ={{1.0, 2.0, 3.0, 4.0, 10.0},{1.0, 2.0, 3.0, 4.0, 10.0},{1.0, 2.0, 3.0, 4.0, 10.0},{1.0, 2.0, 3.0, 4.0, 10.0},{1.0, 2.0, 3.0, 4.0, 10.0},{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},{10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0},{10.0, 10.0, 10.0, 20.0, 10.0, 10.0, 10.0, 10.0},{5.0, 7.0},{5.0, 7.0},{5.0, 7.0},{5.0, 7.0},{1.0},{1.0},{1.0},{1.0},{},				null,{1.0},{1.0, 2.0},{1.0, 2.0, 3.0, 4.0, 5.0, 6.0},{1.0, 2.0}};double[] expectedValues ={12.0, 6.0, 20.0, 20.0, 2.0, 0.0, 0.0, 10.0, 2.0, 2.0, 12.0, 12.0, 1.0, 1.0, -100.0, -100.0, -100.0, -100.0, -100.0, -100.0, 2.0, 1.0};for (int i=0;i<expectedValues.length;i++){ArrayList<Boolean> testArgsBool = new ArrayList<Boolean>();ArrayList<Double> testArgsDouble = new ArrayList<Double>();if (testData1[i] == null){Collections.addAll(testArgsDouble, testData2[i]);double output = ScalesFitness(null, testArgsDouble);if (output == expectedValues[i]){System.out.println("Test passed.");}else{System.out.println("Test failed. On arguments " + null + " & " + testArgsDouble + ". Expected value: " + expectedValues[i] + ", Your value: " + output);}}else if (testData2[i] == null){Collections.addAll(testArgsBool, testData1[i]);double output = ScalesFitness(testArgsBool, null);if (output == expectedValues[i]){System.out.println("Test passed.");}else{System.out.println("Test failed. On arguments " + testArgsBool + " & " + null + ". Expected value: " + expectedValues[i] + ", Your value: " + output);}}else{Collections.addAll(testArgsBool, testData1[i]);Collections.addAll(testArgsDouble, testData2[i]);double output = ScalesFitness(testArgsBool, testArgsDouble);if (output == expectedValues[i]){System.out.println("Test passed.");}else{System.out.println("Test failed. On arguments " + testArgsBool + " & " + testArgsDouble + ". Expected value: " + expectedValues[i] + ", Your value: " + output);}}}}public static void main(String[] args){runUserMethod();}}";
		
		for (int i=0; i<toSend.size(); i++) {
			if (toSend.get(i).contains("runUserMethod")) {
				toSend.remove(i);
			}
		}
		
		return toSend;
	}
	
	private String wk11criiiqs2Modify(String code) {
		String add1 = "import java.util.Collections;import java.util.List;import java.util.ArrayList;import java.util.Random;";
		
		String add2 = uglify("	private static void runUserMethod() {\r\n"
				+ "		int[] testData = {20, 10, 5, 3, 2, 1, 0, -1, -2, -10};\r\n"
				+ "		mainloop: for (int n: testData) {\r\n"
				+ "			String output = RandomBinaryString(n);\r\n"
				+ "			if (n <= 0) {\r\n"
				+ "				if (output.length() == 0) System.out.println(\"Test passed.\");\r\n"
				+ "				else System.out.println(\"Test failed. On argument \" + n + \". Should return empty string.\");\r\n"
				+ "			}\r\n"
				+ "			else {\r\n"
				+ "				if (output.length() != n) {\r\n"
				+ "					System.out.println(\"Test failed. String length is not equal to n.\");\r\n"
				+ "					continue;\r\n"
				+ "				}\r\n"
				+ "				for (char c: output.toCharArray()) {\r\n"
				+ "					if (c != '0' && c != '1') {\r\n"
				+ "						System.out.println(\"Test failed. String should contain only ones and zeroes.\");\r\n"
				+ "						continue mainloop;\r\n"
				+ "					}\r\n"
				+ "				}\r\n"
				+ "				System.out.println(\"Test passed.\");\r\n"
				+ "			}\r\n"
				+ "\r\n"
				+ "		}\r\n"
				+ "	}");
		
		String add3 = "public static void main(String[] args){runUserMethod();}";
		
		code = code.trim();
		code = add1 + code.substring(0, code.length() - 1);
		code = code + add2 + add3 + "}";
		
		return code;
	}
	
	private String wk11criiiqs1Modify(String code) {
		String add1 = "import java.util.ArrayList;import java.util.Collections;";
		String add2 = uglify("	public static void runUserMethod() {\r\n"
				+ "		Boolean[][] testData1 = {\r\n"
				+ "				{true, true, true, false, true},\r\n"
				+ "				{true, true, false, false, true},\r\n"
				+ "				{true, true, true, true, true},\r\n"
				+ "				{false, false, false, false, false},\r\n"
				+ "				{false, true, true, true, false},\r\n"
				+ "				{true, false, true, false, true, false, true, false},\r\n"
				+ "				{true, false, true, false, true, false, true, false},\r\n"
				+ "				{true, false, true, false, true, false, true, false},\r\n"
				+ "				{true, false},\r\n"
				+ "				{false, true},\r\n"
				+ "				{false, false},\r\n"
				+ "				{true, true},\r\n"
				+ "				{true},\r\n"
				+ "				{false},\r\n"
				+ "				{},\r\n"
				+ "				null,\r\n"
				+ "				{true},\r\n"
				+ "				{true},\r\n"
				+ "				{true, false},\r\n"
				+ "				{true, false, true, false},\r\n"
				+ "				{true, false, true, false},\r\n"
				+ "				{true}\r\n"
				+ "				\r\n"
				+ "		};\r\n"
				+ "		\r\n"
				+ "		Double[][] testData2 = {\r\n"
				+ "				{1.0, 2.0, 3.0, 4.0, 10.0},\r\n"
				+ "				{1.0, 2.0, 3.0, 4.0, 10.0},\r\n"
				+ "				{1.0, 2.0, 3.0, 4.0, 10.0},\r\n"
				+ "				{1.0, 2.0, 3.0, 4.0, 10.0},\r\n"
				+ "				{1.0, 2.0, 3.0, 4.0, 10.0},\r\n"
				+ "				{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},\r\n"
				+ "				{10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0},\r\n"
				+ "				{10.0, 10.0, 10.0, 20.0, 10.0, 10.0, 10.0, 10.0},\r\n"
				+ "				{5.0, 7.0},\r\n"
				+ "				{5.0, 7.0},\r\n"
				+ "				{5.0, 7.0},\r\n"
				+ "				{5.0, 7.0},\r\n"
				+ "				{1.0},\r\n"
				+ "				{1.0},\r\n"
				+ "				{1.0},\r\n"
				+ "				{1.0},\r\n"
				+ "				{},\r\n"
				+ "				null,\r\n"
				+ "				{1.0},\r\n"
				+ "				{1.0, 2.0},\r\n"
				+ "				{1.0, 2.0, 3.0, 4.0, 5.0, 6.0},\r\n"
				+ "				{1.0, 2.0}\r\n"
				+ "		};\r\n"
				+ "		\r\n"
				+ "		double[] expectedValues = {12.0, 6.0, 20.0, 20.0, 2.0, 0.0, 0.0, 10.0, 2.0, 2.0, 12.0, 12.0, 1.0, 1.0, -100.0, -100.0, -100.0, -100.0, -100.0, -100.0, 2.0, 1.0};\r\n"
				+ "		\r\n"
				+ "		for (int i=0; i<expectedValues.length; i++) {\r\n"
				+ "			ArrayList<Boolean> testArgsBool = new ArrayList<Boolean>();\r\n"
				+ "			ArrayList<Double> testArgsDouble = new ArrayList<Double>();\r\n"
				+ "			\r\n"
				+ "			if (testData1[i] == null) {\r\n"
				+ "				Collections.addAll(testArgsDouble, testData2[i]);\r\n"
				+ "				\r\n"
				+ "				double output = ScalesFitness(null, testArgsDouble);\r\n"
				+ "				if (output == expectedValues[i]) {\r\n"
				+ "					System.out.println(\"Test passed.\");\r\n"
				+ "				}\r\n"
				+ "				else {\r\n"
				+ "					System.out.println(\"Test failed. On arguments \" + null + \" & \" + testArgsDouble + \". Expected value: \" + expectedValues[i] + \", Your value: \" + output);\r\n"
				+ "				}\r\n"
				+ "			}\r\n"
				+ "			else if (testData2[i] == null) {\r\n"
				+ "				Collections.addAll(testArgsBool, testData1[i]);\r\n"
				+ "				\r\n"
				+ "				double output = ScalesFitness(testArgsBool, null);\r\n"
				+ "				if (output == expectedValues[i]) {\r\n"
				+ "					System.out.println(\"Test passed.\");\r\n"
				+ "				}\r\n"
				+ "				else {\r\n"
				+ "					System.out.println(\"Test failed. On arguments \" + testArgsBool + \" & \" + null + \". Expected value: \" + expectedValues[i] + \", Your value: \" + output);\r\n"
				+ "				}\r\n"
				+ "			}\r\n"
				+ "			else {\r\n"
				+ "				Collections.addAll(testArgsBool, testData1[i]);\r\n"
				+ "				Collections.addAll(testArgsDouble, testData2[i]);\r\n"
				+ "				\r\n"
				+ "				double output = ScalesFitness(testArgsBool, testArgsDouble);\r\n"
				+ "				if (output == expectedValues[i]) {\r\n"
				+ "					System.out.println(\"Test passed.\");\r\n"
				+ "				}\r\n"
				+ "				else {\r\n"
				+ "					System.out.println(\"Test failed. On arguments \" + testArgsBool + \" & \" + testArgsDouble + \". Expected value: \" + expectedValues[i] + \", Your value: \" + output);\r\n"
				+ "				}\r\n"
				+ "			}\r\n"
				+ "\r\n"
				+ "		}\r\n"
				+ "	}");
		
		String add3 = "public static void main(String[] args){runUserMethod();}";
		
		code = code.trim();
		code = add1 + code.substring(0, code.length() - 1);
		code = code + add2 + add3 + "}";
		
		return code;
	}
	
    public static String uglify(String code) {
    	code = code.replace("\r\n", "");
    	
    	String[] regexes = {"\\{", "\\}", ";"};
    	String[] plainSymbols = {"{", "}", ";"};
    	
    	for (int i=0; i<regexes.length; i++) {
        	String[] broken = code.split(regexes[i]);
        	String recode = "";
        	for (String piece: broken) {
        		recode += piece.trim() + plainSymbols[i];
        	}
        	code = recode;
    	}
    	
    	code = code.substring(0, code.length() -3);
    	System.out.println(code);
    	return code;
    }
    
    @GetMapping(value="/codewalker-check")
    private String checkStatus() {
    	return "{\"result\": \"cw server up and running\"}";
    }
    
    @GetMapping(value="/stop-server")
    private void killServer() {
    	System.exit(0);
    }

}














