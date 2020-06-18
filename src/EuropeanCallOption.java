import java.util.List;

public class EuropeanCallOption extends OptionBase {
	double strike;
	public EuropeanCallOption(BlackScholesModel model, int maturity, double strike) {
		super(model, maturity);
		this.strike = strike;
	}
	
	public double payoff(List<Double> prices) {
		double lastPrice = prices.get(prices.size()-1);
		if (lastPrice > strike) {
			return lastPrice - strike;
		} else {
			return 0.0;
		}
	}

}
