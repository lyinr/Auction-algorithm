public class Constances {
	public static int AGENTNUM = 50;// maximum number of agents in one instance
	public static int BIGNUM = AGENTNUM + 10;
	public static double VMULPARA = 1;// decide range of agentV
	public static double VADDPARA = 12;// decide range of agentV
	public static double[] CMULPARA = { 0.625, 0.25, 1, 4, 16 }; // {0.25,
																	// Math.sqrt(0.25),
																	// 1, 2, 4};
	public static double[] CADDPARA = { 3, 6, 12, 24, 24 }; // {3, 6, 12, 24,
															// 24}
	public static double INITIALR = 0.02; // initial value for r in UAM

	public static String[] RAWDATEPATH = { "distri1.txt", "distri2.txt",
			"distri3.txt", "distri4.txt", "distri5.txt" };
	public static String[] AVERAGEPATH = { "average1.txt", "average2.txt",
			"average3.txt", "average4.txt", "average5.txt" };

	// Loop的变化范围
	public static int INITIALLOOP = 1;
	public static int LOOP = 2;

	// st算法的二分变量
	public static int HIGHV = 1000;

	// 分布数和算法数，用于保存数据
	public static int DISTRIBUTION = 5;
	public static int ALGORITHM = 4;

	public static int PAYMIN = 10;
	public static int PAYMAX = 100;
	public static int PAYSTEP = 10;

}
