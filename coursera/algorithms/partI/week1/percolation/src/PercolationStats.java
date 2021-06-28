import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import static java.lang.Math.sqrt;

public class PercolationStats {

    private Percolation exp;
    private  int trials;
    private int n;
    private int[] fraction;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n<=0)
            throw new IllegalArgumentException("The dimension of grid must be positive. \n");
        this.n = n;

        if (trials<=0)
            throw new IllegalArgumentException("The number of trials must be positive. \n");
        this.trials = trials;
        fraction = new int[trials];

    }

    private int deployTrial()
    {
        exp = new Percolation(n);
        while (!exp.percolates()) {
            int x = StdRandom.uniform(1, n + 1);
            int j = StdRandom.uniform(1, n + 1);
            exp.open(x, j);
        }
        return exp.numberOfOpenSites();
    }

    private void deployTrials() {
        for (int i = 0; i < trials; i++) {
            fraction[i] = deployTrial();
            StdOut.println("Fraction["+i+"]="+fraction[i]);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return  mean() - 1.96*stddev()/sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return  mean() + 1.96*stddev()/sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args){
        int n;
        int trials;

        StdOut.print("Please give the dimension of grid. \n");
        n = StdIn.readInt();

        StdOut.print("Please give the number of trials. \n");
        trials = StdIn.readInt();


        PercolationStats stats = new PercolationStats(n,trials);
        stats.deployTrials();
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
