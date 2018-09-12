# Worklists, Tries, and ZIP

This project implemented several different types of worklists, including
`ListFIFOQueue`, `ArrayStack`, `CircularArrayFIFOQueue`, and `MinFourHeap`. Also
implemented were `HashTrieMap`, `HashTrieSet`, and `SuffixTrie`.

The project included with it a Zip.java file that does LZ77Compression using the
previously implemented `PriorityQueue` to do Huffman coding, the `FIFOQueue` as
a buffer, the `Stack` to calculate the keyset of the `Trie`.

The evaluation was performed on the Zip.java file, with the goal being to
compare time, type of input file, and compression ratio by running our code on
various inputs.

The `Script.java` file allows the user to specify the wanted buffer lengths, and
the files to be tested on. It will then automatically run each of the files
through each buffer length, and generate a Markdown formatted file with a table
containing the data for that specific file.
