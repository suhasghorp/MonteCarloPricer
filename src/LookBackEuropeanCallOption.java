import java.util.List;
import java.util.NoSuchElementException;


public class LookBackEuropeanCallOption extends OptionBase {
	double strike;
	public LookBackEuropeanCallOption(BlackScholesModel model, int maturity, double strike) {
		super(model, maturity);
		this.strike = strike;		
	}
	
	public double payoff(List<Double> prices) {
		
		double maxPrice = prices.stream().mapToDouble(d -> d).max().orElseThrow(NoSuchElementException::new);
		
		if (maxPrice > strike) {
			return maxPrice - strike;
		} else {
			return 0.0;
		}
	}

}
