package dev.adi.poc.simplemailserver.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dev.adi.poc.simplemailserver.R;
import dev.adi.poc.simplemailserver.model.MailItem;

public class InboxListAdapter extends RecyclerView.Adapter<InboxListAdapter.InboxListViewHolder> {

    List<MailItem> mailItems;
    Context mContext;

    public InboxListAdapter(Context context, List<MailItem> items) {
        mailItems = items;
        mContext = context;
    }

    @Override
    public InboxListAdapter.InboxListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox_main, parent, false);
        return new InboxListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InboxListAdapter.InboxListViewHolder holder, int position) {
        holder.setData(mailItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mailItems.size();
    }


    public class InboxListViewHolder extends RecyclerView.ViewHolder {
        View parent;
        TextView tvMailSender, tvMailMessage, tvMailSubject, tvMailTime;

        public InboxListViewHolder(View view) {
            super(view);
            tvMailMessage = (TextView) view.findViewById(R.id.tv_mail_message);
            tvMailSender = (TextView) view.findViewById(R.id.tv_mail_sender);
            tvMailSubject = (TextView) view.findViewById(R.id.tv_mail_subject);
            tvMailTime = (TextView) view.findViewById(R.id.tv_mail_time);
            parent = view;
        }

        public void setData(final MailItem item) {
            Spanned msgFormated = Html.fromHtml(item.message);
            if (msgFormated.length() > 25) {
                tvMailMessage.setText(msgFormated.subSequence(0, 25) + "  ...");
            } else {
                tvMailMessage.setText(msgFormated);
            }

            if (item.subject.length() > 20) {
                tvMailSubject.setText(item.subject.subSequence(0, 20) + "  ...");
            } else {
                tvMailSubject.setText(item.subject);
            }

            tvMailSender.setText(item.userEmail);
            tvMailTime.setText(item.createDate.substring(11, 16));

            parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle(item.subject);
                    dialog.setMessage(Html.fromHtml(item.message));
                    dialog.show();
                    return false;
                }
            });

        }
    }
}
