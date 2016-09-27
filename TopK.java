import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopK {
	ArrayList<Agents> agents = new ArrayList<Agents>();
	double targetV = 0;
	

	public void setV(double val) {
		this.targetV = val;
	}

	public double computBudget(double targetV, int n) {
		double p = 1;
		double max;

		do {
			max = 0;
			for (int k = 1; k <= n-1; k++) {
				if (topK(k, p, n) > max) {
					max = topK(k, p, n);
				}
			}
			p = p +  1;	
 			System.out.println("p" +p + "  max:" + max);
		} while (max <= targetV);
		
		
		return max;
	}

	public double topK(int k, double p,int n) {
		double x = 0;
		for (int i = 0; i < n; i++) {
			x = p / (k * agents.get(i).agentC);
			if (x > 1) {
				x = 1;
			}
			agents.get(i).setLevel(x);
		}
		
		Collections.sort(agents, comparator);
		
//		for(int i = 0; i < 10; i++){
//			System.out.println(agents.get(i).agentX*agents.get(i).agentV);
//		}
		
		double xvk1 = agents.get(k).agentV*agents.get(k).agentX;
		double res = 0;
		for(int i = 0; i < k; i ++){
			double v = agents.get(i).agentV;
			double localx = xvk1 / v;
			agents.get(i).setLevel(localx);
			res += v*localx;
		}
		
		return res;
	}

	// @comparator()
	Comparator<Agents> comparator = new Comparator<Agents>() {
		// To define the rule of ArrayList<Agent>.sort()
		// sort all agents by agent.rationVC
		public int compare(Agents a1, Agents a2) {
			// sort by ratioVC
			double xv1 = a1.agentV * a1.agentX;
			double xv2 = a2.agentV * a2.agentX;
			
			return Double.compare(xv2, xv1);

		}// compare
	};// comparator
	
}
