import java.io.*;

/**
 *
 *  Q-Tree class. This class represents the quadtree data structure, QTree, used to compress raw grayscale
 *  images and uncompress back. Conceptually, the tree is a collection of FourZipNode's. A FourZipNode
 *  either holds a grayscale rawImage value (0-255), or QUAD_SPLIT, meaning the node is split into four
 *  sub-nodes that are equally sized sub-regions that divide up the current space.
 *
 *  @Auther: Chris Miler
 */

public class QTree extends Object {
    private int compressedSize;   //the size of the compressed image
    private int dim;   //the square dimension of the tree
    static int QUAD_SPLIT = -1;   //the value of a node that indicates it is split into 4 sub-regions
    private int[][] rawImage;   //the raw 2-D image
    private int rawSize;   //the size of the raw image
    private FourZipNode root;   //the root node in the tree

    /**
     * Create an initially empty tree.
     */
    public QTree() {
        this.root = null;
    }

    /**
     * Check to see whether a region in the raw image contains the same value.
     * NOTE!! did backwards I think, returns true if they are all the same and false if they are not.
     * @param start the start coordinate
     * @param size the size of the block
     * @return if it can be compressed or not (boolean)
     */
    private boolean canCompressBlock(Coordinate start, int size) {
        int x1 = start.getRow();
        int y1 = start.getCol();   // get the row and col of the start and store them, less calls.
        int comp = this.rawImage[x1][y1];   // get the value of the first coord,
        // should be the same as all the others if it compresses
        int dimx = x1 + (size - 1);
        int dimy = y1 + (size - 1);   // set the dimentions
        for (int x = start.getRow(); x <= dimx; x++) {
            for (int y = start.getCol(); y <= dimy; y++) {
                if (comp != this.rawImage[x][y]) {   // check to see if they are all the same as the first.
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Compress a raw image file already read in to this object.
     * @throws FourZipException - if there is no raw image (yet)
     */
    public void compress() throws FourZipException {
        Coordinate start = new Coordinate(0, 0);
        compress(start, this.dim);
    }

    /**
     * This is the core compression routine. Its job is to work over a region of the rawImage and compress it.
     * @param start - the start coordinate for this region
     * @param size - - the size this region represents
     * @return a node containing the compression information for the region
     */
    private FourZipNode compress(Coordinate start, int size) {
        FourZipNode result;
        if (canCompressBlock(start, size)) {   // check to see if it can be comppressed
            result = new FourZipNode(this.rawImage[start.getRow()][start.getCol()]);   // if true all the values are the same so you make a node
        } else {
            Coordinate cord = start;   // if false, split the "object" into 4 equal parts and run the method again.
            FourZipNode c1 = compress(cord, (size / 2));
            FourZipNode c2 = compress((cord = new Coordinate(start.getRow(), start.getCol() + (size / 2))), (size / 2));
            FourZipNode c3 = compress((cord = new Coordinate(start.getRow() + (size / 2), start.getCol())), (size / 2));
            FourZipNode c4 = compress((cord = new Coordinate(start.getRow() + (size / 2), start.getCol() + (size / 2))), (size / 2));
            result = new FourZipNode(c1, c2, c3, c4);
        }
        this.root = result;   // set and return the root
        return result;

    }

    /**
     * This is the core routine for uncompressing an image stored in a file into its raw image (a 2-D array of grayscale values (0-255). The main steps are as follows.
     * Open the compressed image file.
     * Read the file size.
     * Build the FourZip tree from the remaining numerical values in the file.
     * There is only one integer value on each line.
     * @param filename the name of the file containing the compressed image
     * @return the QTree instance created from the file data
     */
    static QTree compressedFromFile(String filename) {
        try (BufferedReader input = new BufferedReader(new FileReader(filename))) {
            QTree q = new QTree();   // make a new instance of QTree
            int lines = 0;
            BufferedReader input2 = new BufferedReader(new FileReader(filename));
            while (input2.readLine() != null) lines++;   // count the amount of lines in the file - ie the compressed size
            input2.close();   // close the second buffreader
            q.compressedSize = (lines-1);   // set the compressed size (-1 for the inital size minus the raw size tag)
            int rawSize = Integer.parseInt(input.readLine());   // set the raw size to the raw size in the file
            int dim = (int) (Math.sqrt(rawSize));   // set the dim
            FourZipNode root = parse(input);   // construct the root tree
            q.rawSize = rawSize;
            q.dim = dim;
            q.root = root;
            q.rawImage = new int[dim][dim];   // set all the varables to the instance
            input.close();
            return q;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;   // change
    }

    /**
     * Get the size of the compressed rawImage
     * @return compressed rawImage size
     */
    public int getCompressedSize() {
        return this.compressedSize;
    }

    /**
     * Get the raw image.
     * @return the raw image
     */
    public int[][] getRawImage() {
        return this.rawImage;
    }

    /**
     * Get the size of the raw image.
     * @return raw image size
     */
    public int getRawSize() {
        return this.rawSize;
    }

    /**
     * Get the image's square dimension.
     * @return the square dimension
     */
    public int getSideDim() {
        return this.dim;
    }

    /**
     * Parse the file being read and find the next FourZipNode subtree. This method is called recursively to read and
     * create the node's children. Recursively speaking, the input file stream contains the root node's value followed
     * when appropriate by the string values of each of its sub-nodes, going in a L-to-R, top-to-bottom order
     * (quadrants UL, UR, LL, LR).
     * @param file - a file that may have already been partially parsed
     * @return the root node of the subtree that has been created
     */
    private static FourZipNode parse(BufferedReader file) {
        try {
            FourZipNode result;
            int i = Integer.parseInt(file.readLine());
            if (i != QUAD_SPLIT) {
                result = new FourZipNode(i);   // make the node
            } else {
                FourZipNode c1 = parse(file);
                FourZipNode c2 = parse(file);
                FourZipNode c3 = parse(file);
                FourZipNode c4 = parse(file);
                result = new FourZipNode(c1, c2, c3, c4);   // make the quad node
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * A preorder (parent, left, right) traversal of a node. It returns a string which is empty if the node is null.
     * Otherwise it returns a string that concatenates the current node's value with the values of the 4 sub-regions
     * (with spaces between).
     * @param node - the node being traversed on
     * @return the string of the node
     */
    private String preorder(FourZipNode node) {
        String answer = "";
        if (node == null) {
            return ("");
        }
        if (node.getValue() == QUAD_SPLIT) {
            answer += "( ";
            for (Quadrant q : Quadrant.values()) {
                answer += preorder(node.getChild(q));
            }
            answer += ") ";
            return answer;
        } else {
            answer += node.getValue() + " ";
            return (answer);
        }
//        return answer;
    }

    /**
     * Load a raw image. The input file is ASCII text. It contains a series of grayscale values as decimal numbers
     * (0-255). The dimension is assumed square, and is computed from the length of file. There is one value per line.
     * @param inputFile the name of the file representing the raw image
     * @return the QTree instance created from the raw data
     */
    static QTree rawFromFile(String inputFile) {
        try (BufferedReader input = new BufferedReader(new FileReader(inputFile))) {
            QTree q = new QTree();
            int lines = 0;
            while (input.readLine() != null) lines++;   // get the number of lines in the file
            input.close();
            BufferedReader input2 = new BufferedReader(new FileReader(inputFile));
            int rawSize = lines;
            int dim = (int) (Math.sqrt(lines));
            q.rawImage = new int[dim][dim];
            for (int x = 0; x < dim; x++) {   // build the raw immage from the file
                for (int y = 0; y < dim; y++) {
                    q.rawImage[x][y] = Integer.parseInt(input2.readLine());
                }
            }
            q.rawSize = rawSize;
            q.dim = dim;
            q.root = null;
            input2.close();
            return q;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return a string that represents a preorder traversal of the tree. The node's (grayscale) image value is returned
     * as a decimal string. However when the node's value is QUAD_SPLIT that value is not shown. Instead a left
     * parenthesis is added before the children's to-string methods are called, and a right parenthesis is added
     * afterwards. Spaces are inserted between all items.
     * @return the qtree string representation
     */
    public String toString() {
        if (this.root == null) {
            return "NO TREE";
        } else {
            return preorder(this.root);
        }
    }

    /**
     * Create the uncompressed image from the internal FourZip tree.
     * @throws FourZipException - if not compressed image has been read in.
     */
    public void uncompress() throws FourZipException {
        if (this.root == null) throw new FourZipException("Error");
        Coordinate coord = new Coordinate(0, 0);
        uncompress(coord, this.dim, this.root);
    }

    /**
     * Convert a subtree of the FourZip tree into a square section of the raw image matrix. The main idea is that we
     * are working with a tree whose root represents the entire 2^n x 2^n rawImage.
     * @param coord - the coordinate of the upper left corner of the square to be filled
     * @param dim2 - both the length and width of the square to be filled
     * @param node - the root of the FourZip subtree that will be converted
     */
    private void uncompress(Coordinate coord, int dim2, FourZipNode node) {
        if (node.getValue() == QUAD_SPLIT) {
            Coordinate cord = coord;   // if it is a branching node recursivly branch it
            uncompress(cord, (dim2 / 2), node.getChild(Quadrant.UL));
            uncompress((cord = new Coordinate(coord.getRow(), coord.getCol() + (dim2 / 2))), (dim2 / 2), node.getChild(Quadrant.UR));
            uncompress((cord = new Coordinate(coord.getRow() + (dim2 / 2), coord.getCol())), (dim2 / 2), node.getChild(Quadrant.LL));
            uncompress((cord = new Coordinate(coord.getRow() + (dim2 / 2), coord.getCol() + (dim2 / 2))), (dim2 / 2), node.getChild(Quadrant.LR));
        } else {   // otherwise build the rawimage of that node color and location
            int x1 = coord.getRow();
            int y1 = coord.getCol();
            int dimx = x1 + (dim2 - 1);
            int dimy = y1 + (dim2 - 1);
            for (int x = coord.getRow(); x <= dimx; x++) {   // code to fill in the raw image
                for (int y = coord.getCol(); y <= dimy; y++) {
                    this.rawImage[x][y] = node.getValue();
                }
            }
        }
    }

    /**
     * The private writer is a recursive helper routine that writes out the compressed rawImage. It goes through the
     * tree in preorder fashion writing out the values of each node as they are encountered.
     * @param node - the current node in the tree
     * @param writer - the writer to write the node data out to
     */
    private void writeCompressed(FourZipNode node, BufferedWriter writer) {
        try {
            this.compressedSize += 1;
            writer.write(String.valueOf(node.getValue()));
            writer.newLine();
            if (node.getValue() == QUAD_SPLIT) {   // split and call the nodes childern
                writeCompressed(node.getChild(Quadrant.UL), writer);
                writeCompressed(node.getChild(Quadrant.UR), writer);
                writeCompressed(node.getChild(Quadrant.LL), writer);
                writeCompressed(node.getChild(Quadrant.LR), writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the compressed rawImage to the output file. This routine is meant to be called from
     * a client after it has been compressed
     * @param outFile - the name of the file to write the compressed rawImage to
     */
    public void writeCompressed(String outFile) {
        try {
            File file = new File(outFile);   // make a new file object
            if (!file.exists()) {   // make the file if it dos not exist
                file.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(file));   // make the BW
            out.write(String.valueOf(this.rawSize));   // print the raw size and then
            // call the private compression method
            out.newLine();
            writeCompressed(this.root, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
