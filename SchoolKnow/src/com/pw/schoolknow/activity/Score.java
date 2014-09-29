package com.pw.schoolknow.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pw.schoolknow.R;
import com.pw.schoolknow.base.BaseActivity;
import com.pw.schoolknow.config.ServerConfig;
import com.pw.schoolknow.helper.InputHelper;
import com.pw.schoolknow.helper.LoginHelper;
import com.pw.schoolknow.helper.TermHelper;
import com.pw.schoolknow.utils.EncodeUtil;
import com.pw.schoolknow.utils.GetUtil;
import com.pw.schoolknow.utils.T;
import com.pw.schoolknow.utils.TextUtils;
import com.pw.schoolknow.widgets.BottomBtn;
import com.pw.schoolknow.widgets.BottomBtn.BottomBtnOnclickListener;
import com.pw.schoolknow.widgets.MyAlertDialog;
import com.pw.schoolknow.widgets.MyProgressBar;
import com.pw.schoolknow.widgets.StuidSelect;
import com.pw.schoolknow.widgets.StuidSelect.StuidSelectInterface;

public class Score extends BaseActivity {
	
	private Spinner term;
	private EditText edit;
	private Button submit;
	private List<CharSequence> termList=null;
	private MyProgressBar mpb;
	private String stuid;
	private String selectTerm;
	public MyAlertDialog mad;
	public Context mcontext;
	
	public BottomBtn bb;
	public LoginHelper lh;
	public Handler handler;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_score);
		setTitle("�ɼ���ѯ");
		setTitleBar(R.drawable.btn_titlebar_back,"",0,"�༶");
		
		mcontext=this;
		lh=new LoginHelper(mcontext);
		
		
		term=(Spinner) super.findViewById(R.id.score_search_term);
		edit=(EditText) super.findViewById(R.id.score_search_edit);
		submit=(Button) super.findViewById(R.id.score_search_sumbit);
		
		submit.setOnClickListener(new submitClickListener());
		term.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectTerm=parent.getItemAtPosition(position).toString();
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		//����ѧ��
		this.termList=TermHelper.getAllTerm();
		term.setPrompt("��ѡ����Ҫ��ѯ��ѧ��");
		 ArrayAdapter<CharSequence> termAda= new ArrayAdapter<CharSequence>(this,
					android.R.layout.simple_spinner_item,termList);
		 termAda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		term.setAdapter(termAda);
		
		
		//�ײ�2����ť
		bb=new BottomBtn(Score.this);
		bb.setBtnVal("�����ϴβ�ѯ", "��ѯ�ҵĳɼ�");
		bb.setOnclickBtn(new BottomBtnOnclickListener() {
			public void onClick(View v, int position) {
				if(position==0){
					T.showShort(mcontext, "�ù������Ժ�汾�Ƴ�");
				}else if(position==1){
					if(lh.hasLogin()){
						stuid=lh.getStuId();
						if(lh.hasBindStuid()){
							if(stuid.trim().length()==14){
								edit.setText(stuid);
								getScoreInfo();
							}else{
								T.showShort(mcontext, "ѧ�����������µ�¼");
								lh.logout();
							}
						}else{
							T.showShort(mcontext, "���Ȱ�ѧ��");
						}
					}else{
						T.showShort(mcontext, "���ȵ�¼");
					}
				}
			}
		});
		
		
		//��Ϣ����
		handler=new Handler(){
			public void handleMessage(Message msg) {
				if(msg.what==102){
					Intent it=new Intent(Score.this,ScoreData.class);
					it.putExtra("jsondata",msg.obj.toString());
					startActivity(it);
				}
				mpb.dismiss();
				submit.setClickable(true);
				super.handleMessage(msg);
			}
			
		};
	}
	
	public class submitClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			InputHelper.Hide(Score.this);
			stuid=edit.getText().toString();
			if(stuid.length()==14&&TextUtils.isAllNum(stuid)){
				getScoreInfo();
			}else if(TextUtils.isAllNum(stuid)&&stuid.length()!=14){
				T.showShort(mcontext, "�����ѧ������");
			}else if(stuid.length()<2||stuid.length()>4){
				T.showShort(mcontext, "��������2-4����");
			}else if(stuid.length()<2||stuid.length()>4){
				T.showShort(mcontext, "��������2-4����");
			}else if(TextUtils.contain(stuid,"%")||TextUtils.contain(stuid,"_")){
				T.showLong(mcontext, "ͬѧʹ���ǲ��Ե�Ӵ(*^__^*)");
			}else{
				String stuName=EncodeUtil.ToUtf8(stuid);
				StuidSelect stuidSelect=new StuidSelect(mcontext);
				stuidSelect.execute(stuName);
				stuidSelect.getSelected(new StuidSelectInterface() {
					public void onSelect(String stuid) {
						Score.this.stuid=stuid;
						getScoreInfo();
					}
				});
			}
			
		}
		
	}
	
	//��ȡ�ɼ���Ϣ
	public void getScoreInfo(){
		if(mpb!=null){
			mpb.show();
		}else{
			mpb=new MyProgressBar(mcontext);
			mpb.setMessage("���ڲ�ѯ��...");
		}
		//���ð�ť���ɵ��
		submit.setClickable(false);
		new Thread(new Runnable() {
			public void run() {
				String t=TermHelper.getNumTerm(selectTerm);
				Message msg=new Message();
				msg.what=102;
				String param=ServerConfig.HOST+"/schoolknow/api/getscore.php?stuid="+
						stuid+"&term="+t;
				msg.obj=GetUtil.getRes(param);
				handler.sendMessage(msg);
			}
		}).start();
		
	}
	

	@Override
	protected void HandleTitleBarEvent(int buttonId, View v) {
		switch(buttonId){
		case 1:
			this.finish();
			break;
		case 2:
			Intent it=new Intent(Score.this,ScoreClass.class);
			startActivity(it);
			finish();
			break;
		case 3:
			break;
		default:
			break;
		}
	}

}
