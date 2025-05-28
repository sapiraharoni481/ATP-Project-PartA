package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple decompressor input stream that reads compressed data and decompresses it
 * by alternating between byte values 0 and 1 based on run-length encoding.
 *
 * The stream expects the input format to have:
 * - First 12 bytes as metadata
 * - Subsequent bytes representing counts for alternating 0 and 1 values
 *
 * @author Sapir Aharoni
 * @version 1.0
 */
public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    /**
     * Constructs a SimpleDecompressorInputStream that wraps the given input stream.
     *
     * @param in the input stream to read compressed data from
     */
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads a single byte from the underlying input stream.
     *
     * @return the next byte of data, or -1 if the end of the stream is reached
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads decompressed data into the specified byte array.
     * The method first reads 12 bytes of metadata, then processes
     * run-length encoded data by alternating between values 0 and 1.
     *
     * @param b the buffer into which the data is read
     * @return the total number of bytes read into the buffer
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        // Read the metadata
        for (int i = 0; i < 12; i++) {
            b[i] = (byte) in.read();
        }

        int index = 12; // Start after the metadata
        byte value = 0; // Start with 0

        // Continue until we finish filling the array
        while (index < b.length) {
            int count = in.read(); // Read the number of occurrences
            if (count == -1) break; // If we reached the end of input

            // Fill the current value multiple times according to the count
            for (int i = 0; i < count && index < b.length; i++) {
                b[index++] = value;
            }

            // Switch between 0 and 1
            value = (byte) (1 - value);
        }

        return b.length;
    }
}