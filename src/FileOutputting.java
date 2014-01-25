import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FileOutputting extends JFrame implements ActionListener{
	
	JLabel outputjlabel;
	
	public FileOutputting() {
		
		JButton button = new JButton("file select");
	    button.addActionListener(this);
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(button);

	    outputjlabel = new JLabel();

	    JPanel labelPanel = new JPanel();
	    labelPanel.add(outputjlabel);

	    getContentPane().add(labelPanel, BorderLayout.CENTER);
	    getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
	    
		// TODO 自動生成されたコンストラクター・スタブ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(10, 10, 300, 200);
	    this.setTitle("出力ファイル");
//	    this.setVisible(true);
	}

	@Override
	  public void actionPerformed(ActionEvent e){
	    JFileChooser filechooser = new JFileChooser();
	    filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	    int selected = filechooser.showOpenDialog(this);
	    if (selected == JFileChooser.APPROVE_OPTION){
	      File file = filechooser.getSelectedFile();
	      outputjlabel.setText(file.getName());
	    }else if (selected == JFileChooser.CANCEL_OPTION){
	    	outputjlabel.setText("キャンセルされました");
	    }else if (selected == JFileChooser.ERROR_OPTION){
	    	outputjlabel.setText("エラー又は取消しがありました");
	    }
	  }
	public JLabel getOutPutJLabel(){
		return outputjlabel;
	}
}
