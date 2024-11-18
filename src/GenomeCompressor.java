/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Landon Moceri
 */

public class GenomeCompressor {

    // Maps to convert codes to letters and vice versa
    static int[] codeLookup = new int[256];
    static char[] letterLookup = new char[4];

    // Fill in the maps
    static {
        codeLookup['A'] = 0b00;
        codeLookup['C'] = 0b01;
        codeLookup['G'] = 0b10;
        codeLookup['T'] = 0b11;

        letterLookup[0b00] = 'A';
        letterLookup[0b01] = 'C';
        letterLookup[0b10] = 'G';
        letterLookup[0b11] = 'T';
    }

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // Read the entire string
        String input = BinaryStdIn.readString();

        // Write the length to the head of the bin file
        int length = input.length();
        BinaryStdOut.write(length, 32);

        // Convert each letter to a 2 bit code and write it to the binary
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            BinaryStdOut.write(codeLookup[c], 2);
        }

        // Close the binary
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        // Get the actual length of the sequence from the head
        int length = BinaryStdIn.readInt();

        // For each real base, convert it to its letter and write it to the txt file
        for (int i = 0; i < length; i++) {
            int n = BinaryStdIn.readInt(2);
            BinaryStdOut.write(letterLookup[n]);
        }

        // Close the binary
        BinaryStdOut.close();
    }

    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}