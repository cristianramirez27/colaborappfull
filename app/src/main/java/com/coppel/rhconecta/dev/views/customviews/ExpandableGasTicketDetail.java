package com.coppel.rhconecta.dev.views.customviews;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coppel.rhconecta.dev.R;
import com.coppel.rhconecta.dev.views.modelview.GasUnit;
import com.coppel.rhconecta.dev.views.utils.TextUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableGasTicketDetail extends ConstraintLayout implements View.OnClickListener {

    private boolean isExpanded;
    @BindView(R.id.ctlContainer)
    ConstraintLayout ctlContainer;
    @BindView(R.id.imgvArrow)
    ImageView imgvArrow;
    @BindView(R.id.txvTicketNumber)
    TextView txvTicketNumber;
    @BindView(R.id.txvDate)
    TextView txvDate;
    @BindView(R.id.txvAmount)
    TextView txvAmount;
    @BindView(R.id.ctlTicketDetail)
    ConstraintLayout ctlTicketDetail;
    @BindView(R.id.txvdProvider)
    TextViewDetail txvdProvider;
    @BindView(R.id.txvdBill)
    TextViewDetail txvdBill;
    @BindView(R.id.txvdCity)
    TextViewDetail txvdCity;
    @BindView(R.id.txvdDate)
    TextViewDetail txvdDate;
    @BindView(R.id.txvdAmount)
    TextViewDetail txvdAmount;

    public ExpandableGasTicketDetail(Context context) {
        super(context);
        initViews();
    }

    public ExpandableGasTicketDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.expandable_gas_ticket_detail, this);
        ButterKnife.bind(this);
        ctlContainer.setOnClickListener(this);
        imgvArrow.setOnClickListener(this);
    }

    public void setTicketDetail(GasUnit.GasTicket ticket) {
        txvTicketNumber.setText(ticket.getTicketNumber());
        txvDate.setText(TextUtilities.getDateFormatText(ticket.getDate()));
        txvAmount.setText(ticket.getAmount());
        txvdProvider.setTexts(getContext().getString(R.string.provider), ticket.getProvider());
        txvdBill.setTexts(getContext().getString(R.string.bill), ticket.getInvoice());
        txvdCity.setTexts(getContext().getString(R.string.city), ticket.getCity());
        txvdDate.setTexts(getContext().getString(R.string.date), ticket.getDateDetail());
        txvdAmount.setTexts(getContext().getString(R.string.lot), ticket.getAmountDetail());
        txvdProvider.setStartTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));
        txvdBill.setStartTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));
        txvdCity.setStartTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));
        txvdDate.setStartTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));
        txvdAmount.setStartTextColor(getContext().getResources().getColor(R.color.colorTextGrayDark));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ctlContainer:
            case R.id.imgvArrow:
                if (isExpanded) {
                    imgvArrow.setImageResource(R.drawable.ic_down_arrow_blue);
                    ctlTicketDetail.setVisibility(View.GONE);
                    isExpanded = false;
                } else {
                    imgvArrow.setImageResource(R.drawable.ic_up_arrow_blue);
                    ctlTicketDetail.setVisibility(View.VISIBLE);
                    isExpanded = true;
                }
                break;
        }
    }
}
