

import java.util.ArrayList;

public class TruthfulAllocationMech {
	ArrayList<Agents> agents = new ArrayList<Agents>();
	double targetV = 0;

	
	public void setV(double val){
		this.targetV = val;
	}
	public double UniformAllocation(int key, int loop) { //loop只是一个辅助变量，为了排错
		// To determine value of r for user key
		// Return: r for user key
		
		double r = Constances.INITIALR;
		double sum = 0;

		// initialization
		for (int i = 1; i <= agents.size(); i++) {
			if (i != key) {
				double v = agents.get(i - 1).agentV;
				double cv = 1 / agents.get(i - 1).ratioVC;
				double ecvr = Math.E - cv / r;
				if (ecvr <= 1) {
					ecvr = 1;
				} 
				sum = sum + v * Math.log(ecvr);		
			}//if
			
		}//for
 		
		// while for r
		double prisum = 0;
		while (sum <= targetV) {			
			r = r + Constances.INITIALR;// r = r + $
			prisum = sum;
			sum = 0;			
			for (int i = 1; i <= agents.size(); i++) {
				if (i != key) {
					double v = agents.get(i-1).agentV;
					double cv = 1 / agents.get(i-1).ratioVC;
					double ecvr = Math.E - cv / r;
					if (ecvr <= 1) {
						ecvr = 1;
					} 
					sum = sum + v * Math.log(ecvr);
 //
				}//if
			
			}// for() 
			if((sum != 0) && Math.abs(prisum - sum) < 0.01)
			{
				break;
			}
 
		}//sum<= target
		
		return r;
	}

	public double[][] TruthfulMech(double targetV, int loop) { //loop只是一个辅助变量，为了排错
		// To determine allocation of task
		// Return: write xi and pi into file
		int len = agents.size();
		double[] arrayFx = new double[len];
		double[] arrayPx = new double[len];
		double px = 0;
		double fx = 0;
		double r = 0;
		for (int i = 1; i <= agents.size(); i++) {
			
			//compute R
 			r = UniformAllocation(i, loop);
//			System.out.println("*************R:"+r);
//			pause();
//			pause();
 
			//computer f() and payment
			double xcv = 1 / agents.get(i-1).ratioVC;
			double v = agents.get(i-1).agentV;
			double ecvr = Math.E - xcv / r;
			if (ecvr <= 1) {
				ecvr = 1;
			}
			px = v	* (xcv * Math.log(ecvr) + r *( (ecvr) * (Math.log(ecvr) - 1) + 1));
			fx = Math.log(ecvr);
			arrayFx[i-1] =fx;
			arrayPx[i-1] = px;
//			System.out.println("---agent:" + i + "--fx:" + fx + "--px:" + px );
		}
		
		
		double effect = 0;
		double totalpay = 0;
		// Store and Return res
		double[][] res = new double[2][len];
		for (int i = 0; i < len; i++) {
			res[0][i] = arrayFx[i];
			res[1][i] = arrayPx[i];
			effect += arrayFx[i]*agents.get(i).agentV;
			totalpay += arrayPx[i];
		}
		
 		return res;
	}
	
	
	

}
