package com.ycxt.pos.device;

import android.content.Context;
import android.util.Log;


import com.landicorp.android.eptapi.card.InsertCpuCardDriver;
import com.landicorp.android.eptapi.card.InsertDriver;
import com.landicorp.android.eptapi.card.PSamCard;
import com.landicorp.android.eptapi.card.Sim4442Driver;
import com.landicorp.android.eptapi.device.InsertCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesUtil;
import com.ycxt.pos.device.card.BankICCard;
import com.ycxt.pos.device.card.ContactCpuCard;
import com.ycxt.pos.device.card.PSamCardReader;


/**
 * This sample show that how to use Contact IC Card
 * @author chenwei
 *
 */
public abstract class ContactICCardSample extends AbstractSample {
	private InsertCpuCardDriver cpuDriver;
	private int powerupMode = InsertCpuCardDriver.MODE_DEFAULT;
	private int powerupMode1 = InsertCpuCardDriver.MODE_ISO;
	private int powerupVol = InsertCpuCardDriver.VOL_DEFAULT;
	
	/**
	 * Create a listener to listen the port of contact ic card.
	 */
	private InsertCardReader.OnSearchListener onSearchListener = new InsertCardReader.OnSearchListener() {
		
		@Override
		public void onCrash() {
			onDeviceServiceCrash();
		}
		
		@Override
		public void onFail(int code) {
			displayICInfo("SEARCH ERROR - "+getErrorDescription(code));
		}
		
		@Override
		public void onCardInsert() {
			displayICInfo("IC card detected, wait for operations.");
			try {
				executeAsCpuCard(getReader().getDriver("CPU"));
//				executeAsAT102(getReader().getDriver("AT102"));
//				executeAsSIM4442(getReader().getDriver("SIM4442"));
			} catch (RequestException e) {
				e.printStackTrace();
				onDeviceServiceCrash();
			} catch (CheckErrorException e) {
				e.printStackTrace();
				displayICInfo(e.getMessage());
			}
		}
		
		public void executeAsCpuCard(InsertDriver driver) throws RequestException, CheckErrorException {

			cpuDriver = (InsertCpuCardDriver) driver;
			cpuDriver.setPowerupMode(powerupMode);
			cpuDriver.setPowerupVoltage(powerupVol);
			cpuDriver.powerup(onPowerupListener);
		}

		
//		void executeAsAT102(InsertDriver driver) throws RequestException, CheckErrorException {
//			At1604Driver at102 = (At1604Driver) driver;
//			BytesBuffer atr = new BytesBuffer();
//			if(0 != at102.powerUp(At1604Driver.VOL_5, atr)) {
//				return;
//			}
//			BytesBuffer data = new BytesBuffer();
//			at102.read(93, 10, data);
//		}
//		
//		void executeAsSIM4442(InsertDriver driver) throws RequestException, CheckErrorException {
//			Sim4442Driver sim4442 = (Sim4442Driver) driver;
//			BytesBuffer atr = new BytesBuffer();
//			checkSIM4442Error(sim4442.powerUp(Sim4442Driver.VOL_5, atr));
//			
//			BytesBuffer data = new BytesBuffer();
//			checkSIM4442Error(sim4442.read(10, 20, data));
//			displayICInfo("DATA READ : "+data.toHexString());
//			
//			IntegerBuffer errorCount = new IntegerBuffer();
//			checkSIM4442Error(sim4442.verify(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, errorCount));
//			displayICInfo("VERIFY SUCCESS ! errcnt = "+errorCount.getData());
//			
//			BooleanBuffer changable = new BooleanBuffer();
//			checkSIM4442Error(sim4442.readStatus(10, changable));
//			displayICInfo("Addr 10 Changable = "+changable.getData());
//			
//			checkSIM4442Error(sim4442.write(10, new byte[]{1, 2, 3, 4, 5, 6}));		
//			displayICInfo("WRITE SUCCESS !");
//			
//			checkSIM4442Error(sim4442.read(10, 20, data));
//			displayICInfo("DATA READ : "+data.toHexString());
//		}
		
		public String getErrorDescription(int code){
			if(code == ERROR_FAILED){
				return "Other error(OS error,etc)"; 
			}
			
			if(code == ERROR_TIMEOUT){			 
				return "Communication error"; 		
			}
			return "unknown error ["+code+"]";
		}
		
		void checkSIM4442Error(int error) throws CheckErrorException {
			String msg = "";
			switch (error) {
			case Sim4442Driver.ERROR_NONE:
				return;
//			case Sim4442Driver.ERROR_ERROR:
//				msg = "失败";
//				break;
			case Sim4442Driver.ERROR_NOCARD:
				msg = "缺卡";
				break;
			case Sim4442Driver.ERROR_ERRTYPE:
				msg = "卡类型错误";
				break;
			case Sim4442Driver.ERROR_ERRPARAM:
				msg = "参数错误（例如address错误)";
				break;
			case Sim4442Driver.ERROR_FAILED:
				msg = "其他错误（操作系统错误等";
				break;
			case Sim4442Driver.ERROR_TIMEOUT:
				msg = "与外置读卡器通信错误";
				break;
			case Sim4442Driver.ERROR_NOPOWER:
				msg = "卡未上电";
				break;
			case Sim4442Driver.ERROR_CHGDISABLE:
				msg = "地址已被事先设置为不可修改";
				break;
			case Sim4442Driver.ERROR_NOVERIFY:
				msg = "卡未校验";
				break;
			}
			throw new CheckErrorException(msg);
		}
	};

	/**
	 * Create a listener to listen the powerup result.
	 */
	InsertCpuCardDriver.OnPowerupListener onPowerupListener = new InsertCpuCardDriver.OnPowerupListener() {

		@Override
		public void onCrash() {
			onDeviceServiceCrash();
		}

		@Override
		public void onPowerup(int protocol, byte[] art) {
			/**
			 * Powerup by PSamCard object.
			 */
			if(cpuDriver instanceof PSamCard) {
				PSamCardReader reader = new PSamCardReader(cpuDriver) {
					@Override
					public void showFinalMessage(String msg) {
						ContactICCardSample.this.displayICInfo(msg);
					}

					@Override
					protected void showErrorMessage(String msg) {
						ContactICCardSample.this.showErrorMessage(msg);
					}

					@Override
					protected void onServiceCrash() {
						onDeviceServiceCrash();
					}

					@Override
					protected void onDataRead(byte[] data) {
						ContactICCardSample.this.displayICInfo("KEY-A - "+BytesUtil.bytes2HexString(data));
					}
				};

				byte[] desKey = new byte[]{(byte)0x10, (byte)0x03, (byte)0x03, (byte)0x06, (byte)0x19};
				reader.startCalcKeyA(desKey);
				return;
			}

			/**
			 * Create a cpu card object to do some operations.
			 */
			BankICCard bankICCard = new BankICCard(getContext(), cpuDriver);
			bankICCard.startSimpleProcess();
//			ContactCpuCard card = new ContactCpuCard(cpuDriver,activity) {
//
//				@Override
//				protected void onServiceCrash() {
//					ContactICCardSample.this.onDeviceServiceCrash();
//				}
//
//				@Override
//				protected void showErrorMessage(String msg) {
//					ContactICCardSample.this.showErrorMessage(msg);
//				}
//
//				@Override
//				public void showFinalMessage(String msg) {
//					ContactICCardSample.this.displayICInfo(msg);
//				}
//
//			};
//          card.OperationCard();
//		    card.startRead();
//   		card.startWrite();
//		    card.ZiGuanCard_read();
		}

		@Override
		public void onFail(int code) {
			displayICInfo("POWER UP ERROR - "+getErrorDescription(code));
			if(cpuDriver instanceof PSamCard) {
				displayICInfo("There is no PSAM card in SAM1 slot, please check it!");
				return;
			}
		}

		public String getErrorDescription(int code){
			switch(code) {
			case ERROR_NOPOWER : return "Hardware error";
			case ERROR_ATRERR : return "ATR error";
			case ERROR_ATRERR_S : return "ATR error in SHB mode";
			case ERROR_NOCARD : return "The card is not present";
			case ERROR_FAILED : return "Other error(OS error,etc)";
			case ERROR_ERRTYPE : return "Card type is not right";
			case ERROR_TIMEOUT : return "Communication error";
			}
			return "unknown error ["+code+"]";
		}
	};


	InsertCpuCardDriver.OnPowerupListener onPowerupListener1 = new InsertCpuCardDriver.OnPowerupListener() {

		@Override
		public void onCrash() {
			onDeviceServiceCrash();
		}

		@Override
		public void onPowerup(int protocol, byte[] art) {
			/**
			 * Powerup by PSamCard object.
			 */
			if(cpuDriver instanceof PSamCard) {
				PSamCardReader reader = new PSamCardReader(cpuDriver) {
					@Override
					public void showFinalMessage(String msg) {
						ContactICCardSample.this.displayICInfo(msg);
					}

					@Override
					protected void showErrorMessage(String msg) {
						ContactICCardSample.this.showErrorMessage(msg);
					}

					@Override
					protected void onServiceCrash() {
						onDeviceServiceCrash();
					}

					@Override
					protected void onDataRead(byte[] data) {
						ContactICCardSample.this.displayICInfo("KEY-A - "+BytesUtil.bytes2HexString(data));
					}
				};

				byte[] desKey = new byte[]{(byte)0x10, (byte)0x03, (byte)0x03, (byte)0x06, (byte)0x19};
				reader.startCalcKeyA(desKey);
				return;
			}

			/**
			 * Create a cpu card object to do some operations.
			 */
			BankICCard bankICCard = new BankICCard(getContext(), cpuDriver);
			bankICCard.startSimpleProcess();
			ContactCpuCard card1 = new ContactCpuCard(cpuDriver) {

				@Override
				protected void onServiceCrash() {
					ContactICCardSample.this.onDeviceServiceCrash();
				}

				@Override
				protected void showErrorMessage(String msg) {
					ContactICCardSample.this.showErrorMessage(msg);
				}

				@Override
				public void showFinalMessage(String msg) {
					ContactICCardSample.this.displayICInfo(msg);
				}

			};
			Log.e("msg","读卡完毕");
//			card1.startRead();
   		    card1.startWrite();
//		    card.ZiGuanCard_read();
		}

		@Override
		public void onFail(int code) {
			displayICInfo("POWER UP ERROR - "+getErrorDescription(code));
			if(cpuDriver instanceof PSamCard) {
				displayICInfo("There is no PSAM card in SAM1 slot, please check it!");
				return;
			}
		}

		public String getErrorDescription(int code){
			switch(code) {
				case ERROR_NOPOWER : return "Hardware error";
				case ERROR_ATRERR : return "ATR error";
				case ERROR_ATRERR_S : return "ATR error in SHB mode";
				case ERROR_NOCARD : return "The card is not present";
				case ERROR_FAILED : return "Other error(OS error,etc)";
				case ERROR_ERRTYPE : return "Card type is not right";
				case ERROR_TIMEOUT : return "Communication error";
			}
			return "unknown error ["+code+"]";
		}
	};
	
	public ContactICCardSample(Context context) {
		super(context);
	}

	/**
	 * Search contact cpu card.
	 */
	public void searchCpuCard() {
		try {
			// Start search with 'onSearchListener'
            Log.e("msg","fdfffffafdfaf");
			InsertCardReader.getInstance().searchCard(onSearchListener);
		} catch (RequestException e) {
			e.printStackTrace();
			onDeviceServiceCrash();
		}
	}
	
	/**
	 * Start PSAM card operations.
	 * It will read the card in SAM1.
	 */
	public void readPSAMCard() {
		try {
			cpuDriver = PSamCard.getCard(1);
            cpuDriver.setPowerupMode(powerupMode);
			cpuDriver.setPowerupVoltage(powerupVol);
			cpuDriver.powerup(onPowerupListener);
		} catch (RequestException e) {
			e.printStackTrace();
			onDeviceServiceCrash();
		}
	}

	/**
	 * Stop contact ic card search.
	 */
	public void stopSearch() {
		try {
			InsertCardReader.getInstance().stopSearch();
		} catch (RequestException e) {
			e.printStackTrace();
			onDeviceServiceCrash();
		}
	}

	/**
	 * Display final info.
	 * @param info
	 */
	protected abstract void displayICInfo(String info);

	public void setToEMVMode() {
		powerupMode = InsertCpuCardDriver.MODE_EMV;
	}
	
	public void setToSHBMode() {
		powerupMode = InsertCpuCardDriver.MODE_SHB;
	}

	public void setToBPS19200Mode() {
		powerupMode = InsertCpuCardDriver.MODE_BPS_192;
	}

	public void setTo3VOL() {
		powerupVol = InsertCpuCardDriver.VOL_3;
	}
	
	public void setTo1_8VOL() {
		powerupVol = InsertCpuCardDriver.VOL_18;
	}

	public void setTo5VOL() {
		powerupVol = InsertCpuCardDriver.VOL_5;
	}
	
	class CheckErrorException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CheckErrorException(String message) {
			super(message);
		}

	}
	
}
