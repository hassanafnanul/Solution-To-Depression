package com.afnanulcoder.aspirant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<PeachRoomInfo> {
    private Activity context;
    private List<PeachRoomInfo> roomList;


    public CustomAdapter(Activity context, List<PeachRoomInfo> roomList) {
        super(context, R.layout.three_line_list_view, roomList);
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.three_line_list_view, null, true);

        PeachRoomInfo peachRoomInfo = roomList.get(position);

        TextView roomName = view.findViewById(R.id.peachRoomNameID);
        TextView roomCategory = view.findViewById(R.id.peachRoomCategoryID);
        TextView roomDescription = view.findViewById(R.id.peachRoomSDescriptionID);
        TextView roomMember = view.findViewById(R.id.peachRoomMemberID);
        TextView roomLevel = view.findViewById(R.id.peachRoomLevelID);


        roomName.setText(peachRoomInfo.getPrName());
        roomCategory.setText(peachRoomInfo.getPrCategory());
        roomDescription.setText(peachRoomInfo.getPrSDescription());
        roomMember.setText("Member: "+peachRoomInfo.getPrMember());
        roomLevel.setText("Level: "+peachRoomInfo.getPrLevel());


        return view;
    }

}
