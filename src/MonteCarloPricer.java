
public class MonteCarloPricer {

	public static void main(String[] args) {
		MonteCarloPricer pricer = new MonteCarloPricer();
		pricer.process();
		
		
	}
	
	private void process() {
		
		int n = 365;
		double dt = 1.0/n;
		double r = 0.05;  // The interest rate
		double s_0 = 1.0;  // The initial stock price
		double drift = 0.1;  // The drift, mu, of the stock
		double volatility = 0.2 ; // The volatility, sigma, of the stock
		int maturity = 365; // maturity of option in days
		double strike = 1.1; //strike price
		int trials = 10000; //number of trials 0.0604
		double upperBarrier = 1.3;
		double lowerBarrier = 0.95;
		
		BlackScholesModel model = new BlackScholesModel(dt, r, s_0, drift, volatility);
		
		EuropeanCallOption euroCallOption = new EuropeanCallOption(model, maturity, strike);
		double[] ret = euroCallOption.monteCarloPricer(trials);
		System.out.println("European Call Option Price: " + ret[0] + ", Error: "+ ret[1]);
		
		AsianCallOption asianCallOption = new AsianCallOption(model, maturity, strike);
		ret = asianCallOption.monteCarloPricer(trials);
		System.out.println("Asian Call Option Price: " + ret[0] + ", Error: "+ ret[1]);
		
		UpAndOutCallOption upAndOutCallOption = new UpAndOutCallOption(model, maturity, strike, upperBarrier);
		ret = upAndOutCallOption.monteCarloPricer(trials);
		System.out.println("Up And Out Call Option Price: " + ret[0] + ", Error: "+ ret[1]);
		
		DownAndOutCallOption downAndOutCallOption = new DownAndOutCallOption(model, maturity, strike, lowerBarrier);
		ret = downAndOutCallOption.monteCarloPricer(trials);
		System.out.println("Down And Out Call Option Price: " + ret[0] + ", Error: "+ ret[1]);
		
		DoubleBarrierOutCallOption doubleBarrierOutCallOption = new DoubleBarrierOutCallOption(model, maturity, strike, upperBarrier, lowerBarrier);
		ret = doubleBarrierOutCallOption.monteCarloPricer(trials);
		System.out.println("Double Barrier Call Option Price: " + ret[0] + ", Error: "+ ret[1]);
		
		LookBackEuropeanCallOption lookBackEuropeanCallOption = new LookBackEuropeanCallOption(model, maturity, strike);
		ret = lookBackEuropeanCallOption.monteCarloPricer(trials);
		System.out.println("LookBack European Call Option Price: " + ret[0] + ", Error: "+ ret[1]);

	}
	
	/*private double[] brownian(int n, double dt) {		
		//zero mean, sqrt(dt) as std dev
		NormalDistribution norm = new NormalDistribution(0.0, Math.sqrt(dt));
		double[] samples = norm.sample(n+1);
		samples[0] = 0.0;
		//cumulative sum
		Arrays.parallelPrefix(samples, Double::sum);
		return samples;
		
	}*/

}
