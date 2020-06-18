import java.util.List;
import java.util.stream.Collectors;

public class DownAndOutCallOption extends OptionBase {
	double strike, barrier;
	public DownAndOutCallOption(BlackScholesModel model, int maturity, double strike, double barrier) {
		super(model, maturity);
		this.strike = strike;
		this.barrier = barrier;
	}
	
	public double payoff(List<Double> prices) {
		
		if (prices.stream().filter(p -> p < barrier).collect(Collectors.toList()).size() > 0)
			return 0.0;
		
		double lastPrice = prices.get(prices.size()-1);
		if (lastPrice > strike) {
			return lastPrice - strike;
		} else {
			return 0.0;
		}
	}

}
