import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FileInputting extends JFrame implements ActionListener {
	JLabel inputljlabel;
	JLabel outputljlabel;
	File inputfile = null;
	File outputfile = null;
	static boolean isok = false;

	public FileInputting() {
		JButton inputbutton = new JButton("Input file select");
		JButton outputbutton = new JButton("Output file select");
		JButton ok = new JButton("ok");
		inputbutton.addActionListener(this);
		outputbutton.addActionListener(this);
		ok.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(inputbutton);
		buttonPanel.add(outputbutton);
		buttonPanel.add(ok);

		inputljlabel = new JLabel();
		outputljlabel = new JLabel();

		JPanel labelPanel = new JPanel();
		labelPanel.add(inputljlabel);
		labelPanel.add(outputljlabel);

		getContentPane().add(labelPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

		// TODO 自動生成されたコンストラクター・スタブ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(10, 10, 350, 200);
		this.setTitle("ファイル選択");
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmdName = e.getActionCommand();
		System.out.println(cmdName);

		// 入力ファイルの選択
		if ("Input file select".equals(cmdName)) {
			JFileChooser filechooser = new JFileChooser();

			int selected = filechooser.showOpenDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION) {
				inputfile = filechooser.getSelectedFile();
				System.out.println("入力ファイルPath:" + inputfile.getAbsolutePath());
				// System.out.println("入力ファイル:" + inputfile.getName());
				inputljlabel.setText(inputfile.getName());
			} else if (selected == JFileChooser.CANCEL_OPTION) {
				inputljlabel.setText("キャンセルされました");
			} else if (selected == JFileChooser.ERROR_OPTION) {
				inputljlabel.setText("エラー又は取消しがありました");
			}
		} else if ("Output file select".equals(cmdName)) {
			System.out.println("Output file select is clicked");
			JFileChooser filechooser = new JFileChooser();
			filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int selected = filechooser.showOpenDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION) {
				outputfile = filechooser.getSelectedFile();
				System.out
						.println("出力ファイルPath:" + outputfile.getAbsolutePath());
				// System.out.println("出力ファイル:" + inputfile.getName());
				outputljlabel.setText(outputfile.getAbsolutePath());
			} else if (selected == JFileChooser.CANCEL_OPTION) {
				outputljlabel.setText("キャンセルされました");
			} else if (selected == JFileChooser.ERROR_OPTION) {
				outputljlabel.setText("エラー又は取消しがありました");
			}
		} else if ("ok".equals(cmdName)) {
			System.out.println("ok Clicked");
			if(inputfile == null && outputfile == null){
				inputljlabel.setText("入力ファイルを選択してください");
			}else if (inputfile != null && outputfile == null) {
				calcLogToGoogleMap(inputfile);
			}else if(inputfile != null && outputfile != null){
				calcLogToGoogleMap(inputfile,outputfile.getAbsoluteFile().toString());
			}
		}
	}

	public JLabel getInputName() {
		return inputljlabel;
	}

	public File getinputfile() {
		// if(checkBeforeReadfile(inputfile)){
		// return null;
		// }
		return inputfile;
	}

	public static boolean getisok() {
		return isok;
	}

	private static boolean checkBeforeReadfile(File file) {
		if (file == null) {
			return false;
		}
		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				System.out.println("true");
				return true;
			}
		}
		System.out.println("false");
		return false;
	}

	public void calcLogToGoogleMap(File inputfile) {
		calcLogToGoogleMap(inputfile, inputfile.getParent());
	}

	public void calcLogToGoogleMap(File inputfile, String outputDirectory) {
		String filename = inputfile.getName();
		String filenames[] = filename.split("\\.");
		File outputfile = new File(outputDirectory + "\\" + filenames[0]
				+ "_output.csv");
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
					outputfile)));
			BufferedReader br = new BufferedReader(new FileReader(inputfile));

			String str = br.readLine();
			while (str != null) {
				String[] contents = str.split(",");
				pw.println(contents[0] + "," + contents[1] + "," + contents[2]);
				str = br.readLine();
			}

			br.close();
			pw.close();

			csvToHTML(outputfile);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void csvToHTML(File csvfile) {
		String filename = csvfile.getAbsolutePath();
		String filenames[] = filename.split("\\.");
		File outputfile = new File(filenames[0] + ".html");

		try {

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
					outputfile)));
			BufferedReader br = new BufferedReader(new FileReader(
					"gpssample.html"));
			BufferedReader brcsv = new BufferedReader(new FileReader(inputfile));

			String str = br.readLine();
			while (str != null) {
				pw.println(str);
				if (str.indexOf("StartArray()") != -1) {
					// ヘッダー行
					String strcsv = brcsv.readLine();
					// 内容1行目
					strcsv = brcsv.readLine();
					int i = 0;
					while (strcsv != null) {
						String[] contents = strcsv.split(",");
						pw.println("patharray[" + i
								+ "] = new google.maps.LatLng(" + contents[1]
								+ "," + contents[2] + ");");
						i++;
						strcsv = brcsv.readLine();
					}
					brcsv.close();
				}
				str = br.readLine();
			}

			br.close();
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
