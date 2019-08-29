package org.weibeld.example.tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.weibeld.example.R;

/* Fragment used as page 1 */
public class AnswerPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page1, container, false);

        final TextView textView = (TextView) rootView.findViewById(R.id.question);


        final EditText editText = (EditText) rootView.findViewById(R.id.enterInput);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 0) {
                    textView.setText(editText.getText());
                }
                return true;
            }
        });

        return rootView;
    }
}
