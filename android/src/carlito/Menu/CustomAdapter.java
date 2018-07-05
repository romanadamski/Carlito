package carlito.Menu;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brentaureli.mariobros.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romek95a on 05.07.2018.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(Context context, ArrayList<String> list) {
        super(context, 0, list);
        this.context=context;
    }
    Context context;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.custom_adapter_layout, parent, false);
        }
        String message=getItem(position);
        TextView label;
        label=(TextView)row.findViewById(R.id.singleUser);
        label.setText(message);

        return row;
    }
}
