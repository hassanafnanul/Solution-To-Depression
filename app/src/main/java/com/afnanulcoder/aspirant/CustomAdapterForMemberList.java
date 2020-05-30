package com.afnanulcoder.aspirant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapterForMemberList extends ArrayAdapter<UserInformations> {
    private Activity context;
    private List<UserInformations> memberList;

    TextView memberName;
    ImageView chatIcon;



    public CustomAdapterForMemberList(Activity context, List<UserInformations> memberList) {
        super(context, R.layout.post_sample_view, memberList);
        this.context = context;
        this.memberList = memberList;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.one_line_list_view, null, true);

        UserInformations userInformations = memberList.get(position);

        memberName = view.findViewById(R.id.memberNameID);
        chatIcon = view.findViewById(R.id.personalChatIconID);

        memberName.setText(userInformations.getMemberName());
        chatIcon.setTag(memberList.get(position));



        return view;
    }

}
