package cn.hayring.caseanalyst.view.magic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cn.hayring.caseanalyst.R;
import cn.hayring.caseanalyst.domain.Person;

public class PersonGraphV2Adapter extends RecyclerView.Adapter<PersonGraphV2Adapter.PersonGraphV2Holder> {

    private ArrayList<Person> persons;

    Context context;


    public PersonGraphV2Adapter(Context context, ArrayList data) {
        this.context = context;
        this.persons = data;
    }

    @NonNull
    @Override
    public PersonGraphV2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //注册单个元素的layout
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.single_person_graph, viewGroup, false);
        return new PersonGraphV2Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonGraphV2Holder vh, int index) {
        Person person = persons.get(index);
        if (person.getImageIndex() != null) {
            try {
                FileInputStream headIS = context.openFileInput(person.getImageIndex() + ".jpg");
                Bitmap image = BitmapFactory.decodeStream(headIS);
                vh.headView.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        vh.nameView.setText(person.getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PersonGraphV2Holder extends RecyclerView.ViewHolder {

        public final ImageView headView;

        public final TextView nameView;

        public PersonGraphV2Holder(@NonNull View itemView) {
            super(itemView);
            headView = itemView.findViewById(R.id.head_image);
            nameView = itemView.findViewById(R.id.name);
        }
    }
}
