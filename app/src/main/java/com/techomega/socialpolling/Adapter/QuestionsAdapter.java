package com.techomega.socialpolling.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.techomega.socialpolling.Model.QuestionsModel;
import com.techomega.socialpolling.R;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.myViewHolder>{

    private Context context;
    private List<QuestionsModel> questionsModels;
    OnPreviousClickListner onPreviousClickListner;
    OnNextClickListner onNextClickListner;

    public QuestionsAdapter(Context context,List<QuestionsModel> questionsModels1){
        this.context = context;
        this.questionsModels = questionsModels1;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_questions,parent,false);

        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.texttitle.setText(questionsModels.get(position).getQuestion());
        holder.txtcat.setText(questionsModels.get(position).getCategory());
        holder.txtc1.setText(questionsModels.get(position).getChoice1());
        holder.txtc2.setText(questionsModels.get(position).getChoice2());
        holder.txtc3.setText(questionsModels.get(position).getChoice3());
        holder.txtc4.setText(questionsModels.get(position).getChoice4());

        if(position == 0){
            holder.materialButton1.setVisibility(View.GONE);
        } else {
            holder.materialButton1.setVisibility(View.VISIBLE);
        }

        if(position == questionsModels.size()-1){
            holder.materialButton2.setVisibility(View.GONE);
        } else {
            holder.materialButton2.setVisibility(View.VISIBLE);
        }

        holder.materialButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onPreviousClickListner!=null)
                {
                    onPreviousClickListner.onPreviousClick(position-1);
                }
            }
        });

        holder.materialButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onNextClickListner!=null)
                {
                    onNextClickListner.onNextClick(position+1);
                }
            }
        });

        holder.linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.linearLayout1.getTag().equals("fill")){
                    holder.linearLayout1.setTag("unfill");
                    holder.txtc1.setTag("unfill");
                    holder.linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc1.setTextColor(context.getResources().getColor(R.color.black));
                } else {
                    holder.linearLayout1.setTag("fill");
                    holder.txtc1.setTag("fill");
                    holder.linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.new_linear_bg));
                    holder.txtc1.setTextColor(context.getResources().getColor(R.color.white));

                    holder.linearLayout2.setTag("unfill");
                    holder.txtc2.setTag("unfill");
                    holder.linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc2.setTextColor(context.getResources().getColor(R.color.black));

                    holder.linearLayout3.setTag("unfill");
                    holder.txtc3.setTag("unfill");
                    holder.linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc3.setTextColor(context.getResources().getColor(R.color.black));

                    holder.linearLayout4.setTag("unfill");
                    holder.txtc4.setTag("unfill");
                    holder.linearLayout4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc4.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        });

        holder.linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.linearLayout2.getTag().equals("fill")){
                    holder.linearLayout2.setTag("unfill");
                    holder.txtc2.setTag("unfill");
                    holder.linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc2.setTextColor(context.getResources().getColor(R.color.black));
                } else {
                    holder.linearLayout2.setTag("fill");
                    holder.txtc2.setTag("fill");
                    holder.linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.new_linear_bg));
                    holder.txtc2.setTextColor(context.getResources().getColor(R.color.white));

                    holder.linearLayout1.setTag("unfill");
                    holder.txtc1.setTag("unfill");
                    holder.linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc1.setTextColor(context.getResources().getColor(R.color.black));

                    holder.linearLayout3.setTag("unfill");
                    holder.txtc3.setTag("unfill");
                    holder.linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc3.setTextColor(context.getResources().getColor(R.color.black));

                    holder.linearLayout4.setTag("unfill");
                    holder.txtc4.setTag("unfill");
                    holder.linearLayout4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc4.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        });

        holder.linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.linearLayout3.getTag().equals("fill")){
                    holder.linearLayout3.setTag("unfill");
                    holder.txtc3.setTag("unfill");
                    holder.linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc3.setTextColor(context.getResources().getColor(R.color.black));
                } else {
                    holder.linearLayout3.setTag("fill");
                    holder.txtc3.setTag("fill");
                    holder.linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.new_linear_bg));
                    holder.txtc3.setTextColor(context.getResources().getColor(R.color.white));

                    holder.linearLayout2.setTag("unfill");
                    holder.txtc2.setTag("unfill");
                    holder.linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc2.setTextColor(context.getResources().getColor(R.color.black));

                    holder.linearLayout1.setTag("unfill");
                    holder.txtc1.setTag("unfill");
                    holder.linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc1.setTextColor(context.getResources().getColor(R.color.black));

                    holder.linearLayout4.setTag("unfill");
                    holder.txtc4.setTag("unfill");
                    holder.linearLayout4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc4.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        });

        holder.linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.linearLayout4.getTag().equals("fill")){
                    holder.linearLayout4.setTag("unfill");
                    holder.txtc4.setTag("unfill");
                    holder.linearLayout4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc4.setTextColor(context.getResources().getColor(R.color.black));
                } else {
                    holder.linearLayout4.setTag("fill");
                    holder.txtc4.setTag("fill");
                    holder.linearLayout4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.new_linear_bg));
                    holder.txtc4.setTextColor(context.getResources().getColor(R.color.white));

                    holder.linearLayout2.setTag("unfill");
                    holder.txtc2.setTag("unfill");
                    holder.linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc2.setTextColor(context.getResources().getColor(R.color.black));

                    holder.linearLayout3.setTag("unfill");
                    holder.txtc3.setTag("unfill");
                    holder.linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc3.setTextColor(context.getResources().getColor(R.color.black));

                    holder.linearLayout1.setTag("unfill");
                    holder.txtc1.setTag("unfill");
                    holder.linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.line_bg));
                    holder.txtc1.setTextColor(context.getResources().getColor(R.color.black));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionsModels.size();
    }

    public interface OnNextClickListner{
        void onNextClick(int position);
    }

    public void setOnNextClickListner(OnNextClickListner onNextClickListner) {
        this.onNextClickListner = onNextClickListner;
    }

    public interface OnPreviousClickListner{
        void onPreviousClick(int position);
    }

    public void setOnPreviousClickListner(OnPreviousClickListner onPreviousClickListner) {
        this.onPreviousClickListner = onPreviousClickListner;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView texttitle,txtcat,txtc1,txtc2,txtc3,txtc4;
        LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
        MaterialButton materialButton1,materialButton2;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            texttitle = (TextView) itemView.findViewById(R.id.title);
            txtcat = (TextView) itemView.findViewById(R.id.cat);
            txtc1 = (TextView) itemView.findViewById(R.id.c1);
            txtc2 = (TextView) itemView.findViewById(R.id.c2);
            txtc3 = (TextView) itemView.findViewById(R.id.c3);
            txtc4 = (TextView) itemView.findViewById(R.id.c4);

            linearLayout1 = (LinearLayout) itemView.findViewById(R.id.choice1);
            linearLayout2 = (LinearLayout) itemView.findViewById(R.id.choice2);
            linearLayout3 = (LinearLayout) itemView.findViewById(R.id.choice3);
            linearLayout4 = (LinearLayout) itemView.findViewById(R.id.choice4);

            materialButton1 = (MaterialButton) itemView.findViewById(R.id.prev);
            materialButton2 = (MaterialButton) itemView.findViewById(R.id.next);
        }
    }

}
