package com.Jsu.framework.widget.wheel;

import android.content.Context;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class FileUtil {

	/**
	 * 删除文件
	 * 
	 * @param context
	 *            程序上下�?
	 * @param fileName
	 *            文件名，要在系统内保持唯�?
	 * @return boolean 存储成功的标�?
	 */
	public static boolean deleteFile(Context context, String fileName) {
		return context.deleteFile(fileName);
	}

	/**
	 * 文件是否存在
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean exists(Context context, String fileName) {
		return new File(context.getFilesDir(), fileName).exists();
	}

	/**
	 * 存储文本数据
	 * 
	 * @param context
	 *            程序上下�?
	 * @param fileName
	 *            文件名，要在系统内保持唯�?
	 * @param content
	 *            文本内容
	 * @return boolean 存储成功的标�?
	 */
	public static boolean writeFile(Context context, String fileName, String content) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			byte[] byteContent = content.getBytes();
			fos.write(byteContent);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * 存储文本数据
	 * @param content
	 *            文本内容
	 * @return boolean 存储成功的标�?
	 */
	public static boolean writeFile(String filePath, String content) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			byte[] byteContent = content.getBytes();
			fos.write(byteContent);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * 读取文本数据
	 * 
	 * @param context
	 *            程序上下�?
	 * @param fileName
	 *            文件�?
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readFile(Context context, String fileName) {
		if (!exists(context, fileName)) {
			return null;
		}
		FileInputStream fis = null;
		String content = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = fis.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				fis.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 读取文本数据
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readFile(String filePath) {
		if (filePath == null || !new File(filePath).exists()) {
			return null;
		}
		FileInputStream fis = null;
		String content = null;
		try {
			fis = new FileInputStream(filePath);
			if (fis != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = fis.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				fis.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 读取文本数据
	 * 
	 * @param context
	 *            程序上下�?
	 * @param fileName
	 *            文件�?
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readAssets(Context context, String fileName) {
		InputStream is = null;
		String content = null;
		try {
			is = context.getAssets().open(fileName);
			if (is != null) {

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true) {
					int readLength = is.read(buffer);
					if (readLength == -1)
						break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				is.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 存储单个Parcelable对象
	 * 
	 * @param context
	 *            程序上下�?
	 * @param fileName
	 *            文件名，要在系统内保持唯�?
	 * @param parcelObject
	 *            对象必须实现Parcelable
	 * @return boolean 存储成功的标�?
	 */
	public static boolean writeParcelable(Context context, String fileName, Parcelable parcelObject) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			Parcel parcel = Parcel.obtain();
			parcel.writeParcelable(parcelObject, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
			byte[] data = parcel.marshall();
			fos.write(data);

			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return success;
	}

	/**
	 * 存储List对象
	 * 
	 * @param context
	 *            程序上下�?
	 * @param fileName
	 *            文件名，要在系统内保持唯�?
	 * @param list
	 *            对象数组集合，对象必须实现Parcelable
	 * @return boolean 存储成功的标�?
	 */
	public static boolean writeParcelableList(Context context, String fileName, List<Parcelable> list) {
		boolean success = false;
		FileOutputStream fos = null;
		try {
			if (list instanceof List) {
				fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
				Parcel parcel = Parcel.obtain();
				parcel.writeList(list);
				byte[] data = parcel.marshall();
				fos.write(data);

				success = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return success;
	}

	/**
	 * 读取单个数据对象
	 * 
	 * @param context
	 *            程序上下�?
	 * @param fileName
	 *            文件�?
	 * @return Parcelable, 读取到的Parcelable对象，失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static Parcelable readParcelable(Context context, String fileName, ClassLoader classLoader) {
		Parcelable parcelable = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {
				bos = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				int bytesRead;
				while ((bytesRead = fis.read(b)) != -1) {
					bos.write(b, 0, bytesRead);
				}

				byte[] data = bos.toByteArray();

				Parcel parcel = Parcel.obtain();
				parcel.unmarshall(data, 0, data.length);
				parcel.setDataPosition(0);
				parcelable = parcel.readParcelable(classLoader);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			parcelable = null;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return parcelable;
	}

	/**
	 * 读取数据对象列表
	 * 
	 * @param context
	 *            程序上下�?
	 * @param fileName
	 *            文件�?
	 * @return List, 读取到的对象数组，失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static List<Parcelable> readParcelableList(Context context, String fileName, ClassLoader classLoader) {
		List<Parcelable> results = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = context.openFileInput(fileName);
			if (fis != null) {
				bos = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				int bytesRead;
				while ((bytesRead = fis.read(b)) != -1) {
					bos.write(b, 0, bytesRead);
				}

				byte[] data = bos.toByteArray();

				Parcel parcel = Parcel.obtain();
				parcel.unmarshall(data, 0, data.length);
				parcel.setDataPosition(0);
				results = parcel.readArrayList(classLoader);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			results = null;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return results;
	}

	public static boolean saveSerializable(Context context, String fileName, Serializable data) {
		boolean success = false;
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
			oos.writeObject(data);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	public static Serializable readSerialLizable(Context context, String fileName) {
		Serializable data = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(context.openFileInput(fileName));
			data = (Serializable) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return data;
	}

	/**
	 * 从assets里边读取字符�?
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getFromAssets(Context context, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFile
	 * @param dstFile
	 * @return
	 */
	public static boolean copy(String srcFile, String dstFile) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {

			File dst = new File(dstFile);
			if (!dst.getParentFile().exists()) {
				dst.getParentFile().mkdirs();
			}

			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(dstFile);

			byte[] buffer = new byte[1024];
			int len = 0;

			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return true;
	}


	/**
	 * 应用默认保存内容的文件夹
	 */
	public static String DEFAULT_SAVE_FOLDER = "Jsu";

	/***
	 * 获取本应用保存资源的文件夹的绝对路径
	 */
	public static String getAppResourcePath(Context context) {
		String path = "";
		if (path.equals("")) {

			// 判断配置好的保存文件的路径

			// 判断SD卡是否存在
			if (checkSDCardExists()) {
				String root = getSDRoot();
				if (root != null && !root.equals("")) {
					path = root + File.separator + DEFAULT_SAVE_FOLDER + File.separator;
				} else {
					path = context.getCacheDir().getAbsolutePath() + File.separator + DEFAULT_SAVE_FOLDER + File.separator;
				}
			} else {
				path =  context.getCacheDir().getAbsolutePath() + File.separator + DEFAULT_SAVE_FOLDER + File.separator;
			}

			if (createPath(path) == PathStatus.ERROR) {
				path =  context.getCacheDir().getAbsolutePath() + File.separator + DEFAULT_SAVE_FOLDER + File.separator;
				if (createPath(path) == PathStatus.ERROR) {
					//ToastBroadCastReceiver.sendToastMsg(.getString(R.string.msg_tost_init_folder_error) + " " + path);
				}
			}
		}
		return path;
	}


	/**
	 * 获取SD卡的根目录
	 */
	private static String getSDRoot() {

		File file = null;

		try {
			file = Environment.getExternalStorageDirectory();
		} catch (Exception e) {

		}
		if (file != null) {
			return file.getAbsolutePath();
		}
		return null;
	}

	/**
	 * 创建目录
	 */
	private static PathStatus createPath(String newPath) {
		if (!newPath.equals("")) {
			File path = new File(newPath);
			if (path.exists()) {
				return PathStatus.EXITS;
			}
			if (path.mkdir()) {
				return PathStatus.SUCCESS;
			} else {
				return PathStatus.ERROR;
			}
		}
		return PathStatus.ERROR;
	}

	/**
	 * 检查是否安装SD卡
	 */
	private static boolean checkSDCardExists() {
		boolean status = false;
		try {
			String sDCardStatus = Environment.getExternalStorageState();
			if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {

		}
		return status;
	}

	public static byte[] getByte(File file) {
		byte[] bytes = null;
		if (file != null) {
			try {
				InputStream is = new FileInputStream(file);
				int length = (int) file.length();
				if (length > Integer.MAX_VALUE) // 当文件的长度超过了int的最大值
				{
					System.out.println("this file is max ");
					return null;
				}
				bytes = new byte[length];
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
					offset += numRead;
				}
				// 如果得到的字节长度和file实际的长度不一致就可能出错了
				if (offset < bytes.length) {
					System.out.println("file length is error");
					return null;
				}
				is.close();
			} catch (Exception e) {

			}
		}
		return bytes;
	}

	public enum PathStatus {
		SUCCESS, EXITS, ERROR
	}
}
