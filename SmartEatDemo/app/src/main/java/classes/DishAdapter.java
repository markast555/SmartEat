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

import java.util.ArrayList;

public class DishAdapter extends ArrayAdapter<Dish> {

    private Context context;
    private ArrayList<Dish> dishes;
    private User user;

    public DishAdapter(Context context, ArrayList<Dish> dishes, User user){
        super(context, R.layout.dish, dishes);
        this.context = context;
        this.dishes = dishes;
        this.user = user;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dish, parent, false);

        TextView textViewNameDish = (TextView) view.findViewById(R.id.textViewNameDish);
        TextView textViewCalories = (TextView) view.findViewById(R.id.textViewCalories);
        TextView textViewEatingName = (TextView) view.findViewById(R.id.textViewEatingName);
        textViewNameDish.setText(this.dishes.get(position).getName());
        textViewCalories.setText(String.valueOf(this.dishes.get(position).getCalorieContent()));
        textViewEatingName.setText(this.dishes.get(position).getMealType().getType());

        LinearLayout linerLayout = (LinearLayout) view.findViewById(R.id.linearLayoutMain);

        System.out.println(dishes.get(position).toString());

        linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DishActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("dish", dishes.get(position));
                intent.putExtra("isRecordDish", true);
                context.startActivity(intent);

            }
        });

        return view;
    }


}
