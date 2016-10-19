package com.ycxt.pos.device;

public class IDCardReaderSample {
//	IDCardReader.getInstance().searchCard(new IDCardReader.OnSearchListener() {
//	
//	@Override
//	public void onCrash() {
//		onDeviceServiceCrash();
//	}
//	
//	@Override
//	public void onFail(int code) {
//		showErrorMessage("ERROR - "+getErrorDescription(code));
//	}
//	
//	public String getErrorDescription(int code) {
//		switch(code) {
//		case ERROR_FAILED:
//			return "�������󣨲���ϵͳ����ȣ� ";
//		case ERROR_TIMEOUT:
//			return "������ʱ";
//		case ERROR_TRANERR:
//			return "���ݴ�ͳ����";
//		}
//		
//		return "unknown error ["+code+"]";
//	}
//	
//	@Override
//	public void onCardPass(byte[] data) {
//		try {
//			checkError(getReader().selectCard());
//			IDCardReader.IDCardInfo info = getReader().readCardInfo();
//			if(!info.isValid()) {
//				checkError(info.getErrorCode());
//			}
//			
//			displayMagCardInfo("READ SUCC / "+info.getName());
//		} catch (RequestException e) {
//			onCrash();
//		} catch (CheckErrorException e) {
//			displayMagCardInfo("ERROR / "+e.getMessage());
//		}
//	}
//	
//	private void checkError(int errorCode) throws CheckErrorException {
//		String message;
//		switch(errorCode) {
//		case IDCardReader.ERROR_NONE:
//			return;
//		case IDCardReader.ERROR_INVALID:
//			message = "�������󣬻������̴���";
//			return;
//		case IDCardReader.ERROR_TIMEOUT:
//			message = "������ʱ";
//		case IDCardReader.ERROR_TRANERR:
//			message = "���ݴ������";
//			return;
//		default:
//			message = "unknown error ["+errorCode+"]";
//		}
//		throw new CheckErrorException(message);
//	}
//	
//	class CheckErrorException extends Exception {
//		private static final long serialVersionUID = 1L;
//
//		public CheckErrorException(String message) {
//			super(message);
//		}
//	}
//});
//
}
