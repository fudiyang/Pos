package com.ycxt.pos.device;

import com.landicorp.android.eptapi.card.At1608Driver;
import com.landicorp.android.eptapi.device.InsertCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesBuffer;
import com.landicorp.android.eptapi.utils.IntegerBuffer;

import android.content.Context;

/**
 * �������At1608��ʾ�����룬���ڲ��˽�ʵ��ʹ�õĿ����֣���ȡ��Ϊ"MyAt1608Reader"
 * @author chenwei
 *
 */
public abstract class MyAt1608Reader {
	private InsertCardReader insertCardReader = InsertCardReader.getInstance();
	private InsertCardReader.OnSearchListener onSearchListener = new InsertCardReader.OnSearchListener() {
		
		@Override
		public void onCrash() {
			onDeviceServiceCrash();
		}
		
		@Override
		public void onFail(int code) {
			showErrorMessage("Ѱ�����󣬴�����Ϊ:"+code);
		}
		
		@Override
		public void onCardInsert() {
			// Ѱ���ɹ�����ȡAt1608����
			At1608Driver at1608 = (At1608Driver) insertCardReader.getDriver("AT1608");
			IntegerBuffer errorCount = new IntegerBuffer();
			BytesBuffer atr = new BytesBuffer();
			try {
				// �ϵ粢����at1608��صķ���
				checkAt1608Error(at1608.powerUp(At1608Driver.VOL_3, atr));
				checkAt1608Error(at1608.authentication(new byte[]{/* TODO ��ʵ����Կ */}, gcCalculator, errorCount));
				checkAt1608Error(at1608.verify(At1608Driver.FLAG_READPWD, new byte[]{/* TODO ��3�ֽ���Կ */}, errorCount));
				// TODO ʹ�����ַ�ʽ������Ĳ���
			} catch (RequestException e) {
				e.printStackTrace();
				onDeviceServiceCrash();
			// ������at1608�κ�һ�����÷��ط�0ʱ�����쳣�����checkAt1608Error
			} catch (CheckErrorException e) {
				e.printStackTrace();
				// �Ѵ�����Ϣ��ʾ����
				showErrorMessage(e.getMessage());
			}
		}
		
		/**
		 * �������룬�����0��Ҫ�׳��쳣
		 * @param errorCode	������
		 * @throws CheckErrorException	����쳣
		 */
		void checkAt1608Error(int errorCode) throws CheckErrorException {
			String msg = null;
			switch (errorCode) {
			case At1608Driver.ERROR_NONE:
				return;
			case At1608Driver.ERROR_NOPOWER:
				msg = "������, ��Ƭδ�ϵ�";
				break;
			case At1608Driver.ERROR_NOCARD:
				msg = "������, ȱ��";
				break;
			case At1608Driver.ERROR_FAILED:
				msg = "������, �������󣨲���ϵͳ����ȣ�";
				break;
			case At1608Driver.ERROR_ERRPARAM:
				msg = "������, ��������";
				break;
			case At1608Driver.ERROR_ERRTYPE:
				msg = "������, �����ʹ���";
				break;
			case At1608Driver.ERROR_WRITEFAIL:
				msg = "������, 1608��д����";
				break;
			case At1608Driver.ERROR_READFAIL:
				msg = "������, 1608��������";
				break;
			case At1608Driver.ERROR_OPERAFORBID:
				msg = "������, 1608����ֹ����";
				break;
			case At1608Driver.ERROR_FUSEDONE:
				msg = "������, 1608���Ѿ�����";
				break;
			case At1608Driver.ERROR_SECCODE_UNVERI:
				msg = "������, 1608��δУ�鱣����";
				break;
			case At1608Driver.ERROR_NOVERIFY:
				msg = "������, ��Ƭ����δУ��";
				break;
			case At1608Driver.ERROR_TIMEOUT:
				msg = "������, ��Ƭд�볬ʱ";
				break;
			case At1608Driver.ERROR_ACKERR:
				msg = "������, 1608��д������ACKʧ��";
				break;
			case At1608Driver.ERROR_READARERR:
				msg = "������, 1608����ARʧ��";
				break;
			case At1608Driver.ERROR_READPACERR:
				msg = "������, 1608����PACʧ��";
				break;
			case At1608Driver.ERROR_VERCOUNTOVL:
				msg = "������, 1608������У��Ĵ����Ѿ������޶�";
				break;
			case At1608Driver.ERROR_USERZONENOTSET:
				msg = "������, 1608���û���δ����";
				break;
			case At1608Driver.ERROR_NOAUTH:
				msg = "������, 1608��δ��֤����";
				break;
			case At1608Driver.ERROR_READAACERR:
				msg = "������, 1608����AACʧ��";
				break;
			case At1608Driver.ERROR_INITAUTHERR:
				msg = "������, 1608����֤��ʼ��ʧ��";
				break;
			case At1608Driver.ERROR_READONLY:
				msg = "������, �û���ֻ��";
				break;
			case At1608Driver.ERROR_READNCERR:
				msg = "������, 1608����NCʧ��";
				break;
			case At1608Driver.ERROR_READCIERR:
				msg = "������, 1608����Ciʧ��";
				break;
			// ��Щ������ʵ��������ͬ��ֵ�����յײ���Ƶ�
			// case At1608Driver.ERROR_OPERATIONFAIL: msg = "������, 1608������ʧ��";
			// break;
			// case At1608Driver.ERROR_RSTERR: msg = "������, 1608���ϵ���Ϣ����"; break;
			// case At1608Driver.ERROR_VERIFYFAIL: msg = "������, 1608����֤ʧ��";
			// break;
			// case At1608Driver.ERROR_READFUSEFAIL: msg =
			// "������, 1608����ȡFUSE��Ϣʧ��"; break;
			// case At1608Driver.ERROR_AUTHERR: msg = "������, 1608����֤ʧ��"; break;
			case At1608Driver.ERROR_OTHER:
				msg = "������, ����ʧ��";
				break;
			default:
				msg = "δ֪����:"+String.format("%02X", errorCode);
				break;
			}
			throw new CheckErrorException(msg);
		}
	};
	
	/**
	 * ����GC�õ�
	 */
	private At1608Driver.GcCalculator gcCalculator = new At1608Driver.GcCalculator() {
		
		@Override
		public byte[] onCalculate(byte[] key, byte[] nc) {
			byte[] gc = new byte[8];
			// TODO ����key��nc����gc��Ȼ�󷵻�
			// ...
			return gc;
		}
	};
	
	public MyAt1608Reader(Context context) {
		
	}
	
	/**
	 * �������
	 */
	public void start() {
		try {
			insertCardReader.searchCard(onSearchListener);
		} catch (RequestException e) {
			e.printStackTrace();
			onDeviceServiceCrash();
		}
	}

	/**
	 * Display info 
	 * @param info
	 */
	protected abstract void displayDeviceInfo(String info);
	
	protected abstract void onDeviceServiceCrash();
	
	protected abstract void showErrorMessage(String msg);
	
	/**
	 * �Զ�����쳣�����ڷ���ֵ���
	 * @author chenwei
	 *
	 */
	static class CheckErrorException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public CheckErrorException(String msg) {
			super(msg);
		}
	}
	
}
