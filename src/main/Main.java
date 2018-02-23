package main;
import java.io.File;
import java.util.*;

import main.exceptions.UnknownExpressionException;

import java.io.FileNotFoundException;

/**
 * <h1>Main</h1>
 * Main class is starter point for Ryss interpreter.
 *
 * @author Szymon Kocur
 * @version 1.0
 * @since 2017-11-17
 *
 */
public class Main {
	static List<String> expressions = new ArrayList<String>();
	static int line = 0;

	/**
	 * This is the main method where scan method starts.
	 * It takes path to a file and save all lines to expressions List.
	 *
	 * @param args This is path to file which will be executed/interpreted.
	 */
	public static void main(String[] args) {
		try {
			File file = new File(args[0]);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNext()) {
				expressions.add(scanner.nextLine());
				line++;
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find .rx file with given name");
		    System.exit(1);
		}

        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Interpreter interpreter = new Interpreter.InterpreterBuilder()
                            .commentPattern("//")
                            .build();
                    interpreter.scan(expressions);
                } catch (UnknownExpressionException e) {
                        System.out.println("Unknown expression at line: " + line);
                }
            }
        });

        mainThread.start();
	}
}
