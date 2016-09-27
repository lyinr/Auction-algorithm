import java.io.*;

public class WriteFile {

	public void FileWriter(String path, double[][][] data) {
		// Store data to assigned txt file

		File datafile = new File(path);

		if (datafile.exists() && datafile.isFile()) {
			System.out.println("ʹ���Ѿ����ڵ�datafile.txt�ļ�");
		} else {
			try {
				// �����ļ�
				datafile.createNewFile();
				System.out.println("����datafile.txt�ļ�");
			} catch (IOException e) {
				System.out.println("����datafile.txt�ļ�ʧ��,������Ϣ��" + e.getMessage());
				return;
			}
		}

		// ���濪ʼ���ļ���д������
		try {
			// ����һ��printWriter���ʵ�����乹�캯������һ��File����
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

			// ����close()�����ͷ���Դ
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
			// System.out.println("ʹ���Ѿ����ڵ�datafile.txt�ļ�");
		} else {
			try {
				// �����ļ�
				datafile.createNewFile();
				System.out.println("����datafile.txt�ļ�");
			} catch (IOException e) {
				System.out.println("����datafile.txt�ļ�ʧ��,������Ϣ��" + e.getMessage());
				return;
			}
		}

		// ���濪ʼ���ļ���д������
		try {
			// ����һ��printWriter���ʵ�����乹�캯������һ��File����
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

			// ����close()�����ͷ���Դ
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
