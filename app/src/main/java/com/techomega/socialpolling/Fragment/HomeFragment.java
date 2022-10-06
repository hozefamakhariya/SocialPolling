package com.techomega.socialpolling.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.techomega.socialpolling.Adapter.QuestionsAdapter;
import com.techomega.socialpolling.Model.QuestionsModel;
import com.techomega.socialpolling.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    int currentPage;
    int position = 0;
    List<QuestionsModel> questionsModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        if(getActivity()!=null){
            recyclerView = (RecyclerView) v.findViewById(R.id.qdata);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            position = linearLayoutManager.findFirstVisibleItemPosition();
            recyclerView.setLayoutManager(linearLayoutManager);

            QuestionsModel upcomingModel = new QuestionsModel();
            upcomingModel.setQuestion("What is your favourite football league?");
            upcomingModel.setChoice1("EPL");
            upcomingModel.setChoice2("La Liga");
            upcomingModel.setChoice3("Bundesliga");
            upcomingModel.setChoice4("Serie A");
            upcomingModel.setCategory("Sports");

            questionsModels.add(upcomingModel);

            QuestionsModel upcomingModel1 = new QuestionsModel();
            upcomingModel1.setQuestion("The FIFA 2022 world cup will go to which continent?");
            upcomingModel1.setChoice1("Europe");
            upcomingModel1.setChoice2("Asia");
            upcomingModel1.setChoice3("South America");
            upcomingModel1.setChoice4("Africa");
            upcomingModel1.setCategory("Sports");

            questionsModels.add(upcomingModel1);

            QuestionsModel upcomingModel2 = new QuestionsModel();
            upcomingModel2.setQuestion("How do you make online payments?");
            upcomingModel2.setChoice1("Cash");
            upcomingModel2.setChoice2("Credit Card");
            upcomingModel2.setChoice3("Debit Card");
            upcomingModel2.setChoice4("Digital Payment");
            upcomingModel2.setCategory("Habits");

            questionsModels.add(upcomingModel2);

            QuestionsModel upcomingModel3 = new QuestionsModel();
            upcomingModel3.setQuestion("What is the biggest challenge in running a business?");
            upcomingModel3.setChoice1("Getting customers");
            upcomingModel3.setChoice2("Getting investors");
            upcomingModel3.setChoice3("Hiring people");
            upcomingModel3.setChoice4("Building product");
            upcomingModel3.setCategory("Startups & Technology");

            questionsModels.add(upcomingModel3);

            QuestionsModel upcomingModel4 = new QuestionsModel();
            upcomingModel4.setQuestion("How long would you want to live?");
            upcomingModel4.setChoice1("50 years");
            upcomingModel4.setChoice2("100 years");
            upcomingModel4.setChoice3("1,000 years");
            upcomingModel4.setChoice4("Forever");
            upcomingModel4.setCategory("Food & Health");

            questionsModels.add(upcomingModel4);

            QuestionsModel upcomingModel5 = new QuestionsModel();
            upcomingModel5.setQuestion("What is your favourite sport in India?");
            upcomingModel5.setChoice1("Football");
            upcomingModel5.setChoice2("Cricket");
            upcomingModel5.setChoice3("Hockey");
            upcomingModel5.setChoice4("Kabaddi");
            upcomingModel5.setCategory("Sports");

            questionsModels.add(upcomingModel5);

            QuestionsModel upcomingModel6 = new QuestionsModel();
            upcomingModel6.setQuestion("Why do you use social media apps?");
            upcomingModel6.setChoice1("To connect with others");
            upcomingModel6.setChoice2("To share");
            upcomingModel6.setChoice3("To view content");
            upcomingModel6.setChoice4("All the above");
            upcomingModel6.setCategory("Habits");

            questionsModels.add(upcomingModel6);

            QuestionsModel upcomingModel7 = new QuestionsModel();
            upcomingModel7.setQuestion("What do you fear will wipe out earth's population?");
            upcomingModel7.setChoice1("Current/New pandemic");
            upcomingModel7.setChoice2("War");
            upcomingModel7.setChoice3("Global warming");
            upcomingModel7.setChoice4("Somthing unforseen");
            upcomingModel7.setCategory("World Affairs");

            questionsModels.add(upcomingModel7);

            QuestionsModel upcomingModel8 = new QuestionsModel();
            upcomingModel8.setQuestion("How healthy do you feel?");
            upcomingModel8.setChoice1("Very healthy");
            upcomingModel8.setChoice2("Healthy");
            upcomingModel8.setChoice3("Moderately Healthy");
            upcomingModel8.setChoice4("Not healthy");
            upcomingModel8.setCategory("Food & Health");

            questionsModels.add(upcomingModel8);

            QuestionsAdapter questionsAdapter = new QuestionsAdapter(getActivity(),questionsModels);
            recyclerView.setAdapter(questionsAdapter);
            questionsAdapter.setOnNextClickListner(new QuestionsAdapter.OnNextClickListner() {
                @Override
                public void onNextClick(int position) {
                    recyclerView.scrollToPosition(position);
                }
            });

            questionsAdapter.setOnPreviousClickListner(new QuestionsAdapter.OnPreviousClickListner() {
                @Override
                public void onPreviousClick(int position) {
                    recyclerView.scrollToPosition(position);
                }
            });

        }

        return v;
    }
}