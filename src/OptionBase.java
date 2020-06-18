import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class OptionBase {
	
	BlackScholesModel model;
	int maturity;
	
	public OptionBase(BlackScholesModel model, int maturity) {
		this.model = model;
		this.maturity = maturity;
	}
	
	public abstract double payoff(List<Double> prices);
	
	public double[] monteCarloPricer(int trials) {
		
		List<Double> samples = IntStream.range(0, trials).parallel()
		.mapToDouble(i -> Math.exp(-model.dt * maturity * model.interest_rate) *
				payoff(model.risklNeutralStockPath(maturity)))
		.boxed()
		.collect(Collectors.toList());
		
		double mean = samples.stream().reduce(0.0, (a,b) -> a + b)/trials;
		double var = samples.stream()
				.map(x -> Math.pow(x - mean, 2))
				.reduce(0.0, (a,b) -> a + b);
		double error = Math.sqrt(var / Math.pow(trials - 1,2));
		
		double[] ret = new double[] { mean, error};
		
		return ret;
	}
}
