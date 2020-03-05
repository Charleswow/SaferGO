package com.miantabs_d_share;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jack.isafety.R;

/**
 * Created by shaohui on 16/10/12.
 */

public class EditTextDialog extends BaseBottomDialog {

    private EditText mEditText;

    @Override
    public int getLayoutRes() {
        return R.layout.maintbas_d_share_dialog_edittext;
    }

    @Override
    public void bindView(View v) {
        mEditText = (EditText) v.findViewById(R.id.edit_text);
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, 0);
            }
        });
    }

    @Override
    public float getDimAmount() {
        return 0.9f;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
