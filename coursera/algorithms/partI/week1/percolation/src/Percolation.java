import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    private boolean [] site;
    private int n;
    private int top; //this is the virtual top site
    private int bottom;//this is the virtual bottom site
    private int sumOpenSites;
    private WeightedQuickUnionUF percolated;

    // creates n-by-n grid, with all sites initially blocked(false)
    // in order to avoid the 2D table we need to convert it into an array
    //create the virtual top and bottom sites
    //initialize the sum of open sites with 0
    //create an object WeightedQuickUnionUF for the percolation
    public Percolation(int n)
    {
        if (n<=0)
            throw new IllegalArgumentException("The dimension of the table must be positive \n");

        this.n = n;
        int idx;
        site = new boolean[n*n];
        for (int i=1; i<=n; i++){
            //StdOut.println();
            for (int j=1; j<=n; j++){
                idx = convertToSingleIdx(i,j);
               // StdOut.print(idx + ", ");
                site[idx] = false;//all blocked
            }
        }

        top = convertToSingleIdx(n,n) + 1; // the index of virtual top
        //StdOut.println("top is: " +top);
        bottom = convertToSingleIdx(n,n) + 2; // the index of virtual top
        //StdOut.println("bottom is: " + bottom);
        sumOpenSites = 0;

        percolated = new WeightedQuickUnionUF(n*n + 2); // plus 2 because of virtual top and bottom
    }

    // the formula to convert the array[i][j] index into array[i] is: N*row + col
    private int convertToSingleIdx(int row, int col) {
        checkIndices(row, col);
        // the row and column indices are integers between 1 and n
        return n * (row - 1) + (col - 1);
    }

    private boolean areIndicesValid(int row, int col){
        return row > 0
                && col > 0
                && row <= n
                && col <= n;
    }

    private void checkIndices(int row, int col) {
        if (!areIndicesValid(row, col))
            throw new IllegalArgumentException("The row and col indices must be positive and less than " + (n + 1) + "\n");
    }

    // opens the site (row, col) if it is not open(true) already
    public void open(int row, int col){

        if (isOpen(row,col))
            return;

        int idx = convertToSingleIdx(row,col);
        site[idx] = true;
        sumOpenSites+=1;
        //open it means, connect it with the open adjacent sites

        //1st check if it's in the top row
        //else it has a site above it
        if (row == 1) {
            percolated.union(idx, top);
        }
        else if (site[convertToSingleIdx(row-1,col)]){
            percolated.union(idx, convertToSingleIdx(row-1,col));
        }

        //2nd check if it's in the bottom
        //else it has a site below it
        if (row == n){
            percolated.union(idx, bottom);
        }
        else if (site[convertToSingleIdx(row+1,col)]){
            percolated.union(idx, convertToSingleIdx(row+1,col));
        }

        //3rd check if it has on right
        if (col!=n && site[convertToSingleIdx(row,col+1)]){
            percolated.union(idx, convertToSingleIdx(row,col+1));
        }

        if (col!=1 && site[convertToSingleIdx(row,col-1)]){
            percolated.union(idx, convertToSingleIdx(row,col-1));
        }

    }

    // is the site (row, col) open?
    // true for open
    // false for blocked
    public boolean isOpen(int row, int col){
        int idx = convertToSingleIdx(row,col);
        return site[idx];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        int idx = convertToSingleIdx(row,col);
        return percolated.find(idx)==percolated.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return sumOpenSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return percolated.find(top)==percolated.find(bottom);
    }
//
//    public boolean[] getSite(){
//        return site;
//    }
//
//    public int getSumOpenSites(){
//        return sumOpenSites;
//    }
//    // test client (optional)
//    public static void main(String[] args){
//
//        StdOut.println("Give the dimension of the grid");
//        int n = StdIn.readInt();
//        Percolation exp = new Percolation(n);
//
//        while (!exp.percolates()) {
//            int x = StdRandom.uniform(1, n + 1);
//            int j = StdRandom.uniform(1, n + 1);
//            exp.open(x, j);
//        }
//
//        int idx;
//        boolean [] site = exp.getSite();
//        for (int i=1; i<=n; i++){
//            StdOut.println();
//            for (int j=1; j<=n; j++){
//                StdOut.print( site [n * (i - 1) + (j - 1)]+ ", ");
//                //all blocked
//            }
//        }
//        StdOut.println();
//        StdOut.print(exp.getSumOpenSites());
//    }
}
