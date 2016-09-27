

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Agents {
	double agentV;//declared effect
	double agentC; // declared cost
	double ratioVC; // ratio of v/c
	double agentX; //participating level
	
	
	Agents(double v, double c, double ratio, double x){
		this.agentV = v;
		this.agentC = c;
		this.ratioVC = ratio;
		this.agentX = 0;
	}
	Agents(){
		this.agentC = 0;
		this.agentV = 0;
		this.ratioVC = 0;
		this.agentX = 0;
	}
	
	public void setV(double v){
		this.agentV = v;
	}
	
	public void setLevel(double x){
		this.agentX = x;
	}
	
	public void resetLeval(){
		this.agentX = 0;
	}
	
	public void setVC(){
		this.ratioVC = this.agentV/this.agentC;
	}
	public ArrayList<Agents> generateRandomAgents(int distri){
    //To generate agents[] by random function
    //
    	ArrayList<Agents> result = new ArrayList<Agents>();
    	Random rand = new Random();
    	

    	int agentNum = Constances.AGENTNUM;
    	for(int i = 1; i <= agentNum; i++ ) // generate agentV and agentC
    	{
    		double effect = (rand.nextGaussian()*Math.sqrt(Constances.VMULPARA) + Constances.VADDPARA);
    		double cost = (rand.nextGaussian()*Math.sqrt(Constances.CMULPARA[distri]) + Constances.CADDPARA[distri]);
    		
    		if( effect <= 0  || cost <= 0 ){
    			System.out.println("........<=0...........");
    		}
    		Agents tem = new Agents(effect, cost, effect/cost,0);
    		result.add(tem);
    	}//for
    	
    	return result;    	
    }// generateRandomAgents
	
	
	
}
