package com.coppel.rhconecta.dev.views.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.customviews.EditTextEmail;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentGetDocument extends DialogFragment implements View.OnClickListener {

    public static final String TAG = DialogFragmentGetDocument.class.getSimpleName();
    public static final int SEND_TO = 0;
    public static final int VOUCHER_SENT = 1;
    public static final int VOUCHER_DOWNLOADED = 2;
    public static final int VOUCHER_SEND_FAIL = 3;
    public static final int VOUCHER_DOWNLOAD_FAIL = 4;
    /*Letters*/
    public static final int LETTER_SENT = 5;
    public static final int LETTER_DOWNLOADED = 6;
    public static final int LETTER_SEND_FAIL = 7;
    public static final int LETTER_DOWNLOAD_FAIL = 8;
    public static final int SEND_TO_LETTER = 9;

    public static final int NO_RESULT_BENEFITS = 10;
    public static final int NO_REFUSE_REMOVE = 11;

    private int selectedType;
    private OnButtonClickListener onButtonClickListener;
    private Context context;
    private String email;
    @BindView(R.id.ctlReady)
    ConstraintLayout ctlReady;
    @BindView(R.id.imgvAction)
    ImageView imgvAction;
    @BindView(R.id.txvAction)
    TextView txvAction;
    @BindView(R.id.txvMsg)
    TextView txvMsg;
    @BindView(R.id.btnActionAccept)
    Button btnActionAccept;
    @BindView(R.id.imgvClose)
    ImageView imgvClose;
    @BindView(R.id.ctlSendTo)
    ConstraintLayout ctlSentTo;
    @BindView(R.id.editTextEmail)
    EditTextEmail editTextEmail;
    @BindView(R.id.btnSendAccept)
    Button btnSendAccept;

    @BindView(R.id.textView)
    TextView textViewSendTo;

    private String contentText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(selectedType == LETTER_SENT ? R.layout.dialog_fragment_get_voucher_short : R.layout.dialog_fragment_get_voucher, container, false);
        ButterKnife.bind(this, view);
        initViews(selectedType);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.color.colorBackgroundDialogs);
        }

        return view;
    }

    private void initViews(int type) {
        switch (type) {
            case SEND_TO:
                ctlReady.setVisibility(View.GONE);
                ctlSentTo.setVisibility(View.VISIBLE);
                editTextEmail.setWhiteMode();
                if(email != null && !email.isEmpty()) {
                    editTextEmail.setText(email);
                }
                imgvClose.setOnClickListener(this);
                btnSendAccept.setOnClickListener(this);
                break;
            case SEND_TO_LETTER:
                ctlReady.setVisibility(View.GONE);
                ctlSentTo.setVisibility(View.VISIBLE);
                textViewSendTo.setText(getString(R.string.send_to_letter));
                editTextEmail.setWhiteMode();
                if(email != null && !email.isEmpty()) {
                    editTextEmail.setText(email);
                }
                imgvClose.setOnClickListener(this);
                btnSendAccept.setOnClickListener(this);
                break;

            case VOUCHER_SENT:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sent));
                txvAction.setText(getString(R.string.voucher_sent));
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;
            case VOUCHER_DOWNLOADED:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sent));
                txvAction.setText(getString(R.string.voucher_download));
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;
            case VOUCHER_SEND_FAIL:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_warning));
                txvAction.setText(getString(R.string.voucher_sent_fail));
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;
            case VOUCHER_DOWNLOAD_FAIL:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_warning));
                txvAction.setText(getString(R.string.voucher_download_fail));
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;

            case LETTER_SENT:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sent));
                txvAction.setText(getString(R.string.employment_letters_sent));
                txvMsg.setVisibility(View.VISIBLE);
                txvMsg.setText(contentText);
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;

            case LETTER_DOWNLOADED:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                txvMsg.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sent));
                txvAction.setText(getString(R.string.employment_letters_download));
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;


            case LETTER_SEND_FAIL:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_warning));
                txvAction.setText(getString(R.string.voucher_sent_fail));
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;
            case LETTER_DOWNLOAD_FAIL:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_warning));
                txvAction.setText(getString(R.string.letter_download_fail));
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;

            case NO_RESULT_BENEFITS:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_warning));
                txvAction.setText(contentText);
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;

            case NO_REFUSE_REMOVE:
                ctlReady.setVisibility(View.VISIBLE);
                ctlSentTo.setVisibility(View.GONE);
                imgvAction.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_warning));
                txvAction.setText(contentText);
                btnActionAccept.setText(getString(R.string.accept));
                btnActionAccept.setOnClickListener(this);
                break;
        }
    }

    public void setType(int type, Context context) {
        this.selectedType = type;
        this.context = context;
    }

    public void setContentText(String content) {
        this.contentText = content;
    }

    public void setPreloadedText(String email) {
        this.email = email;
    }

    public void setWarningMessage(String message) {
        editTextEmail.setWarningMessage(message);
    }

    public void close() {
        if (getDialog() != null && isVisible()) {
            dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (onButtonClickListener != null) {
            switch (view.getId()) {
                case R.id.btnActionAccept:
                    onButtonClickListener.onAccept();
                    break;
                case R.id.btnSendAccept:
                    onButtonClickListener.onSend(editTextEmail.getText());
                    break;
                case R.id.imgvClose:
                    close();
                    break;
            }
        }
    }

    public interface OnButtonClickListener {
        void onSend(String email);

        void onAccept();
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}
