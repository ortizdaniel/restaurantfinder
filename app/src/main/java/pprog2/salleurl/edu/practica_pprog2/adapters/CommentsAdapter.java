package pprog2.salleurl.edu.practica_pprog2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pprog2.salleurl.edu.practica_pprog2.R;
import pprog2.salleurl.edu.practica_pprog2.model.Comment;

/**
 * Created by David on 23/05/2017.
 */

public class CommentsAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> commentsList;

    public CommentsAdapter(Context context, List<Comment> commentsList){
        this.context = context;
        this.commentsList = commentsList;
    }

    @Override
    public int getCount() {
        return commentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_user_comment_cell_layout, parent, false);

        Comment comment = commentsList.get(position);

        /* Afegim els components a la ListView */

        TextView user = (TextView) itemView.findViewById(R.id.listview_user);
        user.setText(comment.getUser());
        TextView message = (TextView) itemView.findViewById(R.id.listview_comment);
        message.setText(comment.getCommment());

        return itemView;
    }

    public void setCommentsList(List<Comment> commentsList){
        this.commentsList = commentsList;
    }
}
