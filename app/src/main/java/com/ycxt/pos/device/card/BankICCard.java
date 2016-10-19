package com.ycxt.pos.device.card;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.landicorp.android.eptapi.card.CpuCardDriver;
import com.landicorp.android.eptapi.emv.process.EMVL2Process;
import com.landicorp.android.eptapi.emv.process.EMVL2Process.EMVL2ProcessHandler;
import com.landicorp.android.eptapi.emv.process.data.BaseParameter;
import com.landicorp.android.eptapi.emv.process.data.CAPKey;
import com.landicorp.android.eptapi.emv.process.data.CHVData;
import com.landicorp.android.eptapi.emv.process.data.CVMData;
import com.landicorp.android.eptapi.emv.process.data.CandidateAIDInfo;
import com.landicorp.android.eptapi.emv.process.data.DisplayMessage;
import com.landicorp.android.eptapi.emv.process.data.FinalSelectData;
import com.landicorp.android.eptapi.emv.process.data.GPOParameter;
import com.landicorp.android.eptapi.emv.process.data.HostData;
import com.landicorp.android.eptapi.emv.process.data.PBOCParameter;
import com.landicorp.android.eptapi.emv.process.data.RecordData;
import com.landicorp.android.eptapi.emv.process.data.SelectedAID;
import com.landicorp.android.eptapi.emv.process.data.StartEmvParameter;
import com.landicorp.android.eptapi.emv.process.data.TerminalRiskManageData;
import com.landicorp.android.eptapi.emv.process.data.TransactionData;
import com.landicorp.android.eptapi.emv.process.data.VISAParameter;
import com.landicorp.android.eptapi.emv.process.data.VerifyOfflinePinResult;
import com.landicorp.android.eptapi.utils.BytesUtil;
import com.landicorp.android.eptapi.utils.StringUtil;

public class BankICCard implements EMVL2ProcessHandler {
	private EMVL2Process emvl2;
	private Context context;
	private StartEmvParameter startTransData = new StartEmvParameter();
	public BankICCard(Context context, CpuCardDriver<?> driver) {
		emvl2 = new EMVL2Process(context, driver, this);
		this.context = context;
	}
	
	public void startSimpleProcess() {
//		startTransaction();
		setBaseParameter();
		setPBOCParameter();
		setVISAParameter();
		setTLVs();
		setDOL();
		setAID();
		setCAIndexList();
		startTransaction();
		setCapk();
	}
	
	private void setCapk(){
        CAPKey cap = new CAPKey();
        String exp = "03";
        String date = "20301230";
        String hash = "ee23b616c95c02652ad18860e48787c079e8e85a";
        String modulus = "b61645edfd5498fb246444037a0fa18c0f101ebd8efa54573ce6e6a7fbf63ed21d66340852b0211cf5eef6a1cd989f66af21a8eb19dbd8dbc3706d135363a0d683d046304f5a836bc1bc632821afe7a2f75da3c50ac74c545a754562204137169663cfcc0b06e67e2109eba41bc67ff20cc8ac80d7b6ee1a95465b3b2657533ea56d92d539e5064360ea4850fed2d1bf";
        String rid = "a000000333";
//        byte HashFlg = 1;
        String HashFlg = "01";
        
//        byte Index = 8;
        String Index = "08";
        cap.setExp(BytesUtil.hexString2Bytes(exp));
        cap.setExpDate(BytesUtil.hexString2Bytes(date));
        cap.setHash(BytesUtil.hexString2Bytes(hash));
        cap.setMod(BytesUtil.hexString2Bytes(modulus));
        cap.setRid(BytesUtil.hexString2Bytes(rid));
        cap.setHashFlg(BytesUtil.hexString2Bytes(HashFlg)[0]);
        cap.setIndex(BytesUtil.hexString2Bytes(Index)[0]);
        
        int result = emvl2.setCAPubKey(cap);
        Log.d("TEST", "result - "+result);
        StringBuilder builder = new StringBuilder("CAP KEY : s ");
        byte[] data = cap.toBinary();
        builder.append(data.length);
        builder.append("|");
        for (byte d : data) {
        	builder.append(String.format("%02X", d))
        	.append(", ");
        }
        Log.d("TEST", builder.toString());
    }
	
	public void abort() {
		emvl2.abort();
	}
	
	private void startTransaction() {
		abort();
		startTransData.setICCLogQueryEnabled(true);
		startTransData.getGPOParameter().setDate(BytesUtil.hexString2Bytes("150420"));
		startTransData.getGPOParameter().setTime(BytesUtil.hexString2Bytes("205959"));
		startTransData.getGPOParameter().setTrace(BytesUtil.hexString2Bytes("00000001"));
		startTransData.getGPOParameter().setAmount(BytesUtil.hexString2Bytes("000000000001"));
		
		emvl2.start(startTransData);
	}

	private void setBaseParameter() {
		BaseParameter param = new BaseParameter();
		param.setTmType((byte) 22);
		param.setTmCap(BytesUtil.hexString2Bytes("E0E9C8"));
		param.setCntCode(BytesUtil.hexString2Bytes("0156"));
		param.setCurCode(BytesUtil.hexString2Bytes("0156"));
		param.setFloorLmt(BytesUtil.hexString2Bytes("000000010000"));
		param.setRandLmt(BytesUtil.hexString2Bytes("000000005000"));
		param.setRandPer((byte) 30);
		param.setRandPerMax((byte) 90);
		
		emvl2.setBaseParameter(param);
	}
	
	private void setPBOCParameter() {
		PBOCParameter param = new PBOCParameter();
		param.setSMSup((byte) 1);
		param.setECashSup((byte) 1);
		param.setECashLmt(BytesUtil.hexString2Bytes("000000005000"));
		param.setTransLmt(BytesUtil.hexString2Bytes("000000300000"));
		param.setCVMLmt(BytesUtil.hexString2Bytes("000000005000"));
		param.setFloorLmt(BytesUtil.hexString2Bytes("000000010000"));
		
		emvl2.setPBOCParameter(param);
	}

	private void setVISAParameter() {
		VISAParameter param = new VISAParameter();
		param.setCVN17Flag((byte) 1);
		param.setTrack1Flag((byte) 1);
		param.setTrack2Flag((byte) 1);
		param.setTTQ(BytesUtil.hexString2Bytes("26004000"));
		param.setFloorLmt(BytesUtil.hexString2Bytes("000000003000"));
		param.setTransLmt(BytesUtil.hexString2Bytes("000000002000"));
		param.setCVMLmt(BytesUtil.hexString2Bytes("000000001000"));
		param.setRCP(BytesUtil.hexString2Bytes("7C00"));

		emvl2.setVISAParameter(param);
	}

	private void setTLVs() {
		emvl2.setTLV(EMVL2Process.EMV_KERNELID_EMV, "9F39",
				BytesUtil.hexString2Bytes("51"));// ����ģʽ
		emvl2.setTLVWithGBKValue(EMVL2Process.EMV_KERNELID_EMV, "9F1E", "E5300001");// �ն����к�
		emvl2.setTLV(EMVL2Process.EMV_KERNELID_VISA, "DF06",
				BytesUtil.hexString2Bytes("7C00"));// �ǽӶ��������ò���
	}

	private void setDOL() {
		emvl2.setDDOL(BytesUtil.hexString2Bytes("9F3704"));
		emvl2.setTDOL(BytesUtil.hexString2Bytes("9F0802"));
	}

	private void setAID() {
		emvl2.addAID(BytesUtil.hexString2Bytes("A0000003330101"), true);
		emvl2.addAID(BytesUtil.hexString2Bytes("A000000333010102"), true);
		emvl2.addAID(BytesUtil.hexString2Bytes("A00000000310"), false);
	}

	private void setCAIndexList() {
		emvl2.updateCAIndexList(BytesUtil.hexString2Bytes("A000000333"),
				hexStringToIntArray("02030508805758616263646566"));
		emvl2.updateCAIndexList(BytesUtil.hexString2Bytes("A000000003"),
				hexStringToIntArray("010708095153929495969799"));
	}
	
	private int[] hexStringToIntArray(String hexString) {
		byte[] array = BytesUtil.hexString2Bytes(hexString);
		int[] result = new int[array.length];
		for (int i=0; i<result.length; i++) {
			result[i] = array[i] & 0xff;
		}
		return result;
	}
	
	@Override
	public void onWaitCard(int flag) {
		// do nothing...
	}

	@Override
	public void onAppSelection(boolean reselect,
			List<CandidateAIDInfo> candList, byte[] aidSelected) {
		
		SelectedAID selectedAID = new SelectedAID();
		selectedAID.setAID(candList.get(0).getAID());
		emvl2.next(selectedAID);
	}

	@Override
	public void onFinalSelect(FinalSelectData finalData) {
		GPOParameter param = startTransData.getGPOParameter();
		param.setAmount(BytesUtil.hexString2Bytes("000000000102"));
		emvl2.next(param);
	}

	@Override
	public void onReadRecord(RecordData recordData) {
		Toast.makeText(context, StringUtil.fromGBK(recordData.getPan()), Toast.LENGTH_SHORT).show();
		TerminalRiskManageData data = new TerminalRiskManageData();
		emvl2.next(data);
	}

	@Override
	public void onCardHolderVerify(CVMData cvm) {
		CHVData data = new CHVData();
		data.setState((byte) 1);
		
		switch((int)cvm.getCVM()) {
		case EMVL2Process.EMV_CVMFLAG_OFFLINEPIN:
			data.setOffPIN(StringUtil.getGBK("123456"));
			break;
		case EMVL2Process.EMV_CVMFLAG_ONLINEPIN:
			break;
		}
		emvl2.next(data);
	}

	@Override
	public void onOnlineProcess(TransactionData transactionData) {
		HostData data = new HostData();
		data.setAuthFlag((byte) 1);
		data.setARC(StringUtil.getGBK("00"));
		data.setIAC(StringUtil.getGBK("12345678"));
		
		emvl2.next(new HostData());		
	}

	@Override
	public void onDisplayMessage(DisplayMessage message) {
		Toast.makeText(context, StringUtil.fromGBK(message.getData()), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEndProcess(int result, TransactionData transData) {
		Log.d("TEST", emvl2.getKernelLog());
		Toast.makeText(context, "END / result = "+result, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onObtain(int arg0, byte[] arg1) {
		
	}

	@Override
	public void onSendOut(int arg0, byte[] arg1) {
		
	}

	@Override
	public void onVerifyOfflinePin(int arg0, byte[] arg1, CAPKey arg2, VerifyOfflinePinResult arg3) {
	}
}
