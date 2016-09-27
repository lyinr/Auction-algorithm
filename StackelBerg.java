import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class StackelBerg {
	ArrayList<Agents> agents = new ArrayList<Agents>();// a instance of agents
	Set setS = new HashSet();// set S
	Set setF = new HashSet();// Set F

	// @addS()
	public void addS(int val) {
		// add new element to setS
		setS.add(val);
	}

	// @addF()
	public void addF(int val) {
		// add new element to setF
		setF.add(val);
	}

	// @modifyX()
	public void modifyX(int pos, double x) {
		Agents tem = this.agents.get(pos);
		tem.setLevel(x);
		this.agents.set(pos, tem);
	}

	// @comparator()
		Comparator<Agents> comparator = new Comparator<Agents>() {
			// To define the rule of ArrayList<Agent>.sort()
			// sort all agents by agent.rationVC
			public int compare(Agents a1, Agents a2) {
				// sort by ratioVC
				if (a1.ratioVC != a2.ratioVC) {
					if (a1.ratioVC < a2.ratioVC)
						return 1;
					else
						return -1;
				}// if
				else {
					// sort by Effect when ratioVC is same
					if (a1.agentV != a2.agentV) {
						if (a1.agentV < a1.agentV)
							return 1;
						else
							return -1;
					}// if
						// sort by Cost
					else {
						if (a1.agentC < a2.agentC)
							return 1;
						else if(a1.agentC > a2.agentC)
							return -1;
						else 
							return 0;
					}// else
				}// else
			}// compare
		};// comparator

	// @isPositive()
	public boolean isSPositive() {
		// To prove whether xi in S is positive
		// Return: true if all positive; false else
		Iterator iterator = setS.iterator();
		while (iterator.hasNext()) {
			int pos = Integer.parseInt(iterator.next().toString());
			
			if (agents.get(pos - 1).agentX <= 0) {
				return false;
			}
		}

		return true;
	}

	// @getSFIndex()
	public int[] getSFIndex() {
		// To get all index of elements in S\F
		// Return: array of index
		Set<Integer> temS = new HashSet<Integer>();
		temS.addAll(setS);
		temS.removeAll(setF);

		Iterator iterator = temS.iterator();
		int[] res = new int[temS.size()];
		int i = 0;
		while (iterator.hasNext()) {
			int pos = Integer.parseInt(iterator.next().toString());
			res[i] = pos;
			i++;
		}

		return res;
	}

	// @getFindex()
	public int[] getFIndex() {
		Iterator iterator = setF.iterator();
		int[] res = new int[setF.size()];
		int i = 0;
		while (iterator.hasNext()) {
			int pos = Integer.parseInt(iterator.next().toString());
			res[i] = pos;
			i++;
		}

		return res;
	}

	// @getSFLength
	public int getSFLength() {
		// Return: length of S\F
		Set temS1 = new HashSet();
		temS1.addAll(setS);
		temS1.removeAll(setF);
		
		return temS1.size();
	}

	// @getFLength()
	public int getFLength() {
		// Return: length of F
		return setF.size();
	}

	// @stackelBergMech
	public double stackelBergMech(double payment, int loop) {
		// To complete stackelBergMech
		// Return: xi & F & S
		// Try to change initial value of X when encountering any problem

		setS.clear();
		setF.clear();

		// ~~~initialization
		Collections.sort(agents, comparator);						// ....................sort agent by ratioVC
		setS.add(1);												//....................S={1}
		int k = 1;													//....................k=1
		double totalX = 0;											//....................X=0
		setF.clear();												//....................F={}

		do {	// ............................outer while
			// SU(k+1)

			setS.add(k + 1);										//....................S=S + (K+1)
			boolean flag = true; // set flag = 1					
			setF.clear(); // set F == null							//....................F=null
			// fixing procedure
			
			double showV = 0;
			while (flag == true) {		                           // ..............................inner while	                 			
/**/	//		System.out.println(".......new loop.............");
			

				// 。。。。。。。。。。。。。。。To compute sum of ci/(vi*p) in S\F          
				int lenSF = 0;
				lenSF = getSFLength();
				
/*SF = NULL*/   if(lenSF == 0){
					break;
				}
				
				int[] indexSF = new int[lenSF];
				indexSF = getSFIndex();
				double sumSFcvp = 0;
				for (int i = 0; i < lenSF; i++) {
					double cv = 1 / agents.get(indexSF[i] - 1).ratioVC;
					sumSFcvp = sumSFcvp + cv / payment;
				}
				
				//。。。。。。。。。。。。。。。。To compute sum of xv in F
				double sumFxv = 0;
				int lenF = 0;
				lenF = getFLength();
								
				if (lenF != 0) {
					int[] indexF = new int[lenF];
					indexF = getFIndex();
					for (int i = 0; i < lenF; i++) {
						double v = agents.get(indexF[i] - 1).agentV;
						double x = agents.get(indexF[i] - 1).agentX;
						sumFxv = sumFxv + x * v;
//						System.out.println("F:<v,c,x>"+v+"--"+agents.get(indexF[i] - 1).agentC+"--"+x);
					}// for(lenF)
				} // if(lenF != 0)
				// 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。To compute total effect X
				totalX = (lenSF - 1 + Math.sqrt(Math.pow(lenSF - 1, 2) + 4
						* sumSFcvp * sumFxv))
						/ (2 * sumSFcvp);				
																				//....................Compute the NE by set x_i for S\F
				// 。。。。。。。。。。。。。。To compute NE in PF game for S\F i.e. best response for
				// users
				for (int i = 0; i < lenSF; i++) {

					double cv = 1 / agents.get(indexSF[i] - 1).ratioVC;
					double v = agents.get(indexSF[i] - 1).agentV;
					double x = (totalX - (cv * Math.pow(totalX, 2)) / payment)
							/ v;
					modifyX(indexSF[i] - 1, x);
	//				System.out.println(x);
				}
				
				boolean flagctn = true;
				// ~~~ compare with X for i in S\F
				for (int i = 0; i < lenSF; i++) {	 			    //....................
					double cv = 1 / agents.get(indexSF[i] - 1).ratioVC;
					double cmpX = (1 + Math.sqrt(1 - 4
							* agents.get(indexSF[i] - 1).agentC / payment))
							/ ((2 * cv) / (payment));

					if (totalX < cmpX) {							//....................If there exists X<cmpX for i in S\F
						modifyX(indexSF[i] - 1, 1);					//....................x_i=1
/*F++*/					setF.add(indexSF[i]);						//....................F=F+i
						showV += agents.get(indexSF[i]-1).agentV;
//						System.out.println("F:<v,c,x>"+agents.get(indexSF[i] - 1).agentV+"--"+agents.get(indexSF[i] - 1).agentC+"--"+agents.get(indexSF[i] - 1).agentX);
						flagctn = false;
						break;
					} 					
				}// for(cmpx)
				
				if(flagctn){
					
					if ((agents.get((k + 1) - 1).agentX <= 0) ) { 				//..................else if X < VALUE
						for (int j = k + 1; j <= setS.size(); j++) {
							modifyX(j - 1, 0);
						}
						
//						flag = false;
						break;
					}		
					else if(setS.size() == agents.size()){							//...................or S==N
//						flag = false;
						break;
					}	
					else{
//						flag = false;
						break;
					}
					
				}
											
			}// inner while(flag == true)
			
//     		System.out.println(".................outer loop");
			k = k + 1;
//			pause();
		}while ((setS.size() != agents.size()) && isSPositive());	
			
		
		if (setS.size() != agents.size()) {
			for (int j = setS.size() + 1; j <= agents.size(); j++) // xi=0 for i
			{
				
				modifyX(j - 1, 0);
			}
		}

		double totalV = 0;
		double totalP = 0;

		for (int i = 0; i < agents.size(); i++) {
			double v = agents.get(i).agentV;
			double x = agents.get(i).agentX;
			double c = agents.get(i).agentC;
			if(x <= 0){
				x = 0;
			}
			totalV = totalV + v * x;
			double showvc =v/c;
			
//			System.out.println("<V:"+v+"...C:"+c+ "------VC:" + showvc + "------x:"+x);
			totalP = totalP + c * x;
			if (x < 0) {
 				System.out.println("***********Warning********");
			}
		}
		
		return totalV;
	}// stackelBergMech

	
}
