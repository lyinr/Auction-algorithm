import java.io.*;

public class WriteFile {

	public void FileWriter(String path, double[][][] data) {
		// Store data to assigned txt file

		File datafile = new File(path);

		if (datafile.exists() && datafile.isFile()) {
			System.out.println("使用已经存在的datafile.txt文件");
		} else {
			try {
				// 创建文件
				datafile.createNewFile();
				System.out.println("创建datafile.txt文件");
			} catch (IOException e) {
				System.out.println("创建datafile.txt文件失败,错误信息：" + e.getMessage());
				return;
			}
		}

		// 下面开始向文件中写入数据
		try {
			// 创建一个printWriter类的实例，其构造函函数是一个File对象
			FileWriter pw = new FileWriter(datafile, true);
			
			for( int targetpay = Constances.PAYMIN; targetpay <= Constances.PAYMAX; targetpay += Constances.PAYMIN){
				for(int alg = 0; alg < Constances.ALGORITHM; alg++){
					pw.write(String.valueOf(data[targetpay/Constances.PAYSTEP - 1][alg][0]));
					pw.write('\t');
					pw.write(String.valueOf(data[targetpay/Constances.PAYSTEP - 1][alg][1]));
					pw.write("\r\n");
				
				}
				pw.write("\r\n");
			}

			// 调用close()方法释放资源
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	// @array[]
	public void rawDateWriter(String path, double [][][] data) {

		File datafile = new File(path);

		if (datafile.exists() && datafile.isFile()) {
			// System.out.println("使用已经存在的datafile.txt文件");
		} else {
			try {
				// 创建文件
				datafile.createNewFile();
				System.out.println("创建datafile.txt文件");
			} catch (IOException e) {
				System.out.println("创建datafile.txt文件失败,错误信息：" + e.getMessage());
				return;
			}
		}

		// 下面开始向文件中写入数据
		try {
			// 创建一个printWriter类的实例，其构造函函数是一个File对象
			FileWriter pw = new FileWriter(datafile, true);
			
			for(int targetpay = Constances.PAYMIN; targetpay <= Constances.PAYMAX; targetpay += Constances.PAYSTEP){
				pw.write("\r\n");
				pw.write("v=" + String.valueOf(targetpay));
				pw.write("\r\n");
				for(int alg = 0; alg < Constances.ALGORITHM; alg++){
					for(int loop = 0; loop < Constances.LOOP; loop ++){
						pw.write(String.valueOf(data[targetpay/Constances.PAYSTEP-1][alg][loop]));
						pw.write('\t');
					}
					pw.write("\r\n");
				}				
			}

			// 调用close()方法释放资源
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
