package classes;

import static com.example.activities.R.*;

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

public class DiaryDishAdapter extends ArrayAdapter<Diary> {
    private Context context;
    private ArrayList<Diary> dishes;
    private User user;

    public DiaryDishAdapter(Context context, ArrayList<Diary> dishes, User user){
        super(context, layout.diary, dishes);
        this.context = context;
        this.dishes = dishes;
        this.user = user;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.diary, parent, false);

        TextView textViewNameDish = (TextView) view.findViewById(R.id.textViewNameDish);
        TextView textViewCalories = (TextView) view.findViewById(R.id.textViewCalories);
        TextView textViewEatingName = (TextView) view.findViewById(R.id.textViewEatingName);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        TextView textViewTime = (TextView) view.findViewById(R.id.textViewTime);

        textViewNameDish.setText(this.dishes.get(position).getDish().getName());
        textViewCalories.setText(String.valueOf(this.dishes.get(position).getDish().getCalorieContent()));
        textViewEatingName.setText(this.dishes.get(position).getDish().getMealType().getType());
        textViewDate.setText(this.dishes.get(position).getDate());
        textViewTime.setText(this.dishes.get(position).getTime());

        LinearLayout linerLayout = (LinearLayout) view.findViewById(R.id.linearLayoutMainDiary);

        System.out.println(dishes.get(position).toString());

        linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DishActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("dish", dishes.get(position).getDish());
                intent.putExtra("isRecordDish", false);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
