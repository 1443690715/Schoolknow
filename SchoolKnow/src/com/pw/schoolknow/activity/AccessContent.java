package com.pw.schoolknow.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.pw.schoolknow.R;
import com.pw.schoolknow.base.BaseActivity;
import com.pw.schoolknow.widgets.MyPj;

public class AccessContent extends BaseActivity {
	
	public String[] pj_lv_tip={
			"�̷��Ͻ���Ϊ��ʦ��",
			"�ϸ�Ҫ��ѧ����������ҵ�������������渺��",
			"ע��ʦ��֮�乵ͨ��������ȡѧ�����",
			"�������棬��������",
			"�ڿβ�η���������׼ȷ�����ݳ�ʵ���ص�ͻ��",
			"����ͨ����ѧ����������������������Ȥ",
			"���ձ����ƣ���������ϵʵ��",
			"������Ч�������ִ���ѧ�����ֶ��ϿΣ����鹤��",
			"���ʩ�̣�ע������������ѧ�������Լ��Ŀ���",
			"ͨ����ѧ�����տγ����ݣ������ѧϰ����"
	};
	
	private LinearLayout pj_layout;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_access_content);
		setTitle("��������");
		setTitleBar(0,0);
		
		pj_layout=(LinearLayout) super.findViewById(R.id.access_content_selector);
		for(int i=0;i<pj_lv_tip.length;i++){
			MyPj pj=new MyPj(this,(i+1)+"��"+pj_lv_tip[i]);
			pj_layout.addView(pj);
		}
	}

	@Override
	protected void HandleTitleBarEvent(int buttonId, View v) {
		// TODO Auto-generated method stub

	}

}
