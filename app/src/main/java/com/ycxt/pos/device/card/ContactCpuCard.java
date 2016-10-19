package com.ycxt.pos.device.card;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.landicorp.android.eptapi.card.InsertCpuCardDriver;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesUtil;
import com.landicorp.android.scan.util.LogUtils;
import com.ycxt.pos.activity.ContactICCardActivity;
import com.ycxt.pos.activity.PrinterActivity;
import com.ycxt.pos.activity.QueryResultsActivity;
import com.ycxt.pos.contans.Contans;
import com.ycxt.pos.date.MyDateFormat;
import com.ycxt.pos.framework.utils.utils.IntentUtils;
import com.ycxt.pos.utils.Changliang;
import com.ycxt.pos.utils.Convert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Date;

/**
 * This code sample show how to exchange APDU with the cpu card.
 * But this code inside the operation is not complete for a real cpu card.
 * 
 * @author chenwei
 *
 */
public abstract class ContactCpuCard extends InsertCpuCardDriver.OnExchangeListener{
	private InsertCpuCardDriver driver;
	private ResponseHandler respHandler;
	private Activity activity;
	private int CARD_SW1;
	private int CARD_SW2;
	String C_card1,C_card2,selectMFile,selectInfoFile,selectDF,selectWrite,selectPure,selectGuid,random2
			,K1,K2,random4,random5,random6,random7,authData,DIV,data1,data2,data3,file1,file2,file3,file4
			,Z_card1,Z_card2,UserReset8,P_Random,P_Random1,P_Random2,P_Random3,P_Random4,P_Random5,Encdata,Encdata1,Encdata2,str1,str2,str10,str20,str100,str200,Z_K10,Z_K100,Z_K1,Z_K2
			,xiaxingFileMsg1,xiaxingFileMsg2,xiaxingFileMsg3,Mac1,Mac2,Mac7;
	String [] receiveFrom82;
	public ContactCpuCard(InsertCpuCardDriver driver,Activity activity) {
		this.driver = driver;
		this.activity=activity;
	}
	public ContactCpuCard(InsertCpuCardDriver driver) {
		this.driver = driver;

	}

	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case 1:
					IntentUtils.openActivity(activity, PrinterActivity.class);
					break;
				case 2:
					Toast.makeText(activity, "充值失败", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
    public void OperationCard(){
		Log.e("msg","aaaaaaaaaaaaaaaaa"+Changliang.a);
		if(Changliang.a==1){
			startRead();
		}else {
			startWrite();
		}
}
	public void startRead() {
		selectMFile(new NextStep() {
            @Override
			public void invoke() {

				selectInfoFile(new NextStep() {
					@Override
					public void invoke() {
						selectDF(new NextStep() {
							@Override
							public void invoke() {
                               selectWrite(new NextStep() {
									@Override
									public void invoke() {

										selectPure(new NextStep() {
											@Override
											public void invoke() {

												selectGuid(new NextStep() {
													@Override
													public void invoke() {

														selectGuid(new NextStep() {
															@Override
															public void invoke() {
																C_card1="68022616"+selectGuid.substring(12, 44)+
																		"04"+"0000000000"+"0000"+"000000000000000000"+
																		"00000000"+"000000000000000011110016";
																C_card2="688217000000000000000000000000000000000000"+
																		String.format("%04d", Integer.parseInt(selectPure.substring(8, 16), 16))+
																		"00"+selectInfoFile.substring(34, 46)+"0000000016";
																Log.e("msg","查询水投卡1----"+C_card1);
																Log.e("msg","查询水投卡2----"+C_card2);

																//If you want to finish this process
														   // 	powerdown();
																SelectRequest();
															}
														});

													}

												});
											}
										});
                                    }
								});
							}
						});
					}
				});
			}
		});
	}
	public void startWrite() {
		selectPFile(new NextStep() {
			@Override
			public void invoke() {
				selectDF(new NextStep() {
					@Override
					public void invoke() {
						selectRandom2(new NextStep() {
							@Override
							public void invoke() {
								selectK0(new NextStep() {
									@Override
									public void invoke() {
										selectK1(new NextStep() {
											@Override
											public void invoke() {
												selectDIV(new NextStep() {
													@Override
													public void invoke() {
														selectRandom3(new NextStep() {
															@Override
															public void invoke() {
																selectK2(new NextStep() {
																	@Override
																	public void invoke() {
																			selectRandom4(new NextStep() {
																				@Override
																				public void invoke() {
																					selectDIV1(new NextStep() {
																						@Override
																						public void invoke() {
																							authData(new NextStep() {
																								@Override
																								public void invoke() {
																									WaibuCaozuo(new NextStep() {
																										@Override
																										public void invoke() {
																											selectRandom5(new NextStep() {
																												@Override
																												public void invoke() {
																													P_JiaMi(new NextStep() {
																														@Override
																														public void invoke() {
																															P_data0(new NextStep() {
																																@Override
																																public void invoke() {
																																	data1(new NextStep() {
																																		@Override
																																		public void invoke() {
																																			XiaXingWenJian(new NextStep() {
																																				@Override
																																				public void invoke() {
																																					selectRandom6(new NextStep() {
																																						@Override
																																						public void invoke() {
																																							P_JiaMi1(new NextStep() {
																																								@Override
																																								public void invoke() {
																																									P_data1(new NextStep() {
																																										@Override
																																										public void invoke() {
																																											data2(new NextStep() {
																																												@Override
																																												public void invoke() {
																																													GouDianFanXie(new NextStep() {
																																														@Override
																																														public void invoke() {
																																															selectRandom7(new NextStep() {
																																																@Override
																																																public void invoke() {
																																																	P_JiaMi2(new NextStep() {
																																																		@Override
																																																		public void invoke() {
																																																			P_data2(new NextStep() {
																																																				@Override
																																																				public void invoke() {
																																																					data3(new NextStep() {
																																																						@Override
																																																						public void invoke() {
																																																							QianBaoWenJian(new NextStep() {
																																																								@Override
																																																								public void invoke() {
																																																									ConfirmRequest();
																																																							//		powerdown();
																																																								}
																																																							});
																																																						}
																																																					});
																																																				}
																																																			});
																																																		}
																																																	});

																																																}
																																															});
																																														}
																																													});

																																												}
																																											});
																																										}
																																									});
																																								}
																																							});

																																						}
																																					});
																																				}
																																			});

																																		}
																																	});
																																}
																															});
																														}
																													});

																												}
																											});

																										}
																									});

																								}
																							});
																						}
																					});
																				}
																			});
																																		}
																});
															}
														});

													}
												});
											}
										});

									}
								});
							}
						});
					}
				});
			}
		});
	}

	public void ZiGuanCard_read() {
		ZiGuanCard_canshu(new NextStep() {
			@Override
			public void invoke() {
				ZiGuanCard_qianbao(new NextStep() {
					@Override
					public void invoke() {
						ZiGuanCard_dierfeilv(new NextStep() {
							@Override
							public void invoke() {
								ZiGuanCard_fanxie(new NextStep() {
									@Override
									public void invoke() {
										Z_card1="68022606"+file1.substring(72,84)+"04"+"0000000000"
												+"0000"+"000000000000000000"+file3.substring(file3.length()-16,file3.length()-8)
												+"000000000000000011110016";
										Z_card2="688217000000000000000000000000000000000000"+String.format("%04d", Integer.parseInt(file2.substring(8, 16), 16))
												+"00"+file1.substring(60, 72)+"0000000016";

										Log.e("msg","查询自管卡1----"+C_card1);
										Log.e("msg","查询自管卡2----"+C_card2);
										powerdown();
									}
								});
							}
						});

					}
				});

			}
		});
	}

	public void ZiGuanCard_write() {
		GetUserReset8(new NextStep() {
			@Override
			public void invoke() {
				YingYongka(new NextStep() {
					@Override
					public void invoke() {
						Psam_1(new NextStep() {
							@Override
							public void invoke() {
								P_Random(new NextStep() {
									@Override
									public void invoke() {
										Psam_21(new NextStep() {
											@Override
											public void invoke() {
												Psam_22(new NextStep() {
													@Override
													public void invoke() {
														Psam_23(new NextStep() {
															@Override
															public void invoke() {
																GetZ_K2(new NextStep() {
																	@Override
																	public void invoke() {
																		P_Random1(new NextStep() {
																			@Override
																			public void invoke() {
																				Psam_31(new NextStep() {
																					@Override
																					public void invoke() {
																						Psam_32(new NextStep() {
																							@Override
																							public void invoke() {
																								Psam_33(new NextStep() {
																									@Override
																									public void invoke() {
																										GetZ_K20(new NextStep() {
																											@Override
																											public void invoke() {
																												P_Random2(new NextStep() {
																													@Override
																													public void invoke() {
																														Psam_41(new NextStep() {
																															@Override
																															public void invoke() {
																																Psam_42(new NextStep() {
																																	@Override
																																	public void invoke() {
																																		Psam_43(new NextStep() {
																																			@Override
																																			public void invoke() {
																																				GetZ_K200(new NextStep() {
																																					@Override
																																					public void invoke() {
																																						P_Random3(new NextStep() {
																																							@Override
																																							public void invoke() {
																																								Psam_51(new NextStep() {
																																									@Override
																																									public void invoke() {
																																										Psam_52(new NextStep() {
																																											@Override
																																											public void invoke() {
																																												Psam_53(new NextStep() {
																																													@Override
																																													public void invoke() {
																																														CanshuXinxi(new NextStep() {
																																															@Override
																																															public void invoke() {
																																																P_Random4(new NextStep() {
																																																	@Override
																																																	public void invoke() {
																																																		Psam_61(new NextStep() {
																																																			@Override
																																																			public void invoke() {
																																																				Psam_62(new NextStep() {
																																																					@Override
																																																					public void invoke() {
																																																						Psam_63(new NextStep() {
																																																							@Override
																																																							public void invoke() {
																																																								Z_qianbao(new NextStep() {
																																																									@Override
																																																									public void invoke() {
																																																										P_Random5(new NextStep() {
																																																											@Override
																																																											public void invoke() {
																																																												Psam_71(new NextStep() {
																																																													@Override
																																																													public void invoke() {
																																																														Psam_72(new NextStep() {
																																																															@Override
																																																															public void invoke() {
																																																																Psam_73(new NextStep() {
																																																																	@Override
																																																																	public void invoke() {
																																																																		Z_fanxie(new NextStep() {
																																																																			@Override
																																																																			public void invoke() {
																																																																				powerdown();
																																																																			}
																																																																		});
																																																																	}
																																																																});
																																																															}
																																																														});
																																																													}
																																																												});
																																																											}
																																																										});
																																																									}
																																																								});
																																																							}
																																																						});
																																																					}
																																																				});
																																																			}
																																																		});
																																																	}
																																																});
																																															}
																																														});
																																													}
																																												});
																																											}
																																										});
																																									}
																																								});
																																							}
																																						});
																																					}
																																				});
																																			}
																																		});
																																	}
																																});
																															}
																														});
																													}
																												});
																											}
																										});
																									}
																								});
																							}
																						});
																					}
																				});
																			}
																		});

																	}
																});
															}
														});
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}
	/**
	 * Show error message in reading process.
	 * @param msg
	 */
	protected abstract void showErrorMessage(String msg);
	
	/**
	 * Select MFile
	 * @param next
	 */

	public void selectMFile(final NextStep next) {
		exchangeApdu("00A40000023f00", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectMFile=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectMFile---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void ZiGuanCard_canshu(final NextStep next) {
		exchangeApdu("00B081002D", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				file1=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","ZiGuanCard_canshu---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void ZiGuanCard_qianbao(final NextStep next) {
		exchangeApdu("00B0820008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				file2=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","ZiGuanCard_qianbao---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void ZiGuanCard_dierfeilv(final NextStep next) {
		exchangeApdu("00B0848082", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				file3=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","ZiGuanCard_dierfeilv---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void ZiGuanCard_fanxie(final NextStep next) {
		exchangeApdu("00B0850031", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				file4=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","ZiGuanCard_fanxie---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void GetUserReset8(final NextStep next) {
		exchangeApdu("00b0990108", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				UserReset8=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","GetUserReset8---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void YingYongka(final NextStep next) {
		exchangeApdu("00A4000002df01", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","YingYongka---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void Psam_1(final NextStep next) {
		exchangeApdu("00a40000021001", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_1---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void P_Random(final NextStep next) {
		exchangeApdu("0084000008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				P_Random=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","P_Random---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void P_Random1(final NextStep next) {
		exchangeApdu("0084000008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				P_Random1=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","P_Random1---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void P_Random2(final NextStep next) {
		exchangeApdu("0084000008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				P_Random2=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","P_Random2---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void P_Random3(final NextStep next) {
		exchangeApdu("0084000010", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				P_Random3=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","P_Random3---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void P_Random4(final NextStep next) {
		exchangeApdu("0084000010", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				P_Random4=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","P_Random4---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void P_Random5(final NextStep next) {
		exchangeApdu("0084000010", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				P_Random5=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","P_Random5---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_21(final NextStep next) {
		exchangeApdu("801a070108"+UserReset8, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_21---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void Psam_22(final NextStep next) {
		exchangeApdu("80fa000010"+P_Random+ Convert.notStoBtoS(P_Random), new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_22---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_23(final NextStep next) {
		exchangeApdu("00c0000010", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Encdata=BytesUtil.bytes2HexString(responseData);
				str1=Encdata.substring(0,16);
				str2=Encdata.substring(Encdata.length()-16,Encdata.length());
				Z_K1=Convert.xorStr(str1,str2);
				Log.e("msg",Z_K1);
				Log.e("msg","Psam_23---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void Psam_31(final NextStep next) {
		exchangeApdu("801a070408"+UserReset8, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_31---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void Psam_32(final NextStep next) {
		exchangeApdu("80fa000010"+P_Random1+ Convert.notStoBtoS(P_Random1), new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_32---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_33(final NextStep next) {
		exchangeApdu("00c0000010", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Encdata1=BytesUtil.bytes2HexString(responseData);
				str10=Encdata1.substring(0,16);
				str20=Encdata1.substring(Encdata1.length()-16,Encdata1.length());
				Z_K10=Convert.xorStr(str10,str20);
				Log.e("msg",Z_K10);
				Log.e("msg","Psam_33---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_41(final NextStep next) {
		exchangeApdu("801a070208"+UserReset8, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_41---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void Psam_42(final NextStep next) {
		exchangeApdu("80fa000010"+P_Random2+ Convert.notStoBtoS(P_Random2), new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_42---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_43(final NextStep next) {
		exchangeApdu("00c0000010", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Encdata2=BytesUtil.bytes2HexString(responseData);
				str100=Encdata2.substring(0,16);
				str200=Encdata2.substring(Encdata2.length()-16,Encdata2.length());
				Z_K100=Convert.xorStr(str100,str200);
				Log.e("msg",Z_K100);
				Log.e("msg","Psam_43---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_51(final NextStep next) {
		exchangeApdu("801A060308"+UserReset8, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_51---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void Psam_52(final NextStep next) {
		exchangeApdu("80fa050050"+P_Random3+"04d6810031"+ xiaxingFileMsg3+"8000000000000000000000000000", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_52---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_53(final NextStep next) {
		exchangeApdu("00C0000004", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
			    Mac1=BytesUtil.bytes2HexString(responseData);
				Log.e("msg",Mac1);
				Log.e("msg","Psam_53---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_61(final NextStep next) {
		exchangeApdu("801A060508"+UserReset8, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_61---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void Psam_62(final NextStep next) {
		exchangeApdu("80fa050020"+P_Random4+"04d682000C"+ "00000010"+"800000", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_62---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_63(final NextStep next) {
		exchangeApdu("00C0000004", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Mac2=BytesUtil.bytes2HexString(responseData);
				Log.e("msg",Mac2);
				Log.e("msg","Psam_63---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_71(final NextStep next) {
		exchangeApdu("801A060208"+UserReset8, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_71---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void Psam_72(final NextStep next) {
		exchangeApdu("80fa050050"+P_Random5+"04d6850035"+ "0000000000000000000"+"800000000000000000", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","Psam_72---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Psam_73(final NextStep next) {
		exchangeApdu("00C0000004", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Mac7=BytesUtil.bytes2HexString(responseData);
				Log.e("msg",Mac7);
				Log.e("msg","Psam_73---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void CanshuXinxi(final NextStep next) {
		exchangeApdu("04d6810031"+xiaxingFileMsg3+Mac1, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				Log.e("msg","CanshuXinxi---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}



	public void Z_qianbao(final NextStep next) {
		exchangeApdu("04d682000C"+"00000010"+Mac2, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				Log.e("msg","Z_qianbao---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void Z_fanxie(final NextStep next) {
		exchangeApdu("04d6850035"+"0000000000000000000"+Mac7, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				Log.e("msg","Z_fanxie---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void GetZ_K2(final NextStep next) {
		exchangeApdu("0088000108 "+P_Random, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Z_K2=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","GetZ_K2---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void GetZ_K20(final NextStep next) {
		exchangeApdu("0082000308"+Z_K10, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","GetZ_K20---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void GetZ_K200(final NextStep next) {
		exchangeApdu("0082000408"+Z_K100, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","GetZ_K200---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void selectPFile(final NextStep next) {
		exchangeApdu("00A40000023F00", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectMFile=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectPFile---"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}


	public void selectInfoFile(final NextStep next) {
		exchangeApdu("00b0820000", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectInfoFile=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectInfoFile---"+BytesUtil.bytes2HexString(responseData));
				Log.e("msg","DIV---"+BytesUtil.bytes2HexString(responseData).substring(48,64));
				if(BytesUtil.bytes2HexString(responseData).substring(24,26).equals("05")){
					Log.e("msg","05为购电卡");
				}else {
					Log.e("msg","不是购电卡");
				}
			}
		});
	}
	
	/**
	 * Select DFile
	 * @param next
	 */
	public void selectDF(final NextStep next) {
		exchangeApdu("00A40000023F01", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectDF=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectDF-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void select21001(final NextStep next) {
		exchangeApdu("00A40000021001", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectDF=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","select21001-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void P_data0(final NextStep next) {
		exchangeApdu("04B0810019"+random5+"04D681001C"+DIV, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","P_data0-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void P_data1(final NextStep next) {
		exchangeApdu("04B0820019"+random6+"04D6820017"+DIV, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","P_data1-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void P_data2(final NextStep next) {
		exchangeApdu("04B0830019"+random7+"04D683000C"+DIV, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","P_data2-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void data1(final NextStep next) {
		exchangeApdu("00C000001C", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				data1=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","data1-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void data2(final NextStep next) {
		exchangeApdu("00c000000017", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				data2=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","data2-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void data3(final NextStep next) {
		exchangeApdu("00c000000C", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				data3=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","data3-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void P_JiaMi(final NextStep next) {
		 xiaxingFileMsg1 = "01001203C300";
		 xiaxingFileMsg2 = xiaxingFileMsg1
				+ String.format("%04x", (int)(2*1)) // 次数
				+ String.format("%08x", (int)(4*100)) //金额
				+ "05E69EC0";
		Date date = new Date(System.currentTimeMillis());
		 xiaxingFileMsg3 = xiaxingFileMsg2
				+ new MyDateFormat().simpleDateFormat(4, date);
		exchangeApdu("00d68100"+"24"+xiaxingFileMsg3, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
			Log.e("msg","P_JiaMi-----"+BytesUtil.bytes2HexString(responseData));
			}
	});
	}

	public void P_JiaMi1(final NextStep next) {
		exchangeApdu("00d68200"+"19"+"0000000000000000000", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","P_JiaMi1-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void P_JiaMi2(final NextStep next) {
		exchangeApdu("00d68300"+"8"+"00000010", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","P_JiaMi2-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void WaibuCaozuo(final NextStep next) {
		exchangeApdu("0082000108"+authData, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","WaibuCaozuo-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void XiaXingWenJian(final NextStep next) {
		exchangeApdu("04D681001C"+data1, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","XiaXingWenJian-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void GouDianFanXie(final NextStep next) {
		exchangeApdu("04D6820017"+data2, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","GouDianFanXie-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void QianBaoWenJian(final NextStep next) {
		exchangeApdu("04D683000C"+data3, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","QianBaoWenJian-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectRandom2(final NextStep next) {
		exchangeApdu("0084000008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				random2=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectRandom2-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectRandom3(final NextStep next) {
		exchangeApdu("80FA000008"+random2, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","selectRandom3-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectRandom4(final NextStep next) {
		exchangeApdu("0084000008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				random4=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectRandom4-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void selectRandom5(final NextStep next) {
		exchangeApdu("0084000004", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				random5=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectRandom5-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void selectRandom6(final NextStep next) {
		exchangeApdu("0084000004", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				random6=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectRandom6-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectRandom7(final NextStep next) {
		exchangeApdu("0084000004", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				random7=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectRandom7-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void selectK0(final NextStep next) {
		exchangeApdu("0088000108"+random2, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectDF=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectK0-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectK1(final NextStep next) {
		exchangeApdu("00c0000008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				K1=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectK1-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void selectK2(final NextStep next) {
		exchangeApdu("00c0000008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				K2=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectK2-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}
	public void selectK3(final NextStep next) {
		exchangeApdu("80FA000008"+random4, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
                Log.e("msg","selectK3-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void authData(final NextStep next) {
		exchangeApdu("00c0000008", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				authData=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","authData-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectDIV(final NextStep next) {
		DIV="6100002800260027"+"0000000000002565";
		exchangeApdu("80FA000110"+DIV, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","selectDIV-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectDIV1(final NextStep next) {
		exchangeApdu("80FA00210"+"6100002800260027"+"0000000000002565", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				Log.e("msg","selectDIV1-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectWrite(final NextStep next) {
		exchangeApdu("00B0820000", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectWrite=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectWrite-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectPure(final NextStep next) {
		exchangeApdu("00B0830000", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectPure=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectPure-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	public void selectGuid(final NextStep next) {
		exchangeApdu("00B085003C", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				next.invoke();
				selectGuid=BytesUtil.bytes2HexString(responseData);
				Log.e("msg","selectGuid-----"+BytesUtil.bytes2HexString(responseData));
			}
		});
	}

	@Override
	public void onFail(int errorCode) {
		showFinalMessage("ERROR - "+getErrorDescription(errorCode));
	}
	
	/**
	 * 
	 * @param msg
	 */
	public abstract void showFinalMessage(String msg);

	@Override
	public void onSuccess(byte[] responseData) {
		if (respHandler != null) {
			CARD_SW1 = responseData[responseData.length - 2] & 0xff;
			CARD_SW2 = responseData[responseData.length - 1] & 0xff;
			respHandler.onResponse(responseData);
		}
	}
	
	/**
	 * To operate the card through the exchange of apdu.
	 * @param apdu
	 */
	public void exchangeApdu(String apdu, ResponseHandler h) {
		this.respHandler = h;
        exchangeApdu(BytesUtil.hexString2Bytes(apdu));
	}
	
	/**
	 * To operate the card through the exchange of apdu.
	 * @param apdu
	 */
	public void exchangeApdu(byte[] apdu) {

		try {
			driver.exchangeApdu(apdu, this);
		} catch (RequestException e) {
			onServiceCrash();
		}
	}

	public void powerdown() {
		try {
			driver.powerdown();
			showFinalMessage("Contact cpu card operate success!");
		} catch (RequestException e) {
			onServiceCrash();
		}
	}

	@Override
	public void onCrash() {
		onServiceCrash();
		
	}
	
	public String getErrorDescription(int code){
		switch(code) {
		case ERROR_DATAERR : return "Read response data error"; 					 
		case ERROR_NOPOWER : return "The card does not power up"; 					 
		case ERROR_NOCARD : return "The card is not present"; 
		case ERROR_SWDIFF : return "SW1!=0X90 or SW2!=0X00";
		case ERROR_FAILED : return "Other error(OS error,etc)"; 					 
		case ERROR_ERRTYPE : return "Card type is not right"; 					 
		case ERROR_TIMEOUT : return "Communication error"; 			 
		}
		return "unknown error ["+code+"]";
	}
	
	protected boolean isSW1Right() {
		return CARD_SW1 == 0x90;
	}
	
	protected int getSW2() {
		return CARD_SW2;
	}
	
	protected int getSW1() {
		return CARD_SW1;
	}
	
	protected abstract void onServiceCrash();
	
	
	protected interface ResponseHandler {
		void onResponse(byte[] responseData);
	}
	
	protected interface NextStep {
		void invoke();
	}
	public void SelectRequest(){
		new Thread() {
			@Override
			public void run() {
				Log.e("tcp", "80查询请求");
				Socket socket = null;
				try {
					socket = new Socket();
					Log.e("tcp", "---");
					socket.connect(new InetSocketAddress("192.168.1.105", Integer.valueOf(9093)), 10000);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					out.write(new String(Contans.pos_select).toString().getBytes());
					byte[] a = new byte[1024];
					in.read(a);
					String receive = new String(a);
					String receive1 = URLDecoder.decode(receive, "UTF-8");
					Log.e("tcp","显示用户信息");
					Log.e("tcp", receive1);
					// 关闭socket
					in.close();
					out.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.run();
			}
		}.start();
		IntentUtils.openActivity(activity,QueryResultsActivity.class);

	}
	public void ConfirmRequest(){
		new Thread() {
			@Override
			public void run() {
				Log.e("tcp", "84确认请求");
				Socket socket = null;
				try {
					socket = new Socket();
					Log.e("tcp", "---");
					socket.connect(new InetSocketAddress("192.168.1.105", Integer.valueOf(9093)), 10000);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					out.write(new String(Contans.pos_success).toString().getBytes());
					byte[] a = new byte[1024];
					in.read(a);
					String receive = new String(a);
					String receive1 = URLDecoder.decode(receive, "UTF-8");
					if(receive1.indexOf("|00|")!=-1){
						handler.sendEmptyMessage(1);
					}if(receive1.indexOf("|00|")==-1) {
						handler.sendEmptyMessage(2);
					}
					Log.e("tcp", receive1);
					// 关闭socket
					in.close();
					out.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.run();
			}
		}.start();
  }
}
