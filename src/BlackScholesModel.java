import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.NormalDistribution;

public class BlackScholesModel {
	
	double dt, interest_rate, s_0, drift, volatility;
	
	public BlackScholesModel(double dt, double interest_rate, double s_0, double drift, double volatility) {
		this.dt = dt;
		this.interest_rate = interest_rate;
		this.s_0 = s_0;
		this.drift = drift;
		this.volatility = volatility;
	}
	
	public List<Double> stockPath(int n) {
		
		List<Double> t = DoubleStream.iterate(0.0, x -> x + this.dt).limit(n).boxed().collect(Collectors.toList());
		double[] samples = brownian(n, this.dt);
		List<Double> out = IntStream.range(0, t.size()).parallel()
		.mapToDouble(i -> s_0 * Math.exp((this.volatility * samples[i]) + (drift - Math.pow(volatility,2) / 2.0) * t.get(i)))
		.boxed()
		.collect(Collectors.toList());
		return out;
	}
	
	public List<Double> risklNeutralStockPath(int n) {
		List<Double> t = DoubleStream.iterate(0.0, x -> x + this.dt).limit(n).boxed().collect(Collectors.toList());
		double[] samples = brownian(n, this.dt);
		List<Double> out = IntStream.range(0, t.size()).parallel()
		.mapToDouble(i -> s_0 * Math.exp((this.volatility * samples[i]) + (interest_rate - Math.pow(volatility,2) / 2.0) * t.get(i)))
		.boxed()
		.collect(Collectors.toList());
		return out;
	}
	
	private double[] brownian(int n, double dt) {		
		//zero mean, sqrt(dt) as std dev
		NormalDistribution norm = new NormalDistribution(0.0, Math.sqrt(dt));
		double[] samples = norm.sample(n+1);
		samples[0] = 0.0;
		//cumulative sum
		Arrays.parallelPrefix(samples, Double::sum);
		return samples;
		
	}

}
