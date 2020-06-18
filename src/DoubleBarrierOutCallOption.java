import java.util.List;
import java.util.stream.Collectors;

public class DoubleBarrierOutCallOption extends OptionBase {
	double strike, upperBarrier, lowerBarrier;
	public DoubleBarrierOutCallOption(BlackScholesModel model, int maturity, double strike, double upperBarrier, double lowerBarrier) {
		super(model, maturity);
		this.strike = strike;
		this.upperBarrier = upperBarrier;
		this.lowerBarrier = lowerBarrier;
	}
	
	public double payoff(List<Double> prices) {
		
		if (prices.stream().filter(p -> p < lowerBarrier || p > upperBarrier).collect(Collectors.toList()).size() > 0)
			return 0.0;
		
		double lastPrice = prices.get(prices.size()-1);
		if (lastPrice > strike) {
			return lastPrice - strike;
		} else {
			return 0.0;
		}
	}

}
