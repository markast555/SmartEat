package classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.activities.DishActivity;
import com.example.activities.R;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class DishAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] dishes;

    public DishAdapter(Context context, String[] dishes){
        super(context, R.layout.dish, dishes);
        this.context = context;
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dish, parent, false);
        TextView dish = (TextView) view.findViewById(R.id.textViewNameDish);
        dish.setText(this.dishes[position]);

        LinearLayout linerLayout = (LinearLayout) view.findViewById(R.id.linearLayoutMain);

        linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toDishFrame = new Intent(context, DishActivity.class);
                toDishFrame.putExtra("dish", dishes[position]);
                context.startActivity(toDishFrame);
            }
        });

        return view;
    }


}
