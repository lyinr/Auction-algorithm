import java.util.ArrayList;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) {

		double[][] stackPay = new double[5][Constances.LOOP]; // [distribtuion][loop]
		double[][] truthfulPay = new double[5][Constances.LOOP];
		double[][] newtruthfulPay = new double[5][Constances.LOOP];
		double[][] topkPay = new double[5][Constances.LOOP];
		double[][] optPay = new double[5][Constances.LOOP];
		double[][] vcgPay = new double[5][Constances.LOOP];

		// double[][][][] rawdate = new double[dis][V][ALG][LOOP];
		double[][][][] rawdate = new double[5][Constances.PAYMAX
				/ Constances.PAYSTEP][Constances.ALGORITHM][Constances.LOOP];

		double[] sameV = new double[Constances.AGENTNUM];

		System.out.println("-------Generated Random Agents..............");

		for (int targetpay = Constances.PAYMIN; targetpay <= Constances.PAYMAX; targetpay += Constances.PAYSTEP) {

			for (int loop = 1; loop <= Constances.LOOP; loop++) {

				for (int distribution = 0; distribution < Constances.DISTRIBUTION; distribution++) {

					// System.out.println("<<<<<<<<<<<<"+loop+">>>>>>>>>>>>>");
					// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					// @generate random agents instance
					double allAgentV = 0;
					Agents agentsObj = new Agents();
					ArrayList<Agents> agentList = new ArrayList<Agents>();
					agentList = agentsObj.generateRandomAgents(distribution);
					int agentlen = agentList.size();

					// 保存首次生成的V分布，使得同一个loop内，5个分布的V值都对应相等；
					if (distribution == 0) {
						for (int i = 0; i < Constances.AGENTNUM; i++) {
							sameV[i] = agentList.get(i).agentV;
							allAgentV += sameV[i];
						}
					} else {
						for (int i = 0; i < Constances.AGENTNUM; i++) {
							agentList.get(i).setV(sameV[i]);
							agentList.get(i).setVC();
							allAgentV += sameV[i];
						}
					}

					System.out.println("-------Generated Random Agents:"
							+ targetpay + "~~~~" + loop + "~~~~" + distribution

					);

					// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					// ==============================
					// algorithm1: StackelBergMech
					// ==============================
					StackelBerg stackInstance = new StackelBerg();

					for (int i = 0; i < agentlen; i++) {
						stackInstance.agents.add(agentList.get(i));
					}

					double mv = 0;
					mv = stackInstance.stackelBergMech(targetpay, loop);

					stackPay[distribution][loop - 1] = mv;

					System.out.println(mv);
					System.out.println(".........StackelBerg---over");

					// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					// @@@@@@@@@ //
					// /////////////////////////////////////////////

					double htv = Constances.HIGHV;
					double ltv = 0;
					double mtv = 0;

					while (Math.abs(htv - ltv) > 1) {

						mtv = (htv + ltv) / 2;

//						System.out.println(htv + " " + ltv);

						TruthfulAllocationMech truthfulMech = new TruthfulAllocationMech();
						truthfulMech.setV(mtv);
						for (int i = 0; i < agentlen; i++) {
							agentList.get(i).resetLeval();
							truthfulMech.agents.add(agentList.get(i));
						}

						// for(int i = 0; i < 10; i++){
						// System.out.println(truthfulMech.agents.get(i).agentC
						// +
						// " " + truthfulMech.agents.get(i).agentV);
						// }
						//

						// get fx[] and px[] from ALG2
						// get fx[] and px[] from ALG2
						double[][] res = new double[2][agentlen];
						res = truthfulMech.TruthfulMech(mtv, loop);

						// To compute budget sumPay
						double sumPay = 0;
						int reslen = res[1].length;
						for (int i = 0; i < reslen; i++) {
							sumPay += res[1][i];
						}

						
						
						if (sumPay < targetpay) {
							ltv = mtv;
						} else {
							htv = mtv;
						}

					}

					truthfulPay[distribution][loop - 1] = mtv;
					System.out.println(mtv);
					System.out.println(".........TruthfulMech---over  ");

					// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					// @@@@@@@@@ //
					// /////////////////////////////////////////////
					double hnv = Constances.HIGHV;
					double lnv = 0;
					double mnv = 0;

					while(Math.abs(hnv - lnv) > 1){
						mnv = (hnv + lnv) / 2;
//						System.out.println(hnv + " " + lnv);
						NewTruthfulAllocationMech newtruthfulMech = new NewTruthfulAllocationMech();

						// Initialization
						newtruthfulMech.setV(mnv);
						for (int i = 0; i < agentlen; i++) {
							agentList.get(i).resetLeval();
							newtruthfulMech.agents.add(agentList.get(i));
						}

						// for(int i = 0; i < 10; i++){
						// System.out.println(newtruthfulMech.agents.get(i).agentC +
						// " " + newtruthfulMech.agents.get(i).agentV);
						// }

						// To call new TruthfulMech and get new res: res[][0] = fx,
						// res[1] = p;
						double[][] newres = new double[2][agentlen];
						newres = newtruthfulMech.TruthfulMech(mnv, loop); // loop只是一个辅助变量，为了排错

						// To compute budget sumPay
						double newsumPay = 0;
						int newreslen = newres.length;
						for (int i = 0; i < newres[1].length; i++) {
							newsumPay += newres[1][i];
						}

						if (newsumPay < targetpay) {
							lnv = mnv;
						} else {
							hnv = mnv;
						}
						 
					}					
 
					newtruthfulPay[distribution][loop - 1] = mnv;
					System.out.println(mnv);
					System.out.println(".........newTruthfulMech---over  ");

					// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					// @@@@@@@@@
					// /////////////////////////////////////////////

					TopK topk = new TopK();
					// Initialization
					for (int i = 0; i < agentlen; i++) {
						topk.agents.add(agentList.get(i));
					}

					double topkpay = topk.computBudget(targetpay, agentlen);

					topkPay[distribution][loop - 1] = topkpay;

					System.out.println(topkpay);
					System.out.println(".........TopK---over  ");

				}// distribution
			}// loop

			for (int distri = 0; distri < 5; distri++) {
				for (int i = 0; i < Constances.LOOP; i++) {
					rawdate[distri][targetpay / Constances.PAYSTEP - 1][0][i] = topkPay[distri][i];
					rawdate[distri][targetpay / Constances.PAYSTEP - 1][1][i] = stackPay[distri][i];
					rawdate[distri][targetpay / Constances.PAYSTEP - 1][2][i] = truthfulPay[distri][i];
					rawdate[distri][targetpay / Constances.PAYSTEP - 1][3][i] = newtruthfulPay[distri][i];
				}
			}
		}// targetV

		saveRawdate(rawdate);
		saveAsd(rawdate);

	}

	public static double getVCG(double target, ArrayList<Agents> vcgList,
			int user, double userVC) {
		double aim = target;
		double sum = 0;
		int len = vcgList.size();
		double userX = 0;

		// System.out.println("......optList:"+ len);
		for (int i = 0; i < len; i++) {
			// System.out.println(i+"sum:"+ sum +"-----aim:"+aim);
			if (sum == aim) {
				break;
			} else if (i == user - 1) {
				continue;
			} else {
				double v = vcgList.get(i).agentV;
				double curvc = v / vcgList.get(i).agentC;

				if (curvc > userVC) {
					double ratio = (aim - sum) / v;
					if (ratio >= 1) {
						sum = sum + v;
					} else if (ratio > 0) {
						sum = sum + v * ratio;
						userX = 0;
						break;
					}
				} else {
					double ratio = (aim - sum) / vcgList.get(user - 1).agentV;
					if (ratio >= 1) {
						userX = 1;
						break;
					} else if (ratio > 0) {
						userX = ratio;
						break;
					}
				}
			}

		}// for

		return userX;
	}

	public static double[] getOPT(double target, ArrayList<Agents> optList) {
		double aim = target;
		double sum = 0;
		double optpay = 0;
		int len = optList.size();
		int num = 0;
		double lastUserX = 1;

		// System.out.println("......optList:"+ len);
		for (int i = 0; i < len; i++) {
			// System.out.println(i+"sum:"+ sum +"-----aim:"+aim);
			if (sum == aim) {
				break;
			} else {
				// System.out.println(".................p:"+optpay);
				double v = optList.get(i).agentV;
				double c = optList.get(i).agentC;

				double ratio = (aim - sum) / v;
				// System.out.println("ration:"+ ratio);
				if (ratio >= 1) {
					sum = sum + v;
					num += 1;
					optpay = optpay + c;
				} else if (ratio > 0) {
					sum = sum + v * ratio;
					num += 1;
					optpay = optpay + c * ratio;
					lastUserX = ratio;

				}
				// System.out.println("--------------optPay:"+optpay);
			}

		}// for

		double[] res = new double[3];
		res[0] = optpay;
		res[1] = num;
		res[2] = lastUserX;

		return res;
	}

	public static void pause() {
		Thread thd = new Thread();
		try {
			thd.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveRawdate(double rawdate[][][][]) {
		// save rawdate

		WriteFile fw = new WriteFile();
		for (int dist = 0; dist < Constances.DISTRIBUTION; dist++) {
			fw.rawDateWriter(Constances.RAWDATEPATH[dist], rawdate[dist]);
		}
	}

	public static void saveAsd(double rawdate[][][][]) {

		double[][][] asd = new double[Constances.PAYMAX / Constances.PAYSTEP][Constances.ALGORITHM][2];
		double avg = 0;
		double sd = 0;

		for (int dist = 0; dist < Constances.DISTRIBUTION; dist++) {
			for (int targetpay = Constances.PAYMIN; targetpay <= Constances.PAYMAX; targetpay += Constances.PAYSTEP) {
				for (int alg = 0; alg < Constances.ALGORITHM; alg++) {

					avg = 0;
					// 计算均值
					for (int loop = 0; loop < Constances.LOOP; loop++) {
						avg += rawdate[dist][targetpay / Constances.PAYSTEP - 1][alg][loop];
					}
					avg = avg / Constances.LOOP;

					// 计算方差
					sd = 0;
					for (int loop = 0; loop < Constances.LOOP; loop++) {
						sd += Math.pow(rawdate[dist][targetpay
								/ Constances.PAYSTEP - 1][alg][loop]
								- avg, 2);
					}
					sd = Math.sqrt(sd / Constances.LOOP);

					asd[targetpay / Constances.PAYSTEP - 1][alg][0] = avg;
					asd[targetpay / Constances.PAYSTEP - 1][alg][1] = sd;
				}// alg
			}// V
			WriteFile fw = new WriteFile();
			fw.FileWriter(Constances.AVERAGEPATH[dist], asd);
		}// dist

	}

	public static double vcgALG(double v, ArrayList<Agents> vcgList) {
		double optres[] = new double[3];
		double totalpay = 0;
		optres = getOPT(v, vcgList);
		int optUserNum = (int) optres[1];
		double lastUX = optres[2];

		for (int j = 1; j <= optUserNum; j++) {

			double vcgPX = 0;
			if (j == optUserNum) {
				vcgPX += vcgList.get(optUserNum - 1).agentC * lastUX;
			} else {
				vcgPX += vcgList.get(j - 1).agentC;
			}

			double curC = vcgList.get(j - 1).agentC;
			double curV = vcgList.get(j - 1).agentV;
			double curX = 1;
			do {
				curC += 0.02;
				curX = getVCG(v, vcgList, j, curV / curC);
				vcgPX += curX * curC;
				// System.out.println(j + "   " + curV / curC + "  " + curX);
			} while (curX > 0);

			totalpay += vcgPX;
		}
		return totalpay;
	}

 
}
