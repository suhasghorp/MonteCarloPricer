import java.util.List;

public class AsianCallOption extends OptionBase {
	double strike;
	public AsianCallOption(BlackScholesModel model, int maturity, double strike) {
		super(model, maturity);
		this.strike = strike;
	}
	
	public double payoff(List<Double> prices) {
		
		double average_price = prices.stream().reduce(0.0, (a,b) -> a + b)/maturity;
		
		if (average_price > strike) {
			return average_price - strike;
		} else {
			return 0.0;
		}
	}

}
