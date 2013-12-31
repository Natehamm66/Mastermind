package net.nate.Mastermind;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

/**
 * Main class for Mastermind.
 * 
 * @author Greg Haines
 * @author Nathan Hammond
 * @see <a
 *      href="http://www.pressmantoy.com/instructions/instruct_mastermind.html">Mastermind
 *      Game Rules</a>
 */
public class Main {

	/**
	 * Length of the secret code.
	 */
	public static final int CODE_LENGTH = 4;
	/**
	 * Color palette for the secret code.
	 */
	public static final char[] COLORS = { 'B', 'G', 'O', 'R', 'W', 'Y' };
	/**
	 * Number of guess attempts a player may make before the game is over.
	 */
	public static final int NUM_GUESSES = 10;

	private static final Random RAND = new Random();
	private static boolean debugMode = false;

	/**
	 * Entry point for Mastermind.
	 * 
	 * @param args
	 *            program arguments
	 */
	static BufferedReader br = new BufferedReader(new InputStreamReader(
			System.in));

	public static void main(final String... args) throws IOException {
		debugMode = (args != null && args.length > 0 && "-debug"
				.equals(args[0]));
		debug("Debug Mode: ON");
		printBanner();
		final char[] secretCode = generateSecretCode();
		boolean codeGuessed = false;
		int guessNum = 0;
		while (guessNum < NUM_GUESSES && !codeGuessed) {
			System.out.println();
			// TODO: Read a guess from the player, validate the guess and check
			// the guess
			String guess = readLine("Guess the code: ");
			if (isValidGuess(guess)) {
				char[] guessAns = guess.toCharArray();
				codeGuessed = checkGuess(guessAns, secretCode);
				guessNum++;
			}
		}
		if (codeGuessed) {
			System.out.println("Congratulations! You guessed the code!");
		} else {
			System.out.println("Bummer... You lost.");
		}
	}

	/**
	 * Check a guess against the secret code.
	 * 
	 * @param curGuess
	 *            the guess
	 * @param secretCode
	 *            the secret code
	 * @return true if the curGuess is the secretCode, false otherwise
	 */
	private static int find(char[] secretCode, char value) {
		boolean isEqual = false;
		for (int i = 0; i < secretCode.length; i++) {
			if (value == secretCode[i]) {
				isEqual = true;
			}
		}
		if (isEqual = true) {
			return value;
		} else {
			return -1;
		}

	}

	private static boolean checkGuess(final char[] curGuess,
			final char[] secretCode) {
		char isEqual = 0;
		int numRedPegs = 0;
		int numWhitePegs = 0;
		for (int i = 0; i < curGuess.length; i++) {
			if (curGuess[i] == secretCode[i]) {
				numRedPegs++;
				continue;
			}
			if (find(secretCode, isEqual) == secretCode[i]);
				numWhitePegs++;
		}

		boolean allCorrect = (numRedPegs == secretCode.length);
		if (!allCorrect) {
			System.out.print("Incorrect. Pegs: ");
			for (int i = 0; i < numRedPegs; i++) {
				System.out.print("(R)");
			}
			for (int i = 0; i < numWhitePegs; i++) {
				System.out.print("(W)");
			}
			System.out.println();

		}
		return allCorrect;
	}

	/**
	 * Print the welcome banner.
	 */
	private static void printBanner() {
		System.out.println("Welcome to Mastermind - The Game");
		System.out.println("--------------------------------");
		System.out.println();
		System.out.printf("FYI: The available code color palette is %s%n",
				Arrays.toString(COLORS));
		System.out.printf("FYI: The Secret Code will be %d colors long%n",
				CODE_LENGTH);
		System.out.println();
	}

	/**
	 * Validates a guess for length and colors.
	 * 
	 * @param guess
	 *            the guess
	 * @return true if the guess is valid, false otherwise
	 */
	public static boolean isValidGuess(final String guess) {
		boolean valid = false;
		char[] guessChars = guess.toCharArray();
		if (guess.length() != CODE_LENGTH) {
			valid = false;
		} else {
			valid = true;
			for (int i = 0; i < guessChars.length; i++) {
				boolean validChar = false;
				for (int j = 0; j < COLORS.length; j++) {
					if (guessChars[i] == COLORS[j]) {
						validChar = true;
					}
				}
				valid = validChar & valid;
			}
		}
		if (!valid && guess != null) {
			System.out.println("Invalid guess, please try again");

		}
		return valid;
	}

	/**
	 * Prints the formatted prompt and then reads a single line from the
	 * terminal.
	 * 
	 * @param prompt
	 *            a printf-style formatted prompt
	 * @param args
	 *            optional arguments for the formatted prompt
	 * @return the line read from the terminal
	 */
	public static String readLine(final String prompt, final Object... args) {
		String line = null;
		final Console console = System.console();
		if (console == null) {
			System.out.printf(prompt, args);
			final BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(System.in));
			try {
				line = bufferedReader.readLine();
			} catch (IOException ioe) {
			} // Ignore
		} else {
			line = console.readLine(prompt, args);
		}
		return line;
	}

	/**
	 * Generates a secret code of {@link CODE_LENGTH} using the colors from
	 * {@link COLORS}.
	 * 
	 * @return a new secret code
	 */
	private static char[] generateSecretCode() {
		final char[] secretCode = new char[CODE_LENGTH];
		for (int i = 0; i < 4; i++) {
			secretCode[i] = randomColor();
		}
		debug("Secret Code: " + Arrays.toString(secretCode));
		return secretCode;

	}

	/**
	 * Outputs a debug message if debug mode is enabled.
	 * 
	 * @param msg
	 *            the message to output
	 */
	public static void debug(final String msg) {
		if (debugMode) {
			System.out.println("DEBUG: " + msg);
		}
	}

	/**
	 * Pick a random color from the color palette.
	 * 
	 * @return a random color
	 */
	public static char randomColor() {
		return COLORS[RAND.nextInt(COLORS.length)];
	}
}
