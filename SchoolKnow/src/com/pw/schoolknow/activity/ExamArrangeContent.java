package com.pw.schoolknow.activity;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.pw.schoolknow.R;
import com.pw.schoolknow.base.BaseActivity;
import com.pw.schoolknow.helper.JsonHelper;

public class ExamArrangeContent extends BaseActivity {
	
	private ListView lv;
	private TextView tip=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_exam_arrange_content);
		setTitle("���԰���");
		setTitleBar(R.drawable.btn_titlebar_back,0);
		
		lv=(ListView) super.findViewById(R.id.exam_arrange_content_lv);
		this.tip=(TextView) super.findViewById(R.id.exam_arrange_tip);
		tip.setVisibility(View.GONE);
		
		String data=getIntent().getStringExtra("data");
		try {
			List<Map<String,Object>> list=new JsonHelper(data).parseJson(
					new String[]{"subject","class","term","week","time",
							"classroom","o_t","i_t"},
					new String[]{"","�༶:","ѧ��:","������:","����ʱ��:","���Խ���:","�࿼��ʦ: ",","});
			SimpleAdapter adapter=new SimpleAdapter(ExamArrangeContent.this,list,
					R.layout.item_exam_arrange_content, 
					new String[]{"subject","class","term","week","time","classroom","o_t","i_t"},
					new int[]{R.id.exam_arrange_data_list_subject,R.id.exam_arrange_data_list_class,
					R.id.exam_arrange_data_list_term,R.id.exam_arrange_data_list_week,
					R.id.exam_arrange_data_list_time,R.id.exam_arrange_data_list_classroom,
					R.id.exam_arrange_data_list_o_t,R.id.exam_arrange_data_list_i_t});
			lv.setAdapter(adapter);
			if(list.size()==0){
				tip.setVisibility(View.VISIBLE);
				lv.setVisibility(View.GONE);
				tip.setText("������İ༶Ŀǰû�п��԰���,���԰���һ��16���Ժ�Ż�½������,"+
			"����������ͬ�����£�����ȴ���");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void HandleTitleBarEvent(int buttonId, View v) {
		// TODO Auto-generated method stub

	}

}
