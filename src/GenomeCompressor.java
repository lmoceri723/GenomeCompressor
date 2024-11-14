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
 */

public class GenomeCompressor {

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {

        String input = BinaryStdIn.readString();

        int length = input.length();
        int count = 0;
        byte buffer = 0;

        while (count < length) {
            char c = input.charAt(count);
            switch (c) {
                case 'A': buffer = (byte) ((buffer << 2)); break;
                case 'C': buffer = (byte) ((buffer << 2) | 1); break;
                case 'G': buffer = (byte) ((buffer << 2) | 2); break;
                case 'T': buffer = (byte) ((buffer << 2) | 3); break;
            }
            count++;

            if (count % 4 == 0) {
                BinaryStdOut.write(buffer);
                buffer = 0;
            }
        }

        if (count % 4 != 0) {
            int filled = count % 4;
            int empty_bits = 2 * (4 - filled);
            buffer <<= empty_bits;
            BinaryStdOut.write(buffer);
        }


        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {

        String input = BinaryStdIn.readString();

        int length = input.length();
        int count = 0;
        char mask = 0b11111100;

        while (count < length) {
            char c = input.charAt(count);

            for (int i = 0; i < 4; i++) {
                int code = c & mask;
                switch (code) {
                    case 0: BinaryStdOut.write('A'); break;
                    case 1: BinaryStdOut.write('C'); break;
                    case 2: BinaryStdOut.write('G'); break;
                    case 3: BinaryStdOut.write('T'); break;
                }
                c <<= 2;
            }
            count++;
        }

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