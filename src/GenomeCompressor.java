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

    static int[] codeLookup = new int[256];
    static char[] letterLookup = new char[4];

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

    public static void compress() {
        String input = BinaryStdIn.readString();
        input = input.replace("\r", "");
        input = input.replace("\n", "");

        int length = input.length();
        BinaryStdOut.write(length, 32);

        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (c != 'A' && c != 'C' && c != 'G' && c != 'T') {
                throw new IllegalArgumentException("Illegal character: " + (int) c);
            }

            BinaryStdOut.write(codeLookup[c], 2);
        }

        BinaryStdOut.close();
    }

    public static void expand() {
        int length = BinaryStdIn.readInt();

        for (int i = 0; i < length; i++) {
            int c = BinaryStdIn.readInt(2);
            BinaryStdOut.write(letterLookup[c]);
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