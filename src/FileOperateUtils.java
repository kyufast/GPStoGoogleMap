import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileOperateUtils {

	/**
	 * �R�s�[���̃p�X[srcPath]����R�s�[��̃p�X[destPath]�փt�@�C���̃R�s�[ 
	 * ���s���܂��B
	 * @param srcPath    �R�s�[���̃p�X
	 * @param destPath    �R�s�[��̃p�X
	 * @param bufferSize    �f�[�^�̓ǂݍ��݃o�b�t�@�T�C�Y(KB)�ł��B
	 * @throws IOException    ���炩�̓��o�͏�����O�����������ꍇ
	 */
	public static void copyStream(String srcPath, String destPath,
	    int bufferSize) throws IOException {
	    InputStream in = new FileInputStream(srcPath);
	    OutputStream os = new FileOutputStream(destPath);
	    copyStream(in, os, bufferSize);
	}

	/**
	 * ���̓X�g���[������o�̓X�g���[���փf�[�^�̏������݂��s���܂��B
	 * ���A�R�s�[�����I����A���́E�o�̓X�g���[������܂��B
	 * @param in    ���̓X�g���[��
	 * @param os    �o�̓X�g���[��
	 * @param bufferSize �f�[�^�̓ǂݍ��݃o�b�t�@�T�C�Y(KB)�ł��B
	 * @throws IOException    ���炩�̓��o�͏�����O�����������ꍇ
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
