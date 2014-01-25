import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileOperateUtils {

	/**
	 * コピー元のパス[srcPath]からコピー先のパス[destPath]へファイルのコピー 
	 * を行います。
	 * @param srcPath    コピー元のパス
	 * @param destPath    コピー先のパス
	 * @param bufferSize    データの読み込みバッファサイズ(KB)です。
	 * @throws IOException    何らかの入出力処理例外が発生した場合
	 */
	public static void copyStream(String srcPath, String destPath,
	    int bufferSize) throws IOException {
	    InputStream in = new FileInputStream(srcPath);
	    OutputStream os = new FileOutputStream(destPath);
	    copyStream(in, os, bufferSize);
	}

	/**
	 * 入力ストリームから出力ストリームへデータの書き込みを行います。
	 * 尚、コピー処理終了後、入力・出力ストリームを閉じます。
	 * @param in    入力ストリーム
	 * @param os    出力ストリーム
	 * @param bufferSize データの読み込みバッファサイズ(KB)です。
	 * @throws IOException    何らかの入出力処理例外が発生した場合
	 */
	public static void copyStream(InputStream in, OutputStream os,
	    int bufferSize) throws IOException {
	    int len = -1;
	    byte[] b = new byte[bufferSize * 1024];
	    try {
	        while ((len = in.read(b, 0, b.length)) != -1) {
	            os.write(b, 0, len);
	        }
	        os.flush();
	    } finally {
	        if (in != null) {
	            try {
	                in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (os != null) {
	            try {
	                os.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

}
